/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.validation;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.exception.ValidationException;
import com.mycompany.cpoint.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,}$");
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_WEEKLY_HOURS = 1;
    private static final int MAX_WEEKLY_HOURS = 44;

    public void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException(List.of("No user was provided."));
        }

        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(user.getFirstName())) {
            errors.add("Enter the first name.");
        }
        if (ValidationUtils.isBlank(user.getLastName())) {
            errors.add("Enter the last name.");
        }
        if (ValidationUtils.isBlank(user.getEmail())) {
            errors.add("Enter the email.");
        } else if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            errors.add("The email is not valid.");
        }
        if (ValidationUtils.isBlank(user.getUsername())) {
            errors.add("Enter the username.");
        }
        if (ValidationUtils.isBlank(user.getPassword())) {
            errors.add("Enter the password.");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            errors.add("The password must have at least " + MIN_PASSWORD_LENGTH + " characters.");
        }
        if (ValidationUtils.isBlank(user.getDepartment())) {
            errors.add("Select the department.");
        }
        if (ValidationUtils.isBlank(user.getGender())) {
            errors.add("Select the gender.");
        }
        if (user.getRole() == null) {
            errors.add("Select the role.");
        }
        if (user.getWeeklyHours() < MIN_WEEKLY_HOURS || user.getWeeklyHours() > MAX_WEEKLY_HOURS) {
            errors.add("Select the weekly hours (between " + MIN_WEEKLY_HOURS
                    + " and " + MAX_WEEKLY_HOURS + ").");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validatePasswordChange(String currentPassword, String newPassword,
            String confirmation) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (ValidationUtils.isBlank(currentPassword)) {
            errors.add("Enter your current password.");
        }
        if (ValidationUtils.isBlank(newPassword)) {
            errors.add("Enter the new password.");
        } else if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            errors.add("The new password must have at least " + MIN_PASSWORD_LENGTH + " characters.");
        } else if (newPassword.equals(currentPassword)) {
            errors.add("The new password must be different from the current one.");
        }
        if (ValidationUtils.isBlank(confirmation)) {
            errors.add("Confirm the new password.");
        } else if (!ValidationUtils.isBlank(newPassword) && !confirmation.equals(newPassword)) {
            errors.add("The confirmation does not match the new password.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateId(int id) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException(List.of("Select a user in the table first."));
        }
    }
}