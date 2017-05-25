package uk.co.ElmHoe;

import java.awt.Graphics;
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
				
				noArtwork = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/noArtwork.png"));
				Image image = noArtwork.getImage(); 
				
				BufferedImage noArt = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
				Graphics g = noArt.createGraphics();
				g.drawImage(noArt, 0, 0, null);
				Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
				
				noArtwork = new ImageIcon(newimg); 
		
				
				buttonBack = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/back.png"));
				Image buttonBackImage = buttonBack.getImage(); 
				Image newButtonBack = buttonBackImage.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonBack = new ImageIcon(newButtonBack); 
				
				
				buttonSkip = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/Skip.png"));
				Image imageSkip = buttonSkip.getImage(); 
				Image newSkip = imageSkip.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonSkip = new ImageIcon(newSkip); 
				
				
				buttonPlay = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/Play.png"));
				Image imagePlay = buttonPlay.getImage(); 
				Image newPlay = imagePlay.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonPlay = new ImageIcon(newPlay); 
				
				
				buttonPause = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/Pause.png"));
				Image imagePause = buttonPause.getImage(); 
				Image newPause = imagePause.getScaledInstance(86, 68,  java.awt.Image.SCALE_SMOOTH);
				buttonPause = new ImageIcon(newPause); 
		
				
				bgIcon = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/Background.JPG"));
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
	
	public static boolean updateIcons(BufferedImage img){
		return loaded;
/*		if (artwork == true){
			Image newimg = img.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
			noArtwork = new ImageIcon(newimg); 

		}else{
			noArtwork = new ImageIcon(MediaPlayerAPI.class.getResource("/resources/noArtwork.png"));
			Image image = noArtwork.getImage(); 
			Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH);
			noArtwork = new ImageIcon(newimg); 

		}
		CurrentlyPlaying.lable1.setIcon(noArtwork);*/
	}
}
