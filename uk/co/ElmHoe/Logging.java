package uk.co.ElmHoe;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.message.Message;

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
	public static void Log(String user, Message message) throws InterruptedException, ExecutionException{
		if (user.equals("222103917306052608")){
			
		}else{
			ExtraEvents.logMessagesToDatabase(user, message);
			writer.println("Channel: " + message.getChannelReceiver().getName() +" > ID: " + user +  " > " +Connection.api.getUserById(user).get().getName() + "> " + message.getContent().toString());
			System.out.println("Channel: " + message.getChannelReceiver().getName() +" > ID: " + user +  " > " +Connection.api.getUserById(user).get().getName() + "> " + message.getContent().toString());
			save();
		}
	}

}
