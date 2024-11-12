package com.kavipriyanrk99.audiotag;

import java.util.Map;

/**
 * AbstractAudioFile
 */
public abstract class AbstractAudioFile {

    String name;
    String filepath;
    String format;
    String samplerate;
    String bitrate;
    String duration;

    abstract Map<String, String> getSongDetails();

    abstract void setSongDetails(String name, String filepath, String format);
}
