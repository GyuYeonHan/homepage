package com.project.homepage.web.api;

import com.project.homepage.domain.user.User;
import com.project.homepage.service.LoginService;
import com.project.homepage.web.dto.user.SessionDto;
import com.project.homepage.web.login.LoginForm;
import com.project.homepage.web.login.LoginResponseDto;
import com.project.homepage.web.login.LoginStatus;
import com.project.homepage.web.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginForm form,
                                                  HttpServletRequest request) {
        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            if (loginService.userExist(form.getLoginId())) {
                return new ResponseEntity<>(new LoginResponseDto(LoginStatus.WRONG_PASSWORD, null, null), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(new LoginResponseDto(LoginStatus.WRONG_ID, null, null), HttpStatus.BAD_REQUEST);
            }
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return new ResponseEntity<>(new LoginResponseDto(LoginStatus.LOGIN_SUCCESS, loginUser.getId(), loginUser.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("Logout Success", HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<SessionDto> getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>(new SessionDto(false, null, null), HttpStatus.OK);

        }
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (user == null) {
            session.invalidate();
            return new ResponseEntity<>(new SessionDto(false, null, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new SessionDto(true, user.getId(), user.getUsername()), HttpStatus.OK);
    }
}
