package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.Session;
import com.adrian.infrastructure.persistence.dao.SessionDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation for SessionDao.
 */
@Repository
public class SessionDaoImpl extends AbstractJdbcDao<Session, Long> implements SessionDao {

    @Override
    protected String getTableName() {
        return "session";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "movie_id, start_time, hall_number";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "movie_id = ?, start_time = ?, hall_number = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 3;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Session s) throws SQLException {
        ps.setLong(1, s.getMovieId());
        ps.setTimestamp(2, s.getStartTime());
        ps.setInt(3, s.getHallNumber());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Session s) throws SQLException {
        setInsertParameters(ps, s);
    }

    @Override
    protected Session mapRow(ResultSet rs) throws SQLException {
        Session s = new Session();
        s.setId(rs.getLong("id"));
        s.setMovieId(rs.getLong("movie_id"));
        s.setStartTime(rs.getTimestamp("start_time"));
        s.setHallNumber(rs.getInt("hall_number"));
        return s;
    }

    @Override
    protected Long getEntityId(Session s) {
        return s.getId();
    }

    @Override
    protected void setEntityId(Session s, Long id) {
        s.setId(id);
    }
}