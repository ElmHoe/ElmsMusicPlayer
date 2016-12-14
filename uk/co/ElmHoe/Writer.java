package uk.co.ElmHoe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	static String logLoc = System.getProperty("user.dir");

    public static PrintWriter writer = null;
    public static boolean firstTime = false;
	
	
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

}
