package com.project.homepage.web.api;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.domain.user.User;
import com.project.homepage.service.NoticeService;
import com.project.homepage.service.UserService;
import com.project.homepage.web.dto.notice.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController {

    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<NoticeResponseDto>> getAllNoticeList(@PathVariable Long userId) {
        if (userId == -1) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<NoticeResponseDto> NoticeDtoList = noticeService.findAllNoticeByUser(user).stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(NoticeDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<NoticeResponseDto>> getUnreadNoticeList(@PathVariable Long userId) {
        if (userId == -1) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<NoticeResponseDto> NoticeDtoList = noticeService.findUnreadNoticeByUser(user).stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(NoticeDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<String> readNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.findById(noticeId);

        if (notice.getStatus() == NoticeStatus.READ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        noticeService.readNotice(notice);

        return new ResponseEntity<>(Long.toString(noticeId) + " is now read", HttpStatus.OK);
    }

}
