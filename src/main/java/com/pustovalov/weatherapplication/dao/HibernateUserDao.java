package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public class HibernateUserDao extends AbstractSessionTransactionManager implements UserDao {
    public HibernateUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        sessionFactory.inTransaction((session) -> session.persist(user));
        return user;
    }

    @Override
    public boolean isExist(User user) {
        String query = "SELECT COUNT(*) FROM User WHERE login = :login";
        Function<Session, Long> action = session -> (session.createQuery(query, Long.class).setParameter("login",
                user.getLogin()).uniqueResult());
        return executeInTransaction(action) == 1;
    }


    //    public Optional<User> findById(Long id) {
    //        Function<Session, Optional<User>> action = session -> Optional.ofNullable(session.find(User.class, id));
    //        return inTransaction(action);

    //    }

}