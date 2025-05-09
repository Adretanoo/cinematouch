package com.adrian;

import com.adrian.domain.entities.Movie;
import com.adrian.infrastructure.persistence.dao.MovieDao;
import com.adrian.infrastructure.persistence.impl.MovieDaoImpl;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;
import com.adrian.infrastructure.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1) Відкриваємо з'єднання та вимикаємо автокоміт
            conn = ConnectionManager.get();
            conn.setAutoCommit(false);

            // 2) Засетимо його в ThreadLocal, щоб DAO брали саме це
            ConnectionHolder.setConnection(conn);

            // 3) Створюємо реалізацію DAO і викликаємо метод
            MovieDao movieDao = new MovieDaoImpl();
            List<Movie> recent = movieDao.findRecent(5);

            // 4) Виводимо результат
            System.out.println("=== Recent Movies ===");
            for (Movie m : recent) {
                System.out.printf("%d: %s%n", m.getId(), m.getTitle());
            }

            // 5) Закриваємо
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionHolder.clearConnection();
            if (conn != null) {
                try { conn.close(); } catch (Exception ignore) {}
            }
        }
    }
}
