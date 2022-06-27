package com.example.javafx_dakar.model;

import java.sql.*;
import java.util.ArrayList;

public class DakarDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/dakar";

    // Insertina irasa i DB (team name, name surname, sponsors, racing cars ir members).
    public static void insert(Dakar dakar) {
        String query = "INSERT INTO teams (team_name, name_surname, sponsors, racing_cars, members, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, dakar.getTeamName());
            preparedStatement.setString(2, dakar.getNameSurname());
            preparedStatement.setString(3, dakar.getSponsors());
            preparedStatement.setString(4, dakar.getRacingCars());
            preparedStatement.setInt(5, dakar.getMembers());
            preparedStatement.setInt(6, dakar.getUserId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Atspausdina visus irasus esancius 'teams' lenteleje
    public static ArrayList<Dakar> printAll() {
        String query = "SELECT * FROM teams";
        ArrayList<Dakar> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new Dakar(
                        resultSet.getInt("id"),
                        resultSet.getString("team_name"),
                        resultSet.getString("name_surname"),
                        resultSet.getString("sponsors"),
                        resultSet.getString("racing_cars"),
                        resultSet.getInt("members")
                ));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            return list;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    // atspausdina irasus is 'teams' lenteles, kai yra nurodomas team name.
    public static ArrayList<Dakar> printByTeamName(String teamName) {
        String query = "SELECT * FROM teams WHERE team_name LIKE '%" + teamName + "%';";
        ArrayList<Dakar> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new Dakar(
                        resultSet.getInt("id"),
                        resultSet.getString("team_name"),
                        resultSet.getString("name_surname"),
                        resultSet.getString("sponsors"),
                        resultSet.getString("racing_cars"),
                        resultSet.getInt("members")
                ));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // atspausdina irasus is 'teams' lenteles, kai yra nurodomas name surname.
    public static ArrayList<Dakar> printByNameSurname(String nameSurname) {
        String query = "SELECT * FROM teams WHERE name_surname LIKE '%" + nameSurname + "%';";
        ArrayList<Dakar> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(new Dakar(
                        resultSet.getInt("id"),
                        resultSet.getString("team_name"),
                        resultSet.getString("name_surname"),
                        resultSet.getString("sponsors"),
                        resultSet.getString("racing_cars"),
                        resultSet.getInt("members")
                ));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // atspausdina irasus is 'teams' lenteles, kai yra nurodomas team name arba name surname
    public static ArrayList<Dakar> printByTeamNameAndNameSurname(String teamName, String nameSurname) {
        String query = "SELECT * FROM teams WHERE team_name LIKE '%" + teamName + "%' OR name_surname LIKE '%" + nameSurname + "%';";
        ArrayList<Dakar> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new Dakar(
                        resultSet.getInt("id"),
                        resultSet.getString("team_name"),
                        resultSet.getString("name_surname"),
                        resultSet.getString("sponsors"),
                        resultSet.getString("racing_cars"),
                        resultSet.getInt("members")
                ));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // atnaujina irasus esancius 'teams' lenteleje, kai yra nurodomas id
    public static void update(Dakar dakar) {
        String query = "UPDATE teams SET team_name = ?, name_surname = ?, sponsors = ?, racing_cars = ?, members = ? WHERE id = ?;";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, dakar.getTeamName());
            preparedStatement.setString(2, dakar.getNameSurname());
            preparedStatement.setString(3, dakar.getSponsors());
            preparedStatement.setString(4, dakar.getRacingCars());
            preparedStatement.setInt(5, dakar.getMembers());
            preparedStatement.setInt(6, dakar.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // pasalina irasus esancius 'teams' lenteleje, kai yra nurodomas id
    public static void deleteById(int id) {
        String query = "DELETE FROM teams WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // suranda irasus esancius 'teams' lenteleje, kai yra nurodomas id
    public static int searchById(int id) {
        String query = "SELECT * FROM teams WHERE id = ?";

        int number = 0;
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt("id");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return number;
    }
}
