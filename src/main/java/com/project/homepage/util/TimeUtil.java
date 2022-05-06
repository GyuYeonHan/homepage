package com.project.homepage.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class TimeUtil {

    private TimeUtil() {
    }

    public static String calculateTimeBeforeNow(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        return getTimeBeforeFormat(time, now);
    }

    public static String calculateTimeBetween(LocalDateTime before, LocalDateTime after) {

        return getTimeBeforeFormat(before, after);
    }

    private static String getTimeBeforeFormat(LocalDateTime time, LocalDateTime now) {
        long second = ChronoUnit.SECONDS.between(time, now);
        if(second < 60) {
            return Long.toString(second) + "초 전";
        }

        long minute = ChronoUnit.MINUTES.between(time, now);
        if(minute < 60) {
            return Long.toString(minute)+ "분 전";
        }

        long hour = ChronoUnit.HOURS.between(time, now);
        if(hour < 24) {
            return Long.toString(hour)+ "시간 전";
        }

        long day = ChronoUnit.DAYS.between(time, now);
        if(day < 30) {
            return Long.toString(day)+ "일 전";
        }

        long month = ChronoUnit.MONTHS.between(time, now);
        if(month < 12) {
            return Long.toString(month)+ "달 전";
        }

        return Long.toString(ChronoUnit.YEARS.between(time, now)) + "년 전";
    }
}
