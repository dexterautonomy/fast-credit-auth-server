package com.hingebridge.devops.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.Lists;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {
	private static final String AUTHORIZATION = "Authorization";
	private static final String CHANNELCODE = "ChannelCode";
	private static final String HEADER = "header";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hingebridge.devops")).paths(PathSelectors.any()).build()
				.apiInfo(metaData()).securitySchemes(apiKeys())
				.securityContexts(Arrays.asList(swaggerSecurityContext()));
	}

	private SecurityContext swaggerSecurityContext() {
		return SecurityContext.builder().securityReferences(swaggerSecurityReferences()).build();
	}

	List<SecurityReference> swaggerSecurityReferences() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(CHANNELCODE, authorizationScopes),
				new SecurityReference(AUTHORIZATION, authorizationScopes));
	}

	private ApiInfo metaData() {
		return new ApiInfo("Authorization Server API for Fast Credit Application", "Documentation", "1.0", "Terms of service",
				new Contact("Fast Credit Ltd", "www.fastcredit-ng.com", "info@fastcredit-ng.com"), "License of API",
				"API license URL", Collections.emptyList());
	}

	private List<ApiKey> apiKeys() {
		List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey(AUTHORIZATION, AUTHORIZATION, HEADER));
		apiKeys.add(new ApiKey(CHANNELCODE, CHANNELCODE, HEADER));

		return apiKeys;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
		registry.addRedirectViewController("/api/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/api/swagger-resources/configuration/security",
				"/swagger-resources/configuration/security");
		registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api/swagger-ui.html**")
				.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}