package com.example.library2.repository;

import com.example.library2.model.Book;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCastom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> filterBook(String str) {
        Session session = em.unwrap(Session.class);
        Criterion criterion1 = Restrictions.ilike("name", "%" + str + "%");
        Criterion criterion2 = Restrictions.ilike("a.lastName", "%" + str + "%");
        List books = session.createCriteria(Book.class)
                .createAlias("author", "a")
                .add(Restrictions.or(criterion1, criterion2))
                .setFetchMode("author", FetchMode.EAGER)
                .list();
        return books;
    }
}