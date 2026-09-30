/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.util;

/**
 *
 * @author Rangel
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String CREATE_TABLE_USERS =
        "CREATE TABLE IF NOT EXISTS users (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "first_name TEXT NOT NULL," +
        "last_name TEXT NOT NULL," +
        "email TEXT NOT NULL UNIQUE," +
        "username TEXT NOT NULL UNIQUE," +
        "password TEXT NOT NULL," +
        "department TEXT NOT NULL," +
        "gender TEXT NOT NULL" +
        ")";

    private static final String CREATE_TABLE_TASKS =
        "CREATE TABLE IF NOT EXISTS tasks (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "name TEXT NOT NULL," +
        "description TEXT NOT NULL," +
        "state TEXT NOT NULL" +
        ")";

    private static final String CREATE_TABLE_PROJECTS =
        "CREATE TABLE IF NOT EXISTS projects (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "name TEXT NOT NULL," +
        "description TEXT NOT NULL," +
        "start_date TEXT NOT NULL," +
        "end_date TEXT" +
        ")";
    
    private static final String CREATE_TABLE_TEAMS =
        "CREATE TABLE IF NOT EXISTS teams (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "name TEXT NOT NULL" +
        ")";

    private static final String CREATE_TABLE_TEAM_MEMBERS =
        "CREATE TABLE IF NOT EXISTS team_members (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "team_id INTEGER NOT NULL," +
        "user_id INTEGER NOT NULL," +
        "FOREIGN KEY (team_id) REFERENCES teams(id)," +
        "FOREIGN KEY (user_id) REFERENCES users(id)" +
        ")";

    public static void initialize() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_USERS);
            stmt.execute(CREATE_TABLE_TASKS);
            stmt.execute(CREATE_TABLE_PROJECTS);
            stmt.execute(CREATE_TABLE_TEAMS);
            stmt.execute(CREATE_TABLE_TEAM_MEMBERS);
            System.out.println("Tabelas prontas.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initialize();
    }
}
