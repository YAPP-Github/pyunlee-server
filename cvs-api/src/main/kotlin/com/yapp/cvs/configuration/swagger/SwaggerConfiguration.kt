package com.yapp.cvs.configuration.swagger

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.ServletContext

@Configuration
class SwaggerConfiguration {

    @Bean
    fun openAPI(servletContext: ServletContext): OpenAPI {
        val contextPath = servletContext.contextPath
        val server = Server().url(contextPath)
        return OpenAPI().servers(listOf(server)).components(authSetting()).info(swaggerInfo())
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper)
    }

    private fun swaggerInfo(): Info {
        val license = License()
        license.url = "https://github.com/YAPP-Github/22nd-Android-Team-1-BE"
        license.name = "편리"
        return Info()
            .version("v1")
            .title("편리 API 명세서")
            .license(license)
    }

    private fun authSetting(): Components {
        return Components()
            .addSecuritySchemes(
                "access-token",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .`in`(SecurityScheme.In.HEADER)
                    .name("Authorization")
            )
    }
}
