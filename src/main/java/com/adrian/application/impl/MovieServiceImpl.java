package com.adrian.application.impl;

import com.adrian.application.service.MovieService;
import com.adrian.domain.entities.Movie;
import com.adrian.infrastructure.persistence.PersistenceContext;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private final PersistenceContext ctx;

    public MovieServiceImpl(PersistenceContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public List<Movie> findAll() {
        return ctx.getMovieDao().findAll();
    }

    @Override
    public List<Movie> findRecent(int count) {
        return ctx.getMovieDao().findRecent(count);
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            ctx.registerNew(movie);
        } else {
            ctx.registerDirty(movie.getId(), movie);
        }
        ctx.commit();
        return movie;
    }

    @Override
    public void delete(Long id) {
        Movie stub = new Movie();
        stub.setId(id);
        ctx.registerDeleted(stub);
        ctx.commit();
    }
}
