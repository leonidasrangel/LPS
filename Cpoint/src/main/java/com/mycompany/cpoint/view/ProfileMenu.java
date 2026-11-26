/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.view;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.controller.UserPhotoController;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.util.ImageUtils;
import com.mycompany.cpoint.util.UserSession;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProfileMenu extends JPopupMenu {

    private static final int DISPLAY_SIZE = 40;

    private final UserPhotoController photoController = new UserPhotoController();
    private final JFrame owner;
    private final JLabel photoLabel;
    private final Icon defaultIcon;
    private final JMenuItem itemRemovePhoto;

    public ProfileMenu(JFrame owner, JLabel photoLabel) {
        this.owner = owner;
        this.photoLabel = photoLabel;
        this.defaultIcon = photoLabel.getIcon();

        JMenuItem itemChangePhoto = new JMenuItem("Change photo");
        itemChangePhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                changePhoto();
            }
        });
        add(itemChangePhoto);

        itemRemovePhoto = new JMenuItem("Remove photo");
        itemRemovePhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removePhoto();
            }
        });
        add(itemRemovePhoto);

        photoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        photoLabel.setToolTipText("Profile");
        photoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                show(photoLabel, 0, photoLabel.getHeight());
            }
        });

        loadPhoto();
    }

    private void loadPhoto() {
        byte[] image;
        try {
            image = photoController.loadPhoto(UserSession.getCurrentUser());
        } catch (DatabaseException ex) {
            image = null;
        }
        showPhoto(image);
    }

    private void showPhoto(byte[] image) {
        if (image == null) {
            photoLabel.setIcon(defaultIcon);
            itemRemovePhoto.setEnabled(false);
        } else {
            photoLabel.setIcon(ImageUtils.toIcon(image, DISPLAY_SIZE));
            itemRemovePhoto.setEnabled(true);
        }
    }

    private void changePhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a profile photo");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Images (PNG, JPG)", "png", "jpg", "jpeg"));

        int answer = chooser.showOpenDialog(owner);
        if (answer != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            byte[] image = photoController.savePhoto(UserSession.getCurrentUser(), chooser.getSelectedFile());
            showPhoto(image);
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(owner, ex.getMessage(),
                    "Photo not saved", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(owner, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removePhoto() {
        int answer = JOptionPane.showConfirmDialog(owner, "Remove your profile photo?",
                "Remove photo", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            photoController.removePhoto(UserSession.getCurrentUser());
            showPhoto(null);
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(owner, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
