/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.dao;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.model.Task;
import com.mycompany.cpoint.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private static final String INSERT_SQL
            = "INSERT INTO tasks (name, description, state) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_SQL
            = "SELECT id, name, description, state FROM tasks ORDER BY id";
    private static final String UPDATE_SQL
            = "UPDATE tasks SET name = ?, description = ?, state = ? WHERE id = ?";
    private static final String DELETE_SQL
            = "DELETE FROM tasks WHERE id = ?";

    public void insert(Task task) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getState());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Task inserted with id: " + generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Task> findAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("state")
                ));
            }
        }

        return tasks;
    }

    public void update(Task task) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getState());
            stmt.setInt(4, task.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No task found with id " + task.getId() + ".");
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No task found with id " + id + ".");
            }
        }
    }
}
