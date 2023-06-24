package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View를 리턴하겠다!
public class IndexController {

    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public String index() {

        // 머스테치 기본폴더 /src/main/resources
        // 뷰 리졸버 설정: /templates/ (prefix), .mustache (suffix)
        return "index"; // src/main/resources/templates/index.mustache
    }
}
