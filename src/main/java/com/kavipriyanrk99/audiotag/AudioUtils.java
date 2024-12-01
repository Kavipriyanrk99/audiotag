package com.kavipriyanrk99.audiotag;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * AudioUtils
 */
public class AudioUtils {

    public static String getDurationsInString(long duration) {
        int hours = (int) (duration / 3600);
        duration %= 3600;
        int minutes = (int) (duration / 60);
        duration %= 60;
        int seconds = (int) duration;

        return String.format("%d:%d:%d", hours, minutes, seconds);
    }

    public static byte[] getImageFromURL(String imageURL) {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] response = null;

        try {
            in = (new URI(imageURL).toURL()).openStream();
            out = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int length = 0;

            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }

            response = out.toByteArray();
        } catch (MalformedURLException e) {
            System.out.println("[ERROR] Invalid image URL structure");
        } catch (URISyntaxException e) {
            System.out.println("[ERROR] Invalid image URL syntax");
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return response;
    }
}
