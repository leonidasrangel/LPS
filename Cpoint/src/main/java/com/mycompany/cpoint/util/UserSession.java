/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.util;

/**
 *
 * @author Rangel
 */
import com.mycompany.cpoint.model.Role;
import com.mycompany.cpoint.model.User;

public final class UserSession {

    private static User currentUser;

    private UserSession() {
    }

    public static void start(User user) {
        currentUser = user;
    }

    public static void end() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return isLoggedIn() && currentUser.getRole() == Role.ADMIN;
    }
}
