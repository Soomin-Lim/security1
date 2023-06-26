package com.cos.security1.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만든다
// 같은 세션 공간인데 시큐리티가 자신만의 세션 공간을 가지는 것, (Security ContextHolder): 키 값
// 세션에 저장될 수 있는 오브젝트 -> Authentication 타입의 객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트 타입은 UserDetails 타입 객체여야 한다

// 시큐리티가 가지고 있는 세션(Security Session 영역)에 들어갈 수 있는 객체는 -> Authentication 타입의 객체
// Authentication 객체 안에 User 정보를 저장할 때 User 정보는 -> UserDetails 타입의 객체여야 함
// Security Session -> Authentication -> UserDetails(PrincipalDetails)

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // composition

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠금
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 계정 비밀번호가 1년이 지났나 등
        return true;
    }

    @Override
    public boolean isEnabled() { // 활성화
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함
        // User에 loginDate 속성 추가
        // user.getLoginDate()
        // 현재 시간 - 로그인 시간 => 1년을 초과하면 return false;

        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
