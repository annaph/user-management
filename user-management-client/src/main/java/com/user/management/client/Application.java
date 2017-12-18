package com.user.management.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
@EntityScan("com.user.management.domain")
public class Application {

    private static ApplicationContext appContext;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        appContext = SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner onStartup(UserService userService) {
        return (args) -> {
            userService.importUsers().onFinish(Function.identity(), ex -> {
                logger.debug(ex.getMessage(), ex);
                SpringApplication.exit(appContext);
            });
        };
    }

}
