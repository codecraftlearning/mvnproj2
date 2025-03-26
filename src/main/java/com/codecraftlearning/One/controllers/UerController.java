package com.codecraftlearning.One.controllers;

import com.codecraftlearning.One.models.UserRequestModel;
import com.codecraftlearning.One.models.UserResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user")
public class UerController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        UserRequestModel requestModel = objectMapper.readValue(sb.toString(), UserRequestModel.class);

        UserResponseModel userResponseModel = new UserResponseModel("a", "b");

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(res.getWriter(), userResponseModel);
    }
}
