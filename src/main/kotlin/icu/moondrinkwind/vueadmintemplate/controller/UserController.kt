package icu.moondrinkwind.vueadmintemplate.controller

import cn.hutool.core.util.IdUtil
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.entity.vo.R
import icu.moondrinkwind.vueadmintemplate.service.UserService
import jakarta.annotation.Resource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

/**
 * 用于用户相关Controller
 */
@RestController
@RequestMapping("/user")
class UserController {
    @Resource
    lateinit var userService: UserService

    @PostMapping("/add")
    @Transactional
    fun addUser(@RequestBody user: User): R<Nothing?> {
        user.id = IdUtil.simpleUUID()
        userService.save(user)
        return R.success()
    }

    @GetMapping("/get/{id}")
    fun getUser(@PathVariable id: String): R<User> {
        val user = userService.getById(id)
        return R.success(user)
    }


}