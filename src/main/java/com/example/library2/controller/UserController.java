package com.example.library2.controller;


import com.example.library2.model.User;
import com.example.library2.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String USER_TABLE = "users/userTable :: user_list";
    private static final String ERROR_ALERT= "fragments/alert::alert";
    private static final String EDIT_MODAL = "users/modal/editUser";
    private static final String ADD_MODAL = "users/modal/addUser";

    @Autowired
    UserService userService;


    @GetMapping("/addUser")
    public String addUser(Model model) {
        try {
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", new User());
            return ADD_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ADD_MODAL;
        }

    }

    @PostMapping("/saveUser")
    public String saveUser(User user, Model model) {
        try {
            userService.createNewUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "пользователь успешно добавлен");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "ошибка добавления пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    @GetMapping("/edit")
    @Transactional(readOnly = true)
    public String editUser(Long pid, Model model) {
        try {
            User user = userService.getUserById(pid);
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", user);
            return EDIT_MODAL;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EDIT_MODAL;
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(User user, Model model) {
        try {
            userService.updateUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "пользователь успешно изменен");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "ошибка редактирования пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }


    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(Long pid, Model model) {
        try {
            userService.deleteUserById(pid);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "пользователь успешно удален");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "ошибка удаления пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    @GetMapping("/filter")
    public String filter(String s, Model model) {
        List<User> users = new ArrayList<>();
        try {
            if (!StringUtils.isBlank(s)){
                users = userService.filter(s);
            }else{
                users = userService.getAllUsers();
            }
            model.addAttribute("users", users);
            return USER_TABLE;
        } catch (Exception e) {

            model.addAttribute("message", "При работе с пользователями произошла ошибка");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    @GetMapping("/checkLogin")
    public String checkLogin(@RequestParam("login") String login, Model model, HttpServletResponse response){
        if(userService.findByLogin(login).isPresent()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Пользователь с таким логином существует");
            model.addAttribute("alertClass", "alert-danger");
        }
        return ERROR_ALERT;
    }





}