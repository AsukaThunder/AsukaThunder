package com.ford.asukathunder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @ClassName: Swagger2
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/10 上午 10:57
 **/
@Configuration
@EnableSwagger2
@Profile({"dev", "qa"})
public class Swagger2 {
    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().enableUrlTemplating(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API for AsukaThunder")
                .description("AsukaThunder后台管理系统1.0 API")
                .termsOfServiceUrl("http://springfox.io")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
