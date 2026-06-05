//package com.fundoo.fundoonotes.config;
//
//import com.fundoo.fundoonotes.security.JwtFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws  Exception{
//        http.csrf(csrf -> csrf.disable());
//
//        http.authorizeHttpRequests(auth -> auth.
//                requestMatchers("/user/register", "/user/login", "/h2-console/**").permitAll().anyRequest().authenticated() );
//
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.headers(headers -> headers.frameOptions( frameOptions-> frameOptions.disable()));
//
////        http.headers(headers-> headers.frameOptions(frame-> frame.sameOrigin()));// for h2
//
//        return  http.build();
//
//    }
//
//}
package com.fundoo.fundoonotes.config;

import com.fundoo.fundoonotes.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        // enable CORS inside Spring Security
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.httpBasic(basic -> basic.disable());
        http.formLogin(form -> form.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/user/register",
                        "/user/login",
                        "/user/forgot-password",
                        "/user/reset-password"
                ).permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // allow React frontend
        config.setAllowedOrigins(List.of("http://localhost:5173"));

        // allow all HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // allow all headers including Authorization
        config.setAllowedHeaders(List.of("*"));

        // allow Authorization header
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}