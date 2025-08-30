package org.vinn.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.vinn.dao.GenreDao;
import org.vinn.dto.GenreRequestDto;
import org.vinn.mapper.GenreMapper;
import org.vinn.mapper.GenreRowMapper;
import org.vinn.model.Genre;

import javax.sql.DataSource;
import java.util.List;

public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private static final GenreRowMapper genreRowMapper = new GenreRowMapper();

    public GenreDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(GenreRequestDto genreRequestDto) {
        Genre genre = GenreMapper.ToModel(genreRequestDto);
        final String sql  = "INSERT INTO genre (name) VALUES (?)";

        jdbcTemplate.update(
                sql,
                genre.getName()
        );
    }


    @Override
    public Genre findById(int id) {
        String sql = "SELECT id, name FROM genre WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                genreRowMapper,
                id
        );
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT id, name FROM genre";
        return jdbcTemplate.query(
                sql,
                genreRowMapper
        );
    }

    @Override
    public void update(int id, GenreRequestDto genreRequestDto) {
        String sql = "UPDATE genre SET name = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                genreRequestDto.getName(),
                id
        );
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM genre WHERE id = ?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public void delete(Genre genre) {
        String sql = "DELETE FROM genre WHERE id = ? AND name = ?";
        jdbcTemplate.update(
                sql,
                genre.getId(),
                genre.getName()
        );
    }
}
