package com.project.homepage.service;

import com.project.homepage.domain.user.User;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * @param loginId: 유저 아이디
     * @param password: 유저 패스워드
     * @return null이면 로그인 실패
     */
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    public boolean userExist(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }
}
