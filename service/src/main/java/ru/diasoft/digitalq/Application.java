package ru.diasoft.digitalq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@EnableCaching
@EnableJpaRepositories({ "ru.diasoft.digitalq.repository" })
@EntityScan({ "ru.diasoft.digitalq.domain" })
@SpringBootApplication
@EnableSwagger2WebMvc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
