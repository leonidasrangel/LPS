/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.util;

/**
 *
 * @author Rangel
 */
public final class DurationFormat {

    private static final int MINUTES_PER_HOUR = 60;

    private DurationFormat() {
    }

    public static String formatHours(long minutes) {
        long hours = minutes / MINUTES_PER_HOUR;
        long remainingMinutes = minutes % MINUTES_PER_HOUR;
        return String.format("%02d:%02d", hours, remainingMinutes);
    }

    public static String formatBalance(long minutes) {
        if (minutes > 0) {
            return "+" + formatHours(minutes);
        }
        if (minutes < 0) {
            return "-" + formatHours(-minutes);
        }
        return formatHours(0);
    }
}