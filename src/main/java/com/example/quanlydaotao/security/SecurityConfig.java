package com.example.quanlydaotao.security;

import com.example.quanlydaotao.security.jwt.CustomAccessDeniedHandler;
import com.example.quanlydaotao.security.jwt.JwtAuthenticationFilter;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }


    @Autowired
    private UserService userService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/logoutUser", "/role", "/api/test/**", "/api/send/**", "/generate-pdf", "api/send/", "api/test/", "/api/tokens/**", "/admin/users/view/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("ROLE_USER", "ROLE_TM", "ROLE_ADMIN")
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/interns/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TM")
                        .requestMatchers("/api/stats/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("api/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_QC")
                        .requestMatchers("api/plans/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TM", "ROLE_HR", "ROLE_DM", "ROLE_QC")
                        .requestMatchers("api/recruitmentRequests/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DM", "ROLE_HR", "ROLE_TM", "ROLE_QC")
                        .requestMatchers("api/plansIntern/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_TM", "ROLE_HR", "ROLE_QC")
                        .requestMatchers("/api/recruitmentStats/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_QC")
                        .requestMatchers("/api/dashboard/**").permitAll()
                        .requestMatchers("/api/notifications/**").permitAll()
                        .requestMatchers("/roles/users/view/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DM", "ROLE_HR", "ROLE_TM", "ROLE_QC")
                        .requestMatchers("/process/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_QC", "ROLE_TM", "ROLE_HR", "ROLE_DM")
                )
                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
