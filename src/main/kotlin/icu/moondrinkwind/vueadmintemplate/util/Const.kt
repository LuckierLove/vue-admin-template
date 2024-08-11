package icu.moondrinkwind.vueadmintemplate.util

/**
 * 字符串常量合集
 * */
class Const{
    companion object{
        // 消息队列
        const val MQ_MAIL: String = "mail"

        // 邮件验证码
        const val VERIFY_EMAIL_LIMIT: String = "verify:email:limit:"
        const val VERIFY_EMAIL_DATA: String = "verify:email:data:"

        const val ROLE_DEFAULT: String = "user"

        //JWT令牌
        const val JWT_BLACK_LIST: String = "jwt:blacklist:"
        const val JWT_FREQUENCY: String = "jwt:frequency:"
    }
}
