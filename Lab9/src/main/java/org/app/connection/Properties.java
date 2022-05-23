package org.app.connection;

import java.io.InputStream;

/**
 * Используется для получения настроек приложения из DB.properties
 */
public class Properties {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static final java.util.Properties properties = new java.util.Properties();

    /**
     * @param name имя параметра настройки
     * @return значение данного параметра
     */
    public static String getProperty(String name) {
        if (properties.isEmpty()){
            try (InputStream is = Properties.class.getClassLoader().getResourceAsStream("DB.properties")) {
                properties.load(is);
            } catch (Exception ex){
                System.out.println("Error reading database settings file.");
                throw new RuntimeException("Error reading database settings file.");
            }
        }
        return properties.getProperty(name);
    }
}
