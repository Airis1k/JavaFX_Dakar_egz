package com.example.javafx_dakar.model;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    // Duomenu bazes(DB) prisijungimo 'linkas'.
    private static final String URL = "jdbc:mysql://localhost:3306/dakar";

    // insertina irasa i DB nurodant values
    public static void insert(User user) {
        String query = "INSERT INTO users (username, password, email, admin) VALUES (?, ?, ?, ?);";

        try {
            // Vykdome prisijungima prie DB ir atliekame uzklausas.
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Siekiant isvengti SQL injekciju, kiekviena laukeli aprasome atskirai - ignoruojami specialus simboliai.
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.getAdmin());

            // Naujo iraso sukurimui, esamo iraso redagavimui ir trynimui naudojame executeUpdate metoda.
            // Esamo iraso paieskai naudojame executeQuery metoda.
            preparedStatement.executeUpdate();

            // Ivykdzius uzklausa, gera praktika yra uzdaryti prisijungimus.
            preparedStatement.close();
            connection.close();

            System.out.println("Irasa pavyko sukurti sekmingai.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko sukurti iraso.");
        }
    }

    // selectina slaptazodi, kai nurodomas vartotojo vardas
    public static String getBCryptPassword(String username) {
        String query = "SELECT password FROM users WHERE username LIKE ?";

        String bCryptPassword = "";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bCryptPassword = resultSet.getString("password");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bCryptPassword;
    }

    // atspausdina irasus, kai nurodomas vardas
    public static String searchByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        String username2 = "";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                username2 = resultSet.getBoolean("admin") ? "ADMINISTRATORIUS" : "VARTOTOJAS";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username2;
    }

    // suranda vartotojo id, kai yra nurodomas vardas
    public static int searchByUsernameReturnId(String username) {
        String query = "SELECT users.id FROM users WHERE username = ?";
        int username2 = 0;

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                username2 = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username2;
    }
}
