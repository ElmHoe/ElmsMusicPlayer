package uk.co.ElmHoe;

import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class KeyListener {
    public static boolean run = true;
	public static double volume = 50;

	public static void main(String[] args) {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

        System.out.println("Global keyboard hook successfully started, press [ins] key to shutdown.");
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override public void keyPressed(GlobalKeyEvent event) {
            	//System.out.println(event);
                if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_MEDIA_NEXT_TRACK){
                	try {
                		if (API.isPlayerPlaying() == true){
                			Main.skip();
                		}else{
                			Main.playAndPause();
                			Main.skip();
                		}
                		
					} catch (UnsupportedTagException | InvalidDataException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD4){
                	System.out.println(Main.vol);
                	double newVol = (int)Main.vol - 0.1;
                	if (newVol >= 91){
                		newVol = 100;
                	}
                	Main.vol(Integer.parseInt(newVol + ""));
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD6){
                	double newVol = (int)Main.vol + 0.1;
                	if (newVol <= 91){
                		newVol = 100;
                	}
                	Main.vol(Integer.parseInt(newVol + ""));
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_NUMPAD5){
                	Main.playAndPause();

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