package com.desafio.agendamentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Classe para configurar a documentação OpenAPI.
 */
@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openApi) {
        Info info = new Info()
                .title("Agendamento de Veículos")
                .description("Microsserviço para gerenciar o agendamento de manutenção em veículos.")
                .version("1.0.0");

        openApi.info(info);
    }
}
