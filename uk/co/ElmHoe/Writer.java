package uk.co.ElmHoe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Writer {
	static String logLoc = System.getProperty("user.dir");

    public static PrintWriter writer = null;
    public static boolean firstTime = true;
	
	
	public static void save(){
		writer.flush();
	}
	public static void close(){
		writer.close();
	}
	public static void buildLocalFile() throws IOException{
		File songList = new File(logLoc + "\\ElmsMusic.json");
		if (songList.exists() == false){
			songList.getParentFile().mkdirs();
			firstTime = true;
			
		}
		writer = new PrintWriter(songList);
		
		save();
		try (BufferedReader br = new BufferedReader(new FileReader(songList))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	try{
		    		System.out.println(line);
		    	}catch(Exception e){
		    		
		    	}
		    }
		}
	}
	public static void debugLog(String debugLog, Exception e) throws FileNotFoundException{
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();

		File log = new File(logLoc + "\\log#.yml");
		if (firstTime == true){
			if (log.exists() == false){
				log.getParentFile().mkdirs();
			}
			writer = new PrintWriter(log);
		}

		
		if (firstTime == true){
			
			writer.write("DEBUGGING BEGUN: " + df.format(dateobj) + "\n");
			firstTime = false;

		}
		DateFormat df1 = new SimpleDateFormat("HH:mm:ss");

		if (e == null){
			writer.write(df1.format(dateobj) + ": " + debugLog + "\n");

		}else{
			writer.write(df1.format(dateobj) + ": " + debugLog + e.getMessage() + "\n");

		}
		
		save();
		
	}

}
