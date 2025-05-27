package com.portalasig.ms.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Entry point for the Notification microservice application.
 * <p>
 * This class bootstraps the Spring Boot application and enables JPA auditing,
 * JPA repositories, and asynchronous execution.
 */
@EnableAsync
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.portalasig.ms.commons", "com.portalasig.ms.notify"})
public class ServerApplication {

    /**
     * Main method to start the Notification service.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}