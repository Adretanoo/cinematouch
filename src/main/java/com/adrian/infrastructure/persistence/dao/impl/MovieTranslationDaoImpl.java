package com.adrian.infrastructure.persistence.dao.impl;

import com.adrian.domain.entities.MovieTranslation;
import com.adrian.infrastructure.persistence.dao.MovieTranslationDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieTranslationDaoImpl
    extends AbstractJdbcDao<MovieTranslation, Long>
    implements MovieTranslationDao {

    @Override
    protected String getTableName() {
        return "movie_translation";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "movie_id, language_code, translated_title, translated_description";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "movie_id = ?, language_code = ?, translated_title = ?, translated_description = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 4;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, MovieTranslation t) throws SQLException {
        ps.setLong(1, t.getMovieId());
        ps.setString(2, t.getLanguageCode());
        ps.setString(3, t.getTranslatedTitle());
        ps.setString(4, t.getTranslatedDescription());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, MovieTranslation t) throws SQLException {
        setInsertParameters(ps, t);
    }

    @Override
    protected MovieTranslation mapRow(ResultSet rs) throws SQLException {
        MovieTranslation t = new MovieTranslation();
        t.setId(rs.getLong("id"));
        t.setMovieId(rs.getLong("movie_id"));
        t.setLanguageCode(rs.getString("language_code"));
        t.setTranslatedTitle(rs.getString("translated_title"));
        t.setTranslatedDescription(rs.getString("translated_description"));
        return t;
    }

    @Override
    protected Long getEntityId(MovieTranslation t) {
        return t.getId();
    }

    @Override
    protected void setEntityId(MovieTranslation t, Long id) {
        t.setId(id);
    }
}
