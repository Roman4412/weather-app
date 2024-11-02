package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepository extends AbstractSessionTransactionManager implements IUserRepository {

    private static final String UNIQUE_VIOLATION_CODE = "23505";

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User save(User user) {
        return executeInTransaction(s -> {
            try {
                s.persist(user);
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

    @Override
    public Optional<User> findBy(Long id) {
        return Optional.ofNullable(executeInTransaction(s -> s.get(User.class, id)));
    }

    @Override
    public Optional<User> findBy(String login) {
        return Optional.ofNullable(executeInTransaction(session -> {
            String query = "SELECT u FROM User u WHERE u.login=:login";
            return session.createQuery(query, User.class).setParameter("login", login).uniqueResult();
        }));
    }

}