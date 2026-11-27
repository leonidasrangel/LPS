/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.dao.TimeRecordDAO;
import com.mycompany.cpoint.dao.UserDAO;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.model.HoursSummary;
import com.mycompany.cpoint.model.RecordType;
import com.mycompany.cpoint.model.TimeRecord;
import com.mycompany.cpoint.model.User;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ReportController {

    private static final int WORKDAYS_PER_WEEK = 5;
    private static final int MINUTES_PER_HOUR = 60;

    private final UserDAO userDAO = new UserDAO();
    private final TimeRecordDAO timeRecordDAO = new TimeRecordDAO();

    public List<HoursSummary> buildMonthlyHoursReport(YearMonth month) throws DatabaseException {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.plusMonths(1).atDay(1).atStartOfDay();

        List<User> users = userDAO.findAll();
        List<TimeRecord> records = timeRecordDAO.findInPeriodOldestFirst(start, end);
        int workdays = countWorkdaysUntilToday(month);

        List<HoursSummary> report = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            long expectedMinutes = workdays * minutesPerWorkday(user);
            long workedMinutes = calculateWorkedMinutes(user, records);
            report.add(new HoursSummary(user, expectedMinutes, workedMinutes));
        }
        return report;
    }

    private long minutesPerWorkday(User user) {
        return (long) user.getWeeklyHours() * MINUTES_PER_HOUR / WORKDAYS_PER_WEEK;
    }

    private int countWorkdaysUntilToday(YearMonth month) {
        LocalDate today = LocalDate.now();
        int workdays = 0;

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate date = month.atDay(day);
            if (!date.isAfter(today) && isWorkday(date)) {
                workdays++;
            }
        }
        return workdays;
    }

    private boolean isWorkday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private long calculateWorkedMinutes(User user, List<TimeRecord> records) {
        long workedMinutes = 0;
        LocalDateTime openClockIn = null;

        for (int i = 0; i < records.size(); i++) {
            TimeRecord record = records.get(i);
            boolean belongsToUser = record.getUser().getId() == user.getId();

            if (belongsToUser && record.getType() == RecordType.CLOCK_IN) {
                openClockIn = record.getRecordedAt();
            } else if (belongsToUser && openClockIn != null) {
                Duration interval = Duration.between(openClockIn, record.getRecordedAt());
                workedMinutes = workedMinutes + interval.toMinutes();
                openClockIn = null;
            }
        }
        return workedMinutes;
    }
}