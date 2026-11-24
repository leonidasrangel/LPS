/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.dao.ProjectDAO;
import com.mycompany.cpoint.dao.TaskDAO;
import com.mycompany.cpoint.dao.TeamDAO;
import com.mycompany.cpoint.dao.TimeRecordDAO;
import com.mycompany.cpoint.dao.UserDAO;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.model.RecordType;
import com.mycompany.cpoint.model.TimeRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardController {

    private final UserDAO userDAO = new UserDAO();
    private final TeamDAO teamDAO = new TeamDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final TaskDAO taskDAO = new TaskDAO();
    private final TimeRecordDAO timeRecordDAO = new TimeRecordDAO();

    public long countUsers() throws DatabaseException {
        return userDAO.count();
    }

    public long countTeams() throws DatabaseException {
        return teamDAO.count();
    }

    public long countProjects() throws DatabaseException {
        return projectDAO.count();
    }

    public long countTasks() throws DatabaseException {
        return taskDAO.count();
    }

    public List<TimeRecord> listTodayPunches() throws DatabaseException {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);
        return timeRecordDAO.findInPeriod(startOfDay, startOfNextDay);
    }

    public int countWorkingNow(List<TimeRecord> todayPunches) {
        Set<Integer> usersAlreadySeen = new HashSet<>();
        int working = 0;

        for (int i = 0; i < todayPunches.size(); i++) {
            TimeRecord punch = todayPunches.get(i);
            int userId = punch.getUser().getId();

            if (!usersAlreadySeen.contains(userId)) {
                usersAlreadySeen.add(userId);
                if (punch.getType() == RecordType.CLOCK_IN) {
                    working++;
                }
            }
        }
        return working;
    }
}
