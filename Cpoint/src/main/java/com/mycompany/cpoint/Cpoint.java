/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.cpoint;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.controller.UserController;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.view.FrLogin;
import java.awt.EventQueue;
import javax.swing.JOptionPane;

public class Cpoint {

    public static void main(String[] args) {
        EventQueue.invokeLater(Cpoint::start);
    }

    private static void start() {
        try {
            User defaultAdmin = new UserController().createDefaultAdminIfNeeded();
            if (defaultAdmin != null) {
                JOptionPane.showMessageDialog(null,
                        "No users were found, so a default administrator was created.\n"
                        + "Username: " + defaultAdmin.getUsername() + "\n"
                        + "Password: " + defaultAdmin.getPassword() + "\n\n"
                        + "Sign in and change this password in Register User.",
                        "First access", JOptionPane.INFORMATION_MESSAGE);
            }
            new FrLogin().setVisible(true);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(null,
                    "Database error: " + ex.getMessage()
                    + "\nCheck that PostgreSQL is running and try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
