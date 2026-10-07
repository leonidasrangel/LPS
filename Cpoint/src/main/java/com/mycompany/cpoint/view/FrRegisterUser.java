/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cpoint.view;

import com.mycompany.cpoint.controller.UserController;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rangel
 */
public class FrRegisterUser extends javax.swing.JFrame {

    private final UserController userController = new UserController();
    private List<User> users = new ArrayList<>();
    private User editingUser;

    /**
     * Creates new form FrRegisterUser
     */
    public FrRegisterUser() {
        initComponents();
        tblUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshTable();
        clearFields();
        enableFields(false);
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
        model.setRowCount(0);

        try {
            users = userController.listUsers();
        } catch (SQLException ex) {
            users = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User user : users) {
            model.addRow(new Object[]{
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getDepartment(),
                user.getGender()
            });
        }
    }

    private User getSelectedObjectFromGrid() {
        int row = tblUsers.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return users.get(tblUsers.convertRowIndexToModel(row));
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        cmbDepartment.setSelectedIndex(-1);
        bgGender.clearSelection();
    }

    private void enableFields(boolean enabled) {
        txtFirstName.setEnabled(enabled);
        txtLastName.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        txtUsername.setEnabled(enabled);
        txtPassword.setEnabled(enabled);
        txtConfirmPassword.setEnabled(enabled);
        cmbDepartment.setEnabled(enabled);
        rdbMan.setEnabled(enabled);
        rdbWoman.setEnabled(enabled);
        rdbNotDisclose.setEnabled(enabled);

        btnSave.setEnabled(enabled);
        btnCancel.setEnabled(enabled);

        btnNew.setEnabled(!enabled);
        btnEdit.setEnabled(!enabled);
        btnDelete.setEnabled(!enabled);
        tblUsers.setEnabled(!enabled);
    }

    private void objectToFields(User user) {
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtEmail.setText(user.getEmail());
        txtUsername.setText(user.getUsername());
        txtPassword.setText("");
        txtConfirmPassword.setText("");

        cmbDepartment.setSelectedIndex(-1);
        cmbDepartment.setSelectedItem(user.getDepartment());

        String gender = user.getGender();
        if (rdbMan.getText().equals(gender)) {
            rdbMan.setSelected(true);
        } else if (rdbWoman.getText().equals(gender)) {
            rdbWoman.setSelected(true);
        } else if (rdbNotDisclose.getText().equals(gender)) {
            rdbNotDisclose.setSelected(true);
        } else {
            bgGender.clearSelection();
        }
    }

    private User fieldsToObject() throws ValidationException {
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        boolean keepCurrentPassword = editingUser != null
                && password.isEmpty() && confirmPassword.isEmpty();

        if (keepCurrentPassword) {
            password = editingUser.getPassword();
        } else if (!password.equals(confirmPassword)) {
            throw new ValidationException(List.of("The passwords do not match."));
        }

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String username = txtUsername.getText();
        String department = (String) cmbDepartment.getSelectedItem();
        String gender = getSelectedGender();

        if (editingUser == null) {
            return new User(firstName, lastName, email, username, password, department, gender);
        }
        return new User(editingUser.getId(), firstName, lastName, email, username,
                password, department, gender);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgGender = new javax.swing.ButtonGroup();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblConfirmPassword = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblGender = new javax.swing.JLabel();
        rdbMan = new javax.swing.JRadioButton();
        rdbWoman = new javax.swing.JRadioButton();
        rdbNotDisclose = new javax.swing.JRadioButton();
        cmbDepartment = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        scrUsers = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        lblIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Register User");
        lblTitle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblFirstName.setText("First name:");

        lblEmail.setText("Email:");

        lblLastName.setText("Last name:");

        lblPassword.setText("Password:");

        lblConfirmPassword.setText("Confirm Password:");

        lblDepartment.setText("Departament:");

        lblUserName.setText("Username:");

        lblGender.setText("Gender:");

        bgGender.add(rdbMan);
        rdbMan.setText("Man");

        bgGender.add(rdbWoman);
        rdbWoman.setText("Woman");

        bgGender.add(rdbNotDisclose);
        rdbNotDisclose.setText("Not disclose");

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administration", "Accounting", "Finance", "Human Resources", "Information Technology", "Procurement" }));

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First name", "Last name", "Email", "Username", "Department", "Gender"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        scrUsers.setViewportView(tblUsers);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFirstName)
                    .addComponent(lblLastName))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFirstName)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblGender)
                .addGap(69, 69, 69)
                .addComponent(rdbMan)
                .addGap(18, 18, 18)
                .addComponent(rdbWoman)
                .addGap(18, 18, 18)
                .addComponent(rdbNotDisclose)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfirmPassword)
                    .addComponent(lblDepartment))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtConfirmPassword)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName)
                    .addComponent(lblPassword))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnNew)
                .addGap(18, 18, 18)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave))
            .addComponent(scrUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLastName)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGender)
                    .addComponent(rdbMan)
                    .addComponent(rdbWoman)
                    .addComponent(rdbNotDisclose))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmPassword)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepartment)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel)
                    .addComponent(btnNew)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addComponent(scrUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/cpoint/resources/logoTest.jpg"))); // NOI18N
        lblIcon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //User u = camposparaobjeto() <-- pega os textos da tela
        //validateUser vu <- valida os dados que o código pegou. se setiver ok, entrega para o controller, senao, dispara exception

        try {
            User user = fieldsToObject();
            boolean isNew = user.getId() == 0;

            userController.saveUser(user);

            editingUser = null;
            refreshTable();
            clearFields();
            enableFields(false);
            JOptionPane.showMessageDialog(this,
                    isNew ? "User registered successfully." : "User updated successfully.");
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked
        if (!tblUsers.isEnabled()) {
            return;
        }
        User user = getSelectedObjectFromGrid();
        if (user != null) {
            objectToFields(user);
        }
    }//GEN-LAST:event_tblUsersMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        editingUser = null;
        tblUsers.clearSelection();
        clearFields();
        enableFields(true);
        txtFirstName.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        User user = getSelectedObjectFromGrid();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Select a user in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        editingUser = user;
        objectToFields(user);
        enableFields(true);
        txtFirstName.requestFocus();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        User user = getSelectedObjectFromGrid();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Select a user in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Delete the user \"" + user + "\"?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            userController.deleteUser(user.getId());
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        editingUser = null;
        clearFields();
        enableFields(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private String getSelectedGender() {
        if (rdbMan.isSelected()) {
            return rdbMan.getText();
        } else if (rdbWoman.isSelected()) {
            return rdbWoman.getText();
        } else if (rdbNotDisclose.isSelected()) {
            return rdbNotDisclose.getText();
        }
        return null;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrRegisterUser().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgGender;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JRadioButton rdbMan;
    private javax.swing.JRadioButton rdbNotDisclose;
    private javax.swing.JRadioButton rdbWoman;
    private javax.swing.JScrollPane scrUsers;
    private javax.swing.JTable tblUsers;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
