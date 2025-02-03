package by.tms.bookpoint.configuration;

import by.tms.bookpoint.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) //*
                .logout(AbstractHttpConfigurer::disable) //*
                .formLogin(AbstractHttpConfigurer::disable) //*
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) //db H2 for dev
                .authorizeHttpRequests(e -> e
//                        .requestMatchers(HttpMethod.GET, "/**","/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/account/create","/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, SecurityEndpoints.publicGET()).permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityEndpoints.publicPOST()).permitAll()
                        .requestMatchers(SecurityEndpoints.publicALL()).permitAll() //db H2 for dev
                        .requestMatchers(SecurityEndpoints.userAccess()).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, SecurityEndpoints.userAccessGet()).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, SecurityEndpoints.userAccessPost()).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, SecurityEndpoints.userAccessPut()).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/account/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/account/**").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/account/**", "/auth/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(c ->
                        c.authenticationEntryPoint(authenticationEntryPoint()))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    record AuthResponse(String message) {}

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            var authResponse = new AuthResponse(authException.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            objectMapper.writeValue(response.getOutputStream(), authResponse);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(accountService)
                .passwordEncoder(new BCryptPasswordEncoder(11))
                .and()
                .build();
    }
}
