package com.example.library2.controller;

import com.example.library2.model.Author;
import com.example.library2.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/login")
    private String getLoginPage(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("message", "Неверный логин или пароль");
        model.addAttribute("alertClass", "alert-danger");
        return "login";
    }

    @PostMapping("/createAuthor")
    public String createAuthor(Author author, Model model){
        try{
            authorRepository.save(author);
            model.addAttribute("author", authorRepository.findAll());
            model.addAttribute("message", "author успешно изменена");
            model.addAttribute("alertClass", "alert-success");
            return "references/author/author";
        } catch (Exception e) {
            model.addAttribute("author", authorRepository.findAll());
            model.addAttribute("message", "ошибка редактирования книги");
            model.addAttribute("alertClass", "alert-danger");
            return "references/author/author";
        }
    }

}