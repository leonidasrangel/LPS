/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.controller;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.dao.UserDAO;
import com.mycompany.cpoint.exception.AuthenticationException;
import com.mycompany.cpoint.exception.DatabaseException;
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.util.UserSession;
import com.mycompany.cpoint.validation.ValidationUtils;
import java.util.ArrayList;

import java.util.List;

public class AuthController {

    private static final String INVALID_CREDENTIALS = "Invalid email/username or password.";

    private final UserDAO userDAO = new UserDAO();

    public User login(String login, String password)
            throws ValidationException, AuthenticationException, DatabaseException {
        UserSession.end();
        validateInput(login, password);

        String typedLogin = login.trim();
        User user = userDAO.findByEmail(typedLogin);
        if (user == null) {
            user = userDAO.findByUsername(typedLogin);
        }

        if (user == null || !passwordMatches(password, user.getPassword())) {
            throw new AuthenticationException(INVALID_CREDENTIALS);
        }

        UserSession.start(user);
        return user;
    }

    public void logout() {
        UserSession.end();
    }

    private void validateInput(String login, String password) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(login)) {
            errors.add("Enter your email or username.");
        }
        if (ValidationUtils.isBlank(password)) {
            errors.add("Enter your password.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private boolean passwordMatches(String typedPassword, String storedPassword) {
        return storedPassword != null && storedPassword.equals(typedPassword);
    }
}
