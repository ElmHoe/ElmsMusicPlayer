package uk.co.ElmHoe;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logging {
	
	static String logLoc = System.getProperty("user.dir");
	static DateFormat df = new SimpleDateFormat("dd-MM-yy$HH-mm");
	static Date dateobj = new Date();
    public static PrintWriter writer = null;
	
	public static void startLog() throws IOException{
		File f = new File(logLoc + "\\" + df.format(dateobj) + ".yml");
		f.getParentFile().mkdirs();

		writer = new PrintWriter(f);
						
		writer.println("-- Logging Begun --");
		writer.println(df.format(dateobj));
		save();
	
	}
	public static void save(){
		writer.flush();
	}
	public static void close(){
		writer.close();
	}

}
