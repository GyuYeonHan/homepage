package com.project.homepage.service;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.user.User;
import com.project.homepage.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void create(String message, String url, User user) {
        Notice notice = Notice.builder()
                .message(message)
                .url(url)
                .status(NoticeStatus.UNREAD)
                .user(user)
                .build();

        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("알람이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Notice> findAllNoticeByUser(User user) {
        return noticeRepository.findAllByUser(user).orElseThrow(() -> new IllegalArgumentException("알람이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Notice> findUnreadNoticeByUser(User user) {
        return noticeRepository.findUnreadByUser(user).orElseThrow(() -> new IllegalArgumentException("알람이 없습니다."));
    }

    @Transactional
    public void readNotice(Notice notice) {
        notice.read();
    }
}
