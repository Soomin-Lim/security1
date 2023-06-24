package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository 어노테이션 불필요. IoC 가능. 이유는 JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> Username은 문법
    // select * from user where username = ?
    User findByUsername(String username);
}
