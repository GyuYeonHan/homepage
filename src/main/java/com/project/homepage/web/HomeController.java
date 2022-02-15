package com.project.homepage.web;

import com.project.homepage.domain.user.User;
import com.project.homepage.web.login.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@Login User loginUser, Model model) {
        //세션에 회원 데이터가 있으면 모델에 유저 추가
        if (loginUser != null) {
            log.info("username: {}", loginUser.getUsername());
            model.addAttribute("user", loginUser);
        }

        return "home";
    }
}
