package io.github.xbeeant.easy.config;

import io.github.xbeeant.easy.config.message.SecurityHandlerMethodArgumentResolver;
import io.github.xbeeant.easy.config.message.SecurityHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author xiaobiao
 * @version 2021/4/5
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SecurityHandlerMethodArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new SecurityHttpMessageConverter());
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}
