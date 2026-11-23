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
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.RecordType;
import com.mycompany.cpoint.model.TimeRecord;
import com.mycompany.cpoint.model.User;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TimeRecordController {

    private static final int MIN_SECONDS_BETWEEN_PUNCHES = 60;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final TimeRecordDAO timeRecordDAO = new TimeRecordDAO();

    public TimeRecord registerPunch(User user) throws ValidationException, DatabaseException {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        TimeRecord last = timeRecordDAO.findLastByUser(user.getId());

        checkMinimumInterval(last, now);

        RecordType type = RecordType.CLOCK_IN;
        if (last != null) {
            boolean sameDay = last.getRecordedAt().toLocalDate().equals(now.toLocalDate());
            if (sameDay && last.getType() == RecordType.CLOCK_IN) {
                type = RecordType.CLOCK_OUT;
            }
        }

        TimeRecord record = new TimeRecord(user, now, type);
        timeRecordDAO.insert(record);
        return record;
    }

    public List<TimeRecord> listPunches(User user) throws DatabaseException {
        return timeRecordDAO.findByUser(user.getId());
    }

    public List<TimeRecord> listTodayPunches(User user) throws DatabaseException {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);
        return timeRecordDAO.findByUserInPeriod(user.getId(), startOfDay, startOfNextDay);
    }

    private void checkMinimumInterval(TimeRecord last, LocalDateTime now) throws ValidationException {
        if (last == null) {
            return;
        }

        long seconds = Duration.between(last.getRecordedAt(), now).getSeconds();
        if (seconds < MIN_SECONDS_BETWEEN_PUNCHES) {
            throw new ValidationException(List.of(
                    "Your last punch (" + last.getType() + ") was at "
                    + last.getRecordedAt().format(TIME_FORMAT) + ".",
                    "Wait at least " + MIN_SECONDS_BETWEEN_PUNCHES + " seconds between punches."));
        }
    }
}
