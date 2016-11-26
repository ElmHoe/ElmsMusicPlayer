package uk.co.ElmHoe;

import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

public class Connection {
    public static DiscordAPI api = Javacord.getApi("MjIyMTAzOTE3MzA2MDUyNjA4.Cq4i2g.hYGoVwv8q7VyOQEWd4EGxfOTvQY", true);
	
	public static FutureCallback<DiscordAPI> connect(){
		api.connect(new FutureCallback<DiscordAPI>() {
            public void onSuccess(DiscordAPI api) {
            	api.setGame("Playin' with daddy.");
                // register listener
                api.registerListener(new MessageCreateListener() {
					@Override
					public void onMessageCreate(DiscordAPI api, Message message) {
						try {
							RegisterChatEvents.msg(message);
						} catch (InterruptedException | ExecutionException e) {System.out.println("Error with sending the message to the chatevent class, from the Connection class.");}
						
						
					}
                });
                
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
		return null;
	}
}
