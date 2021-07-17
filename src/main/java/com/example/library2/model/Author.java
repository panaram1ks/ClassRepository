package com.example.library2.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @OneToMany
    @JoinColumn(name = "book_pid")
    private List<Book> books = new ArrayList<>();

    @Transient
    public String  getLastNameInitials(){
        return String.format("%s %s.%s." , lastName, firstName.substring(0,1), middleName.substring(0,1));
    }

    @Transient
    public String showAllBooksName(){
        if(books != null){
            StringBuilder sb = new StringBuilder();
               books.forEach(book -> sb.append(book.getName() + " ,"));
        }
        return "no books detected";
    }

    public Author( String lastName, String firstName, String middleName, List<Book> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.books = books;
    }

    public Author() {
    }



    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return getLastNameInitials();
    }


    //    public String dateHelper(){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        return dateFormat.format(birthday);
//    }
}
