package com.nantaaditya.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket docket() {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(new ParameterBuilder().name("Authorization").description("authorization")
        .modelRef(new ModelRef("string")).parameterType("header").build());
    return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(parameters).select()
        .apis(RequestHandlerSelectors.basePackage("com.nantaaditya.web.rest"))
        .paths(PathSelectors.any()).build().apiInfo(metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfo("Nanta REST API", "REST API for Nanta Web", "1.0",
        "Terms of service",
        new Contact("Nanta Aditya",
            "https://www.linkedin.com/in/pramuditya-anantanur-013859136/",
            "pramuditya.social@gmail.com"),
        "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");
  }
}
