package com.project.homepage.service;
import com.project.homepage.domain.post.Post;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUser() {
        return userRepository.findAllDesc();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + id));
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
}
