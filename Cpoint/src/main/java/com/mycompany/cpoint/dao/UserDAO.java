/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.dao;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String INSERT_SQL
            = "INSERT INTO users (first_name, last_name, email, username, password, department, gender) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL
            = "SELECT id, first_name, last_name, email, username, password, department, gender "
            + "FROM users ORDER BY id";
    private static final String UPDATE_SQL
            = "UPDATE users SET first_name = ?, last_name = ?, email = ?, username = ?, "
            + "password = ?, department = ?, gender = ? WHERE id = ?";
    private static final String DELETE_SQL
            = "DELETE FROM users WHERE id = ?";
    private static final String EMAIL_EXISTS_SQL
            = "SELECT COUNT(*) FROM users WHERE email = ? AND id <> ?";
    private static final String USERNAME_EXISTS_SQL
            = "SELECT COUNT(*) FROM users WHERE username = ? AND id <> ?";
    private static final String IS_TEAM_MEMBER_SQL
            = "SELECT COUNT(*) FROM team_members WHERE user_id = ?";

    public void insert(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            fillCommonParameters(stmt, user);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("User inserted with id: " + generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }
        }

        return users;
    }

    public void update(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            fillCommonParameters(stmt, user);
            stmt.setInt(8, user.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No user found with id " + user.getId() + ".");
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No user found with id " + id + ".");
            }
        }
    }

    public boolean existsByEmail(String email, int ignoredId) throws SQLException {
        return countMatches(EMAIL_EXISTS_SQL, email, ignoredId) > 0;
    }

    public boolean existsByUsername(String username, int ignoredId) throws SQLException {
        return countMatches(USERNAME_EXISTS_SQL, username, ignoredId) > 0;
    }

    public boolean isTeamMember(int userId) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(IS_TEAM_MEMBER_SQL)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private int countMatches(String sql, String value, int ignoredId) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, value);
            stmt.setInt(2, ignoredId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private void fillCommonParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getUsername());
        stmt.setString(5, user.getPassword());
        stmt.setString(6, user.getDepartment());
        stmt.setString(7, user.getGender());
    }

    static User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("department"),
                rs.getString("gender")
        );
    }
}
