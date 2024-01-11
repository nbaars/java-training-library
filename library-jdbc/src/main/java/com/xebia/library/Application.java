package com.xebia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * `SpringBootApplication` is a convenience annotation that adds the following:
 * <li>`@Configuration` tags the class as a source of bean definitions for the application context.
 * <li>`@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath
 * settings, other beans, and various property settings.
 */
@SpringBootApplication
@Import({SecurityConfig.class})
/**
 * `EnableGlobalMethodSecurity` is used to enable global method security. `prePostEnabled` attribute
 * is used to enable Spring Security pre- / post-annotations. `securedEnabled` attribute is used to
 * enable @Secured annotation. `jsr250Enabled` attribute is used to enable @RolesAllowed annotation.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableTransactionManagement
public class Application {

    /**
     * `SpringApplication.run` method bootstraps Spring Boot application.
     */
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
