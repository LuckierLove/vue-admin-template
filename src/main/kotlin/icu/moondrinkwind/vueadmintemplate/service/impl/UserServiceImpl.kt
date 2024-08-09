package icu.moondrinkwind.vueadmintemplate.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.mapper.UserMapper
import icu.moondrinkwind.vueadmintemplate.service.UserService
import icu.moondrinkwind.vueadmintemplate.util.Const
import icu.moondrinkwind.vueadmintemplate.util.FlowUtils
import jakarta.annotation.Resource
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
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
     * 针对IP地址进行邮件验证码进行限流
     */
    private fun verifyLimit(address: String): Boolean = flow.limitOnceCheck(Const.VERIFY_EMAIL_LIMIT + address, verifyLimit)

    private fun getVerifiedCode(): String = (Random.nextDouble() * 1000000).toInt().toString()
}