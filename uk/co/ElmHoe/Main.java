package uk.co.ElmHoe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Main extends JFrame{

	static InputStream in = System.in;
	static Scanner s = new Scanner(in);
	static String dir = null;
	static ArrayList<String> filesToPlay =  new ArrayList<String>();
	static HashMap<String,String> filesAndNames =  new HashMap<String,String>();
	static ArrayList<String> pastSongs =  new ArrayList<String>();
	static boolean firstTime = true;
	static boolean toolkit = false;
	private static final long serialVersionUID = 1L;
	public static boolean firstText = true;
	public static CurrentlyPlaying window = null;
	public static float vol = (float) 0.50;
	public static int playNum = 0;
	private static String oldTitle = null;
	public static String title = "";
	private static String forBack = "";
	private static String SongAPI = null;
	
	
	private static boolean debugging = true;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Enter directory to load all mp3 files from, or press return to use current directory.");
		dir = s.nextLine();
		if (dir.equals("")){ dir = "A:\\Music\\ALL Songs"; }
		if (dir.equals("this")){ dir = System.getProperty("user.dir");}
		if (!(dir.endsWith("\\"))){  dir = dir + "\\"; }
		
		LoadingIcons.loadIcons();
		
		/*
		 *     THIS SECTION LOADS DISCORD API. GET READY LADS
		 *     
		 */
		
		//Logging.startLog();
		//Connection.connect();
		//ExtraEvents.mysql();

		
		/*
		 * 
		 * 		END OFF DISCORD SECTION
		 * 
		 * 
		 */
		
		
		
		new Main();
		
		KeyListener.main(args);

	}
	
	
	
	public Main(){
	
		/*
		 * TO DO LIST
		 * 
		 * CHANGE + IMPLEMENT FIRST TIME LISTENERS AND SETUP LOADING ALL SONGS.
		 * 
		 * Time Played and Time Left needs implementing.
		 * 
		 * Back is broken as fuck.
		 * 		 
		 * new Application image logo?
		 * 
		 * add image updating for the player its self. 
		 * 
		 * create standalone version without any discord support.
		 * 
		 * DISABLE ALL DISCORD SUPPORT - SWITCH OVER TO STANDALONE VERSION
		 *
		 * REWRITING UP NEXT + GO BACK 
		 */
		window = new CurrentlyPlaying();
		
		try {
			checkDir(dir);
			showLoadedSongs();
			vol(50);
			if (debugging == true){
				System.out.println("Stage 1 \n Checking and loading songs complete.");
			}
		} catch (IOException | UnsupportedTagException | InvalidDataException e2) {
			e2.printStackTrace();
		}
		
	}

	
	
	public static void checkDir(String dir) throws IOException{
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if (listOfFiles[i].getName().endsWith(".mp3")){
		    		  filesToPlay.add(dir + listOfFiles[i].getName());
		    		  filesAndNames.put(dir + listOfFiles[i].getName(), listOfFiles[i].getName().replaceAll(".mp3", ""));
		    	  }
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		    
		    if (debugging == true){
				System.out.println("Loaded songs stage. Songs loaded have been pasted.");
			}
	}
	
	public static MediaPlayer mediaPlayer;
	
	
	public static void play(File file){
		
		//This gets the file path of the mp3 file.
		String filePath = file.getAbsolutePath();
		URI format = new File(filePath).toURI();
		Media hit = new Media(format.toString());
		SongAPI = filePath;
		if (debugging == true){
			System.out.println("Grabbing song URL done");
		}
		//Media Player section to load and begin playing.
		
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		mediaPlayer.setVolume(vol);
		if (debugging == true){
			System.out.println("Media Player has been set to play.");
		}
		mediaPlayer.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
				atTheEndOfMedia(filePath);
		    }
		});

	}
	
	
	public static boolean atTheEndOfMedia(String oldFilePath){
		try{
			pastSongs.add(oldFilePath);
			mediaPlayer.stop();
			if (debugging == true){
				System.out.println("Media Player has been stopped as of EndOfMedia");
			}
			return true;
		}catch(Exception e){
			
			
			return false;
		}		
	}
	public static void atEndOrSkip(String oldFilePath) throws UnsupportedTagException, InvalidDataException, IOException{
		
		pastSongs.add(oldFilePath);
		mediaPlayer.stop();
		//CurrentlyPlaying.slider1.setValue(0);
    	generatePlayList();
    	Main.updateSongList();
    	try{
    		ExtraEvents.pullImageArtwork(new File(oldFilePath));
    	}catch(Exception e){
    		System.out.println("Failed to update image icon." + "\n" + e.getMessage());
    	}
	}

	public static boolean vol(int volume){
		vol = (float)volume / 100;
		if (API.isPlayerPlaying() == true){
			mediaPlayer.setVolume(vol);
			return true;
		}else{
			return false;			
		}
	}
	public static void nextVol(float volume){
		if (API.isPlayerPlaying() == true){
			mediaPlayer.setVolume(vol);
		}
	}
	public static void playAndPause(){
		boolean check = API.isPlayerPlaying();
		
		if (check == true){
			mediaPlayer.pause();
			System.out.println("PLAYER WAS " + check + ", now paused.");
		}else{
			mediaPlayer.play();
			System.out.println("PLAYER WAS " + check + ", now playing.");
		}
	}
	
	public static void skip() throws UnsupportedTagException, InvalidDataException, IOException{
		atEndOrSkip(SongAPI);
    }
	
	public static void goBack(){
		if (pastSongs.isEmpty()){
			try{
				System.out.println("Nothing to go back too...");
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}else{
			

			try{
				filesToPlay.add(forBack);
				mediaPlayer.stop();
				play(new File(pastSongs.get(pastSongs.size() - 1)));		
				updateTitle(new File(pastSongs.get(pastSongs.size()-1)), playNum, true);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void updateTitle(File file, int playNum, boolean back) throws UnsupportedTagException, InvalidDataException, IOException{
		Mp3File song = new Mp3File(filesToPlay.get(playNum));
		title = filesAndNames.get(song.getFilename()).toString();
		oldTitle = filesAndNames.get(song.getFilename()).toString();
		showSongsInList();
		System.out.println("Next Song : " + title); 
		CurrentlyPlaying.textPane.setText(title.replaceAll(" - ", "\n"));
		window.setTitle("ElmMusic: " + title);
	}
	
	public static void generatePlayList(){
		if (filesToPlay.isEmpty()){
			try { checkDir(dir); } catch (IOException e) {}
		}
		
		removeCurrentSong(playNum ,false);
		nextVol(vol);

		try {
			updateTitle(new File(filesToPlay.get(playNum)), playNum, false);
			ExtraEvents.pullImageArtwork(new File(filesToPlay.get(playNum)));
			
		} catch (UnsupportedTagException | InvalidDataException | IOException e) { e.printStackTrace(); }
	}
	
	public static void playFirst() throws UnsupportedTagException, InvalidDataException, IOException{
		if (firstTime == true){
			Collections.shuffle(filesToPlay);
			JavaFXInitializer.initToolKit();
			updateTitle(new File(filesToPlay.get(0)), 0, false);			
			removeCurrentSong(0, false);
			firstTime = false;
		}
	}
	
	public static void removeCurrentSong(int SongCount, boolean back){
		
		if (back == true){
			
			filesToPlay.add(pastSongs.get(SongCount));
			pastSongs.remove(SongCount);	
		}else{
			pastSongs.add(filesToPlay.get(SongCount));
			filesToPlay.remove(SongCount);
		}
		play(new File(filesToPlay.get(playNum)));
		
	}
	public static void updateSongList(){
		try{
			CurrentlyPlaying.textPane_2.setText("");
			showSongsInList();
		}catch(Exception e){
			System.out.println("Unable to update song list. Error being thrown: " + "\n" + e.getMessage());
		}
		
	}
	public static ArrayList<String> showSongsInList() throws UnsupportedTagException, InvalidDataException, IOException{

		for (int i = 0 ; i < filesToPlay.size() ;  i++){
			if (filesToPlay.size() == 0){
				
			}else if (i <= 5){
				Mp3File song = new Mp3File(filesToPlay.get(i));
				String title = filesAndNames.get(song.getFilename()).toString();
				if (firstText == true){
					CurrentlyPlaying.textPane_2.setText(title + "\n");
					firstText = false;
				}else{
					String text = CurrentlyPlaying.textPane_2.getText();
					CurrentlyPlaying.textPane_2.setText(text.replaceFirst(oldTitle, "") + title + "\n");

				}
			}
		}
		return filesToPlay;
	}
	
	public static boolean showLoadedSongs() throws UnsupportedTagException, InvalidDataException, IOException{
		if (filesToPlay.isEmpty()){
			return false;
		}else{
			for (int i = 0; i < filesToPlay.size() ; i++ ){
				System.out.println(filesToPlay.get(i));
			}
			playFirst();
			return true;
		}
	}
	

}
