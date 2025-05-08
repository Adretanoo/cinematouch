package com.adrian.infrastructure.persistence.dao.impl;

import com.adrian.domain.entities.FoodItem;
import com.adrian.infrastructure.persistence.dao.FoodItemDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC-реалізація FoodItemDao.
 */
public class FoodItemDaoImpl extends AbstractJdbcDao<FoodItem, Long> implements FoodItemDao {

    @Override
    protected String getTableName() {
        return "food_item";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "name, price, available";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "name = ?, price = ?, available = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 3;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, FoodItem f) throws SQLException {
        ps.setString(1, f.getName());
        ps.setBigDecimal(2, f.getPrice());
        ps.setBoolean(3, f.getAvailable());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, FoodItem f) throws SQLException {
        setInsertParameters(ps, f);
    }

    @Override
    protected FoodItem mapRow(ResultSet rs) throws SQLException {
        FoodItem f = new FoodItem();
        f.setId(rs.getLong("id"));
        f.setName(rs.getString("name"));
        f.setPrice(rs.getBigDecimal("price"));
        f.setAvailable(rs.getBoolean("available"));
        return f;
    }

    @Override
    protected Long getEntityId(FoodItem f) {
        return f.getId();
    }

    @Override
    protected void setEntityId(FoodItem f, Long id) {
        f.setId(id);
    }
}
