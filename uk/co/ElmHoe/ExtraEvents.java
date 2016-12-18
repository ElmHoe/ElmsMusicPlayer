package uk.co.ElmHoe;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.imageio.ImageIO;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.btobastian.javacord.entities.message.Message;

public class ExtraEvents {
	static final String sqlDriver = "com.mysql.jdbc.Driver";  
	static final String sqlUrl = "jdbc:mysql://178.62.57.94/messages";
	private static String username = "java";
	private static String password = "Catchaids98.";
	public static java.sql.Connection conn = null;
	private static boolean debug = true;
	private static int debugCount = 0;

	public static HashMap<Integer, String> pasta = new HashMap<Integer, String>();
		
	public static boolean mysql(){
		try{
			
			System.out.println("Attempting");
			Class.forName(sqlDriver).newInstance();
			System.out.println("Set the class.");

			conn = DriverManager.getConnection(sqlUrl,username,password);
			System.out.println("Worked");
			//API.databaseConnected = true;
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public static void buildDatabase(String user, String id) throws SQLException{
	      Calendar calendar = Calendar.getInstance();
	      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
	      
	      String query = " insert into users (username, id, date_created)"
	    	        + " values (?, ?, ?, ?, ?)";

	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString(1, user);
	      preparedStmt.setString(2, id);
	      preparedStmt.setDate(3, startDate);
	      preparedStmt.execute();

	}
	public static void logMessagesToDatabase(String user, Message message){
		try{
		      Statement st = conn.createStatement();
		      st.executeUpdate("CREATE DATABASE IF NOT EXISTS messages;");
		}catch(Exception e){
			for (int i = 0; i < 10 ; i++){
				System.out.println(" ");
			}
			System.out.println("Issue creating the database \"messages\"");
		}
		
		
		try{
			System.out.println("" + Calendar.DAY_OF_MONTH + Calendar.MONTH + Calendar.YEAR);
			String query = " insert into messages (username, user_id, message, message_id, date, channel)"
					+ " values (?, ?, ?, ?, ?, ?)";
			
			Calendar calendar = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, message.getAuthor().getName());
			preparedStmt.setString(2, message.getAuthor().getId());
			preparedStmt.setString(3, message.getContent());
			preparedStmt.setString(4, message.getId());
			preparedStmt.setString(5, dateFormat.format(calendar.getTime()));
			preparedStmt.setString(6, message.getChannelReceiver().getName());
			preparedStmt.execute();

		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
				
		
		
	}
	
	public static BufferedImage toBufferedImage(Image img){
	    if (img instanceof BufferedImage){
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    return bimage;
	}
	
	public static void pullImageArtwork(File file) throws UnsupportedTagException, InvalidDataException, IOException{
		Mp3File song = new Mp3File(file);
		BufferedImage img = null;
		if (!(song.getId3v2Tag().getAlbumImage().equals(null))){
		     ID3v2 id3v2tag = song.getId3v2Tag();
		     byte[] imageData = id3v2tag.getAlbumImage();
		     //converting the bytes to an image
		     
		     try{
		    	 img = ImageIO.read(new ByteArrayInputStream(imageData));
			     LoadingIcons.updateIcons(img);
		     }catch(Exception e){
		     }
		}
	}
	
	/*
	 * Currently this is very basic.
	 * Set of tools for me to debug with later down the line.
	 * 
	 */
	public static void debug(String debug, Exception e){
		if (ExtraEvents.debug == true){
			debugCount = debugCount + 1;
			if (!(debug == null)){
				System.out.println(" ");
				System.out.println("DEBUG ERR: #" + debugCount + "\n" + debug);
				System.out.println(" ");
			}
			if (!(e == null)){
				System.out.println(" ");
				System.out.println("Exception Thrown: " + "\n" + e.getMessage());
				System.out.println(" ");
			}
			try {
				Writer.debugLog(debug, e);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

