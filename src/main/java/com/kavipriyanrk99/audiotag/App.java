package com.kavipriyanrk99.audiotag;

import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("> Enter filename:");
        String filename = sc.nextLine();

        if(filename != null && !filename.isEmpty()){
            Mp3Handler mp3handler = new Mp3Handler();

            mp3handler.setMp3file(filename);

            Map<String, String> songDetails = mp3handler.getSongDetails();
            System.out.println("<-------------------------- SONG DETAILS -------------------------->");
            System.out.println("MUSIC FILE NAME     :   " + songDetails.get("filename"));
            System.out.println("LENGTH              :   " + songDetails.get("len") + "(secs)");
            System.out.println("BITRATE             :   " + songDetails.get("bitrate") + "(kbps)");
            System.out.println("SAMPLE RATE         :   " + songDetails.get("samplerate") + "(Hz)");
            System.out.println("ID3V1 TAG (yes/no)  :   " + songDetails.get("id3v1"));
            System.out.println("ID3V2 TAG (yes/no)  :   " + songDetails.get("id3v2"));
            System.out.println("CUSTOM TAG (yes/no) :   " + songDetails.get("customtag"));
        }

        sc.close();
    }
}
