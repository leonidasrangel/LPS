/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.model;

/**
 *
 * @author Rangel
 */

public class Task {

    private int id;
    private String name;
    private String description;
    private String state;

    public Task(String name, String description, String state) {
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public Task(int id, String name, String description, String state) {
        this(name, description, state);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", name=" + name + ", description=" + description
                + ", state=" + state + "}";
    }
}
