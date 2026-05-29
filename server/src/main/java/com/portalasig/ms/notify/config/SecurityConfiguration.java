package com.portalasig.ms.notify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

 /**
 * Security configuration for the Notify microservice.
 */
@Configuration
public class SecurityConfiguration {

    @Value("${portalasig.security.jwk-set-uri}")
    private String internalJwksUri;

    /**
     * Configures the security filter chain for JWT resource server validation.
     *
     * @param http the HTTP security builder
     * @return configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
        );
        http.oauth2ResourceServer(oauth -> oauth.jwt(jwt ->
                jwt.decoder(NimbusJwtDecoder.withJwkSetUri(internalJwksUri).build())
        ));
        return http.build();
    }
}
