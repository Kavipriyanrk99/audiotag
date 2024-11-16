package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertyLoader
 */
public class PropertyLoader {

    private String CLIENT_ID;
	private String CLIENT_SECRET;
	private String PROPERTIES_FILENAME = "application.properties";

	public String getCLIENT_ID() {
		return CLIENT_ID;
	}

	public String getCLIENT_SECRET() {
		return CLIENT_SECRET;
	}

    public void setCLIENT_ID(String CLIENT_ID) {
        if(CLIENT_ID != null && !CLIENT_ID.isEmpty())
            this.CLIENT_ID = CLIENT_ID;
        else
            throw new IllegalArgumentException("[ERROR] Invalid CLIENT_ID loaded");
	}

    public void setCLIENT_SECRET(String CLIENT_SECRET) {
        if(CLIENT_SECRET != null && !CLIENT_SECRET.isEmpty())
            this.CLIENT_SECRET = CLIENT_SECRET;
        else
            throw new IllegalArgumentException("[ERROR] Invalid CLIENT_SECRET loaded");
	}

    PropertyLoader() {
        Properties properties = new Properties();
        InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);

        try {
			properties.load(inputStream);
            setCLIENT_ID(properties.getProperty("CLIENT_ID"));
            setCLIENT_SECRET(properties.getProperty("CLIENT_SECRET"));
		} catch (IOException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
