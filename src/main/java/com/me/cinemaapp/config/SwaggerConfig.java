package com.me.cinemaapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.version}")
    private String version;

    @Value("${swagger.baseUrl}")
    private String baseUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String bearerSchemeName = "bearerAuth";
        return new OpenAPI()
                .servers(getServers())
                .components(
                        new Components()
                                .addSecuritySchemes(bearerSchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer").bearerFormat("jti"))
                )
                .security(List.of(new SecurityRequirement().addList(bearerSchemeName)))
                .info(new Info().title(title).version(version));
    }

    public List<Server> getServers() {
        List<Server> servers = new LinkedList<>();
        servers.add(new Server().url(baseUrl));
        return servers;
    }
}