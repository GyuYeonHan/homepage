package com.project.homepage.repository;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n where n.user = :user order by n.id desc")
    List<Notice> findAllByUser(@Param("user") User user);

    @Query("select n from Notice n where n.user = :user and n.status = com.project.homepage.domain.notice.NoticeStatus.UNREAD order by n.id desc")
    List<Notice> findUnreadByUser(@Param("user") User user);

}
