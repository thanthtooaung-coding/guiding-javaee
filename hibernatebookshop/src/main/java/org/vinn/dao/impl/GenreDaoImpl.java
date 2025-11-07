package org.vinn.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vinn.config.HibernateUtil;
import org.vinn.dao.GenreDao;
import org.vinn.model.Genre;

import java.util.List;

public class GenreDaoImpl implements GenreDao {
    @Override
    public void save(Genre genre) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction(); // Start

            session.save(genre); // Save Data

            transaction.commit(); // Transaction Complete

        }
    }

    @Override
    public Genre findById(int id) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(
                    Genre.class,
                    id
            );
        }
    }

    @Override
    public List<Genre> findAll() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Genre",
                    Genre.class
            ).list();
        }
    }

    @Override
    public void update(int id, Genre genre) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction(); // Start

            session.update(genre); // Update Data

            transaction.commit(); // Transaction Complete

        }
    }

    @Override
    public void deleteById(int id) throws Exception {
        Transaction transaction = null;
        Genre genre = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            genre = session.get(
                    Genre.class,
                    id
            );

            transaction = session.beginTransaction(); // Start

            if(genre != null) {
                session.delete(genre); // Delete Data

                transaction.commit(); // Transaction Complete
            }
        }
    }

    @Override
    public void delete(Genre genre) throws Exception {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction(); // Start

            session.delete(genre); // Delete Data

            transaction.commit(); // Transaction Complete

        }
    }
}
