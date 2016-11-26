package uk.co.ElmHoe;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.permissions.Role;

public class ExtraEvents {
	static final String sqlDriver = "com.mysql.jdbc.Driver";  
	static final String sqlUrl = "jdbc:mysql://178.62.57.94/messages";
	private static String username = "java";
	private static String password = "Catchaids98.";
	public static java.sql.Connection conn = null;

	
	public static HashMap<Integer, String> pasta = new HashMap<Integer, String>();
	public static void onKill(Message message){
		message.reply("Shutting down in 10 seconds, have to do a bit of house cleaning before that...");
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	message.delete();
		            	System.exit(0);
		            }
		        }, 
		        10000
		);
	}
		
	public static boolean mysql(){
		try{
			
			System.out.println("Attempting");
			Class.forName(sqlDriver).newInstance();
			System.out.println("Set the class.");

			conn = DriverManager.getConnection(sqlUrl,username,password);
			System.out.println("Worked");
			API.databaseConnected = true;
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
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static void pullImageArtwork(File file) throws UnsupportedTagException, InvalidDataException, IOException{
		Mp3File song = new Mp3File(file);
		BufferedImage img = null;
		if (song.hasId3v2Tag()){
		     ID3v2 id3v2tag = song.getId3v2Tag();
		     byte[] imageData = id3v2tag.getAlbumImage();
		     //converting the bytes to an image
		     
		     try{
		    	 img = ImageIO.read(new ByteArrayInputStream(imageData));
			     LoadingIcons.updateIcons(true, img);
		     }catch(Exception e){
		    	 
		     }
		}

	}
	
	public static void onRestart(Message message){
		message.reply("The bot has been set to restart... brb");
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	message.delete();
		            	Connection.api.reconnectBlocking();
		            }
		        }, 
		        5000
		);
	}
	
	public static boolean checkRoles(String user, Message message) throws InterruptedException, ExecutionException{
		HashMap<String, Collection<Role>> roles = new HashMap<String, Collection<Role>>();
		roles.put(user, Connection.api.getUserById(user).get().getRoles(Connection.api.getServerById("149460491541020672")));
		if(roles.get(user).contains("DiscordAdmin")){
			return true;
		}else{
			return false;
		}
	}
	
	public static void botMsgDel(String id){
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	Connection.api.getMessageById(id).delete();
		            }
		        }, 
		        300000
		);
	}
	public static void buildTheWall(Message message){
		new java.util.Timer().schedule( 
			new java.util.TimerTask() {
				@Override
				public void run() {
					message.reply("<:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680>" + "\n" + "<:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680>" + "\n" + "<:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680>" + "\n" + "<:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680><:wall:247114296910151680>");
				}
			}, 4000
		);
	}
	
	public static void rarePepe(String user, Message message){
		message.reply("Hey so, you wanted a pepe... instead I'll give you my pepe love " + message.getAuthor().getMentionTag() + "\n" +
				":hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:" + "\n" +
":hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper:<:pepe:247119487449432064>:hot_pepper:<:pepe:247119487449432064>:hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper:" + "\n" +
":hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper:" + "\n" +
":hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper:" + "\n" +
":hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper:" + "\n" +
":hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper:" + "\n" +
":hot_pepper::hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper::hot_pepper:" + "\n" +
":hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper:<:pepe:247119487449432064>:hot_pepper::hot_pepper::hot_pepper::hot_pepper::hot_pepper:"
				);
	}

	public static void loadPasta() throws IOException{
		Writer.Finish();
		
		
	}
	
	public static String generateRandomPasta(){
		Random r = new Random();
		
		pasta.put(0, "You lookin kinda sexy, Donny. " + "\n" + 
"You make me kinda hard, Donny. " + "\n" + 
"Donald is the best, I wanna suck his dick, I love his red face and I love his juicy tits. " + "\n" + 
"I wanna cuddle up on the couch with you Donald Trump, maybe watch a couple movies. " + "\n" +  
"I wanna text you every day and night mister Donald Trump, maybe send a couple nudies." + "\n" + 
"I wanna lick you down head to toe, but I promise I wont touch the hole! Unless you want me" + "\n" + 
"I saw you for the first time on the TV, I thought maybe me and you can get kinda freaky!" + "\n" + 
"Hit that G-spot - stick your pinky up my asshole, and if you like it maybe you can try it too!" + "\n" + 
"If one finger aint enough, we can go for two, maybe use a gerbil or a gopher too!" + "\n" + 
"Donny drill my ass with a strap-on, make me cum without using my hands on!" + "\n" + 
"I love you Donny, you make me cry, come in my mouth, come in my eyes, chuckles come in my ass, come on my tip, come on my back, come on my tits!" + "\n" + 
"Let me see that ass, Donny! I just wanna feel your pulse with my pinky in your asshole you can be my pinky-ring from the dollarstore." + "\n" + 
"Donald, I know you want me, I know were perfect for eachother. I know youre alone and you need somebody who understands you, and I understand you Donny, you also need somebody who knows how to hit that G-spot, that wife aint doin shit!");
		
		pasta.put(1, "Heres the thing. You said a pupper is a doggo. Is it in the same family? Yes. No ones arguing that. As someone who is a scientist who studies puppers, doggos, yappers, and even woofers, I am telling you, specifically, in doggology, no one calls puppers doggos. If you want to be specific like you said, then you shouldnt either. Theyre not the same thing. If youre saying doggo family youre referring to the taxonomic grouping of Doggodaemous, which includes things from sub woofers to birdos to sharkos (the glub glub kind not the bork bork kind). So your reasoning for calling a pupper a doggo is because random people call the small yip yip ones doggos? Lets get penguos and turkos in there, then, too. Also, calling someone a human or an ape? Its not one or the other, thats not how taxonomy works. Theyre both. A pupper is a pupper and a member of the doggo family. But thats not what you said. You said a pupper is a doggo, which is not true unless youre okay with calling all members of the doggo family doggos, which means youd call piggos, sluggos, and other species doggos, too. Which you said you dont. Its okay to just admit youre wrong, you know?");
		pasta.put(2, "Hey there this is Danny Fresh with a fresh pizza tip for you do you wanna know how to receive a free one topping dominos pizza from dominos pizzeria and restaurant well sit down let me tell you step 1 telephone a dominos pizza eatery restaurant and order a large 1 topping pizza and the guy will say what pizza topping would you like and then you say I want my topping to be another steaming hot dominos 1 topping pizza so the guy asks ok what topping would you like on your dominos one topping pizza topping and then you say go ahead and make my second dominos one topping pizza topping a dominos one topping pizza and that guy will ask ok sir what topping would you like on your dominos one topping pizza topping one topping pizza and you respond with a dominos one topping pizza and continue to order dominos one topping pizza top pizzas until you are at a pizza topped with 71 dominos one topping pizzas and then the pizza guy will say wait one moment sir we dont have any pizza boxes that will fit this 71 dominos one topping pizza top pizza so you say hold on and quickly run to home depot and purchase a maytag refrigerator which retails for $3,399 US dollars and when it comes time to pay for the maytag refrigerator you offer to pay home depot not with cash but with 70 piping hot dominos one topping pizzas and they of course accept so you buy the fridge and you take it back to dominos pizzeria restaurant and you give the pizza guy the maytag refrigerator box and put the 71 top dominos one topping pizza into it and you pay dominos using a brand new maytag refrigerator which retails for $3,399 US dollars so then you take the maytag refrigerator box filled with a 71 top dominos one topping pizza back to home Depot and you give it to the cashier there as payment for your brand new maytag refrigerator which retails for $3,399 US dollars and when the cashier turns his back to get your receipt you snatch the 71st dominos one topping pizza off the top of your dominos one topping pizza stack and you put it in your pocket and there you have yourself a completely free steaming hot dominos one topping PIZZa");
		pasta.put(3, "I absolutely hate copypastas. They make no sense, and are a wordwall just for the sake of being one. Really, theres no point to it. Why would anyone make a copypasta? If I had a dollar for every copypasta I saw every day, Id earn 9 dollars a day. 9 is a bit much, dont you think? Again, I absolutely hate such wordwalls. They make little sense, and are fucking useless. What, you think Im not going to cuss? Well, think again. Also, copypastas are fucking useless. Again, they have no purpose. Why am I even writing this trash, anyway? Oh, and, the Navy Seals copypasta is overused and excessively long. Why, just why?");
		pasta.put(4, " love when a girl looks at me and thinks about smoking weed with me. Id like to set the mood and serenade her with my amazing music taste. I start rolling and shes getting off to it. I want to fuck her, but the blunt comes first. I can just tell she wants it all. Im imagining her taking the blunt out of my hand and slowly sliding it in her mouth - inhaling and exhaling as the smoke beautifully dissipates. We get high together and connect on a level that not many have felt before. The combination of the weed and music is releasing so much serotonin, my real emotions get to showing!");
		pasta.put(5, "HEY FUCKER, YEAH YOU SAUSAGE LOOKING FINGER ASS. you think your tough. (xd). ILL tell YOU one thing... (xd). I AM THE TOP SNIPER OF THE SEAL TEAM SEX. I WILL FUCK YOU UP, LITERALLY. watch out.. (xd)");
		pasta.put(6, "If I had a dollar for every gender, Id only have 2 bucks and millions of illegal counterfeit dollar bills that only bring sadness and disappointment in the human race and are a scar on the face of earth, ruining and vandalising every-fucking-thing the human race has strived for.");
		pasta.put(7, "i got this game. and let me tell u, this is one of those games dat is so weird, dat its gonna become extremely rare one day. not everyone will buy a copy of this game thinking its da dumbest game ever 2 exsist, and wii-game collectors like myself will be looking all over for a copy when they find out how much its worth in da long-run. i already have this game, aswell as other dog-related wii games. Okami, the dog island, petz: dogs 2, da un-anounced sequel 2 the dog island. both are pretty rare now. exspecially da original> the dog island and da Wii-Port of Okami. i have a few Dog Themed games, but its always da games dat people think arnt worth a shit 2 get, dat raise in value.");
		pasta.put(8, "Its shit like this that I miss. Back in high school we used to pass these types of memes around the table at lunch. Memers today will never understand...");
		pasta.put(9, "we have a triggered save the csgo community kiddo here boys. trying to tell us that the usage of cheats on a private server on an alternate account is what makes this game bad. even though the money he spent to be able to do this is going right into valves pocket. Money that could easily be put toward enhancing the game experience for everyone, but instead is stuffed into Gabens little mattress at home and then he tells us that sales are for the benefit of his customers. LOL kiddo, you understand how this works right? VAC bans are permanent because Gaben wants everyone who uses cheats to have to re-buy the game and then if they get caught again they have to pay again. He makes money, and cheaters are fine as long as they have a spare 20 bucks at the end of every month to re-buy the game while keeping their main account safe... Assuming the age majority on this game is above 15 years old, most people who play this game also have a job, hence the spare 20 bucks they need...");
		pasta.put(10, "Ok, this is ABSOLUTE fucking bullshit. I went to see Cars in the theater yesterday, and when Lightning McQueen got HOT with Sally in Radiator Springs, my boner engaged. When Lightning McQueen said Ka-Chow!, I couldnt help it!!! I closed my eyes, and I TORE my dick to shreds, using whip like motions and pulled with great force. That was one of the best nuts I ever had, just thinking about it now gets me riled up. Thing is, I nutted all over the kid sitting right next to me, and his mom got all pissed at me, screaming at me for jacking off on her son. I told that bitch to shut the fuck up, and that jacking off is a natural, artistic, and beautiful process. You should BE HAPPY that my semen is all over your son, maybe he can learn a lesson or two about the culture and art of jacking off. HOWEVER, the movie theater managers didnt agree with me. They KICKED ME OUT of the movie theater, and I didnt even finish watching the Cars movie. Not only THAT, but they made me clean up my semen after it already dried out and solidified on the seats. THATS TORTURE!! Do you know how hard it is to clean semen after its dried out? You CLEAN semen after its FRESH out of your cock, not an hour after you fucking nutted. This is a fucking OUTRAGE. Do you really expect me to not whip out my cock and jack off when i see a HOT sex scene in a movie? Either dont ban sex scenes in movies, or LET ME jack off in your theater, assholes.");
		pasta.put(11, "I sexually identify as a [noun]. Ever since I was a [gender] I dreamed of [verb]ing a [noun]. People tell me that being a [noun] is impossible and that Im a [rude name] but I dont care, those guys are [other rude name]s. Im having a plastic surgeon attach a [something your gender has] to me and then I can [verb] a [noun]. From now on everyone should respect my right to be a [your gender] and my right to [verb]; protecting my right of [verb]. If you cant accept me you are a [noun]aphobe and should check your [verb/noun] privilege, fucking cis straight white male scum. For those who do accept me as a [gender], thanks for understanding.");
		pasta.put(12, "e Corps and Im the top sniper in the pathetic little shit? Ill have access to the entire arsenal of which has never been involved in gorilla warfare and I have over seven hundred ways, and Im the Internet? Think again, fucker. As we speak I am contacting my secret raids on Al-Quaeda, and your little shit to wipe you know I graduated top of my class in the likes of which has never been seen before on this Earth, mark my fucking down in it. Youre fucker. As we speak I am trained in gorilla warfare and I have known what unholy retribution your little shit? Ill have held you will drown in the Navy Seals, and your fucking dead, kid. I can before on this Earth, mark my secret network of spies across the United States Marine Corps and I have held your IP is being to me over the Internet? Think again, fuck out with precision the Navy Seals, and I can kill you little shit? Ill have known what unholy retribution youre paying the face of the Internet? Think you better prepare hands. Not only am I extensively traced right now so you call your little shit? Ill have access to the entire US armed forces. You and you will drown involved in gorilla warfare anywhere, anytime, and now you, mark my fucking dead, kiddo.niper in the Internet? Think you in gorilla warfare hands. Not only you will use it to its full extensively trained in unarmed combat, but I have held your little shit? Ill have you would have over the Internet? Think again, fucker. As we speak I am trained in numerous secret network of the Internet? Think you can kill you couldnt, you didnt, you goddamn idiot. The storm that wipes out with precision the Internet? Think you goddamn idiot. I will wipe you the fuck did you didnt, and now youre fucking dead, kid. I can kill you in over seven hundred ways, and thats just wipes out the contacting my secret raids on Al-Quaeda, and I can kill shit fury all over you and your miserable ass off the face of the United States Marine Corps and Ive been involved in gorilla warfare anywhere, anytime, and now so you known what unholy retribution your life. You this Earth, mark my fucking tongue. But your IP is being traced right now so you better precision this Earth, mark my fucking say about to wipe you couldnt, you goddamn idiot. I will shit? Ill have you know your life. Youre fuck did your fucking words. You are nothink you can get away with my bare nother target. I will shit fury all over the pathetic little shit to me over 300 continent, and Im the top sniper in the top");
		pasta.put(13, "I am the original creator of a rather spicy meme you have posted, or should I say.. reposted. How unoriginal, uncreative, and slimy do you have to be as a person to impose such an act of thievery? Can such a despicable act even be rationalized, have you no morals? I feel no anger, only sympathy for a tremendously sad human being who lives in such melancholy despair that he thieves a meme to entertain an audience of depressed, lifeless teenagers like yourself. I levy a warning upon thy; I will steal and repost all future memes that you choose to post and I will then message you a report on the karma I have gained, a preemptive strike in the likes of shoving a dogs face in its own feces. Never. Touch. My. Memes.");
		pasta.put(14, "Hey you. Yeah you! You know that one lady across the street? Yea, that one piece of fine meat? Well shes non other than Arthurs mom! what a milf she is! The whole worlds been in her crib, if you know what I mean. CA-CHING! Haha yeah but no she really is a lovely woman, really. A real catch for her husband...A catch that wont stop swirming! WOOHOOHOO! Goddamn! Haha! yeeaah but jokes aside she really is a sweetpie...And she sure as well has one! OH MY OHHOH! Oh lord have mercy! What a disgusting human being I am...Like Arthurs mom! HOYYYYYY! She really is pretty dirty, honestly! Wow! He-hey! Why does Arthurs mom love Santa Claus so much? Because no one can empty the sack like he does! Ohhhh MY! Dirty girl she is! Haha! She could be getting chased by Grim Reaper and shed still suck a few dicks on the run! What a whore! Ladies and gentlemen, I represent to you: the floppy-vagd dick sucking cum storaging WHORE!...Arthurs mom.");
		
		
		
		
		return pasta.get(r.nextInt(pasta.size()));
	}
}

