package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Mp3AudioFile
 */
public class Mp3AudioFile extends AbstractAudioFile {

    Mp3File mp3file;
    boolean hasID3v1;
    boolean hasID3v2;
    boolean hasCustomTag;

    @Override
    Map<String, String> getSongDetails() {
        Map<String, String> songdetails = new HashMap<>();
        songdetails.put("filename", this.name);
        songdetails.put("filepath", this.filepath);
        songdetails.put("format", this.format);
        songdetails.put("samplerate", this.samplerate);
        songdetails.put("bitrate", this.bitrate);
        songdetails.put("duration", this.duration);
        songdetails.put("id3v1", this.hasID3v1 ? "yes" : "no");
        songdetails.put("id3v2", this.hasID3v2 ? "yes" : "no");
        songdetails.put("customtag", this.hasCustomTag ? "yes" : "no");

        return songdetails;
    }

    @Override
    void setSongDetails(String name, String filepath, String format) {
        this.name = name;
        this.filepath = filepath;
        this.format = format;

        try {
            this.mp3file = new Mp3File(filepath);
            this.samplerate = Integer.toString(mp3file.getSampleRate());
            this.bitrate = Integer.toString(mp3file.getBitrate());
            this.duration = AudioUtils.getDurationsInString(mp3file.getLengthInSeconds());
            this.hasID3v1 = mp3file.hasId3v1Tag();
            this.hasID3v2 = mp3file.hasId3v2Tag();
            this.hasCustomTag = mp3file.hasCustomTag();
        } catch (UnsupportedTagException e) {
            System.out.println("[ERROR] : " + e.getMessage());
        } catch (InvalidDataException e) {
            System.out.println("[ERROR] : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[ERROR] : " + e.getMessage());
        }
    }

}
