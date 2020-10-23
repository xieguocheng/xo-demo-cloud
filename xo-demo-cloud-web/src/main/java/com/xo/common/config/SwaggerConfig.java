package com.xo.common.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: xo
 * @NAME: SwaggerConfig
 * @DATE: 2020/7/23
 * @Description: SwaggerConfig
 **/
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.xo.**.controller"})
@EnableSwaggerBootstrapUI
public class SwaggerConfig {


    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger信息
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("XO", "http://www.xo.com", "88888888@163.com");
        return new ApiInfoBuilder()
                .title("API接口")
                .description("API接口的描述")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
