package com.adrian.infrastructure.persistence.impl;

import com.adrian.infrastructure.persistence.dao.GenericDao;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Абстрактна реалізація GenericDao через JDBC.
 */

@Repository
public abstract class AbstractJdbcDao<T, ID> implements GenericDao<T, ID> {

    protected abstract String getTableName();
    protected abstract String getIdColumn();

    protected abstract String getInsertColumns();
    protected abstract String getInsertPlaceholders();
    protected abstract String getUpdateAssignments();
    protected abstract int    getUpdateParameterCount();

    protected abstract void setInsertParameters(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement ps, T entity) throws SQLException;

    protected abstract T     mapRow(ResultSet rs) throws SQLException;
    protected abstract ID    getEntityId(T entity);
    protected abstract void  setEntityId(T entity, ID id);

    @Override
    public T save(T entity) {
        if (getEntityId(entity) == null) {
            return insert(entity);
        } else {
            update(entity);
            return entity;
        }
    }

    private T insert(T entity) {
        String sql = String.format(
            "INSERT INTO %s (%s) VALUES (%s)",
            getTableName(), getInsertColumns(), getInsertPlaceholders()
        );
        Connection con = ConnectionHolder.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertParameters(ps, entity);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    setEntityId(entity, (ID)(Long)keys.getLong(1));
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed in " + getTableName(), e);
        }
    }

    private void update(T entity) {
        String sql = String.format(
            "UPDATE %s SET %s WHERE %s = ?",
            getTableName(), getUpdateAssignments(), getIdColumn()
        );
        Connection con = ConnectionHolder.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            setUpdateParameters(ps, entity);
            ps.setObject(getUpdateParameterCount() + 1, getEntityId(entity));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update failed in " + getTableName(), e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", getTableName(), getIdColumn());
        Connection con = ConnectionHolder.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById failed in " + getTableName(), e);
        }
    }

    @Override
    public List<T> findAll() {
        String sql = String.format("SELECT * FROM %s", getTableName());
        Connection con = ConnectionHolder.getConnection();
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("findAll failed in " + getTableName(), e);
        }
    }

    @Override
    public void deleteById(ID id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", getTableName(), getIdColumn());
        Connection con = ConnectionHolder.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("deleteById failed in " + getTableName(), e);
        }
    }
}
