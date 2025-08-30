package org.vinn.dao;

import org.vinn.model.Genre;

import java.util.List;

public interface GenreDao {
    void save(Genre genre);
    Genre findById(int id);
    List<Genre> findAll();
    void update(Genre genre);
    void delete(Genre genre);
    void deleteById(int id);
}
