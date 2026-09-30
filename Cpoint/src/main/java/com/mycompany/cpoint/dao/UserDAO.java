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
    
    private static final String SELECT_ALL_SQL =
    "SELECT id, first_name, last_name, email, username, password, department, gender FROM users";
    
    private static final String INSERT_SQL =
        "INSERT INTO users (first_name, last_name, email, username, password, department, gender) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

public List<User> findAll() throws SQLException {
    List<User> users = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            User user = new User(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("department"),
                rs.getString("gender")
            );
            users.add(user);
        }
    }

    return users;
}

    public void insert(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getDepartment());
            stmt.setString(7, user.getGender());

            stmt.executeUpdate();

            // Recupera o id que o banco gerou automaticamente e injeta no objeto User
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    // Não temos setId() de propósito (lembra?), então isso é só informativo por enquanto.
                    System.out.println("Usuário inserido com id: " + generatedId);
                }
            }
        }
    }
}
