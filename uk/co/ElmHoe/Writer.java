package uk.co.ElmHoe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	static String logLoc = System.getProperty("user.dir");

    public static PrintWriter writer = null;

	
	public static void Finish() throws IOException{
		File f = new File(logLoc + "\\Pasta.yml");
		if (!(f.exists())){
			f.getParentFile().mkdirs();
		}else{
		}

		writer = new PrintWriter(f);			
		save();
		
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	try{
			    	ExtraEvents.pasta.put(ExtraEvents.pasta.size() + 1, line.replaceAll("\"", "").replaceAll("'", ""));		    		
		    	}catch(Exception e){
		    		
		    	}
		    }
		}
	}
	public static void save(){
		writer.flush();
	}
	public static void close(){
		writer.close();
	}

}
