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
public class Mp3AudioFile extends AudioFile {

    Mp3File mp3file;
    boolean hasID3v1;
    boolean hasID3v3;
    boolean hasCustomTag;

	public Mp3File getMp3file() {
		return mp3file;
	}

	public void setMp3file(Mp3File mp3file) {
		this.mp3file = mp3file;
	}

	public boolean isHasID3v1() {
		return hasID3v1;
	}

	public void setHasID3v1(boolean hasID3v1) {
		this.hasID3v1 = hasID3v1;
	}

	public boolean isHasID3v3() {
		return hasID3v3;
	}

	public void setHasID3v3(boolean hasID3v3) {
		this.hasID3v3 = hasID3v3;
	}

	public boolean isHasCustomTag() {
		return hasCustomTag;
	}

	public void setHasCustomTag(boolean hasCustomTag) {
		this.hasCustomTag = hasCustomTag;
	}

	@Override
	Map<String, String> getSongDetails() {
        Map<String, String> songdetails = new HashMap<>();
        songdetails.put("name", this.name);
        songdetails.put("filepath", this.filepath);
        songdetails.put("format", this.format);
        songdetails.put("samplerate", this.samplerate);
        songdetails.put("bitrate", this.bitrate);
        songdetails.put("duration", this.duration);

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
            this.hasID3v3 = mp3file.hasId3v2Tag();
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
