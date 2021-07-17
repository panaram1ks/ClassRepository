package com.example.library2.repository;

import com.example.library2.model.Book;

import java.util.List;

public interface BookRepositoryCastom {

    List<Book> filterBook(String str);
}
