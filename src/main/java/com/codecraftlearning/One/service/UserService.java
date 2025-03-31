package com.codecraftlearning.One.service;

import com.codecraftlearning.One.entity.User;
import com.codecraftlearning.One.models.UserRequestModel;
import com.codecraftlearning.One.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    public List<User> getAllUsers() throws SQLException {
        return UserRepository.findAll();
    }

    public User getUserById(Integer id) throws SQLException {
        return UserRepository.findById(id);
    }

    public String createNewUser(UserRequestModel model) throws SQLException {
        if (UserRepository.createUser(model)) {
            return "User Created Successfully";
        }
        return "Something went wrong, Please try again!!";
    }

    public String updateUser(UserRequestModel model) throws SQLException {
        if (model.getId() == null) {
            return "User Id is mandatory";
        }

        User user = getUserById(model.getId());
        if (user != null) {
            if (model.getName() != null && !model.getName().isEmpty()) {
                user.setName(model.getName());
            }
            if (model.getEmail() != null && !model.getEmail().isEmpty()) {
                user.setEmail(model.getEmail());
            }
            UserRepository.updateUser(user);
            return "User Updated Successfully";
        }

        return "Something went wrong, Please try again!!";
    }

    public String deleteUser(UserRequestModel model) throws SQLException {
        if (model.getId() == null) {
            return "User Id is mandatory";
        }

        User user = getUserById(model.getId());
        if (user != null) {
            UserRepository.deleteUser(model.getId());
            return "User Updated Successfully";
        }

        return "Something went wrong, Please try again!!";
    }
}
