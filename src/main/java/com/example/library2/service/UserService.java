package com.example.library2.service;


import com.example.library2.model.Role;
import com.example.library2.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    List<Role> getAllRoles();

    User createNewUser(User user);

    User updateUser(User user);

    User getUserById(Long id);

    void deleteUserById(Long pid);

    List<User> filter(String s);

    Optional<User> findByLogin(String login);
}
