package icu.moondrinkwind.vueadmintemplate.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * RabbitMQ相关配置
 */
@Configuration
class RabbitConfiguration {
    @Bean("mailQueue")
    fun mailQueue(): Queue = QueueBuilder.durable("mail").build()

    @Bean
    fun messageConverter(): MessageConverter {
        val defaultClassMapper = DefaultClassMapper()
        defaultClassMapper.setTrustedPackages("icu.moondrinkwind.vueadmintemplate")
        val converter = Jackson2JsonMessageConverter()
        converter.setClassMapper(defaultClassMapper)
        return converter
    }
}