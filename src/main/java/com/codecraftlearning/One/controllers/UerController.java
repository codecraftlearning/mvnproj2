package com.codecraftlearning.One.controllers;

import com.codecraftlearning.One.entity.User;
import com.codecraftlearning.One.models.UserRequestModel;
import com.codecraftlearning.One.models.UserResponseModel;
import com.codecraftlearning.One.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/*")
public class UerController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }

        UserRequestModel userRequestModel = objectMapper.readValue(sb.toString(), UserRequestModel.class);
        try {
            res.getWriter().println(userService.createNewUser(userRequestModel));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }

        UserRequestModel userRequestModel = objectMapper.readValue(sb.toString(), UserRequestModel.class);
        try {
            res.getWriter().println(userService.updateUser(userRequestModel));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String pathInfo = req.getPathInfo();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                getAllUsers(res);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (pathInfo.startsWith("/id/")) {
            try {
                Integer id = Integer.parseInt(pathInfo.replace("/id/", ""));
                System.out.println(id);
                getUserById(id, res);
            } catch (NumberFormatException e) {
                System.out.println("Id should be a number");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }

        UserRequestModel userRequestModel = objectMapper.readValue(sb.toString(), UserRequestModel.class);
        try {
            res.getWriter().println(userService.deleteUser(userRequestModel));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllUsers(HttpServletResponse response) throws IOException, SQLException {
        List<User> users = userService.getAllUsers();
        objectMapper.writeValue(response.getWriter(), users);
    }

    private void getUserById(Integer id, HttpServletResponse response) throws IOException, SQLException {
        User user = userService.getUserById(id);
        objectMapper.writeValue(response.getWriter(), user);
    }

}
