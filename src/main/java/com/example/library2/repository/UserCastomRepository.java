package com.example.library2.repository;

import com.example.library2.model.User;

import java.util.List;

public interface UserCastomRepository {

    List<User> filterUser(String s);
}
