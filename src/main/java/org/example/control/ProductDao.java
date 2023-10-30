package org.example.control;

import org.example.logic.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ProductDao {
    private SessionFactory sessionFactory;

    public ProductDao() {
        this.sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public void performTransaction(Consumer<Session> operation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            operation.accept(session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        performTransaction(session -> {
            session.save(product);
        });
    }

    public void updateProduct(Product product) {
        performTransaction(session -> {
            session.update(product);
        });
    }

    public boolean existProduct(Product product) {
        AtomicBoolean exists = new AtomicBoolean(false);
        performTransaction(session -> {
            Product existingProduct = session.get(Product.class, product.getCode());
            exists.set(existingProduct != null);
        });
        return exists.get();
    }
}

