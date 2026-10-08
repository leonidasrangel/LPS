/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpoint.util;

/**
 *
 * @author Rangel
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class JPAUtil {

    private static final String PERSISTENCE_UNIT = "cpointPU";
    private static final String CONFIG_FILE = "database.properties";

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    public static synchronized EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, loadOverrides());
        }
        return factory.createEntityManager();
    }

    public static synchronized void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    private static Map<String, String> loadOverrides() {
        Properties config = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            config.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read " + CONFIG_FILE
                    + " in the project folder. Copy database.properties.example and fill in the password.", ex);
        }

        String password = config.getProperty("db.password");
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("db.password is missing in " + CONFIG_FILE + ".");
        }

        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.password", password);
        return overrides;
    }
}
