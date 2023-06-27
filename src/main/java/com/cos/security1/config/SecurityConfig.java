package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// 1. 코드 받기(인증됨) 2. 코드를 통해 액세스 토큰 받기(권한이 있음)
// 3. 사용자 프로필 정보 가져오고 4-1. 그 정보를 통해서 회원가입을 자동으로 진행시키기도 함
// 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> 집 주소 필요, 백화점몰 -> vip 등급 등 추가적인 정보가 필요하면, 추가적인 회원가입 화면 필요

@Configuration
//@EnableWebSecurity // 활성화: 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // Secured 어노테이션 활성화, PreAuthorize 어노테이션 활성화
public class SecurityConfig {

//    // 패스워드 암호화 빈 등록
//    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
//    securityConfig와 principalOauth2UserService 사이에서 순환참조 오류가 나므로, 클래스 생성하여 따로 빈 등록 후 주석처리
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 다른 요청은 모든 사용자 허용 -> 다른 주소는 누구나 접속할 수 있음
                .and()
                .formLogin()
                .loginPage("/loginForm") // 인증이 필요하면 무조건 loginForm으로 이동함
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // 구글 로그인이 완료(인증)된 후의 후처리가 필요함. Tip. 구글 로그인이 완료되면, 코드X, (액세스토큰 + 사용자 프로필 정보 O)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        return http.build();
    }
}
