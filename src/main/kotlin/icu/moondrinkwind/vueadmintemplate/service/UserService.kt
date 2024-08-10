package icu.moondrinkwind.vueadmintemplate.service

import com.baomidou.mybatisplus.extension.service.IService
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.entity.vo.request.EmailRegisterVO
import org.springframework.security.core.userdetails.UserDetails

interface UserService: IService<User> {
    fun registerEmailVerifiedCode(type: String, email: String, address: String): String?
    fun loadUserByUsername(username: String): UserDetails
    fun registerEmailUser(info: EmailRegisterVO): String?
}