package com.xebia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * `SpringBootApplication` is a convenience annotation that adds the following:
 * <li>`@Configuration` tags the class as a source of bean definitions for the application context.
 * <li>`@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath
 *     settings, other beans, and various property settings.
 *
 *     <p>NOTE: change <code>scanBasePackages = "com.xebia.library.jpa"</code> to <code>
 *     scanBasePackages = "com.xebia.library.jdbc</code> if you want to use the JDBC implementation
 *     of the repository
 *
 *     <p>If you want to only use the JPA implementation of the repository, you can remove the JDBC
 *     implementation and remove the `@Import({SecurityConfig.class})` annotation and
 *     scanBasePackages.
 */
@SpringBootApplication(scanBasePackages = "com.xebia.library.jpa")
// NOTE this import is only needed because we are using two variants of the repositories in the same
// project
@Import({SecurityConfig.class})
/** `EnableJpaRepositories` is used to enable JPA repositories. */
@EnableJpaRepositories
/**
 * `EnableGlobalMethodSecurity` is used to enable global method security. `prePostEnabled` attribute
 * is used to enable Spring Security pre- / post-annotations. `securedEnabled` attribute is used to
 * enable @Secured annotation. `jsr250Enabled` attribute is used to enable @RolesAllowed annotation.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class Application {

  /** `SpringApplication.run` method bootstraps Spring Boot application. */
  public static void main(String[] args) {
    new SpringApplication(Application.class).run(args);
  }
}
