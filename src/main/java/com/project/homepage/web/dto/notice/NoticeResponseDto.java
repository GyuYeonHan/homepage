package com.project.homepage.web.dto.notice;

import com.project.homepage.domain.notice.Notice;
import com.project.homepage.domain.notice.NoticeStatus;
import com.project.homepage.util.TimeUtil;
import lombok.Data;

@Data
public class NoticeResponseDto {

    private Long id;
    private NoticeStatus status;
    private String message;
    private String url;
    private String createdDate;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.status = notice.getStatus();
        this.message = notice.getMessage();
        this.url = notice.getUrl();
        this.createdDate = TimeUtil.calculateTimeBeforeNow(notice.getCreatedDate());
    }
}
