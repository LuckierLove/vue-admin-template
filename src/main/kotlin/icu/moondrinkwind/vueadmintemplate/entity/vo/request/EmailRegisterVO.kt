package icu.moondrinkwind.vueadmintemplate.entity.vo.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class EmailRegisterVO(
    @Email
    var email: String?,
    @Length(max = 6, min = 6)
    var code: String?,
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 1, max = 10)
    var username: String?,
    @Length(min = 6, max = 20)
    var password: String?
)