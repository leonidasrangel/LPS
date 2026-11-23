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
import com.mycompany.cpoint.model.RecordType;
import com.mycompany.cpoint.model.TimeRecord;
import com.mycompany.cpoint.model.User;
import java.time.LocalDateTime;

public class TimeRecordController {

    private final TimeRecordDAO timeRecordDAO = new TimeRecordDAO();

    public TimeRecord registerPunch(User user) throws DatabaseException {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);

        TimeRecord lastToday = timeRecordDAO.findLastInPeriod(user.getId(), startOfDay, startOfNextDay);

        RecordType type;
        if (lastToday == null) {
            type = RecordType.CLOCK_IN;
        } else if (lastToday.getType() == RecordType.CLOCK_IN) {
            type = RecordType.CLOCK_OUT;
        } else {
            type = RecordType.CLOCK_IN;
        }

        TimeRecord record = new TimeRecord(user, now, type);
        timeRecordDAO.insert(record);
        return record;
    }
}
