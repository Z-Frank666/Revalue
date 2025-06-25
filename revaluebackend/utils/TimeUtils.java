package com.frank.revaluebackend.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    public static String trans(LocalDateTime start, LocalDateTime end) {
        // 计算时间差
        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();
        long hours = duration.toHours();

        // 计算日期差
        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();

        // 总天数计算（考虑跨年跨月）
        long totalDays = ChronoUnit.DAYS.between(start, end);

        if (minutes <= 1) {
            return "刚刚";
        } else if (hours < 1) {
            return minutes + "分钟前";
        } else if (totalDays < 1) {
            return hours + "小时前";
        } else if (totalDays <= 30) {
            return totalDays + "天前";
        } else {
            // 计算总月数（考虑跨年）
            long totalMonths = years * 12 + months;
            if (days > 15) {
                totalMonths++; // 超过15天算一个月
            }
            return totalMonths + "个月前";
        }
    }
}