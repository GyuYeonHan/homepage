package com.project.homepage.web.api;

import com.project.homepage.domain.user.User;
import com.project.homepage.service.LoginService;
import com.project.homepage.web.dto.user.SessionDto;
import com.project.homepage.web.login.LoginForm;
import com.project.homepage.web.login.LoginResponseDto;
import com.project.homepage.web.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginForm form,
                                                  @RequestParam(defaultValue = "/") String redirectURL,
                                                  HttpServletRequest request, HttpServletResponse response) {
        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        LoginResponseDto data = new LoginResponseDto(loginUser, session.getId(), redirectURL);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Logout Success";
    }

    @GetMapping("/session")
    public SessionDto getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new SessionDto(false, null);
        }
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (user == null) {
            session.invalidate();
            return new SessionDto(false, null);
        }

        return new SessionDto(true, user.getUsername());
    }
}
