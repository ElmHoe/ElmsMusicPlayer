package uk.co.ElmHoe;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class LoadingIcons {
	
	public static boolean loaded = false;
	public static ImageIcon noArtwork;
	public static ImageIcon buttonBack;
	public static ImageIcon buttonSkip;
	public static ImageIcon buttonPlay;
	public static ImageIcon buttonPause;
	public static ImageIcon bgIcon;

	
	public static boolean loadIcons(){
		if (loaded == true){
			return true;
		}else{
			try{
				noArtwork = new ImageIcon(Main.class.getResource("/resources/noArtwork.png"));
				Image image = noArtwork.getImage(); 
				Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
				noArtwork = new ImageIcon(newimg); 
		
				
				buttonBack = new ImageIcon(Main.class.getResource("/resources/back.png"));
				Image buttonBackImage = buttonBack.getImage(); 
				Image newButtonBack = buttonBackImage.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonBack = new ImageIcon(newButtonBack); 
				
				buttonSkip = new ImageIcon(Main.class.getResource("/resources/Skip.png"));
				Image imageSkip = buttonSkip.getImage(); 
				Image newSkip = imageSkip.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonSkip = new ImageIcon(newSkip); 
				
				buttonPlay = new ImageIcon(Main.class.getResource("/resources/Play.png"));
				Image imagePlay = buttonPlay.getImage(); 
				Image newPlay = imagePlay.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonPlay = new ImageIcon(newPlay); 
				
				buttonPause = new ImageIcon(Main.class.getResource("/resources/Pause.png"));
				Image imagePause = buttonPause.getImage(); 
				Image newPause = imagePause.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonPause = new ImageIcon(newPause); 
		
				bgIcon = new ImageIcon(Main.class.getResource("/resources/Background.JPG"));
				Image bgImage = bgIcon.getImage(); 
				Image bgImg = bgImage.getScaledInstance(380, 700,  java.awt.Image.SCALE_SMOOTH);
				bgIcon = new ImageIcon(bgImg);
				
				
				loaded = true;
				return loaded;
			}catch (Exception e){
				System.out.println("There's been an error...");
				e.printStackTrace();
				return loaded;
			}
		}
	}
	
	public static void updateIcons(boolean artwork, BufferedImage img){
		if (artwork == true){
			Image newimg = img.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
			noArtwork = new ImageIcon(newimg); 

		}else{
			noArtwork = new ImageIcon(Main.class.getResource("/resources/noArtwork.png"));
			Image image = noArtwork.getImage(); 
			Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
			noArtwork = new ImageIcon(newimg); 

		}
		CurrentlyPlaying.lable1.setIcon(noArtwork);
	}
}
