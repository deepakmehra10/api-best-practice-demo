package com.deepak.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPIConfig {

    @Value("${app.version}")
    String appVersion;

    @Bean
    public OpenAPI myOpenAPI() {
        System.out.println("I am inside OPEN API" + appVersion);
        Server devServer = new Server();
        devServer.setUrl("localhost:9000");
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl("http://127.0.0.1:9000");
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("todeepakmehra@gmail.com");
        contact.setName("Deepak Mehra");
        contact.setUrl("TBD");

        License mitLicense = new License().name("License").url("TBD");

        Info info = new Info()
                .title("Product APIs")
                .version(appVersion)
                .contact(contact)
                .description("Product API Information").termsOfService("TBD")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(Arrays.asList(devServer, prodServer));
    }
}
