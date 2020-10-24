package J3_L2_hw_semenov.service;

import J3_L2_hw_semenov.inter.DBService;

import java.sql.*;
import java.util.ArrayList;

public class DBServiceImpl implements DBService {

    public static Connection getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/geekbrains_1", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();

        } return null;
    }
    @Override
    public ArrayList<UserEntity> findAll() {

        ArrayList<UserEntity> users = new ArrayList<UserEntity>();
        Connection connection = null;

        try {
            connection = getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
                users.add(user);
            }

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
        return users;
    }

    @Override
    public UserEntity findUser(String login) {

        Connection connection = null;
        UserEntity user = new UserEntity();

        try {
            connection = getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = " + login);
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
    public boolean updateUserName(String login, String newNick) {
        Connection connection = null;
        try {
            connection = getInstance();
            PreparedStatement statement = connection.prepareStatement("UPDATE users set nickname=" + newNick + "WHERE nickname =" + login);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("SWW Обновление ника");
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