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

    public static void setFilepath(String filepath) throws FileNotFoundException {
        if (filepath != null && !filepath.isEmpty()) {
            File audiofile = new File(filepath);
            if (audiofile.exists())
                App.filepath = filepath;
            else
                throw new FileNotFoundException("[ERROR] Input file doesn't exist");
        } else
            throw new IllegalArgumentException("[ERROR] Empty filepath");
    }

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
                    throw new IllegalArgumentException("[ERROR] Input is not an audio file");
            } else
                throw new FileNotFoundException("[ERROR] Input file doesn't exist");
        } else
            throw new IllegalArgumentException("[ERROR] Empty filepath");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("> ENTER FILEPATH:");
        try {
            App.setFileProperties(sc.nextLine());

            switch (format) {
                case "mp3":
                    Mp3AudioFile mp3AudioFile = new Mp3AudioFile();
                    mp3AudioFile.setSongDetails(filename, filepath, format);

                    System.out.println("<-------------------------- SONG DETAILS-------------------------->");
                    Map<String, String> songDetails = mp3AudioFile.getSongDetails();
                    System.out.println("MUSIC FILE NAME     :   " + songDetails.get("filename"));
                    System.out.println("MUSIC TYPE          :   " + songDetails.get("format"));
                    System.out.println("DURATION            :   " + songDetails.get("duration"));
                    System.out.println("BITRATE             :   " + songDetails.get("bitrate") + "(kbps)");
                    System.out.println("SAMPLE RATE         :   " + songDetails.get("samplerate") + "(Hz)");
                    System.out.println("ID3V1 TAG (yes/no)  :   " + songDetails.get("id3v1"));
                    System.out.println("ID3V2 TAG (yes/no)  :   " + songDetails.get("id3v2"));
                    System.out.println("CUSTOM TAG (yes/no) :   " + songDetails.get("customtag"));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

        sc.close();
    }
}
