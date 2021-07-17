package com.example.library2.service;

import com.example.library2.model.Book;
import com.example.library2.model.User;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book createNewBook(Book book);

    void saveBook(Book book);

}
