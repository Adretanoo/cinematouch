package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.FoodItemTranslation;
import com.adrian.infrastructure.persistence.dao.FoodItemTranslationDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 * JDBC-реалізація FoodItemTranslationDao.
 */

@Repository
public class FoodItemTranslationDaoImpl
    extends AbstractJdbcDao<FoodItemTranslation, Long>
    implements FoodItemTranslationDao {

    @Override
    protected String getTableName() {
        return "food_item_translation";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected String getInsertColumns() {
        return "food_item_id, language_code, translated_name";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "food_item_id = ?, language_code = ?, translated_name = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 3;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, FoodItemTranslation t)
        throws SQLException {
        ps.setLong(1, t.getFoodItemId());
        ps.setString(2, t.getLanguageCode());
        ps.setString(3, t.getTranslatedName());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, FoodItemTranslation t)
        throws SQLException {
        setInsertParameters(ps, t);
    }

    @Override
    protected FoodItemTranslation mapRow(ResultSet rs) throws SQLException {
        FoodItemTranslation t = new FoodItemTranslation();
        t.setId(rs.getLong("id"));
        t.setFoodItemId(rs.getLong("food_item_id"));
        t.setLanguageCode(rs.getString("language_code"));
        t.setTranslatedName(rs.getString("translated_name"));
        return t;
    }

    @Override
    protected Long getEntityId(FoodItemTranslation t) {
        return t.getId();
    }

    @Override
    protected void setEntityId(FoodItemTranslation t, Long id) {
        t.setId(id);
    }
}
