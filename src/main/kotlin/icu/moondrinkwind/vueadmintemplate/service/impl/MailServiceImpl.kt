package icu.moondrinkwind.vueadmintemplate.service.impl

import icu.moondrinkwind.vueadmintemplate.service.MailService
import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import kotlin.random.Random

/**
 * 验证码相关服务
 */
@Service
class MailServiceImpl: MailService {

    // 验证邮件发送冷却时间 秒作为单位
    @Value("\${spring.web.verify.mail-limit}")
    var verifyLimit: Int = 0

    // 验证邮件有效时间，以分钟作为单位
    @Value("\${spring.web.verify.mail-time}")
    var verifyTime: Int = 0

    @Resource
    lateinit var stringRedisTemplate: StringRedisTemplate

    /**
     * 存储验证码到Redis当中
     * @param code 验证码
     * */
    override fun storeVerifiedCode(code: String) {

    }

    override fun getVerifiedCode(): String = (Random.nextDouble() * 1000000).toInt().toString()
}