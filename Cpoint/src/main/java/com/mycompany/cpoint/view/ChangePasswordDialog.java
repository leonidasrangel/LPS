/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.view;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.controller.AuthController;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

public class ChangePasswordDialog extends JDialog {

    private static final int FIELD_COLUMNS = 15;

    private final AuthController authController = new AuthController();
    private final JPasswordField txtCurrentPassword = new JPasswordField(FIELD_COLUMNS);
    private final JPasswordField txtNewPassword = new JPasswordField(FIELD_COLUMNS);
    private final JPasswordField txtConfirmPassword = new JPasswordField(FIELD_COLUMNS);

    public ChangePasswordDialog(JFrame owner) {
        super(owner, "Change password", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        fieldsPanel.add(new JLabel("Current password:"));
        fieldsPanel.add(txtCurrentPassword);
        fieldsPanel.add(new JLabel("New password:"));
        fieldsPanel.add(txtNewPassword);
        fieldsPanel.add(new JLabel("Confirm new password:"));
        fieldsPanel.add(txtConfirmPassword);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                savePassword();
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 12, 16));
        content.add(fieldsPanel, BorderLayout.CENTER);
        content.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(content);

        getRootPane().setDefaultButton(btnSave);
        pack();
        setLocationRelativeTo(owner);
    }

    private void savePassword() {
        String currentPassword = new String(txtCurrentPassword.getPassword());
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmation = new String(txtConfirmPassword.getPassword());

        try {
            authController.changePassword(currentPassword, newPassword, confirmation);
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Password not changed", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Your password was changed successfully.",
                "Password changed", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}