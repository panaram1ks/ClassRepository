package com.example.library2.controller;

import com.example.library2.model.Author;
import com.example.library2.model.Book;
import com.example.library2.repository.AuthorRepository;
import com.example.library2.repository.BookRepository;
import com.example.library2.service.BookService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/references")
public class BookController {

    private static final String ADD_MODAL_BOOK = "references/books/modal/addBook";
    private static final String BOOK_LIST = "references/books/bookTable :: book_list";
    private static final String EDIT_BOOK = "references/books/modal/editBook";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/books")
    public String showBooks(Model model) {
        List<Book> lib = bookRepository.findAll();
        model.addAttribute("books", lib);
        return "references/books/book";
    }

    @GetMapping("/addBook")
    public String addBook(Model model) {
        try {
            List<Author> authorList = authorRepository.findAll();
            model.addAttribute("author", new Author());
            model.addAttribute("authorList", authorList);
            model.addAttribute("book", new Book());
            return ADD_MODAL_BOOK;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ADD_MODAL_BOOK;
        }
    }

    @PostMapping(value = "/createBook", consumes = {"multipart/form-data"})
    public String createBook(@RequestParam("imgCover") MultipartFile imgCover, Book book, Model model) {
        System.out.println(book);
        System.out.println(imgCover);
        try {
            book.setPhoto(imgCover.getBytes());
            bookRepository.save(book);
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "книга успешно добавлена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_LIST;
        } catch (Exception e) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "ошибка добавления книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_LIST;
        }
    }

    @GetMapping("/deleteBook")
    public String deleteBook(Long pid, Model model) {
        try {
            bookRepository.deleteById(pid);
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "книга успешно удалена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_LIST;
        } catch (Exception e) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "ошибка удаления книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_LIST;
        }
    }

    @GetMapping("/updateBook")
    public String updateBook(Long pid, Model model) {
        try {
            bookService.saveBook(bookRepository.getOne(pid));
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "книга успешно отредактирована");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_LIST;
        } catch (Exception e) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "ошибка при редактировании книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_LIST;
        }
    }

    @GetMapping("/editBook")
    @Transactional(readOnly = true)
    public String editBook(Long pid, Model model) {
        try {
            Book book = bookRepository.getOne(pid);
            //byte[] imageBook = book.getPhoto();
            String str = new SimpleDateFormat("yyyy-MM-dd").format(book.getDateUpdate());
            List<Author> authorList = authorRepository.findAll();
            model.addAttribute("photo", new File(book.getPhoto().toString(), "image/jpg"));

            // model.addAttribute("imageBook", imageBook);
            model.addAttribute("updateDate", str);
            model.addAttribute("authorList", authorList);
            model.addAttribute("book", book);
            return EDIT_BOOK;
        } catch (Exception e) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "ошибка при редактировании книги");
            model.addAttribute("alertClass", "alert-danger");
            return EDIT_BOOK;
        }
    }

    @PostMapping("/saveBook")
    public String saveBook(Book book, Model model) {
        try {
            bookRepository.save(book);
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "книга успешно изменена");
            model.addAttribute("alertClass", "alert-success");
            return BOOK_LIST;
        } catch (Exception e) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("message", "ошибка редактирования книги");
            model.addAttribute("alertClass", "alert-danger");
            return BOOK_LIST;
        }
    }

    @GetMapping("/filter")
    public String filter(String str, Model model) {
        List<Book> books = new ArrayList<>();
        if (!StringUtils.isBlank(str)) {
            books = bookRepository.filterBook(str);
        } else {
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        return BOOK_LIST;
    }


    @RequestMapping(value = "/image",
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> showImage(Long pid) {

        byte[] imageContent = bookRepository.getOne(pid).getPhoto();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
    }

}