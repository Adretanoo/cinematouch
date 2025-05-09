package com.adrian.application.viewmodel;

import com.adrian.application.service.MovieService;
import com.adrian.domain.entities.Movie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MoviesViewModel {
    private final MovieService movieService;
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();
    private final ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    public MoviesViewModel(MovieService movieService) {
        this.movieService = movieService;
        loadMovies();
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public ObjectProperty<Movie> selectedMovieProperty() {
        return selectedMovie;
    }

    public Movie getSelectedMovie() {
        return selectedMovie.get();
    }

    public void setSelectedMovie(Movie movie) {
        selectedMovie.set(movie);
    }

    public void loadMovies() {
        List<Movie> all = movieService.findAll();
        movies.setAll(all);
    }

    public void addMovie(Movie m) {
        Movie saved = movieService.save(m);
        loadMovies();
        setSelectedMovie(saved);
    }

    public void updateMovie(Movie m) {
        movieService.save(m);
        loadMovies();
        setSelectedMovie(m);
    }

    public void deleteSelected() {
        Movie m = selectedMovie.get();
        if (m != null) {
            movieService.delete(m.getId());
            loadMovies();
            setSelectedMovie(null);
        }
    }
}
