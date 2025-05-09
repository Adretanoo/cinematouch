package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.Movie;
import com.adrian.infrastructure.persistence.dao.MovieDao;

import com.adrian.infrastructure.persistence.exception.DatabaseAccessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;

/**
 * Реалізація MovieDao через AbstractJdbcDao.
 */
@Repository
public class MovieDaoImpl extends AbstractJdbcDao<Movie, Long> implements MovieDao {

    private static final String FIND_RECENT_SQL =
        "SELECT * FROM movie ORDER BY id DESC LIMIT ?";

    @Override
    public List<Movie> findRecent(int count) {
        try (Connection conn = ConnectionHolder.getConnection();
            PreparedStatement ps = conn.prepareStatement(FIND_RECENT_SQL)) {

            ps.setInt(1, count);
            try (ResultSet rs = ps.executeQuery()) {
                List<Movie> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error fetching recent movies", e);
        }
    }

    @Override
    protected String getTableName() {
        return "movie";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "title, duration, genre, rating, description, poster_image_url";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "title = ?, duration = ?, genre = ?, rating = ?, description = ?, poster_image_url = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 6;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Movie m) throws SQLException {
        ps.setString(1, m.getTitle());
        ps.setInt(2, m.getDuration());
        ps.setString(3, m.getGenre());
        ps.setString(4, m.getRating());
        ps.setString(5, m.getDescription());
        ps.setString(6, m.getPosterImageUrl());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Movie m) throws SQLException {
        setInsertParameters(ps, m);
    }

    @Override
    protected Movie mapRow(ResultSet rs) throws SQLException {
        Movie m = new Movie();
        m.setId(rs.getLong("id"));
        m.setTitle(rs.getString("title"));
        m.setDuration(rs.getInt("duration"));
        m.setGenre(rs.getString("genre"));
        m.setRating(rs.getString("rating"));
        m.setDescription(rs.getString("description"));
        m.setPosterImageUrl(rs.getString("poster_image_url"));
        return m;
    }


    @Override
    protected Long getEntityId(Movie m) {
        return m.getId();
    }

    @Override
    protected void setEntityId(Movie m, Long id) {
        m.setId(id);
    }
}
