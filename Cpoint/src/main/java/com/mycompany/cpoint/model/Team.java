/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.model;

/**
 *
 * @author Rangel
 */
import java.util.ArrayList;
import java.util.List;

public class Team {

    private int id;
    private String name;
    private List<User> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public Team(int id, String name) {
        this(name);
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

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    @Override
    public String toString() {
        return "Team{id=" + id + ", name=" + name + ", members=" + members + "}";
    }
}
