package com.adrian.infrastructure.persistence.dao.impl;


import com.adrian.domain.entities.PaymentMethod;
import com.adrian.infrastructure.persistence.dao.PaymentMethodDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC-реалізація PaymentMethodDao через AbstractJdbcDao.
 */
public class PaymentMethodDaoImpl
    extends AbstractJdbcDao<PaymentMethod, Long>
    implements PaymentMethodDao {

    @Override
    protected String getTableName() {
        return "payment_method";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "method_name";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "method_name = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 1;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, PaymentMethod pm) throws SQLException {
        ps.setString(1, pm.getMethodName());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, PaymentMethod pm) throws SQLException {
        setInsertParameters(ps, pm);
    }

    @Override
    protected PaymentMethod mapRow(ResultSet rs) throws SQLException {
        PaymentMethod pm = new PaymentMethod();
        pm.setId(rs.getLong("id"));
        pm.setMethodName(rs.getString("method_name"));
        return pm;
    }

    @Override
    protected Long getEntityId(PaymentMethod pm) {
        return pm.getId();
    }

    @Override
    protected void setEntityId(PaymentMethod pm, Long id) {
        pm.setId(id);
    }
}
