package brunofujisaki.ecommerce.infra.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Ecommerce API Rest")
                        .description("""
                                        API para gerenciamento de uma loja online, permitindo 
                                        que usuários realizem pedidos, adicionem produtos aos 
                                        pedidos e acompanhem o status das compras. 
                                        Inclui autenticação, controle de estoque e administração 
                                        de produtos.
                                        """)
                        .contact(new Contact()
                                .email("brunofujisaki2004@gmail.com")
                        )

                );
    }
}
