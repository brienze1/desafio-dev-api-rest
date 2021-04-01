package br.com.brienze.desafio.dock.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.brienze.desafio.dock.endpoint"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Desafio Dock", 
				"Recursos em API Rest que realizam operações bancárias",
				"0.0.1.SNAPSHOT",
				"Terms of Service",
				new Contact("Luis Brienze", "https://www.linkedin.com/in/luisbrienze/", "lfbrienze@gmail.com"),
				"",
				"",
				Collections.emptyList());
	}
	
}
