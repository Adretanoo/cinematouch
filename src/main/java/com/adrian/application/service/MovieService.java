package com.adrian.application.service;
import com.adrian.domain.entities.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findRecent(int count);
    Movie save(Movie movie);
    void delete(Long id);
}