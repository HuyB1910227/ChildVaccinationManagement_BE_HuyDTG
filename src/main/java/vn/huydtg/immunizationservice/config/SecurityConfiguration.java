package vn.huydtg.immunizationservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.huydtg.immunizationservice.security.jwt.AuthenticationFilter;
import vn.huydtg.immunizationservice.security.jwt.ImmunizationUnitActivityFilter;
import vn.huydtg.immunizationservice.security.jwt.JwtAuthenticationEntryPoint;
import vn.huydtg.immunizationservice.security.jwt.UserActivityFilter;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private AuthenticationFilter authenticationFilter;

    private ImmunizationUnitActivityFilter immunizationUnitActivityFilter;

    private UserActivityFilter userActivityFilter;

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, AuthenticationFilter authenticationFilter, ImmunizationUnitActivityFilter immunizationUnitActivityFilter, UserActivityFilter userActivityFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
        this.immunizationUnitActivityFilter = immunizationUnitActivityFilter;
        this.userActivityFilter = userActivityFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/api/auth/organization/**").permitAll()
                                .requestMatchers("/api/auth/user/**").permitAll()
                                .requestMatchers("/api/users/**").permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(immunizationUnitActivityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
