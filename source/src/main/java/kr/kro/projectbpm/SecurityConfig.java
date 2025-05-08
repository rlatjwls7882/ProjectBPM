//package kr.kro.projectbpm;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Component;
//
////@Configuration
//public class SecurityConfig {
//    // HSTS (HTTP Strict Transport Security) 설정 - "이 사이트를 HTTPS로만 접속한다"는 HSTS 설정을 응답헤더에 직접 설정
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest()
//                        .permitAll()) // 인증 요구 안함
//                .requiresChannel(channel -> channel
//                        .anyRequest()
//                        .requiresSecure()) // Https 강제
//                .headers(headers -> headers
//                        .httpStrictTransportSecurity(hsts -> hsts
//                        .includeSubDomains(true)
//                        .preload(true)
//                        .maxAgeInSeconds(60*60*24*365))) // 1년
//                .csrf(csrf -> csrf.disable())
//                .build();
//    }
//}
