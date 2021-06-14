package com.mercadolibre.desafio_quality.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mercadolibre.desafio_quality.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Desafio Quality",
                "Documentação do projeto em Spring de Testes Unitários e de Integração",
                "1.0",
                "http://opensource.org/licenses/MIT",
                new Contact("Israel Solha", "https://mercadolivre.com.br", "israel.solha@mercadolivre.com"),
                "MIT License",
                "http://opensource.org/licenses/MIT",new ArrayList<>());
        return apiInfo;
    }
}