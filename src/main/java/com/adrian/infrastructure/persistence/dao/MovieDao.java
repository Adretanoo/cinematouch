
package com.adrian.infrastructure.persistence.dao;

import com.adrian.domain.entities.Movie;
import java.util.List;

public interface MovieDao extends GenericDao<Movie, Long> {
    List<Movie> findRecent(int count);
}
