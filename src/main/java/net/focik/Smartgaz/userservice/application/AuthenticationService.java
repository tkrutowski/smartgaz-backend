package net.focik.Smartgaz.userservice.application;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.Smartgaz.userservice.api.dto.AuthenticationRequest;
import net.focik.Smartgaz.userservice.api.dto.AuthenticationResponse;
import net.focik.Smartgaz.userservice.api.dto.RefreshRequest;
import net.focik.Smartgaz.userservice.domain.AppUser;
import net.focik.Smartgaz.userservice.domain.JwtService;
import net.focik.Smartgaz.userservice.domain.exceptions.TokenExpiredException;
import net.focik.Smartgaz.userservice.domain.port.primary.GetUserUseCase;
import net.focik.Smartgaz.userservice.domain.port.secondary.IAppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final GetUserUseCase getUserUseCase;
    private final JwtService jwtService;
    private final IAppUserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
        AppUser loginUser = getUserUseCase.findUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(loginUser);
        var refreshToken = jwtService.generateRefreshToken(loginUser);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        log.info("Success logged user: {}", request.getUsername());

        return authenticationResponse;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String username = null;
        try {
            String refreshToken = request.getRefreshToken();
            username = jwtService.extractUsername(refreshToken);
            if (username != null) {
                var user = userRepository.findUserByUsername(username);
                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                    log.info("Successfully refreshed token for user: {}", username);
                    return authenticationResponse;
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("Refresh token expired for user: {}", e.getClaims().getSubject());
            throw new TokenExpiredException("REFRESH TOKEN EXPIRED");
        }
        return null;
    }
}
