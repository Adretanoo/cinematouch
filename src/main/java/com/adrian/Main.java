package com.adrian;
import com.adrian.domain.entities.Movie;
import com.adrian.infrastructure.persistence.PersistenceContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext("com.adrian.infrastructure.persistence");
        PersistenceContext uow = ctx.getBean(PersistenceContext.class);

        Movie movie = new Movie();
        movie.setTitle("UOW Test");
        movie.setDuration(99);
        movie.setGenre("Test");
        movie.setRating("G");
        movie.setDescription("...");
        movie.setPosterImageUrl(null);

        uow.registerNew(movie);
        uow.commit();
        System.out.println("Inserted Movie ID = " + movie.getId());

        ctx.close();
    }
}
