package kr.kro.projectbpm.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig는 Spring Security의 설정을 담당하는 클래스입니다.
 * 이 클래스는 비밀번호 인코더와 HTTP 보안 필터 체인을 정의합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * 비밀번호 인코더를 정의합니다.
     * BCryptPasswordEncoder를 사용하여 비밀번호를 안전하게 암호화합니다.
     *
     * @return PasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 보안 필터 체인을 정의합니다.
     * CSRF 보호를 비활성화하고 모든 요청을 허용합니다.
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 인스턴스
     * @throws Exception 예외가 발생할 경우
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .build();
    }
}
