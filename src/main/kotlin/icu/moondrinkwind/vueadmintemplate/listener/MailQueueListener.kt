package icu.moondrinkwind.vueadmintemplate.listener

import jakarta.annotation.Resource
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

/**
 * 用于处理邮件发送的消息队列监听器
 */
@Component
@RabbitListener(queues = ["mail"])
class MailQueueListener {
    @Resource
    lateinit var sender: JavaMailSender

    @Value("\${spring.mail.username}")
    lateinit var username: String

    /**
     * 处理邮件发送
     * @param data
     */
    @RabbitHandler
    fun sendMailMessage(data: Map<String, Any>) {
        val email = data["email"].toString()
        val code = data["code"].toString().toInt()
        val message: SimpleMailMessage? = when(data["type"].toString()){
            "register" -> createMessage("欢迎注册我们的网站", "您的邮件注册验证码为: ${code}，有效时间3分钟，为了保障您的账户安全，请勿向他人泄露验证码信息。",email)
            "case" -> createMessage("您的密码重置邮件", "你好，您正在执行重置密码操作，验证码: ${code}，有效时间3分钟，如非本人操作，请无视。", email)
            else -> null
        }
        if(message == null) return
        sender.send(message)
    }

    /**
     * 快速封装简单邮件消息实体
     * @param title 标题
     * @param content 内容
     * @param email 收件人
     * @return 消息实体
     */
    private fun createMessage(title: String, content: String, email: String): SimpleMailMessage{
        val message: SimpleMailMessage = SimpleMailMessage()
        message.subject = title
        message.text = content
        message.setTo(email)
        message.from = username
        return message
    }
}