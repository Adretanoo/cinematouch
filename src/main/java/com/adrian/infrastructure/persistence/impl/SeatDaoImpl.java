package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.Seat;
import com.adrian.infrastructure.persistence.dao.SeatDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation for SeatDao.
 */
@Repository
public class SeatDaoImpl extends AbstractJdbcDao<Seat, Long> implements SeatDao {

    @Override
    protected String getTableName() {
        return "seat";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "hall_number, row, seat_number";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "hall_number = ?, row = ?, seat_number = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 3;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Seat s) throws SQLException {
        ps.setInt(1, s.getHallNumber());
        ps.setString(2, s.getRow());
        ps.setInt(3, s.getSeatNumber());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Seat s) throws SQLException {
        setInsertParameters(ps, s);
    }

    @Override
    protected Seat mapRow(ResultSet rs) throws SQLException {
        Seat s = new Seat();
        s.setId(rs.getLong("id"));
        s.setHallNumber(rs.getInt("hall_number"));
        s.setRow(rs.getString("row"));
        s.setSeatNumber(rs.getInt("seat_number"));
        return s;
    }

    @Override
    protected Long getEntityId(Seat s) {
        return s.getId();
    }

    @Override
    protected void setEntityId(Seat s, Long id) {
        s.setId(id);
    }
}