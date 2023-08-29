package pingwit.beautysaloon.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("aniayavorsk@gmail.com");
        contact.setName("Anna Yavorska");
        contact.setUrl("https://github.com/Anna-Yavorska");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Beauty Salon API")
                .version("1.0")
                .contact(contact)
                .description("API for Beauty Salon System")
                .license(mitLicense);

        return new OpenAPI().info(info);
    }
}
