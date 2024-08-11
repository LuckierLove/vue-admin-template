package icu.moondrinkwind.vueadmintemplate.controller

import cn.hutool.core.util.IdUtil
import icu.moondrinkwind.vueadmintemplate.entity.R
import icu.moondrinkwind.vueadmintemplate.entity.dto.User
import icu.moondrinkwind.vueadmintemplate.entity.vo.request.EmailRegisterVO
import icu.moondrinkwind.vueadmintemplate.service.UserService
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.function.Supplier

/**
 * 用于用户相关Controller
 */
@RestController
@RequestMapping("/user")
class UserController {
    @Resource
    private lateinit var userService: UserService

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

    @GetMapping("/ask-code")
    fun askVerifyCode(
        @RequestParam
        @Email
        email: String,
        @RequestParam
        @Pattern(regexp = "(register|reset)")
        type: String,
        request: HttpServletRequest
    ) = this.messageHandle { userService.registerEmailVerifiedCode(type, email, request.remoteAddr) }

    @PostMapping("/register")
    fun register(@RequestBody vo: EmailRegisterVO)
    = this.messageHandle {
            userService.registerEmailUser(vo)
    }

    private fun messageHandle(action: Supplier<String?>): R<out String?> {
        val message: String? = action.get()
        return if(message == null) R.success() else R.failed(message)
    }
}