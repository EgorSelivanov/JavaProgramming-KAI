package org.app.connection;

import java.sql.*;

/**
 * Используется для установления подключения к БД и доступа к нему
 */
public class DBConnection {

    private static Connection connection;
    private static Statement statement;

    private DBConnection(){}

    /**
     * функция установления подключения
     */
    public static void getConnection()
    {
        try{
            connection = DriverManager.getConnection(
                    Properties.getProperty(Properties.DB_URL),
                    Properties.getProperty(Properties.DB_LOGIN),
                    Properties.getProperty(Properties.DB_PASSWORD));
            getStatement();
        } catch (SQLException | RuntimeException ex) {
            System.out.println(ex);
            throw new RuntimeException("Error connecting to database.");
        }
    }

    private static void getStatement() {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return возвращает установленное подключение
     */
    public static Connection connection() {
        return connection;
    }
    public static Statement statement() {
        return statement;
    }
}
