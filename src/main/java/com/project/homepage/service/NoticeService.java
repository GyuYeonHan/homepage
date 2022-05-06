package com.project.homepage.service;

import com.project.homepage.domain.notice.Notice;
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

    @Transactional(readOnly = true)
    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다. id=" + id));
    }

    @Transactional(readOnly = true)
    public List<Notice> findAllNoticeByUser(User user) {
        return noticeRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Notice> findUnreadNoticeByUser(User user) {
        return noticeRepository.findUnreadByUser(user);
    }
}
