package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.CategoryDao;
import org.vinn.model.Category;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public void save(Category category) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        }
    }

    @Override
    public List<Category> findAll() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from Category", Category.class)
                    .list();
        }
        // return List.of();
    }

    @Override
    public void delete(Long id) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.delete(category);
            transaction.commit();
        }
    }
}










