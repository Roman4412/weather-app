package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepository extends AbstractSessionTransactionManager implements IUserRepository {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User save(User user) {
        return executeInTransaction(s -> {
            try {
                s.persist(user);
            } catch (Exception e) {
                if (e instanceof ConstraintViolationException) {
                    ConstraintViolationException.ConstraintKind kind = ((ConstraintViolationException) e).getKind();
                    if (kind.equals(ConstraintViolationException.ConstraintKind.UNIQUE)) {
                        throw new ObjectAlreadyExistException("The login %s already exists".formatted(user.getLogin()));
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