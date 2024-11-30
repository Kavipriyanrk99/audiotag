package com.kavipriyanrk99.audiotag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    private static String filepath;
    private static String filename;
    private static String format;

    public static void setFileProperties(String filepath) throws FileNotFoundException {
        Set<String> audiofileExts = new HashSet<>();
        Collections.addAll(audiofileExts, "mp3");
        String filename;
        String fileWithoutExt;
        String fileExt;

        if (filepath != null && !filepath.isEmpty()) {
            File audiofile = new File(filepath);
            if (audiofile.exists()) {
                filename = audiofile.getName();
                fileWithoutExt = filename.substring(0, filename.lastIndexOf("."));
                fileExt = filename.substring(filename.lastIndexOf(".") + 1);

                if (!fileExt.isEmpty() && audiofileExts.contains(fileExt)) {
                    App.filepath = filepath;
                    App.filename = fileWithoutExt;
                    App.format = fileExt;
                } else
                    throw new IllegalArgumentException("[ERROR] Input is not an supported audio file");
            } else
                throw new FileNotFoundException("[ERROR] Input file doesn't exist");
        } else
            throw new IllegalArgumentException("[ERROR] Empty filepath");
    }

    public static void printTableOfResults(SongMetadata[] topResults) {
        if (topResults != null) {
            for (SongMetadata item : topResults) {
                int longest = 0;
                longest = (longest < (item.getTitle()).length()) ? (item.getTitle()).length() : longest;
                longest = (longest < (item.getAlbumName()).length()) ? (item.getAlbumName()).length() : longest;
                longest = (longest < (item.getArtistName()).length()) ? (item.getArtistName()).length() : longest;
                longest = (longest < (item.getReleaseYear()).length()) ? (item.getReleaseYear()).length() : longest;
                longest = (longest < (item.getReleaseDate()).length()) ? (item.getReleaseDate()).length() : longest;
                longest = (longest < (item.getAlbumCoverArtURL()).length()) ? (item.getAlbumCoverArtURL()).length() : longest;
                longest = (longest < (item.getAudioPreviewURL()).length()) ? (item.getAudioPreviewURL()).length() : longest;

                String contentPlaceholder = "| %-20S | " + "%-" + longest + "s" + " |%n";

                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");
                System.out.format(contentPlaceholder, "Title", item.getTitle());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Album", item.getAlbumName());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Artist(s)", item.getArtistName());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Year", item.getReleaseYear());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Date", item.getReleaseDate());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Cover Art Preview", item.getAlbumCoverArtURL());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n");

                System.out.format(contentPlaceholder, "Audio Preview", item.getAudioPreviewURL());
                System.out.format("+" + "-".repeat(22) + "+" + "-".repeat(longest + 2) + "+%n%n");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("> ENTER FILEPATH:");
        try {
            App.setFileProperties(sc.nextLine());
            AbstractAudioFile audiofile = null;

            switch (format) {
                case "mp3":
                    audiofile = new Mp3AudioFile();
                    audiofile.setSongDetails(filename, filepath, format);
            }

            if (audiofile != null) {
                System.out.println("<-------------------------- SONG DETAILS -------------------------->");
                Map<String, String> songDetails = audiofile.getSongDetails();
                System.out.println("MUSIC FILE NAME     :   " + songDetails.get("filename"));
                System.out.println("MUSIC TYPE          :   " + songDetails.get("format"));
                System.out.println("DURATION            :   " + songDetails.get("duration"));
                System.out.println("BITRATE             :   " + songDetails.get("bitrate") + "(kbps)");
                System.out.println("SAMPLE RATE         :   " + songDetails.get("samplerate") + "(Hz)");
                System.out.println("ID3V1 TAG (yes/no)  :   " + songDetails.get("id3v1"));
                System.out.println("ID3V2 TAG (yes/no)  :   " + songDetails.get("id3v2"));
                System.out.println("CUSTOM TAG (yes/no) :   " + songDetails.get("customtag"));

                System.out.println();
                System.out.println("> ENTER SEARCH TERM RELATED TO SONG:");

                String searchTerm = sc.nextLine();
                Metadata metadataObj = new Metadata(searchTerm);
                SongMetadata[] topResultSongMetadatas = metadataObj.getMetadata();
                printTableOfResults(topResultSongMetadatas);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        sc.close();
    }
}
