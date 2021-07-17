package com.example.library2.repository;

import com.example.library2.model.Role;
import com.example.library2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCastomRepository {

    Optional<User> findByLogin(String login);

    @Query("select r from Role r")
    List<Role> getAllRolesForUser();

}
