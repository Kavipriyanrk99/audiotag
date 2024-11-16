package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Metadata
 */
public class Metadata {

    public static void main(String[] args) {
        try {

            JSONObject jo = new JSONObject(
                    "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
            System.out.println(jo.get("city"));
            System.out.println(jo.get("citys"));
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

}
