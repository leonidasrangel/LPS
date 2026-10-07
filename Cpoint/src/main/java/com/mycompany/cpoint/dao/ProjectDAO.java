/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.dao;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.model.Project;
import com.mycompany.cpoint.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    private static final String INSERT_SQL
            = "INSERT INTO projects (name, description, start_date, end_date) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL
            = "SELECT id, name, description, start_date, end_date FROM projects ORDER BY id";
    private static final String UPDATE_SQL
            = "UPDATE projects SET name = ?, description = ?, start_date = ?, end_date = ? WHERE id = ?";
    private static final String DELETE_SQL
            = "DELETE FROM projects WHERE id = ?";

    public void insert(Project project) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            fillCommonParameters(stmt, project);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Project inserted with id: " + generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Project> findAll() throws SQLException {
        List<Project> projects = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        fromDbDate(rs.getString("start_date")),
                        fromDbDate(rs.getString("end_date"))
                ));
            }
        }

        return projects;
    }

    public void update(Project project) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            fillCommonParameters(stmt, project);
            stmt.setInt(5, project.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No project found with id " + project.getId() + ".");
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No project found with id " + id + ".");
            }
        }
    }

    private void fillCommonParameters(PreparedStatement stmt, Project project) throws SQLException {
        stmt.setString(1, project.getName());
        stmt.setString(2, project.getDescription());
        stmt.setString(3, toDbDate(project.getStartDate()));
        stmt.setString(4, toDbDate(project.getEndDate()));
    }

    private String toDbDate(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    private LocalDate fromDbDate(String text) {
        return text != null ? LocalDate.parse(text) : null;
    }

}
