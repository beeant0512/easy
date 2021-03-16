package io.github.xbeeant.easy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Easy APIs")
                        .description("# Easy APIs")
                        .contact(new Contact("beeant","https://github.com/beeant0512","huangxb0512@gmail.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("io.github.xbeeant.easy.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}