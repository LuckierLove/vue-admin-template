package icu.moondrinkwind.vueadmintemplate.service

import com.baomidou.mybatisplus.extension.service.IService
import icu.moondrinkwind.vueadmintemplate.entity.dto.User

interface UserService: IService<User> {
    fun registerEmailVerifiedCode(type: String, email: String, address: String): String?
}