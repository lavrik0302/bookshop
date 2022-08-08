package com.intexsoft.controler;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConnectionPool {
    private static final int CONNECTION_POOL_SIZE = 5;
    private List<Connection> connectionPool = new ArrayList<>(CONNECTION_POOL_SIZE);
    private List<Connection> usedConnections = new ArrayList<>(CONNECTION_POOL_SIZE);


    private ConnectionPool() {

    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null)
            instance = ConnectionPool.create("jdbc:postgresql://localhost:55000/bookshop", "postgres", "postgrespw");
        return instance;
    }

    public static ConnectionPool create(
            String url, String user,
            String password) throws SQLException {
        ConnectionPool connectionPool1 = new ConnectionPool();
        List<Connection> pool = new ArrayList<>(CONNECTION_POOL_SIZE);
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
            System.out.println("Connection #" + i + " : " + pool.get(i));
        }
        List<Connection> used = new ArrayList<>(CONNECTION_POOL_SIZE);
        connectionPool1.setConnectionPool(pool);
        connectionPool1.setUsedConnections(used);
        instance = connectionPool1;
        return instance;
    }

    // standard constructors

    public Connection getConnection() throws SQLException {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        if (connection != null) {
            System.out.println("Connection established");
        } else {
            System.out.println("Connection failed");
        }
        usedConnections.add(connection);
        return connection;
    }


    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}