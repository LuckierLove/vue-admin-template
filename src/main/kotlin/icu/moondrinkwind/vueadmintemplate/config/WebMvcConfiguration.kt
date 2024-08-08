package icu.moondrinkwind.vueadmintemplate.config

import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter
import com.alibaba.fastjson2.support.spring6.webservlet.view.FastJsonJsonView
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 一般Web配置
 * */
@Configuration
class WebMvcConfiguration: WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val converter = FastJsonHttpMessageConverter()
        converter.supportedMediaTypes = listOf(MediaType.APPLICATION_JSON)
        converters.add(0, converter)
    }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        val fastJsonJsonView = FastJsonJsonView()
        registry.enableContentNegotiation(fastJsonJsonView)
    }
}