package com.portalasig.ms.notify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Notify microservice. Sets up JWT decoding and authentication
 * requirements for all incoming requests.
 */
@Configuration
public class SecurityConfiguration {

    /**
     * The URI of the JWT issuer used to configure the JWT decoder.
     */
    @Value("${portalasig.security.oauth2.authorization-server.jwt.issuer-uri}")
    private String jwtIssuerUri;

    /**
     * Configures the security filter chain to require authentication for all requests and sets up
     * JWT decoding using the configured issuer URI.
     *
     * @param http the {@link HttpSecurity} instance used to configure security
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
        );
        http.oauth2ResourceServer(oauth -> oauth.jwt(
                jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(jwtIssuerUri))
        ));
        return http.build();
    }
}