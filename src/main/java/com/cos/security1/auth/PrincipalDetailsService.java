package com.cos.security1.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername() 함수가 실행됨
// 규칙이다
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 세션 내부에 (Authentication 타입 객체 내부에 (UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("original username: " + username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            System.out.println("userEntityName: " + userEntity.getUsername());
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
