package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.ProductDao;
import org.vinn.model.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void save(Product product) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        }
    }

    @Override
    public List<Product> findAll() throws Exception {
       try(Session session = HibernateUtil.getSessionFactory().openSession()){
           return session
                   .createQuery("from Product", Product.class)
                   .list();
       }
    }

    @Override
    public Product findById(Long id) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session
                    .get(Product.class, id);
        }
    }

    @Override
    public void update(Product product) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            transaction.commit();
        }
    }
}
