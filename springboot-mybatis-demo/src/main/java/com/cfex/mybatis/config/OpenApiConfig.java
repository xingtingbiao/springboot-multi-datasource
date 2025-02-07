package com.cfex.customer.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.cfex.customer.entity.constant.GlobalConstant.RequestId;

@Slf4j
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components().addParameters("globalRequestId",
                new QueryParameter().name(RequestId).description("used to trace api(default uuid), better add")
                        .schema(new StringSchema()).required(false));
        log.info("add a global queryParameter[globalRequestId] components in the openAPI object.");
        return new OpenAPI()
                .components(components)
                .info(new Info().title("Customer Data Service OpenAPI Definition")
                        .description("Customer Data Service OpenAPI Definition For SpringBoot Application")
                        .version("v1.0.0")
                        .license(new License().name("Spring Doc").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Customer Data Service Wiki")
                        .url("https://energix.atlassian.net/wiki/spaces/ENERGIX/pages/11436336/Data+Platform"));
    }

    @Bean
    public OpenApiCustomizer globalQueryParameter() {
        log.info("Enter OpenApi customization program to add global queryParameter[globalRequestId].");
        return openApi -> {
            List<Operation> operations = openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream()).toList();
            operations.forEach(operation -> {
                        String $ref = "#/components/parameters/globalRequestId";
                        List<Parameter> requestId = null;
                        if (!CollectionUtils.isEmpty(operation.getParameters())) {
                            requestId = operation.getParameters()
                                    .stream()
                                    .filter(p -> RequestId.equals(p.getName()) || $ref.equals(p.get$ref()))
                                    .toList();
                        }
                        if (CollectionUtils.isEmpty(requestId)) {
                            log.info("add global query parameter[requestId] to " + operation.getOperationId());
                            operation.addParametersItem(new QueryParameter().$ref($ref));
                        }
                    });
        };
    }
}

