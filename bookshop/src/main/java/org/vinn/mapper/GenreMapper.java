package org.vinn.mapper;

import org.vinn.dto.GenreRequestDto;
import org.vinn.dto.GenreResponseDto;
import org.vinn.model.Genre;

public class GenreMapper {
    public static GenreResponseDto ToResponseDto(Genre genre) {
        return new GenreResponseDto(
                genre.getId(),
                genre.getName()
        );
    }

    public static Genre ToModel(GenreRequestDto genreRequestDto) {
        return new Genre(
                genreRequestDto.getName()
        );
    }
}
