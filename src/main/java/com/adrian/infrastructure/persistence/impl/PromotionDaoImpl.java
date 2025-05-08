package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.Promotion;
import com.adrian.infrastructure.persistence.dao.PromotionDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionDaoImpl extends AbstractJdbcDao<Promotion, Long> implements PromotionDao {

    @Override
    protected String getTableName() {
        return "promotion";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "name, discount_percent, start_date, end_date, description, image_path";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "name = ?, discount_percent = ?, start_date = ?, end_date = ?, description = ?, image_path = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 6;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Promotion p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setBigDecimal(2, p.getDiscountPercent());
        ps.setDate(3, java.sql.Date.valueOf(p.getStartDate()));
        ps.setDate(4, java.sql.Date.valueOf(p.getEndDate()));
        ps.setString(5, p.getDescription());
        ps.setString(6, p.getImagePath());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Promotion p) throws SQLException {
        setInsertParameters(ps, p);
    }

    @Override
    protected Promotion mapRow(ResultSet rs) throws SQLException {
        Promotion p = new Promotion();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setDiscountPercent(rs.getBigDecimal("discount_percent"));
        p.setStartDate(rs.getDate("start_date").toLocalDate());
        p.setEndDate(rs.getDate("end_date").toLocalDate());
        p.setDescription(rs.getString("description"));
        p.setImagePath(rs.getString("image_path"));
        return p;
    }

    @Override
    protected Long getEntityId(Promotion p) {
        return p.getId();
    }

    @Override
    protected void setEntityId(Promotion p, Long id) {
        p.setId(id);
    }
}