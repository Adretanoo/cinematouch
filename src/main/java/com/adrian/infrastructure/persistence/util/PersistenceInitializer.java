package com.adrian.infrastructure.persistence.util;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Ініціалізатор БД: виконує DDL та DML скрипти після запуску Spring Context.
 */
@Component
public class PersistenceInitializer {

    private static final String DDL_SCRIPT_PATH = "ddl_postgresql.sql";
    private static final String DML_SCRIPT_PATH = "dml_postgresql.sql";

    private final boolean runDml = true;

    public void init() {
        try (Connection conn = ConnectionManager.get();
            Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);
            stmt.execute(getSql(DDL_SCRIPT_PATH));
            if (runDml) {
                stmt.execute(getSql(DML_SCRIPT_PATH));
            }
            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка ініціалізації бази", e);
        }
    }

    private String getSql(String path) {
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path))
            ))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Не вдалося прочитати SQL: " + path, e);
        }
    }
}

