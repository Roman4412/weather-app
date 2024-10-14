package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractSessionTransactionManager implements IUserDao {

    private static final String UNIQUE_VIOLATION_CODE = "23505";

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        return executeInTransaction(session -> {
            try {
                session.persist(user);
            } catch (Exception e) {
                Throwable cause = e.getCause();
                if (cause instanceof PSQLException psqlException) {
                    if (UNIQUE_VIOLATION_CODE.equals(psqlException.getSQLState())) {
                        throw new ObjectAlreadyExistException(
                                String.format("The user with the login %s already exists", user.getLogin()), cause);
                    }
                } else {
                    throw e;
                }
            }
            return user;
        });
    }


    //    public Optional<User> findById(Long id) {
    //        Function<Session, Optional<User>> action = session -> Optional.ofNullable(session.find(User.class, id));
    //        return inTransaction(action);

    //    }

}