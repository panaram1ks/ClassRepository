package com.example.library2.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name="author_pid", nullable=false)
    private Author author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdate;

    @Column
    private Integer totalOfSheets;

    @Column(nullable = false)
    private Integer totalOfBook = 0;

    @Column(nullable = false)
    private Integer availableQuantity  = 0;

    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;

    public Book(Long pid, String name, Genre genre, Author author,
                Date dateUpdate, Integer totalOfSheets, Integer totalOfBook,
                Integer availableQuantity, byte[] photo) {
        this.pid = pid;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.dateUpdate = dateUpdate;
        this.totalOfSheets = totalOfSheets;
        this.totalOfBook = totalOfBook;
        this.availableQuantity = availableQuantity;
        this.photo = photo;
    }

    public Book() {
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDateString(){
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(this.dateUpdate);
        return dateString;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Integer getTotalOfSheets() {
        return totalOfSheets;
    }

    public void setTotalOfSheets(Integer totalOfSheets) {
        this.totalOfSheets = totalOfSheets;
    }

    public Integer getTotalOfBook() {
        return totalOfBook;
    }

    public void setTotalOfBook(Integer totalOfBook) {
        this.totalOfBook = totalOfBook;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                ", dateUpdate=" + dateUpdate +
                ", totalOfSheets=" + totalOfSheets +
                ", totalOfBook=" + totalOfBook +
                ", availableQuantity=" + availableQuantity +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
