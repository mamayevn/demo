package kg.asiamotors.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwaggerConfigTest {

    @Test
    void testOpenAPIDefinitionAnnotation() {
        OpenAPIDefinition openAPIDefinition = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);
        assertNotNull(openAPIDefinition, "@OpenAPIDefinition должна быть задана");
        Info info = openAPIDefinition.info();
        assertNotNull(info, "Info внутри @OpenAPIDefinition не должен быть null");
        assertTrue(info.title().equals("API Documentation"), "Title в @OpenAPIDefinition должен быть 'API Documentation'");
        assertTrue(info.version().equals("1.0"), "Version в @OpenAPIDefinition должен быть '1.0'");
        assertTrue(info.description().equals("API для вашего приложения"), "Description в @OpenAPIDefinition должен быть корректным");

        SecurityRequirement[] securityRequirements = openAPIDefinition.security();
        assertNotNull(securityRequirements, "SecurityRequirement не должен быть null");
        assertTrue(securityRequirements.length > 0, "Должен быть хотя бы один SecurityRequirement");
        assertTrue(securityRequirements[0].name().equals("bearerAuth"), "SecurityRequirement name должен быть 'bearerAuth'");
    }

    @Test
    void testSecuritySchemeAnnotation() {
        SecurityScheme securityScheme = SwaggerConfig.class.getAnnotation(SecurityScheme.class);
        assertNotNull(securityScheme, "@SecurityScheme должна быть задана");
        assertTrue(securityScheme.name().equals("bearerAuth"), "Name в @SecurityScheme должен быть 'bearerAuth'");
        assertTrue(securityScheme.type() == SecuritySchemeType.HTTP, "Type в @SecurityScheme должен быть HTTP");
        assertTrue(securityScheme.scheme().equals("bearer"), "Scheme в @SecurityScheme должен быть 'bearer'");
        assertTrue(securityScheme.bearerFormat().equals("JWT"), "BearerFormat в @SecurityScheme должен быть 'JWT'");
    }
}
