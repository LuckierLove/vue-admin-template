package icu.moondrinkwind.vueadmintemplate.service

interface MailService {
    fun storeVerifiedCode(code: String)
    fun getVerifiedCode(): String
}