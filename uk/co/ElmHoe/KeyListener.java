package uk.co.ElmHoe;

import javafx.scene.media.MediaPlayer;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyListener {
    public static boolean run = true;
	public static int oldVolume = (int)MediaPlayerAPI.vol * 100;

	public static void main(String[] args) {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

        System.out.println("Global keyboard hook successfully started, press [ins] key to shutdown.");
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed(GlobalKeyEvent event) {
            	oldVolume = (int)MediaPlayerAPI.vol * 100;
            	//System.out.println(event);
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_MEDIA_NEXT_TRACK){
                	if (MediaPlayerAPI.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
						MediaPlayerAPI.onSongEnd();
					}else{
						MediaPlayerAPI.playAndPause();
						MediaPlayerAPI.onSongEnd();
					}
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD4){
                	System.out.println(MediaPlayerAPI.vol * 100);
                	oldVolume = oldVolume - 10;
                	MediaPlayerAPI.onVolumeUpdate(oldVolume);
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD6){
                	System.out.println(MediaPlayerAPI.vol * 100);
                	oldVolume = oldVolume + 10;
                	MediaPlayerAPI.onVolumeUpdate(oldVolume);
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD5){
                	MediaPlayerAPI.playAndPause();

                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_INSERT){
                	
                	run = false;
                	System.out.println("ANY BINDS ON THE KEYBOARD WILL NO LONGER FUNCTION, KEY LOGGING HAS BEEN SHUT DOWN!");
                }
            }
            @Override public void keyReleased(GlobalKeyEvent event) {
                //System.out.println(event);
            	}
        });

        try {
            while(run) Thread.sleep(128);
        } catch(InterruptedException e) { /* nothing to do here */ }
          finally { keyboardHook.shutdownHook(); }
	}	
}