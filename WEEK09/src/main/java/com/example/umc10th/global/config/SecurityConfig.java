package com.example.umc10th.global.config;

import com.example.umc10th.global.security.exception.CustomAccessDenied;
import com.example.umc10th.global.security.exception.CustomEntryPoint;
import com.example.umc10th.global.security.filter.JwtAuthFilter;
import com.example.umc10th.global.security.handler.OAuthSuccessHandler;
import com.example.umc10th.global.security.service.CustomOAuthService;
import com.example.umc10th.global.security.service.CustomUserDetailsService;
import com.example.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final CustomOAuthService customOAuthService;
    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/users/signup",
            "/users/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuthService customOAuthService) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .usernameParameter("email")
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )
                .sessionManagement(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customEntryPoint())
                        .accessDeniedHandler(customAccessDenied())
                )
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth/authorize")
                        )
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/oauth/callback/**")
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuthService)
                        )
                        .successHandler(oAuthSuccessHandler())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CustomAccessDenied customAccessDenied() {
        return new CustomAccessDenied();
    }
    @Bean
    public CustomEntryPoint customEntryPoint() {
        return new CustomEntryPoint();
    }
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }
    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(jwtUtil);
    }

}