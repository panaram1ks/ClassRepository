package com.example.library2.service;

import com.example.library2.model.Author;
import com.example.library2.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }
}
