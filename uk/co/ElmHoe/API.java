package uk.co.ElmHoe;

import java.io.File;

import javafx.scene.media.MediaPlayer;

public class API {
	public static boolean databaseConnected = false;
	public static String CurrentSong(){
		if (isPlayerPlaying() == true){
			String title = Main.title;
			return title;
		}
		return "PAUSED";
	}
	
	public static boolean isPlayerPlaying(){
		if (Main.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isPlayerReady(){
		if (Main.mediaPlayer.getStatus() == MediaPlayer.Status.READY){
			return true;
		}else{
			return false;
		}
	}
	
	public static void nextSong(File file){
		/*
		 * THIS SECTION IS WHAT HAPPENS WHEN THE NEXT SONG STARTS. 
		 */
		Main.updateSongList();
		
	}
	
	
}
