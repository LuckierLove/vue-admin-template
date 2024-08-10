package icu.moondrinkwind.vueadmintemplate.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.entity.vo.request.EmailRegisterVO
import icu.moondrinkwind.vueadmintemplate.mapper.UserMapper
import icu.moondrinkwind.vueadmintemplate.service.UserService
import icu.moondrinkwind.vueadmintemplate.util.Const
import icu.moondrinkwind.vueadmintemplate.util.FlowUtils
import jakarta.annotation.Resource
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * 验证码相关服务
 */
@Service
class UserServiceImpl: ServiceImpl<UserMapper, User>(),UserService {

    // 验证邮件发送冷却时间 秒作为单位
    @Value("\${spring.web.verify.mail-limit}")
    var verifyLimit: Int = 0

    @Resource
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Resource
    lateinit var rabbitTemplate: AmqpTemplate

    @Resource
    lateinit var flow: FlowUtils

    @Resource
    lateinit var passwordEncoder: PasswordEncoder


    /**
     * 生成注册验证码并存入Redis中，将邮件发送请求提交到消息队列等待发送
     * @param type 类型
     * @param email 邮箱地址
     * @param address 请求IP地址
     * @return 操作结果, null表示正常，否则为错误
     */
    override fun registerEmailVerifiedCode(type: String, email: String, address: String): String?{
        synchronized(address.intern()){
            if(!this.verifyLimit(address)){
                return "请求频繁，请稍后再试"
            }
            val code: Int = Random.nextInt(899999) + 100000
            val data: Map<String, Any> = mapOf("type" to type, "email" to email, "code" to code)
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, data)
            stringRedisTemplate.opsForValue()
                .set(Const.VERIFY_EMAIL_DATA + email, code.toString(), 3, TimeUnit.MINUTES)
            return null
        }
    }

    /**
     * 从数据库中通过用户名或者邮箱查找用户详细信息
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundExpcetion 如果用户没找到则抛出此异常
     */
    override fun loadUserByUsername(username: String): UserDetails{
        val user = this.findUserByUsernameOrEmail(username) ?: throw UsernameNotFoundException("用户名或密码错误")
        return org.springframework.security.core.userdetails.User
            .withUsername(user.username)
            .password(user.password)
            .roles(user.role.toString())
            .build()
    }

    /**
     * 针对IP地址进行邮件验证码进行限流
     * @param address IP地址
     * @return 是否通过验证
     */
    private fun verifyLimit(address: String): Boolean = flow.limitOnceCheck(Const.VERIFY_EMAIL_LIMIT + address, verifyLimit)

    /**
     * 通过用户名或者邮箱查找用户
     * @param text 用户名或邮箱
     * @return 用户实体
     */
    private fun findUserByUsernameOrEmail(text: String): User? = this.query().eq("username", text).or().eq("email", text).one()

    /**
     * 查找指定邮箱的用户是否已经存在
     * @param email 邮箱
     * @return 是否存在
     */
    private fun existsUserByEmail(email: String): Boolean = this.baseMapper.exists(Wrappers.query<User>().eq("email", email))

    /**
     * 查询指定用户名的用户是否已经存在
     * @param username 用户名
     * @return 是否存在
     */
    private fun existsUserByUsername(username: String): Boolean = this.baseMapper.exists(Wrappers.query<User>().eq("username", username))

    /**
     * 获取Redis中存储的邮件验证码
     * @param email 邮箱
     * @return 验证码
     */
    private fun getEmailVerifiedCode(email: String): String? = stringRedisTemplate.opsForValue().get(Const.VERIFY_EMAIL_DATA + email)

    /**
     * 移除Redis中存储的邮件验证码
     * @param email 邮箱
     */
    private fun deleteEmailVerifiedCode(email: String): Unit{
        stringRedisTemplate.delete(Const.VERIFY_EMAIL_DATA + email)
    }

    /**
     * 邮件验证码注册账号操作，需要检查验证码是否正确以及邮箱、用户名是否存在重名
     * @param info 注册基本信息
     * @return 操作结果 null表示正常， 否则为错误原因
     */
    override fun registerEmailUser(info: EmailRegisterVO): String?{
        val email = info.email!!
        val code: String = this.getEmailVerifiedCode(email) ?: return "请先获取验证码"

        if(code != info.code) return "验证码错误，请重新输入"
        if(this.existsUserByEmail(email)) return "该邮箱地址已被注册"

        val username: String = info.username!!
        if(this.existsUserByUsername(username)) return "该用户名已被他人使用，请重新更换"

        val password = passwordEncoder.encode(info.password)
        val user = User(null, username, password, email, mutableListOf(Const.ROLE_DEFAULT), null, null)
        if(!this.save(user)) return "内部错误，注册失败"
        else{
            this.deleteEmailVerifiedCode(email)
            return null
        }
    }

}