package com.dnd.global.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title("D-Hi API Document");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }
}
