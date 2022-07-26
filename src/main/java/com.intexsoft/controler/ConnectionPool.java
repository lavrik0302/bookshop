package com.intexsoft.controler;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class ConnectionPool {
    private static final int CONNECTION_POOL_SIZE = 5;
    private static final int CONNECTION_POOL_MAX_SIZE = 10;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections;

    private ConnectionPool(String url, String user, String password) throws SQLException {
        System.out.println("Starting of creation Connection Pool");
        connectionPool = new ArrayList<>(CONNECTION_POOL_SIZE);
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            connectionPool.add(createConnection(url, user, password));
            System.out.println("Connection #" + i + " : " + connectionPool.get(i));
        }
        usedConnections = new ArrayList<>(CONNECTION_POOL_SIZE);
    }

    private static volatile ConnectionPool instance = null;
    private static Object mutex = new Object();

    public static ConnectionPool getInstance() throws SQLException, InterruptedException {
        ConnectionPool result = instance;
        if (result == null) {

            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    Thread.sleep(5000);
                    instance = result = new ConnectionPool("jdbc:postgresql://localhost:55001/bookshop", "postgres", "postgrespw");
                }
            }
        }
        return result;
    }


    public synchronized Connection getConnection() throws SQLException, InterruptedException {
        Connection connection = null;
        if (ConnectionPool.getInstance().connectionPool.isEmpty()) {
            if (ConnectionPool.getInstance().usedConnections.size() == CONNECTION_POOL_MAX_SIZE) {
                Thread.sleep(500);
                System.out.println("no free connections pls w8 a bit " + LocalDateTime.now());
                connection = getConnection();
            } else {
                System.out.println("There are no free connections ");
                connection = createConnection("jdbc:postgresql://localhost:55000/bookshop", "postgres", "postgrespw");
                ConnectionPool.getInstance().usedConnections.add(connection);
                System.out.println("Created new Connection : " + connection);
                System.out.println("Current List of Connections : " + ConnectionPool.getInstance().usedConnections);
            }
        } else {
            connection = connectionPool.remove(connectionPool.size() - 1);
            if (connection != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }

            usedConnections.add(connection);
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean contains = false;

        if (usedConnections.contains(connection)) {
            connectionPool.add(connection);
            contains = usedConnections.remove(connection);
        }
        return contains;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}