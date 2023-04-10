package io.hskim.learnapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/**
 * 변경된 Spring Security 스펙은 다음 참조
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    return httpSecurity
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
      .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // 해당 방법 보다는 SecurityFilterChain의 permitAll을 통해서 사용하기를 권장
    return web -> web.ignoring().antMatchers("/ignore1", "/ignore2");
  }
}
