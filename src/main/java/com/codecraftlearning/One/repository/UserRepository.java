package com.codecraftlearning.One.repository;

import com.codecraftlearning.One.entity.User;
import com.codecraftlearning.One.models.UserRequestModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String url = "jdbc:mysql://localhost:3306/code-craft";
    private static final String userName = "root";
    private static final String password = "root";
    private static Connection connection;

    private UserRepository() {}

    private static void initializeConnection() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> findAll()  {
        initializeConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "select * from user";
            ResultSet rs = statement.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setCreatedAt(rs.getTimestamp("created_at").toString());
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return new ArrayList<User>();
    }

    public static User findById(Integer id) {
        initializeConnection();
        try {
            String query = "select * from user where id = %d";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(String.format(query, id));
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setCreatedAt(rs.getTimestamp("created_at").toString());
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public static boolean createUser(UserRequestModel model) {
        initializeConnection();
        try {
            String query = "insert into user (name, email) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, model.getName());
            preparedStatement.setString(2, model.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    public static boolean updateUser(User user) {
        initializeConnection();
        try {
            String query = "update user set name = ?, email = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    public static boolean deleteUser(Integer id) {
        initializeConnection();
        try {
            String query = "delete from user where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }
}
