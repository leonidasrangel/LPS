/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.model;

/**
 *
 * @author Rangel
 */
public class HoursSummary {

    private final User user;
    private final long expectedMinutes;
    private final long workedMinutes;

    public HoursSummary(User user, long expectedMinutes, long workedMinutes) {
        this.user = user;
        this.expectedMinutes = expectedMinutes;
        this.workedMinutes = workedMinutes;
    }

    public User getUser() {
        return user;
    }

    public long getExpectedMinutes() {
        return expectedMinutes;
    }

    public long getWorkedMinutes() {
        return workedMinutes;
    }

    public long getBalanceMinutes() {
        return workedMinutes - expectedMinutes;
    }
}