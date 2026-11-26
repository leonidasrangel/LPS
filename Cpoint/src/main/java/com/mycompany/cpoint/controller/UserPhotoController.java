/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.dao.UserPhotoDAO;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.util.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class UserPhotoController {

    private static final int PHOTO_SIZE = 96;
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    private final UserPhotoDAO userPhotoDAO = new UserPhotoDAO();

    public byte[] loadPhoto(User user) throws DatabaseException {
        return userPhotoDAO.findImageByUserId(user.getId());
    }

    public byte[] savePhoto(User user, File file) throws ValidationException, DatabaseException {
        if (file.length() > MAX_FILE_SIZE) {
            throw new ValidationException(List.of("The image must be at most 5 MB."));
        }

        BufferedImage source;
        try {
            source = ImageIO.read(file);
        } catch (IOException ex) {
            throw new ValidationException(List.of("Could not read the selected file."));
        }
        if (source == null) {
            throw new ValidationException(List.of("The selected file is not a valid image."));
        }

        byte[] image;
        try {
            image = ImageUtils.toRoundPng(source, PHOTO_SIZE);
        } catch (IOException ex) {
            throw new ValidationException(List.of("Could not process the selected image."));
        }

        userPhotoDAO.save(user.getId(), image);
        return image;
    }

    public void removePhoto(User user) throws DatabaseException {
        userPhotoDAO.deleteByUserId(user.getId());
    }
}
