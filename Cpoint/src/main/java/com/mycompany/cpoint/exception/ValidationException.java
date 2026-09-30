/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.exception;

/**
 *
 * @author Rangel
 */
import java.util.List;

public class ValidationException extends Exception {

    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super(String.join("\n", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
