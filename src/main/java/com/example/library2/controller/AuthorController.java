package com.example.library2.controller;

import com.example.library2.model.Author;
import com.example.library2.model.Book;
import com.example.library2.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/references")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String getAll(Model model) {
        List<Author> lib = authorService.getAllAuthor();
        model.addAttribute("authors", lib);
        return "references/author/author";
    }

    @GetMapping("/addAuthor")
    public String showFormAuthor(Model model){
        model.addAttribute("author", new Author());
        return "references/author/modal/addAuthor";
    }


}
