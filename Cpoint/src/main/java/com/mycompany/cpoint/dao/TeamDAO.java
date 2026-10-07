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
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    private static final String INSERT_TEAM_SQL = "INSERT INTO teams (name) VALUES (?)";
    private static final String UPDATE_TEAM_SQL = "UPDATE teams SET name = ? WHERE id = ?";
    private static final String DELETE_TEAM_SQL = "DELETE FROM teams WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM teams ORDER BY id";

    private static final String INSERT_MEMBER_SQL
            = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";
    private static final String DELETE_MEMBERS_SQL
            = "DELETE FROM team_members WHERE team_id = ?";
    private static final String SELECT_MEMBERS_SQL
            = "SELECT u.id AS id, u.first_name AS first_name, u.last_name AS last_name, "
            + "u.email AS email, u.username AS username, u.password AS password, "
            + "u.department AS department, u.gender AS gender "
            + "FROM team_members tm "
            + "JOIN users u ON u.id = tm.user_id "
            + "WHERE tm.team_id = ? "
            + "ORDER BY u.first_name, u.last_name";

    public void insert(Team team) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int teamId;
                try (PreparedStatement stmt = conn.prepareStatement(INSERT_TEAM_SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, team.getName());
                    stmt.executeUpdate();

                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (!generatedKeys.next()) {
                            throw new SQLException("Could not get the id of the new team.");
                        }
                        teamId = generatedKeys.getInt(1);
                    }
                }

                insertMembers(conn, teamId, team.getMembers());
                conn.commit();
                System.out.println("Team inserted with id: " + teamId);
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    public List<Team> findAll() throws SQLException {
        List<Team> teams = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teams.add(new Team(rs.getInt("id"), rs.getString("name")));
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(SELECT_MEMBERS_SQL)) {
                for (Team team : teams) {
                    stmt.setInt(1, team.getId());
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            team.addMember(UserDAO.mapRow(rs));
                        }
                    }
                }
            }
        }

        return teams;
    }

    public void update(Team team) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement(UPDATE_TEAM_SQL)) {
                    stmt.setString(1, team.getName());
                    stmt.setInt(2, team.getId());
                    if (stmt.executeUpdate() == 0) {
                        throw new SQLException("No team found with id " + team.getId() + ".");
                    }
                }

                deleteMembers(conn, team.getId());
                insertMembers(conn, team.getId(), team.getMembers());
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                deleteMembers(conn, id);

                try (PreparedStatement stmt = conn.prepareStatement(DELETE_TEAM_SQL)) {
                    stmt.setInt(1, id);
                    if (stmt.executeUpdate() == 0) {
                        throw new SQLException("No team found with id " + id + ".");
                    }
                }

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    private void insertMembers(Connection conn, int teamId, List<User> members) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_MEMBER_SQL)) {
            for (User member : members) {
                stmt.setInt(1, teamId);
                stmt.setInt(2, member.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void deleteMembers(Connection conn, int teamId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_MEMBERS_SQL)) {
            stmt.setInt(1, teamId);
            stmt.executeUpdate();
        }
    }
    // Temporary test method. We'll remove it once the grid is working.
public static void main(String[] args) throws SQLException {
    List<Team> teams = new TeamDAO().findAll();
    for (Team team : teams) {
        System.out.println(team);
    }

    User fromUserDAO = new UserDAO().findAll().get(0);
    User fromTeamDAO = teams.get(0).getMembers().get(0);
    System.out.println("Same object in memory? " + (fromUserDAO == fromTeamDAO));
    System.out.println("Same user by equals? " + fromUserDAO.equals(fromTeamDAO));
}
}
