package uk.co.ElmHoe;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.scene.control.Slider;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class CurrentlyPlaying extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static JTextPane textPane = new JTextPane();
	public static JTextPane textPane_2 = new JTextPane();
	public Container contentPane = getContentPane();
	public static JSlider slider1 = null;
	public static Slider slider2 = null;
	public static ImageIcon noArtwork = null;
	public static ImageIcon buttonBack;
	public static ImageIcon buttonSkip;
	public static ImageIcon buttonPlay;
	public static ImageIcon buttonPause;
	public static ImageIcon bgIcon;
	public static JLabel lable1;
	
	
	public CurrentlyPlaying(){

 
		if (LoadingIcons.loaded == true){
			noArtwork = LoadingIcons.noArtwork;
			buttonBack = LoadingIcons.buttonBack;
			buttonSkip = LoadingIcons.buttonSkip;
			buttonPlay = LoadingIcons.buttonPlay;
			buttonPause = LoadingIcons.buttonPause;
			bgIcon = LoadingIcons.bgIcon;
			
			JLabel background;
			background = new JLabel(bgIcon);
			contentPane = background;
		}else{
			System.out.println("ERROR, Failed to load icons for images... Has the code been tampered with?!");
		}
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 700);
		this.setResizable(false);
		textPane.setOpaque(false);

		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		
		lable1 = new JLabel("");
		lable1.setIcon(noArtwork);
		lable1.setBounds(60, 79, 250, 250);
		contentPane.add(lable1);

		
		textPane.setEditable(false);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font SFBOLD = null;
		@SuppressWarnings("unused")
		Font SFTHIN = null;
		@SuppressWarnings("unused")
		Font SFREG = null;
		try {
			SFBOLD = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream ("/resources/SFBOLD.ttf")).deriveFont(16f);
			SFTHIN = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream ("/resources/SFTHIN.ttf")).deriveFont(12f);
			SFREG = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream ("/resources/SFREG.ttf")).deriveFont(12f);
			
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream ("/resources/SFBOLD.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream ("/resources/SFTHIN.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream ("/resources/SFREG.ttf")));
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}

		textPane.setFont(SFBOLD);
		
        Font font = new Font("Courier", Font.ITALIC,14);
		textPane_2.setOpaque(false);
		textPane_2.setFont(font);
		textPane.setBounds(10, 11, 345, 58);
		textPane_2.setBounds(10, 525, 345, 120);
		textPane_2.setEditable(false);
		textPane_2.setBackground(SystemColor.inactiveCaption);
		textPane_2.setForeground(new Color(0, 191, 255));
		textPane.setForeground(new Color(0, 191, 255));

		contentPane.add(textPane);
		contentPane.add(textPane_2);

		JButton button_Back = new JButton();
		button_Back.setIcon(buttonBack);
		button_Back.setContentAreaFilled(false);
		button_Back.setBorderPainted(false);
		button_Back.setIcon(buttonBack);
		button_Back.setBounds(40, 400, 86, 68);
		contentPane.add(button_Back);

		JButton button_Play = new JButton();
		button_Play.setIcon(buttonPlay);
		button_Play.setContentAreaFilled(false);
		button_Play.setBorderPainted(false);
		button_Play.setBounds(145, 400, 86, 68);
		contentPane.add(button_Play);

		JButton button_Pause = new JButton();
		button_Pause.setIcon(buttonPause);
		button_Pause.setContentAreaFilled(false);
		button_Pause.setBorderPainted(false);
		button_Pause.setBounds(145, 400, 86, 68);
		contentPane.add(button_Pause);

		JButton button_Skip = new JButton();
		button_Skip.setIcon(buttonSkip);
		button_Skip.setContentAreaFilled(false);
		button_Skip.setBorderPainted(false);
		button_Skip.setBounds(248, 400, 86, 68);
		contentPane.add(button_Skip);


		
		
        JSlider slider = new JSlider(0, 100, 50);
        slider1 = new JSlider(0, 100, 50);

		
		button_Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Main.goBack();
			}
		});
		button_Play.setVisible(false);

		
		button_Pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (API.isPlayerPlaying() == true){
					button_Pause.setVisible(false);
					button_Play.setVisible(true);
					Main.playAndPause();
				}else{
					button_Pause.setVisible(true);
					button_Play.setVisible(false);

				}
			}
		});
		
		button_Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (API.isPlayerPlaying() == false){
					button_Pause.setVisible(true);
					button_Play.setVisible(false);
					Main.playAndPause();
				}else{
					button_Pause.setVisible(false);
					button_Play.setVisible(true);

				}
			}
		});

		
		button_Skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.skip();
				} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (button_Pause.isVisible() == false){
					button_Pause.setVisible(true);
					button_Play.setVisible(false);
				}
			}
		});
		
        UIDefaults sliderDefaults = new UIDefaults();
        sliderDefaults.put("Slider.thumbWidth", 20);
        sliderDefaults.put("Slider.thumbHeight", 20);
        slider.putClientProperty("Nimbus.Overrides",sliderDefaults);
        slider.putClientProperty("Nimbus.Overrides.InheritDefaults",false);

        sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new Painter<JComponent>() {
            public void paint(Graphics2D g, JComponent c, int w, int h) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2f));
                g.setColor(Color.RED);
                g.fillOval(1, 1, w-3, h-3);
                g.setColor(Color.WHITE);
                g.drawOval(1, 1, w-3, h-3);
            }
        });
        sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new Painter<JComponent>() {
            public void paint(Graphics2D g, JComponent c, int w, int h) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2f));
                g.setColor(Color.GRAY);
                g.fillRoundRect(0, 6, w-1, 8, 8, 8);
                g.setColor(Color.WHITE);
                g.drawRoundRect(0, 6, w-1, 8, 8, 8);
            }
        });	
        slider.setBounds(10, 490, 354, 23);
        slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event) {
        		int newVolume = slider.getValue();
        		System.out.println("Volume updated to " + newVolume);
        		Main.vol(newVolume);
        	
            }
        	
        });
		contentPane.add(slider);
		
        
        //slider1.addChangeListener();
        contentPane.add(slider1);
        
        this.setContentPane(contentPane);
	}
        
}
