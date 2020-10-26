package J3_L2_L3_hw_semenov.service;

import J3_L2_L3_hw_semenov.inter.DBService;

import java.sql.*;

public class DBServiceImpl implements DBService {

    public static Connection getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/geekbrains_1?useUnicode=true&serverTimezone=UTC&useSSL=false", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public UserEntity findUser(String login) {

        Connection connection = null;
        UserEntity user = new UserEntity();

        try {
            connection = getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = \"" + login + "\"");
            resultSet.next();

            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return user;
    }

    @Override
    public boolean add(UserEntity user) {
        Connection connection = null;
        try {
            connection = getInstance();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?,?,?)");
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during DB-query");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void updateUserName(UserEntity user, String newName) {
        Connection connection = null;
        try {
            connection = getInstance();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET name = ? WHERE id = ?");
            statement.setString(1, newName);
            statement.setInt(2, user.getId());
            statement.execute();
            user.setName(newName);
        } catch (SQLException e) {
            throw new RuntimeException("SWW during update nick");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}