package com.example.library2.service;

import com.example.library2.model.Author;
import com.example.library2.model.Book;
import com.example.library2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional()
    public Book createNewBook(Book book) {
        book.setAuthor(new Author());
        return bookRepository.save(book);
    }

    @Override
    public void saveBook(Book book) {
       bookRepository.save(book);
    }


}
