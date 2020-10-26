package com.stoom.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class GeneralSpringBeanConfig {

    @Bean
    public ModelMapper getModelMapper(){return new ModelMapper();}

    @Bean
    public Object getObjectMapper(){return new ObjectMapper();}

    @Bean
    public Docket StoomAPI(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(stoomApiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build();
    }

    public ApiInfo stoomApiInfo(){
        return new ApiInfoBuilder()
                .title("StoomAPI version 1.0.0")
                .description("Web-ProgrammingCourseProject")
                .version("1.0.0")
                .build();
    }
}
