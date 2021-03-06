package uk.co.ElmHoe;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
 *  All code seen here is free to use for personal use.
 *  This notice must remain and will remain copyright to ElmHoe (Joshua Fennell).
 *  Any other APIs used or called WILL be documented below and all use of these APIs remain personal.
 *  
 *  This notice is subject to change at any time without any notice needed - if changed, any updates following that 
 *  are subject to the new agreement. Please ensure you are fully aware of this.
 *  
 *  If this code created and/or used by me, breaks any hardware, software, or anything. 
 *  All responsibilities falls to those using this code. I (Joshua.) am not responsible for any issues that occur.
 */
public class MediaPlayerAPI extends JFrame{
	/**
	 * 
	 */
	static boolean toolkit = false;
	private static final long serialVersionUID = 1L;
	public static MediaPlayer mediaPlayer;
	public static float vol = (float) 0.50;
	public static ArrayList<String> filesToPlay =  new ArrayList<String>();
	public static ArrayList<String> filesPlayed =  new ArrayList<String>();
	private static String title;
	private static boolean firstRun = true;
	public static CurrentlyPlaying window = null;
	
	public static void main(String[] args) throws IOException {
		LoadingIcons.loadIcons();
		JavaFXInitializer.initToolKit();
		MediaPlayerAPI.onFirstRun();
		MediaPlayerAPI.onVolumeUpdate(50);

	}
	
	/*
	 * This plays a new song. 
	 * Checks if anything else was playing, cancels them, continues to play this.
	 */
	public static boolean onPlay(File file, boolean clear){
		//This gets the file path of the mp3 file.
		
		
		String filePath = file.getAbsolutePath();
		URI format = new File(filePath).toURI();
		Media hit = new Media(format.toString());
		
		
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		mediaPlayer.setVolume(vol);
		try {
			updateTitle(file);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			
		}
		

		mediaPlayer.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
				onSongEnd();
				

		    }
		});
		
		return true;
	}
	
	public static void onFirstRun(){
		
		if (firstRun == true){
			
			loadDirectories();
			Collections.shuffle(filesToPlay);
			window = new CurrentlyPlaying();
			onPlay(new File(filesToPlay.get(0)), false);
			firstRun = false;
		}
	}
	
	public static void updateTitle(File file) throws UnsupportedTagException, InvalidDataException, IOException{
		/*
		 * This section just changes and updates Titles when a song finishes and a new song starts.
		 */
		
		String tempTitle = "";
		try{
			Mp3File song = new Mp3File(file);
			if (song.hasId3v2Tag()){
				if (!(song.getId3v2Tag().getTitle() == null)){
					tempTitle = song.getId3v2Tag().getTitle();
				}else{
					//
				}
				
				if (!(song.getId3v2Tag().getArtist() == null)){
					tempTitle = tempTitle + "\n" + song.getId3v2Tag().getArtist();
				}else{
					//Do Nothing, if the song doesn't have an artist at this moment, it won't do anything.
				}
			}
		}catch(Exception e){
			
		}
		if (tempTitle == ""){
			tempTitle = file.getName().replaceAll(".mp3", "").replaceAll(" - ", "\n");
		}
		title = tempTitle;
		window.setTitle(title.replace("\n", " By "));
		CurrentlyPlaying.textPane.setText(title);
		
		
		/*
		 * This next section is for the up-next section below.
		 */
		
		String upNext = "";
		for (int i = 1; i < 7; i++){
			try{
				String songFile = filesToPlay.get(i);
				File f = new File(songFile);
				upNext = upNext + f.getName().replaceAll(".mp3", "") + "\n";
			}catch(Exception e){
				
			}
		}
		CurrentlyPlaying.textPane_2.setText(upNext);
	}
	
	public static void onBack(){
		/*
		 * This section is when the player wants to back a song - it'll load the last song played if any were played.
		 */
		if (filesPlayed.size() > 0){
			mediaPlayer.stop();
			onPlay(new File(filesPlayed.get(0)), false);
		}else{
			System.out.println("Nothing to go back to? Perhaps skip or shuffle?");
		}
	}
	
	public static void onShuffle(){
		/*
		 * This is when the player presses shuffle - it'll shuffle their playlist for them.
		 * Only for this session though, after end of the session, playlist will be reverted.
		 */
	}
	
	public static void onSavePlaylist(){
		/*
		 * This section would be when a user creates a playlist - to be setup and changed with the GUI down the line.
		 */
	}
	
	public static void onLoadPlaylist(){
		/*
		 * This checks the SQL database for any playlists the user has - all song will need to be on the PC Still.
		 * but if they save a playlist and keep the song in the same directory, it'll load playlists.
		 */
	}
	
	public static void onSongEnd(){
		/*
		 * This section is when anything that the media player is playing - ends.
		 * It'll run this section trying to clean up as much as possible and remain stable.
		 */
		
		filesPlayed.add(filesToPlay.get(0));
		filesToPlay.remove(0);
		
		mediaPlayer.stop();
		onPlay(new File(filesToPlay.get(0)), false);
		
	}
	
	public static boolean onVolumeUpdate(int volume){
		/*
		 * This is when the user updates the volume, by dragging the slider.
		 * Sets the volume for the rest of the section.
		 */
		
		vol = (float)volume / 100;
		if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) == true){
			mediaPlayer.setVolume(vol);
			
			return true;
		}else{
			return false;
		}

	}
	
	public static void loadDirectories(){
		/*
		 * This section contains the default directories to check and/or load songs from.
		 * Also includes duplicate checking - if a song has the same value and size, it'll not load.
		 */
		
		ArrayList<String> Directories = new ArrayList<String>();
		Directories.add(System.getProperty("user.dir"));
		Directories.add("A:\\Music\\");
		Directories.add("C:\\Users\\" + System.getProperty("user.name") + "\\Music\\");
		Directories.add("A:\\Music\\Pandora\\");
		for (int i = 0; i < Directories.size(); i++){
			try{
				checkDir(Directories.get(i));
			}catch(Exception e){
				
			}
		}
	}
	
	public static void checkDir(String dir) throws IOException{
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().endsWith(".mp3")){
					filesToPlay.add(dir + listOfFiles[i].getName());
					System.out.println("Loading song... " + dir + listOfFiles[i].getName());
					}
			}else if(listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}
	
	public static void playAndPause(){
		if (MediaPlayerAPI.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
			mediaPlayer.pause();
			System.out.println("PLAYER WAS PLAYING, now paused.");
		}else{
			mediaPlayer.play();
			System.out.println("PLAYER WAS PAUSED, now playing.");

		}
	}
}
