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
	static HashMap<Double,String> pastSongs =  new HashMap<Double,String>();
	static boolean firstTime = true;
	static boolean toolkit = false;
	private static final long serialVersionUID = 1L;
	public static boolean Playing = true;
	public static boolean firstText = true;
	public static CurrentlyPlaying window = null;
	private static Double playCount = 0.0;
	public static float vol = (float) 0.50;
	public static int playNum = 0;
	private static String oldTitle = null;
	public static String title = "";
	
	public static void main(String[] args) {
		System.out.println("Enter directory to load all mp3 files from, or press return to use current directory.");
		dir = s.nextLine();
		if (dir.equals(null)){ dir = System.getProperty("user.dir"); }
		if (!(dir.endsWith("\\"))){  dir = dir + "\\"; }
		LoadingIcons.loadIcons();
		new Main();
	}
	
	
	
	public Main(){
	
		/*
		 * TO DO LIST
		 * 
		 * 
		 * 
		 * 
		 * PAUSE Button, needs updating to an image of Pause as well as does the Play Button.
		 * 
		 * Time Played and Time Left needs implementing.
		 * 
		 * Back is broken as fuck.
		 * 
		 * Skip and back need images.
		 * 
		 * new Application image logo?
		 */
		window = new CurrentlyPlaying();
		
		try {
			checkDir(dir);
			showLoadedSongs();
			vol(50);
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
	}
	
	public static MediaPlayer mediaPlayer;
	
	
	public static void play(File file){
		String bip = file.getAbsolutePath();
		URI format = new File(bip).toURI();
		Media hit = new Media(format.toString());
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		mediaPlayer.setVolume(vol);
		nextVol(vol);
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
				pastSongs.put(playCount, bip);
				playCount = playCount + 1;
				mediaPlayer.stop();
				Playing = false;
				CurrentlyPlaying.slider1.setValue(0);
		    	generatePlayList();
		    	Main.updateSongList();
		    }
		});

	}
	public static boolean vol(int volume){
		vol = (float)volume / 100;
		if (Playing == true){
			System.out.println("Volume level has been set to: " + (vol * 100));
			mediaPlayer.setVolume(vol);
			return true;
		}else{
			return false;			
		}
	}
	public static void nextVol(float volume){
		if (Playing == true){
			mediaPlayer.setVolume(vol);
		}
	}
	public static void playAndPause(){
		if (Playing == true){
			mediaPlayer.pause();
			Playing = false;
		}else{
			mediaPlayer.play();
			Playing = true;

		}
	}
	public static void skip(){
		pastSongs.put(playCount, filesToPlay.get(playNum));
		playCount = playCount + 1;
		Playing = true;
		mediaPlayer.stop();
		generatePlayList();
	}
	
	public static void goBack(){
		//mediaPlayer.stop();
	}
	
	public static void updateDuration(){
		while (Playing == true){
			Duration playingLength = mediaPlayer.getCycleDuration();
			Duration playingTotal = mediaPlayer.getTotalDuration();
			System.out.println(""+playingLength.toSeconds() + playingTotal.toSeconds());
			Duration updateAmount = mediaPlayer.getBufferProgressTime();
			CurrentlyPlaying.slider1.setValue(Integer.parseInt(updateAmount.toString()));
			System.out.println(updateAmount);
			if (playingLength == playingTotal){
				break;
			}
		}
	}
	
	public static void updateTitle(File file, int playNum, boolean back) throws UnsupportedTagException, InvalidDataException, IOException{
		Mp3File song = new Mp3File(filesToPlay.get(playNum));
		title = filesAndNames.get(song.getFilename()).toString();
		oldTitle = filesAndNames.get(song.getFilename()).toString();
		showSongsInList();
		System.out.println(oldTitle);
		System.out.println("Next Song : " + title);
		CurrentlyPlaying.textPane.setText(title.replaceAll(" - ", "\n"));
		window.setTitle("ElmMusic: " + title);
		pastSongs.put(playCount, filesToPlay.get(0));
		playCount = playCount + 1;
	}
	
	public static void generatePlayList(){
		if (filesToPlay.isEmpty()){
			try {
				checkDir(dir);
			} catch (IOException e) {
			}
		}
		play(new File(filesToPlay.get(playNum)));
		nextVol(vol);

		try {
			updateTitle(new File(filesToPlay.get(playNum)), playNum, false);
			filesToPlay.remove(playNum);

		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void playFirst() throws UnsupportedTagException, InvalidDataException, IOException{
		if (firstTime == true){
			Collections.shuffle(filesToPlay);
			JavaFXInitializer.initToolKit();
			firstTime = false;
			if (playCount == null){
				playCount = 0.0;
			}
			playCount = playCount + 1;
			play(new File(filesToPlay.get(0)));
			updateTitle(new File(filesToPlay.get(0)), 0, false);
			pastSongs.put(playCount, filesToPlay.get(0));
			
			filesToPlay.remove(0);
		}
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
	
	public static void API(){
		boolean isItLoaded = true;
		if (filesToPlay.isEmpty()){
			isItLoaded = false;
		}
	}
}
