package uk.co.ElmHoe;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import de.btobastian.javacord.entities.message.Message;

public class RegisterChatEvents{
	private static String prefix = ";";
	private static String user;
	private static Message msg;
	


    public static void msg(Message message) throws InterruptedException, ExecutionException {
    	msg = message;
    	user = message.getAuthor().getId();
    	Logging.Log(user, message);
    	if (message.getAuthor().getId().equals("222103917306052608")){		ExtraEvents.botMsgDel(message.getId());}
    	if (message.getContent().startsWith(prefix)){						ExtraEvents.botMsgDel(message.getId());}
    	if (message.getContent().startsWith(prefix + "help")){      		help(user);
    	}else if (message.getContent().startsWith(prefix + "h")){			help(user);
    	}else if (message.getContent().startsWith(prefix + "restart")){   	ExtraEvents.onRestart(message);
    	}else if (message.getContent().startsWith(prefix + "r")){			ExtraEvents.onRestart(message);
    	}else if (message.getContent().startsWith(prefix + "shutdown")){	ExtraEvents.onKill(message);
    	}else if (message.getContent().startsWith(prefix + "ping")){		ping(user, message);
    	}else if (message.getContent().startsWith(prefix + "skip")){		skip(user, message);
    	}else if (message.getContent().startsWith(prefix + "admin")){		admin(user, message);
    	}else if (message.getContent().startsWith(prefix + "a")){			admin(user, message);
    	}else if (message.getContent().startsWith(prefix + "play")){		play(user, message);
    	}else if (message.getContent().startsWith(prefix + "setgame")){		setGame(user, message);
    	}else if (message.getContent().startsWith(prefix + "sg")){			setGame(user, message);
    	}else if (message.getContent().startsWith(prefix + "sa")){			updateAvatar(user, message);
    	}else if (message.getContent().startsWith(prefix + "setavatar")){	updateAvatar(user, message);
    	}else if (message.getContent().startsWith(prefix + "channels")){	getChannels(user, message);
    	}else if (message.getContent().startsWith(prefix + "volume")){		volume(user, message);
    	}else if (message.getContent().startsWith(prefix + "ily")){			ily(user, message);
    	}else if (message.getContent().startsWith(prefix + "pasta")){		pasta(user, message);
    	}else if (message.getContent().startsWith(prefix + "wall")){		buildTheWall(user, message);
    	}else if (message.getContent().startsWith(prefix + "pepe")){		pepe(user, message);
    	}else if (message.getContent().startsWith(prefix + "kms")){			kms(user, message);

    	}
		
		
		
	}
    private static void pepe(String user, Message message){
    	ExtraEvents.rarePepe(user, message);
    	
    }
    
    private static void kms(String user, Message message){
    	message.reply("Alright listen up you faggot. You're talking to a bot right now and informing a bot that you want to kill yourself? holy shit this is cringeworthy go KYS please you autistic shit stain did you honestly think that people who dont know you or give a single fuck about you care about this? If you did then you are a fucking retard well congratioulations retard but we dont give a fuck wow this subreddits fallen so far hang on im going to just walk over here and kill myself thats how fucking retarded this post is that i killed myself and came back as a ghost to write this");
    }
    private static void buildTheWall(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
    		message.reply("Oh, so you'd like a wall? Well you know what " + message.getAuthor().getMentionTag() + "\n" + " A wall, is what you'll get...");
    		
    			ExtraEvents.buildTheWall(message);
    	}

    }
    private static void pasta(String user, Message message){
    	String Pasta = ExtraEvents.generateRandomPasta();
    	message.reply(Pasta);
    }
    private static void ily(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
    		message.reply("ily more " + message.getAuthor().getMentionTag());
    	}else if (Connection.api.getUserById(user).get().getId().equals("150701733335662592")){
    		message.reply("ily more beauty (;");
    	}else{
    		message.reply("no fuck u.");
    	}
    }
    public static void newSong(String song){
    	try{
    		msg.reply("Now Playing: " + song);   
    	}catch(Exception e){
    		
    	}
    }
    
    private static void help(String user){
    	try {
			Connection.api.getUserById(user).get().sendMessage(
					"        - ElmsBot -" + "\n" +
					"Usage: " + "\n" +
					"          ;<command> <args>"+ "\n"+
					"Commands:"+ "\n"+
					"          <H/Help>" + "\n" +
					"          Returns the help page via message."+ "\n" +
					" "+ "\n" +
					"          <R/Restart>"+ "\n" +
					"          Restarts the bot."+ "\n" +
					" "+ "\n" +
					"          <Shutdown>"+ "\n" +
					"          Shutsdown the bot."+ "\n" +
					" "+ "\n" +
					"          <Ping>"+ "\n" +
					"          Pings da bot."+ "\n" +
					" "+ "\n" +
					"          <A/Admin>"+ "\n" +
					"          Returns an admin log."+ "\n" +
					" "+ "\n" +
					"          <SG/SetGame> [args]"+ "\n" +
					"          Sets the bots game."+ "\n" +
					" "+ "\n" + 
					"          <SA/SetAvatar> [args]"+ "\n" +
					"          Sets the bots avatar to a link given."
					);
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("There's been an issue getting the user and sending them the help page.");
		}
    }
   
    private static void ping(String user, Message message){
    	if (message.getContent() == ";ping"){
    		message.reply("" + message.getAuthor().getMentionTag() + " PONG");
    	}else{
    		String newMsg = message.getContent().replace(";ping", "").replace("<", "").replace(">", "").replace("@", "");
    		try {
				message.reply("" + Connection.api.getUserById(newMsg).get().getMentionTag() + " GET PONG'D KID.");
			} catch (InterruptedException | ExecutionException e) {
				message.reply("I struggled to pong " + newMsg+ " pls forgive me.");
			}
    	}
    }
    
    public static void skip(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
        	Connection.api.getChannelById("214075987799834625").sendMessage("#skip");
    	}else{
    		message.reply("You lack permission to run this command... Contact sir @ElmHoe for more information.");
    	}
    }
    
    public static void volume(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
            String volume = message.getContent().substring(5);

        	Connection.api.getChannelById("214075987799834625").sendMessage("#volume " + volume);
    	}else{
    		message.reply("You lack permission to run this command... Contact sir @ElmHoe for more information.");
    	}
    }
    
    private static void admin(String user, Message message) throws InterruptedException, ExecutionException{
    	message.reply(
		"Server Name: " + Connection.api.getServerById("149460491541020672").getName() + "\n" + 
		"Server ID: 149460491541020672" + "\n" + 
		"Total Members: " + Connection.api.getServerById("149460491541020672").getMemberCount() + "\n" +
		"Your Roles: " + Connection.api.getUserById(user).get().getRoles(Connection.api.getServerById("149460491541020672")));
    }
    
    public static void play(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129") && (message.getContent().length() >= 5)){
        	Connection.api.getChannelById("214075987799834625").sendMessage("#play" + message.getContent().replace("#play", "").replace("#p", ""));
    	}else{
    		message.reply("You lack permission to run this command... Contact sir @ElmHoe for more information.");
    	}
    }
    
    private static void setGame(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
    		if (message.getContent().length() >= 3){
    			String game = message.getContent().replace(prefix + "sg", "").replace(prefix + "setgame", "");
    			message.reply("The game has been updated to > " + game);
    			Connection.api.setGame(game);
    		}
    	}
    }
    
    private static void updateAvatar(String user, Message message) throws InterruptedException, ExecutionException{
    	if (Connection.api.getUserById(user).get().getId().equals("129479613117104129")){
    		String imageLink = message.getContent().replace(prefix + "setavatar", "").replace(prefix + "sa", "");
    		if (imageLink.length() <=1){
    			message.reply("Please make sure you've used an actual image...");
    		}else{
	    			
	    		BufferedImage image = null;
	    		try {
	    		    URL url = new URL(imageLink);
	    		    image = ImageIO.read(url);
	        		Connection.api.updateAvatar(image);
	        		message.reply("The image has been successfully updated.");
	    		} catch (IOException e) {
	    			message.reply("There was an issue updating the avatar of the bot...");
	    			System.out.println("STACKTRACE: " + e.getMessage());
	    		}
    		}
    	}
    }
    
/*    private static void joinVoiceChannel(String user, Message message){
    	List<VoiceChannel> channels = new ArrayList<VoiceChannel>();
    	for (int i = 0; i < Connection.api.getVoiceChannels().size() ; i++){
    		channels.add((VoiceChannel) Connection.api.getVoiceChannels());
    	}
    	if (Connection.api.getVoiceChannelById("").getName().equals("ElmHoe's Room!")){
    	}
    }*/
    
    private static void getChannels(String user, Message message){
    	Connection.api.getChannelById("212932946556878848").sendMessage("Channels:" + "\n" + Connection.api.getChannels()
    	+ "\n" + "Voice Channels:" + "\n" + Connection.api.getVoiceChannels());
    	
    }
    
}
