package com.example.library2.repository;

import com.example.library2.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserCastomRepository {

   @PersistenceContext
   private EntityManager em;


    @Override
    public List<User> filterUser(String s) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(User.class);
        criteria.createAlias("roles", "role");
        Disjunction disjunction = Restrictions.or(
                Restrictions.ilike("login", s, MatchMode.ANYWHERE),
                Restrictions.ilike("firstName", s, MatchMode.ANYWHERE),
                Restrictions.ilike("middleName", s, MatchMode.ANYWHERE),
                Restrictions.ilike("lastName", s, MatchMode.ANYWHERE),
                Restrictions.ilike("role.pid", s, MatchMode.ANYWHERE)
        );
        criteria.add(disjunction);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}
