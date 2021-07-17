package com.example.library2.service;

import com.example.library2.model.Role;
import com.example.library2.model.User;
import com.example.library2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return userRepository.getAllRolesForUser();
    }

    @Override
    @Transactional()
    public User createNewUser(User user) {
        //Hibernate.initialize(user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long pid) {
        userRepository.deleteById(pid);
    }


    @Override
    public List<User> filter(String s) {
        return userRepository.filterUser(s);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }


}
