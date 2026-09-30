/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.dao;

/**
 *
 * @author Rangel
 */

import com.mycompany.cpoint.model.Team;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamDAO {

    private static final String INSERT_TEAM_SQL = "INSERT INTO teams (name) VALUES (?)";
    private static final String INSERT_MEMBER_SQL =
        "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";

    public void insert(Team team) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            int teamId;
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_TEAM_SQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, team.getName());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        teamId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Não foi possível obter o id do time criado.");
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(INSERT_MEMBER_SQL)) {
                for (User member : team.getMembers()) {
                    stmt.setInt(1, teamId);
                    stmt.setInt(2, member.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
            System.out.println("Team inserido com id: " + teamId);

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
