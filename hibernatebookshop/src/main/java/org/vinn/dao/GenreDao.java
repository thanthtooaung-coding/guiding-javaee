package org.vinn.dao;

import org.vinn.model.Genre;

import java.util.List;

public interface GenreDao {
    void save(Genre genre) throws Exception;
    Genre findById(int id) throws Exception;
    List<Genre> findAll() throws Exception;
    void update(int id, Genre genre) throws Exception;
    void deleteById(int id) throws Exception;
    void delete(Genre genre) throws Exception;
}
