/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cpoint.view;

import com.mycompany.cpoint.controller.TaskController;
import com.mycompany.cpoint.controller.UserController;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.Task;
import com.mycompany.cpoint.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrRegisterTask extends javax.swing.JFrame {

    private Task editingTask;

    private final TaskController taskController = new TaskController();
    private final UserController userController = new UserController();
    private List<Task> tasks = new ArrayList<>();
    private final List<User> selectedMembers = new ArrayList<>();

    public FrRegisterTask() {
        initComponents();
        tblTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        DefaultTableModel model = (DefaultTableModel) tblTasks.getModel();
        model.setRowCount(0);

        try {
            tasks = taskController.listTasks();
        } catch (DatabaseException ex) {
            tasks = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            model.addRow(new Object[]{
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getState(),
                membersToText(task.getMembers())
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

    private Task getSelectedObjectFromGrid() {
        int row = tblTasks.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return tasks.get(tblTasks.convertRowIndexToModel(row));
    }

    private void clearFields() {
        txtNameTask.setText("");
        txtDescriptionTask.setText("");
        bgState.clearSelection();
        cmbUsers.setSelectedIndex(-1);
        selectedMembers.clear();
    }

    private void enableFields(boolean enabled) {
        txtNameTask.setEnabled(enabled);
        txtDescriptionTask.setEnabled(enabled);
        rdbNotStarted.setEnabled(enabled);
        rdbInProgress.setEnabled(enabled);
        rdbCompleted.setEnabled(enabled);
        cmbUsers.setEnabled(enabled);
        btnAddMember.setEnabled(enabled);
        btnRemoveMember.setEnabled(enabled);

        btnSave.setEnabled(enabled);
        btnCancel.setEnabled(enabled);

        btnNew.setEnabled(!enabled);
        btnEdit.setEnabled(!enabled);
        btnDelete.setEnabled(!enabled);
        tblTasks.setEnabled(!enabled);
    }

    private void objectToFields(Task task) {
        txtNameTask.setText(task.getName());
        txtDescriptionTask.setText(task.getDescription());

        String state = task.getState();
        if (rdbNotStarted.getText().equals(state)) {
            rdbNotStarted.setSelected(true);
        } else if (rdbInProgress.getText().equals(state)) {
            rdbInProgress.setSelected(true);
        } else if (rdbCompleted.getText().equals(state)) {
            rdbCompleted.setSelected(true);
        } else {
            bgState.clearSelection();
        }

        cmbUsers.setSelectedIndex(-1);
        selectedMembers.clear();
        selectedMembers.addAll(task.getMembers());
    }

    private Task fieldsToObject() {
        String name = txtNameTask.getText();
        String description = txtDescriptionTask.getText();
        String state = getSelectedState();

        Task task;
        if (editingTask == null) {
            task = new Task(name, description, state);
        } else {
            task = new Task(editingTask.getId(), name, description, state);
        }

        for (int i = 0; i < selectedMembers.size(); i++) {
            task.addMember(selectedMembers.get(i));
        }
        return task;
    }

    private String getSelectedState() {
        if (rdbNotStarted.isSelected()) {
            return rdbNotStarted.getText();
        } else if (rdbInProgress.isSelected()) {
            return rdbInProgress.getText();
        } else if (rdbCompleted.isSelected()) {
            return rdbCompleted.getText();
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgState = new javax.swing.ButtonGroup();
        lblTitleTask = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblNameTask = new javax.swing.JLabel();
        txtNameTask = new javax.swing.JTextField();
        lblDescriptionTask = new javax.swing.JLabel();
        txtDescriptionTask = new javax.swing.JTextField();
        lblState = new javax.swing.JLabel();
        rdbNotStarted = new javax.swing.JRadioButton();
        rdbInProgress = new javax.swing.JRadioButton();
        rdbCompleted = new javax.swing.JRadioButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        scrTasks = new javax.swing.JScrollPane();
        tblTasks = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblSelectMember = new javax.swing.JLabel();
        cmbUsers = new javax.swing.JComboBox<>();
        btnAddMember = new javax.swing.JButton();
        btnRemoveMember = new javax.swing.JButton();
        btnListMember = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitleTask.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        lblTitleTask.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleTask.setText("Register Task");
        lblTitleTask.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNameTask.setText("Name task:");

        lblDescriptionTask.setText("Description:");

        lblState.setText("State:");

        bgState.add(rdbNotStarted);
        rdbNotStarted.setText("Not started");

        bgState.add(rdbInProgress);
        rdbInProgress.setText("In progress");

        bgState.add(rdbCompleted);
        rdbCompleted.setText("Completed");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tblTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description", "State", "Members"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTasksMouseClicked(evt);
            }
        });
        scrTasks.setViewportView(tblTasks);

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

        btnListMember.setText("List Member");
        btnListMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescriptionTask)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNameTask)
                        .addGap(18, 18, 18)
                        .addComponent(txtNameTask))
                    .addComponent(scrTasks)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDescriptionTask)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSelectMember)
                                .addGap(18, 18, 18)
                                .addComponent(cmbUsers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNew)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnCancel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSave))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblState)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdbNotStarted)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdbInProgress)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdbCompleted))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAddMember)
                                .addGap(18, 18, 18)
                                .addComponent(btnRemoveMember)
                                .addGap(18, 18, 18)
                                .addComponent(btnListMember)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNameTask)
                    .addComponent(txtNameTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblDescriptionTask)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDescriptionTask, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSelectMember)
                            .addComponent(cmbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddMember)
                            .addComponent(btnRemoveMember)
                            .addComponent(btnListMember))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew)
                            .addComponent(btnEdit)
                            .addComponent(btnDelete)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblState)
                            .addComponent(rdbNotStarted)
                            .addComponent(rdbInProgress)
                            .addComponent(rdbCompleted))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnCancel))))
                .addGap(18, 18, 18)
                .addComponent(scrTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitleTask, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Task task = fieldsToObject();
        boolean isNew = task.getId() == 0;

        try {
            taskController.saveTask(task);

            editingTask = null;
            refreshTable();
            clearFields();
            enableFields(false);
            if (isNew) {
                JOptionPane.showMessageDialog(this, "Task registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Task updated successfully.");
            }
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblTasksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTasksMouseClicked
        if (!tblTasks.isEnabled()) {
            return;
        }
        Task task = getSelectedObjectFromGrid();
        if (task != null) {
            objectToFields(task);
        }
    }//GEN-LAST:event_tblTasksMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        editingTask = null;
        tblTasks.clearSelection();
        clearFields();
        enableFields(true);
        txtNameTask.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Task task = getSelectedObjectFromGrid();
        if (task == null) {
            JOptionPane.showMessageDialog(this, "Select a task in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        editingTask = task;
        objectToFields(task);
        enableFields(true);
        txtNameTask.requestFocus();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Task task = getSelectedObjectFromGrid();
        if (task == null) {
            JOptionPane.showMessageDialog(this, "Select a task in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Delete the task \"" + task.getName() + "\"?\n"
                + "The users will not be deleted, only removed from this task.",
                "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            taskController.deleteTask(task.getId());
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Task deleted successfully.");
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        editingTask = null;
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
            JOptionPane.showMessageDialog(this, "This user is already in the task.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedMembers.add(selected);
        JOptionPane.showMessageDialog(this, selected + " added to the task.");// TODO add your handling code here:
    }//GEN-LAST:event_btnAddMemberActionPerformed

    private void btnRemoveMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMemberActionPerformed
        User selected = (User) cmbUsers.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a user first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedMembers.remove(selected)) {
            JOptionPane.showMessageDialog(this, selected + " removed from the task.");
        } else {
            JOptionPane.showMessageDialog(this, "This user is not in the task.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveMemberActionPerformed

    private void btnListMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListMemberActionPerformed
        if (selectedMembers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members added yet.");
            return;
        }

        StringBuilder text = new StringBuilder("Task members:\n");
        for (int i = 0; i < selectedMembers.size(); i++) {
            text.append("- ").append(selectedMembers.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(this, text.toString());// TODO add your handling code here:
    }//GEN-LAST:event_btnListMemberActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgState;
    private javax.swing.JButton btnAddMember;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnListMember;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemoveMember;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<User> cmbUsers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDescriptionTask;
    private javax.swing.JLabel lblNameTask;
    private javax.swing.JLabel lblSelectMember;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblTitleTask;
    private javax.swing.JRadioButton rdbCompleted;
    private javax.swing.JRadioButton rdbInProgress;
    private javax.swing.JRadioButton rdbNotStarted;
    private javax.swing.JScrollPane scrTasks;
    private javax.swing.JTable tblTasks;
    private javax.swing.JTextField txtDescriptionTask;
    private javax.swing.JTextField txtNameTask;
    // End of variables declaration//GEN-END:variables
}
