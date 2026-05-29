package pma.store.exchange.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exchange Service API")
                        .version("1.0.0")
                        .description("Currency exchange rate service — fetches real-time rates from AwesomeAPI"));
    }
}