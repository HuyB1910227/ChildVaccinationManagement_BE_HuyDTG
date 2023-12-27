package vn.huydtg.immunizationservice.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.huydtg.immunizationservice.domain.RefreshToken;
import vn.huydtg.immunizationservice.repository.RoleRepository;
import vn.huydtg.immunizationservice.repository.UserRepository;
import vn.huydtg.immunizationservice.security.RefreshTokenService;
import vn.huydtg.immunizationservice.security.UserDetailsImpl;
import vn.huydtg.immunizationservice.security.jwt.TokenProvider;
import vn.huydtg.immunizationservice.service.AuthService;
import vn.huydtg.immunizationservice.web.rest.vm.JWTAuthResponse;
import vn.huydtg.immunizationservice.web.rest.vm.LoginVM;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private TokenProvider tokenProvider;

    private RefreshTokenService refreshTokenService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           TokenProvider tokenProvider,
                           RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public JWTAuthResponse login(LoginVM loginVM) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                     loginVM.getUsername(),
                     loginVM.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRefreshToken(refreshToken.getToken());
        jwtAuthResponse.setId(userDetails.getId());
        jwtAuthResponse.setUsername(userDetails.getUsername());
        jwtAuthResponse.setPhone(userDetails.getPhone());
        jwtAuthResponse.setRoles(roles);
        jwtAuthResponse.setImmunizationUnitId(userDetails.getImmunizationUnitId());
        return jwtAuthResponse;
    }

}
