package org.vinn.dao;

import org.vinn.dto.GenreRequestDto;
import org.vinn.model.Genre;

import java.util.List;

// Repository Layer
public interface GenreDao {
    void save(GenreRequestDto genreRequestDto);
    Genre findById(int id);
    List<Genre> findAll();
    void update(int id, GenreRequestDto genreRequestDto);
    void deleteById(int id);
    void delete(Genre genre);
}
