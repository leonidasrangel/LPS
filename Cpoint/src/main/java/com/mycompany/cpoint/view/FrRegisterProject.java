/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cpoint.view;

import com.mycompany.cpoint.controller.ProjectController;
import com.mycompany.cpoint.controller.UserController;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Project;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.validation.ValidationUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrRegisterProject extends javax.swing.JFrame {

    private static final DateTimeFormatter DATE_FORMAT
            = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final ProjectController projectController = new ProjectController();
    private final UserController userController = new UserController();
    private List<Project> projects = new ArrayList<>();
    private final List<User> selectedMembers = new ArrayList<>();
    private Project editingProject;

    public FrRegisterProject() {
        initComponents();
        tblProjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadUsers();
        refreshTable();
        clearFields();
        enableFields(false);
    }

    private void loadUsers() {
        cmbUsers.removeAllItems();

        List<User> users;
        try {
            users = userController.listUsers();
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            cmbUsers.addItem(users.get(i));
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblProjects.getModel();
        model.setRowCount(0);

        try {
            projects = projectController.listProjects();
        } catch (DatabaseException ex) {
            projects = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Error loading projects: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            model.addRow(new Object[]{
                project.getId(),
                project.getName(),
                project.getDescription(),
                formatDate(project.getStartDate()),
                formatDate(project.getEndDate()),
                membersToText(project.getMembers())
            });
        }
    }

    private String membersToText(Set<User> members) {
        StringBuilder text = new StringBuilder();
        for (User member : members) {
            if (text.length() > 0) {
                text.append(", ");
            }
            text.append(member.getFirstName()).append(" ").append(member.getLastName());
        }
        return text.toString();
    }

    private Project getSelectedObjectFromGrid() {
        int row = tblProjects.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return projects.get(tblProjects.convertRowIndexToModel(row));
    }

    private void clearFields() {
        txtNameProject.setText("");
        txtDescriptionProject.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        cmbUsers.setSelectedIndex(-1);
        selectedMembers.clear();
    }

    private void enableFields(boolean enabled) {
        txtNameProject.setEnabled(enabled);
        txtDescriptionProject.setEnabled(enabled);
        txtStartDate.setEnabled(enabled);
        txtEndDate.setEnabled(enabled);
        cmbUsers.setEnabled(enabled);
        btnAddMember.setEnabled(enabled);
        btnRemoveMember.setEnabled(enabled);

        btnSave.setEnabled(enabled);
        btnCancel.setEnabled(enabled);

        btnNew.setEnabled(!enabled);
        btnEdit.setEnabled(!enabled);
        btnDelete.setEnabled(!enabled);
        tblProjects.setEnabled(!enabled);
    }

    private void objectToFields(Project project) {
        txtNameProject.setText(project.getName());
        txtDescriptionProject.setText(project.getDescription());
        txtStartDate.setText(formatDate(project.getStartDate()));
        txtEndDate.setText(formatDate(project.getEndDate()));

        cmbUsers.setSelectedIndex(-1);
        selectedMembers.clear();
        selectedMembers.addAll(project.getMembers());
    }

    private Project fieldsToObject() throws ValidationException {
        List<String> errors = new ArrayList<>();
        LocalDate startDate = parseDate(txtStartDate.getText(), "start date", errors);
        LocalDate endDate = parseDate(txtEndDate.getText(), "end date", errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        String name = txtNameProject.getText();
        String description = txtDescriptionProject.getText();

        Project project;
        if (editingProject == null) {
            project = new Project(name, description, startDate, endDate);
        } else {
            project = new Project(editingProject.getId(), name, description, startDate, endDate);
        }

        for (int i = 0; i < selectedMembers.size(); i++) {
            project.addMember(selectedMembers.get(i));
        }
        return project;
    }

    private LocalDate parseDate(String text, String fieldLabel, List<String> errors) {
        if (ValidationUtils.isBlank(text)) {
            return null;
        }
        try {
            return LocalDate.parse(text.trim(), DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            errors.add("Invalid " + fieldLabel + ". Use the format dd/MM/yyyy.");
            return null;
        }
    }

    private String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMAT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitleProject = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblNameProject = new javax.swing.JLabel();
        txtNameProject = new javax.swing.JTextField();
        lblDescriptionProject = new javax.swing.JLabel();
        txtDescriptionProject = new javax.swing.JTextField();
        lblStartDate = new javax.swing.JLabel();
        txtStartDate = new javax.swing.JTextField();
        lblEndDate = new javax.swing.JLabel();
        txtEndDate = new javax.swing.JTextField();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblSelectMember = new javax.swing.JLabel();
        cmbUsers = new javax.swing.JComboBox<>();
        btnAddMember = new javax.swing.JButton();
        btnRemoveMember = new javax.swing.JButton();
        btnListMembers = new javax.swing.JButton();
        scrProjects = new javax.swing.JScrollPane();
        tblProjects = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitleProject.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        lblTitleProject.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleProject.setText("Register Project");
        lblTitleProject.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        lblNameProject.setText("Name project:");

        lblDescriptionProject.setText("Description:");

        lblStartDate.setText("Start date:");

        lblEndDate.setText("End date (optional):");

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

        lblSelectMember.setText("Select Member:");

        btnAddMember.setText("Add Member");
        btnAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMemberActionPerformed(evt);
            }
        });

        btnRemoveMember.setText("Remove Member");
        btnRemoveMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMemberActionPerformed(evt);
            }
        });

        btnListMembers.setText("List Member");
        btnListMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListMembersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescriptionProject)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNameProject)
                        .addGap(18, 18, 18)
                        .addComponent(txtNameProject, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(lblStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescriptionProject)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNew)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSelectMember)
                        .addGap(18, 18, 18)
                        .addComponent(cmbUsers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddMember)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveMember)
                        .addGap(18, 18, 18)
                        .addComponent(btnListMembers)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNameProject)
                    .addComponent(txtNameProject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(lblDescriptionProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescriptionProject, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectMember)
                    .addComponent(cmbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddMember)
                    .addComponent(btnRemoveMember)
                    .addComponent(btnListMembers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartDate)
                    .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEndDate)
                    .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addGap(16, 16, 16))
        );

        tblProjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description", "Start Date", "End Date", "Members"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProjectsMouseClicked(evt);
            }
        });
        scrProjects.setViewportView(tblProjects);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrProjects)
                    .addComponent(lblTitleProject, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleProject, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrProjects, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            Project project = fieldsToObject();
            boolean isNew = project.getId() == 0;

            projectController.saveProject(project);

            editingProject = null;
            refreshTable();
            clearFields();
            enableFields(false);
            if (isNew) {
                JOptionPane.showMessageDialog(this, "Project registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Project updated successfully.");
            }
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblProjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProjectsMouseClicked
        if (!tblProjects.isEnabled()) {
            return;
        }
        Project project = getSelectedObjectFromGrid();
        if (project != null) {
            objectToFields(project);
        }
    }//GEN-LAST:event_tblProjectsMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        editingProject = null;
        tblProjects.clearSelection();
        clearFields();
        enableFields(true);
        txtNameProject.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Project project = getSelectedObjectFromGrid();
        if (project == null) {
            JOptionPane.showMessageDialog(this, "Select a project in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        editingProject = project;
        objectToFields(project);
        enableFields(true);
        txtNameProject.requestFocus();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Project project = getSelectedObjectFromGrid();
        if (project == null) {
            JOptionPane.showMessageDialog(this, "Select a project in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Delete the project \"" + project.getName() + "\"?\n"
                + "The users will not be deleted, only removed from this project.",
                "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            projectController.deleteProject(project.getId());
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Project deleted successfully.");
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        editingProject = null;
        clearFields();
        enableFields(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMemberActionPerformed
        User selected = (User) cmbUsers.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a user first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedMembers.contains(selected)) {
            JOptionPane.showMessageDialog(this, "This user is already in the project.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedMembers.add(selected);
        JOptionPane.showMessageDialog(this, selected + " added to the project.");// TODO add your handling code here:
    }//GEN-LAST:event_btnAddMemberActionPerformed

    private void btnRemoveMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMemberActionPerformed
        User selected = (User) cmbUsers.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a user first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedMembers.remove(selected)) {
            JOptionPane.showMessageDialog(this, selected + " removed from the project.");
        } else {
            JOptionPane.showMessageDialog(this, "This user is not in the project.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveMemberActionPerformed

    private void btnListMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListMembersActionPerformed
        if (selectedMembers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members added yet.");
            return;
        }

        StringBuilder text = new StringBuilder("Project members:\n");
        for (int i = 0; i < selectedMembers.size(); i++) {
            text.append("- ").append(selectedMembers.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(this, text.toString());// TODO add your handling code here:
    }//GEN-LAST:event_btnListMembersActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMember;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnListMembers;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemoveMember;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<User> cmbUsers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDescriptionProject;
    private javax.swing.JLabel lblEndDate;
    private javax.swing.JLabel lblNameProject;
    private javax.swing.JLabel lblSelectMember;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JLabel lblTitleProject;
    private javax.swing.JScrollPane scrProjects;
    private javax.swing.JTable tblProjects;
    private javax.swing.JTextField txtDescriptionProject;
    private javax.swing.JTextField txtEndDate;
    private javax.swing.JTextField txtNameProject;
    private javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
