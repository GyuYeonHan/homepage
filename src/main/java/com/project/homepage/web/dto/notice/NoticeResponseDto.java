package com.project.homepage.web.dto.notice;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class NoticeResponseDto {

    private NoticeStatus status;
    private String message;
    private String createdDate;

    public NoticeResponseDto(Notice notice) {
        this.status = notice.getStatus();
        this.message = notice.getMessage();
        this.createdDate = notice.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));;
    }
}
