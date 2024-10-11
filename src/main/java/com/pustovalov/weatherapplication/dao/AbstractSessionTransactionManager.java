package com.pustovalov.weatherapplication.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractSessionTransactionManager {

    protected final SessionFactory sessionFactory;

    public <R> R executeInTransaction(Function<Session, R> action) {
        Session session = null;
        Transaction tx = null;
        R result;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            result = action.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
}
