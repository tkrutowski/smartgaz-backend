package net.focik.Smartgaz.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.userservice.api.dto.AuthenticationRequest;
import net.focik.Smartgaz.userservice.api.dto.AuthenticationResponse;
import net.focik.Smartgaz.userservice.api.dto.RefreshRequest;
import net.focik.Smartgaz.userservice.application.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/api/v1/auth"})
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Login attempt for user: {}", authenticationRequest.getUsername());
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        log.info("Attempt to refresh token");
        AuthenticationResponse refreshedToken = authenticationService.refreshToken(refreshRequest);
        return ResponseEntity.ok(refreshedToken);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        log.info("Test ping");
        return ResponseEntity.ok("OK");
    }
}
