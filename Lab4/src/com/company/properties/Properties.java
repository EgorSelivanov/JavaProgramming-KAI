package com.company.properties;

import java.io.InputStream;

/**
 * используется для получения настроек приложения из DB.properties
 */
public class Properties {
    public static final String CLIENT_HOST = "client.host";
    public static final String CLIENT_PORT = "client.port";
    public static final String SERVER_LOG = "server.log";

    private static final java.util.Properties properties = new java.util.Properties();

    /**
     * @param name имя параметра настройки
     * @return значение данного параметра
     */
    public static String getProperty(String name) {
        if (properties.isEmpty()){
            try (InputStream is = Properties.class.getClassLoader().getResourceAsStream("resources/app.properties")) {
                properties.load(is);
            } catch (Exception ex){
                throw new RuntimeException("Error reading database settings file.");
            }
        }
        return properties.getProperty(name);
    }
}
