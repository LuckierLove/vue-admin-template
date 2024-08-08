package icu.moondrinkwind.vueadmintemplate.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer

/**
 * Spring Security配置
 * */
@Configuration
class WebSecurityConfiguration{
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer = WebSecurityCustomizer{
        it.ignoring().anyRequest()
    }
}