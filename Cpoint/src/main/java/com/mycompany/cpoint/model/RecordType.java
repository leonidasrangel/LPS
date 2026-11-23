/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.cpoint.model;

/**
 *
 * @author Rangel
 */
public enum RecordType {
    CLOCK_IN("Clock in"),
    CLOCK_OUT("Clock out");

    private final String label;

    RecordType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
