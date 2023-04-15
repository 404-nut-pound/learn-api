package io.hskim.learnapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/**
 * 변경된 Spring Security 스펙은 다음 참조
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */
public class SecurityConfig {

  private static BCryptPasswordEncoder encoder;

  @Bean
  public static BCryptPasswordEncoder getEncoder() {
    if (encoder == null) {
      encoder = new BCryptPasswordEncoder();
    }

    return encoder;
  }

  // private final UserService userService; //UserDetailService 구현 클래스 지정

  /**
   * 참조 링크
   * https://hou27.tistory.com/entry/Spring-Security-%EC%84%B8%EC%85%98-%EC%9D%B8%EC%A6%9D
   * @return
   */
  // @Bean
  // public UserDetailsService userDetailsService() {
  //   return userService();
  // }

  /**
   * Session 참조 링크
   * https://ugo04.tistory.com/167
   * @param httpSecurity
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    return httpSecurity
      .authorizeHttpRequests(request -> request.anyRequest().permitAll())
      .sessionManagement(management ->
        management
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //stateless 설정
          .sessionFixation(sf -> sf.changeSessionId()) //세션 생성 방식, 매번 새로운 세션 생성
          .maximumSessions(1) //동일한 접속 정보에 대해 최대 세션 수
          .maxSessionsPreventsLogin(true) //최대 세션 수를 초과했을 때 로그인 허용 여부
          .expiredUrl("/auth") //세션 만료 시 redirect url
      )
      .csrf(csrf -> csrf.disable()) //stateless 설정에 따라 csrf off
      .headers(header -> header.frameOptions().disable()) //헤더를 통한 frame option off
      .formLogin(formLogin -> formLogin.disable()) //별도의 front-end를 사용하기 때문에 로그인 페이지 비활성화
      .logout(logout -> logout.disable())
      .build();
  }

  // 해당 방법 보다는 SecurityFilterChain의 permitAll을 통해서 사용하기를 권장
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().antMatchers("/ignore1", "/ignore2");
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsManager() {
    UserDetails user = User
      .builder()
      .username("user")
      .password("passw0rd")
      .roles("USER")
      .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
