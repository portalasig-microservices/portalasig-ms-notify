package com.portalasig.ms.site.rest;

import com.portalasig.ms.uaa.client.UserAuthenticationClient;
import com.portalasig.ms.uaa.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/demo")
@RequiredArgsConstructor
public class DemoController {

    @Qualifier("userAuthenticationClientV1")
    private final UserAuthenticationClient userAuthenticationClient;

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello, role user");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello, role ADMIN!!");
    }

    @GetMapping("/test-client")
    public User testClient() {
        return userAuthenticationClient.findUserByIdentity(23950509L).block();
    }
}
