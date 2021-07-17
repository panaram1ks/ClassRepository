package com.example.library2.controller;

import com.example.library2.model.User;
import com.example.library2.repository.BookRepository;
import com.example.library2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NavigationController {

    UserServiceImpl userService;
    BookRepository bookRepository;

    @Autowired
    public NavigationController(UserServiceImpl userService, BookRepository bookRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = {"/", "/users"}, method = RequestMethod.GET)
    //   @GetMapping("/home")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String users(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "users/user";
    }


    @GetMapping("/references")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    public String references() {
        return "references/references";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('USER')")
    public String getBooksFromUser(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

}
