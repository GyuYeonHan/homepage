package com.project.homepage.repository;

import com.project.homepage.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByLoginId(String loginId);

    @Query("select u from User u order by u.id desc")
    List<User> findAllDesc();
}
