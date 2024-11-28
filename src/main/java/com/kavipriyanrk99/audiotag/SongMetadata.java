package com.kavipriyanrk99.audiotag;

/**
 * SongMetadata
 */
public class SongMetadata {

    private String title;
    private String albumName;
    private String releaseDate;
    private String releaseYear;
    private String albumCoverArtURL;
    private String artistName;
    private String audioPreviewURL;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid song title");
		this.title = title;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
        if(albumName == null || albumName.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid album name");
		this.albumName = albumName;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
        if(releaseDate == null || releaseDate.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid song release date");
		this.releaseDate = releaseDate;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
        if(releaseYear == null || releaseYear.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid song release year");
		this.releaseYear = releaseYear;
	}

	public String getAlbumCoverArtURL() {
		return albumCoverArtURL;
	}

	public void setAlbumCoverArtURL(String albumCoverArtURL) {
        if(albumCoverArtURL == null || albumCoverArtURL.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid cover art url");
		this.albumCoverArtURL = albumCoverArtURL;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
        if(artistName == null || artistName.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid artist name");
		this.artistName = artistName;
	}

	public String getAudioPreviewURL() {
		return audioPreviewURL;
	}

	public void setAudioPreviewURL(String audioPreviewURL) {
        if(audioPreviewURL == null || audioPreviewURL.isEmpty())
            this.audioPreviewURL = "No preview URL";
		this.audioPreviewURL = audioPreviewURL;
	}
}
