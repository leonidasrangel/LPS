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
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.User;
import com.mycompany.cpoint.validation.UserValidator;
import com.mycompany.cpoint.exception.DatabaseException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final UserDAO userDAO = new UserDAO();
    private final UserValidator userValidator = new UserValidator();

    public void saveUser(User user) throws ValidationException, DatabaseException {
        userValidator.validate(user);
        checkUniqueness(user);

        if (user.getId() == 0) {
            userDAO.insert(user);
        } else {
            userDAO.update(user);
        }
    }

    public void deleteUser(int id) throws ValidationException, DatabaseException {
        userValidator.validateId(id);

        if (userDAO.isTeamMember(id)) {
            throw new ValidationException(List.of(
                "This user is a member of a team and cannot be deleted. "
                + "Remove them from the team first."));
        }

        userDAO.delete(id);
    }

    public List<User> listUsers() throws DatabaseException {
        return userDAO.findAll();
    }

    private void checkUniqueness(User user) throws ValidationException, DatabaseException {
        List<String> errors = new ArrayList<>();

        if (userDAO.existsByEmail(user.getEmail(), user.getId())) {
            errors.add("This email is already in use.");
        }
        if (userDAO.existsByUsername(user.getUsername(), user.getId())) {
            errors.add("This username is already in use.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
