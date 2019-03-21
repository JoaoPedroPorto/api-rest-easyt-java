package com.easyt.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Bean
    public Docket easytApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.easyt.controller"))
                //.paths(PathSelectors.any())
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

	@SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Easyt", 
				"API RESTful", 
				"1.0", 
				"Terms of Service", 
				new Contact("EasyT", "colocar url", "tcc.easyt@gmail.com"), 
				"lincense", 
				"lincenseURL", 
				new ArrayList<VendorExtension>());

        return apiInfo;
    }

}
