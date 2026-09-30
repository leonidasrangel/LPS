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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserController {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final UserDAO userDAO = new UserDAO();

    public void registerUser(String firstName, String lastName, String email, String username,
                              String password, String confirmPassword, String department,
                              String gender) throws ValidationException, SQLException {

        List<String> errors = new ArrayList<>();

        if (isBlank(firstName)) {
            errors.add("Informe o primeiro nome.");
        }
        if (isBlank(lastName)) {
            errors.add("Informe o sobrenome.");
        }
        if (isBlank(email)) {
            errors.add("Informe o email.");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.add("O email informado não é válido.");
        }
        if (isBlank(username)) {
            errors.add("Informe o username.");
        }
        if (isBlank(password)) {
            errors.add("Informe a senha.");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("A senha deve ter pelo menos " + MIN_PASSWORD_LENGTH + " caracteres.");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("As senhas não coincidem.");
        }
        if (isBlank(department)) {
            errors.add("Selecione o departamento.");
        }
        if (isBlank(gender)) {
            errors.add("Selecione o gênero.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        User user = new User(firstName, lastName, email, username, password, department, gender);
        userDAO.insert(user);
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
