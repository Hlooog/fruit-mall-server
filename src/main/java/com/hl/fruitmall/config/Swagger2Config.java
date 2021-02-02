package com.hl.fruitmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JoingFy
 * @create 2020/12/5 19:54
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.hl.fruitmall.controller";

    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("X-Token", "X-Token", "header"));
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("X-Token", scopes()));
        securityContexts.add(SecurityContext
                .builder()
                .securityReferences(securityReferences)
                .forPaths(PathSelectors.any())
                .build());
        return securityContexts;
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("农夫果园网店系统")
                .description("农夫果园网店API接口文档")
                .version(VERSION)
                .termsOfServiceUrl("http://localhost:8080/fruit-mall/")
                .build();
    }
}
