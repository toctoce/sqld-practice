package toctoce.sqldpractice.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/login", "/oauth2/**", "/login/**").permitAll() // 누구나 접근 가능
                        .anyRequest().authenticated() // 그 외 로그인 필수
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                );
        return http.build();
    }
}
