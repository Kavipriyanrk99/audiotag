package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertyLoader
 */
public class PropertyLoader {

    static private String CLIENT_ID;
    static private String CLIENT_SECRET;
    static private String AUTH_URL;
    static private String API_URL;
    static private String PROPERTIES_FILENAME = "application.properties";

    static public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    static public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    static public String getAUTH_URL() {
        return AUTH_URL;
    }

    static public String getAPI_URL() {
        return API_URL;
    }

    static public void setCLIENT_ID(String CLIENT_ID) {
        if (CLIENT_ID != null && !CLIENT_ID.isEmpty())
            PropertyLoader.CLIENT_ID = CLIENT_ID;
        else
            throw new IllegalArgumentException("[ERROR] Invalid CLIENT_ID loaded");
    }

    static public void setCLIENT_SECRET(String CLIENT_SECRET) {
        if (CLIENT_SECRET != null && !CLIENT_SECRET.isEmpty())
            PropertyLoader.CLIENT_SECRET = CLIENT_SECRET;
        else
            throw new IllegalArgumentException("[ERROR] Invalid CLIENT_SECRET loaded");
    }

    static public void setAUTH_URL(String AUTH_URL) {
        if (AUTH_URL != null && !AUTH_URL.isEmpty())
            PropertyLoader.AUTH_URL = AUTH_URL;
        else
            throw new IllegalArgumentException("[ERROR] Invalid AUTH_URL loaded");
    }

    static public void setAPI_URL(String API_URL) {
        if (API_URL != null && !AUTH_URL.isEmpty())
            PropertyLoader.API_URL = API_URL;
        else
            throw new IllegalArgumentException("[ERROR] Invalid API_URL loaded");
    }

    static public void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);

        try {
            properties.load(inputStream);
            setCLIENT_ID(properties.getProperty("CLIENT_ID"));
            setCLIENT_SECRET(properties.getProperty("CLIENT_SECRET"));
            setAUTH_URL(properties.getProperty("AUTH_URL"));
            setAPI_URL(properties.getProperty("API_URL"));
        } catch (IOException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
