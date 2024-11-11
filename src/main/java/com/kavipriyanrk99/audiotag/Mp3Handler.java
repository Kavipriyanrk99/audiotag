package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Mp3Handler
 */
public class Mp3Handler {

    private Mp3File mp3file;

	public Mp3File getMp3file() {
		return mp3file;
	}

	public void setMp3file(String filename) {
        try {
			this.mp3file = new Mp3File(filename);
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public Map<String, String> getSongDetails () {
        Map<String, String> details = new HashMap<>();

        details.put("filename", mp3file.getFilename());
        details.put("len", Long.toString(mp3file.getLengthInSeconds()));
        details.put("bitrate", Integer.toString(mp3file.getBitrate()));
        details.put("samplerate", Integer.toString(mp3file.getSampleRate()));
        details.put("id3v1", mp3file.hasId3v1Tag() ? "yes" : "no");
        details.put("id3v2", mp3file.hasId3v2Tag() ? "yes" : "no");
        details.put("customtag", mp3file.hasCustomTag() ? "yes" : "no");

        return details;
    }
}
