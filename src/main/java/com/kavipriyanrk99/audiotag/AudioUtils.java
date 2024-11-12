package com.kavipriyanrk99.audiotag;

/**
 * AudioUtils
 */
public class AudioUtils {

    public static String getDurationsInString (long duration) {
        int hours = (int)(duration / 3600);
        duration %= 3600;
        int minutes = (int)(duration / 60);
        duration %= 60;
        int seconds = (int) duration;

        return String.format("%d:%d:%d", hours, minutes, seconds);
    }    
}
