package com.adrian.infrastructure.persistence.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

    private static final String URL_KEY           = "db.url";
    private static final String USERNAME_KEY      = "db.username";
    private static final String PASSWORD_KEY      = "db.password";
    private static final String POOL_SIZE_KEY     = "db.pool.size";
    private static final String AUTO_COMMIT_KEY   = "db.auto.commit";
    private static final int    DEFAULT_POOL_SIZE = 5;

    private static BlockingQueue<Connection> pool;
    private static List<Connection>           sourceConnections;
    private static boolean                    defaultAutoCommit;

    static {
        loadDriver();
        initAutoCommit();
        initConnectionPool();
    }

    private ConnectionManager() {}

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не знайдено драйвер PostgreSQL", e);
        }
    }

    private static void initAutoCommit() {
        String ac = PropertiesUtil.get(AUTO_COMMIT_KEY);
        defaultAutoCommit = ac == null ? false : Boolean.parseBoolean(ac);
    }

    private static void initConnectionPool() {
        int size = DEFAULT_POOL_SIZE;
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        if (poolSize != null) {
            size = Integer.parseInt(poolSize);
        }

        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Connection realConn = open();
            Connection proxyConn = (Connection) Proxy.newProxyInstance(
                ConnectionManager.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        // безопасный возврат прокси в пул без исключений
                        pool.offer((Connection) proxy);
                        return null;
                    }
                    return method.invoke(realConn, args);
                }
            );
            pool.add(proxyConn);
            sourceConnections.add(realConn);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Помилка отримання з'єднання з пулу", e);
        }
    }

    private static Connection open() {
        try {
            Connection conn = DriverManager.getConnection(
                PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USERNAME_KEY),
                PropertiesUtil.get(PASSWORD_KEY)
            );
            conn.setAutoCommit(defaultAutoCommit);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Не вдалося відкрити з'єднання з базою даних", e);
        }
    }

    public static void closePool() {
        for (Connection connection : sourceConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Помилка при закритті з'єднання", e);
            }
        }
    }
}
