package com.adrian.infrastructure.persistence.dao.impl;


import com.adrian.domain.entities.Payment;
import com.adrian.domain.enums.PaymentStatus;
import com.adrian.infrastructure.persistence.dao.PaymentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl extends AbstractJdbcDao<Payment, Long> implements PaymentDao {

    @Override
    protected String getTableName() {
        return "payment";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "order_id, amount, method_id, status";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "order_id = ?, amount = ?, method_id = ?, status = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 4;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Payment p) throws SQLException {
        ps.setLong(1, p.getOrderId());
        ps.setBigDecimal(2, p.getAmount());
        if (p.getMethodId() != null) {
            ps.setLong(3, p.getMethodId());
        } else {
            ps.setNull(3, Types.BIGINT);
        }
        // enum as Postgres enum type
        ps.setObject(4, p.getStatus().name(), Types.OTHER);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Payment p) throws SQLException {
        setInsertParameters(ps, p);
    }

    @Override
    protected Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getLong("id"));
        p.setOrderId(rs.getLong("order_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        long m = rs.getLong("method_id");
        if (!rs.wasNull()) {
            p.setMethodId(m);
        }
        p.setStatus(PaymentStatus.valueOf(rs.getString("status")));
        p.setTimestamp(rs.getTimestamp("timestamp"));
        return p;
    }

    @Override
    protected Long getEntityId(Payment p) {
        return p.getId();
    }

    @Override
    protected void setEntityId(Payment p, Long id) {
        p.setId(id);
    }
}
