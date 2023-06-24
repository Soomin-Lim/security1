package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 활성화: 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 패스워드 암호화 빈 등록
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 다른 요청은 모든 사용자 허용 -> 다른 주소는 누구나 접속할 수 있음
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
                .defaultSuccessUrl("/");
    }
}
