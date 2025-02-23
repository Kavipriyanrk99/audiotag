package com.kavipriyanrk99.audiotag;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mpatric.mp3agic.ID3Wrapper;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;

/**
 * Metadata
 */
public class Metadata {
    private String searchTerm;
    private final String TAG_INDICATOR = "TAGGED";

    Metadata(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank())
            throw new IllegalArgumentException("[ERROR] Invalid search term");
        this.searchTerm = searchTerm;
    }

    SongMetadata[] getMetadata() {
        APIHandler.setAccessToken();
        String accessToken = APIHandler.getAccessToken();
        String tokenType = APIHandler.getTokenType();

        if (accessToken == null || accessToken.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid access token");

        if (tokenType == null || tokenType.isEmpty())
            throw new IllegalArgumentException("[ERROR] Invalid token type");

        try {
            String type = "track";
            String limit = "5";
            URI requestUri = APIHandler.requestURLConstructor(type, limit, this.searchTerm);
            String token = tokenType + " " + accessToken;
            String contentType = "";
            String method = "GET";
            BodyPublisher bodyPublisher = BodyPublishers.noBody();
            String responseData = APIHandler.makeHttpRequest(requestUri, token, contentType, method, bodyPublisher);

            if (responseData == null || responseData.isEmpty())
                throw new IllegalStateException("[ERROR] Invalid reponse data");

            JSONObject data = new JSONObject(responseData);
            JSONObject tracks = (JSONObject) data.get("tracks");
            JSONArray items = (JSONArray) tracks.get("items");

            int index = 0;
            SongMetadata[] topResults = new SongMetadata[Integer.parseInt(limit)];

            for (Object item : items) {
                if (item instanceof JSONObject) {
                    JSONObject jsonObjItem = (JSONObject) item;
                    JSONObject album = (JSONObject) jsonObjItem.get("album");
                    JSONArray images = (JSONArray) album.get("images");
                    JSONArray artists = (JSONArray) jsonObjItem.get("artists");

                    // [x] Title "name": "Blood // Water",
                    // [x] Artist "name": "grandson"
                    // [x] Album "name": "Blood // Water"
                    // AlbumArtist "name": "grandson"
                    // [x] Year
                    // [x] Date "release_date": "2018-05-04"
                    // Genre
                    // Lyrics
                    // Composer
                    // Publisher
                    // [x] AlbumImage

                    String title = (String) jsonObjItem.get("name");
                    String albumName = (String) album.get("name");
                    String releaseDate = (String) album.get("release_date");
                    String releaseYear = releaseDate.substring(0, releaseDate.indexOf('-'));
                    String albumCoverArtURL = (String) ((JSONObject) images.get(1)).get("url"); // 300 x 300
                    String artistName = null;
                    String audioPreviewURL = null;

                    for (Object jsonObjArtist : artists) {
                        if (jsonObjArtist instanceof JSONObject) {
                            JSONObject artist = (JSONObject) jsonObjArtist;
                            if (artistName == null)
                                artistName = (String) artist.get("name");
                            else
                                artistName = artistName + ", " + artist.get("name");
                        } else
                            throw new IllegalStateException("[ERROR] Invalid JSON data while parsing");
                    }

                    if (jsonObjItem.get("preview_url") == null)
                        audioPreviewURL = (String) jsonObjItem.get("preview_url");

                    topResults[index] = new SongMetadata();
                    topResults[index].setTitle(title);
                    topResults[index].setAlbumName(albumName);
                    topResults[index].setAlbumCoverArtURL(albumCoverArtURL);
                    topResults[index].setReleaseDate(releaseDate);
                    topResults[index].setReleaseYear(releaseYear);
                    topResults[index].setArtistName(artistName);
                    topResults[index].setAudioPreviewURL(audioPreviewURL);
                    index++;
                } else
                    throw new IllegalStateException("[ERROR] Invalid JSON data while parsing");
            }

            return topResults;
        } catch (URISyntaxException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (IllegalStateException e) {
            System.out.println(e);
        }

        return null;
    }

    private ID3Wrapper setCoverArt(ID3Wrapper id3Wrapper, String imageURL) {
        byte[] imageBytes = null;
        imageBytes = AudioUtils.getImageFromURL(imageURL);
        id3Wrapper.setAlbumImage(imageBytes, "image/jpeg");
        return id3Wrapper;
    }

    Mp3File tagger(SongMetadata songMetadata, Mp3AudioFile audioFile) {
        if (songMetadata != null && audioFile != null) {
            Mp3File mp3File = audioFile.mp3file;

            ID3Wrapper id3Wrapper = new ID3Wrapper(new ID3v1Tag(), new ID3v24Tag());
            id3Wrapper.setTitle(songMetadata.getTitle());
            id3Wrapper.setAlbum(songMetadata.getAlbumName());
            id3Wrapper.setAlbumArtist(songMetadata.getArtistName());
            id3Wrapper.setYear(songMetadata.getReleaseDate());
            id3Wrapper = setCoverArt(id3Wrapper, songMetadata.getAlbumCoverArtURL());
            id3Wrapper.getId3v2Tag().setPadding(true);

            if (audioFile.hasID3v1)
                mp3File.setId3v1Tag(id3Wrapper.getId3v1Tag());

            if (audioFile.hasID3v2)
                mp3File.setId3v2Tag(id3Wrapper.getId3v2Tag());

            return mp3File;
        }
    }
}
