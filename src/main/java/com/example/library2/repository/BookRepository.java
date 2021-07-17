package com.example.library2.repository;

import com.example.library2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCastom {
}
