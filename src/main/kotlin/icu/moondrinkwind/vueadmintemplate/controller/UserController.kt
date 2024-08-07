package icu.moondrinkwind.vueadmintemplate.controller

import cn.hutool.core.util.IdUtil
import icu.moondrinkwind.vueadmintemplate.entity.R
import icu.moondrinkwind.vueadmintemplate.entity.User
import icu.moondrinkwind.vueadmintemplate.service.UserService
import jakarta.annotation.Resource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @Resource
    lateinit var userService: UserService

    @PostMapping("/add")
    @Transactional
    fun addUser(@RequestBody user: User): R {
        user.id = IdUtil.simpleUUID()
        userService.save(user)
        return R.success()
    }
}