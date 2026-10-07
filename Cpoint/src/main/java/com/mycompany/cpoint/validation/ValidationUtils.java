/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.validation;

/**
 *
 * @author Rangel
 */
public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
