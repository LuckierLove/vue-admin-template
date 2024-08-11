package icu.moondrinkwind.vueadmintemplate.controller.exception

import icu.moondrinkwind.vueadmintemplate.entity.R
import jakarta.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationController {
    companion object{
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 与SpringBoot保持一致，校验不通过打印警告信息，而不是直接抛出异常
     * @param exception 验证异常
     * @return 校验结果
     */
    @ExceptionHandler(ValidationException::class)
    fun validateError(exception: ValidationException): R<Nothing> {
        log.warn("Resolved: [${exception.javaClass.name}: ${exception.message}]")
        return R(400, "请求参数有误", null)
    }
}