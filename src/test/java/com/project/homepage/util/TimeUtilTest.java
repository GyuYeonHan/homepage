package com.project.homepage.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TimeUtilTest {



    @Test
    void second() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 3, 31, 14, 0, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("30초 전", result);
    }

    @Test
    void minute() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 3, 31, 14, 6, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("6분 전", result);
    }

    @Test
    void hour() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 3, 31, 19, 6, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("5시간 전", result);
    }

    @Test
    void day() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 4, 3, 19, 6, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("3일 전", result);
    }

    @Test
    void month() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2022, 5, 30, 19, 6, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("1달 전", result);
    }

    @Test
    void year() {
        LocalDateTime time1 = LocalDateTime.of(2022, 3, 31, 14, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2029, 5, 3, 19, 6, 30);

        String result = TimeUtil.calculateTimeBetween(time1, time2);

        Assertions.assertEquals("7년 전", result);
    }

}