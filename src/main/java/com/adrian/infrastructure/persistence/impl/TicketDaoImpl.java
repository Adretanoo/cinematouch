package com.adrian.infrastructure.persistence.impl;


import com.adrian.domain.entities.Ticket;
import com.adrian.infrastructure.persistence.dao.TicketDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of TicketDao.
 */
@Repository
public class TicketDaoImpl extends AbstractJdbcDao<Ticket, Long> implements TicketDao {

    @Override
    protected String getTableName() {
        return "ticket";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "session_id, seat_id, price, printed";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "session_id = ?, seat_id = ?, price = ?, printed = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 4;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Ticket t) throws SQLException {
        ps.setLong(1, t.getSessionId());
        ps.setLong(2, t.getSeatId());
        ps.setBigDecimal(3, t.getPrice());
        ps.setBoolean(4, t.getPrinted());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Ticket t) throws SQLException {
        setInsertParameters(ps, t);
    }

    @Override
    protected Ticket mapRow(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getLong("id"));
        t.setSessionId(rs.getLong("session_id"));
        t.setSeatId(rs.getLong("seat_id"));
        t.setPrice(rs.getBigDecimal("price"));
        t.setPrinted(rs.getBoolean("printed"));
        return t;
    }

    @Override
    protected Long getEntityId(Ticket t) {
        return t.getId();
    }

    @Override
    protected void setEntityId(Ticket t, Long id) {
        t.setId(id);
    }
}