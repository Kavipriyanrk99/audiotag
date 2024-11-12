package com.kavipriyanrk99.audiotag;

import java.util.Map;

/**
 * AudioFile
 */
public abstract class AudioFile {

    String name;
    String filepath;
    String format;
    String samplerate;
    String bitrate;
    String duration;

    abstract Map<String, String> getSongDetails();

    abstract void setSongDetails(String name, String filepath, String format);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(String samplerate) {
		this.samplerate = samplerate;
	}

	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
