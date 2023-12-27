package vn.huydtg.immunizationservice.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.huydtg.immunizationservice.domain.RefreshToken;
import vn.huydtg.immunizationservice.security.RefreshTokenService;
import vn.huydtg.immunizationservice.security.SecurityUtils;
import vn.huydtg.immunizationservice.security.jwt.TokenProvider;
import vn.huydtg.immunizationservice.service.AuthService;
import vn.huydtg.immunizationservice.web.rest.errors.BadRequestAlertException;
import vn.huydtg.immunizationservice.web.rest.errors.TokenRefreshException;
import vn.huydtg.immunizationservice.web.rest.vm.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthResource {

    @Value("${spring.application.name}")
    private String applicationName;
    private AuthService authService;

    private RefreshTokenService refreshTokenService;

    private TokenProvider tokenProvider;

    public AuthResource(AuthService authService, RefreshTokenService refreshTokenService, TokenProvider tokenProvider) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(value = {"/auth/organization/login", "/auth/organization/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginVM loginVM) {
        JWTAuthResponse jwtAuthResponse = authService.login(loginVM);
        if ((jwtAuthResponse.getRoles().contains("CUSTOMER") && (long) jwtAuthResponse.getRoles().size() == 1) || (long) jwtAuthResponse.getRoles().size() < 1) {
            throw new BadRequestAlertException("Not found organization account", "REST_ACCOUNT", "notfoundorganizationaccount");
        }
        return ResponseEntity.ok(jwtAuthResponse);
    }


    @PostMapping(value = {"/auth/user/login", "/auth/user/signin"})
    public ResponseEntity<JWTAuthResponse> customerLogin(@RequestBody LoginVM loginVM) {
        JWTAuthResponse jwtAuthResponse = authService.login(loginVM);
        if (!jwtAuthResponse.getRoles().contains("CUSTOMER")) {
            throw new BadRequestAlertException("Not found account", "REST_ACCOUNT", "notfoundaccount");
        }
        return ResponseEntity.ok(jwtAuthResponse);
    }



    @PostMapping("/auth/user/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();


        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenProvider.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token errors!"));
    }

    @PostMapping("/auth/signout")
    public ResponseEntity<?> logoutUser() {
        UUID currentUserId = SecurityUtils.getUserId();
        if (currentUserId.toString().isEmpty()) {
            throw new BadRequestAlertException("Logout error", "REST_USER", "userisnotexist");
        }
        refreshTokenService.deleteByUserId(currentUserId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}
