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
import java.awt.Image;
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
	public static Container contentPane = null;
	

	
	public CurrentlyPlaying(){
		contentPane = getContentPane();
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 700);
		textPane.setOpaque(false);

		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		ImageIcon imageIcon = new ImageIcon(Main.class.getResource("/resources/noArtwork.png")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(250, 250,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		ImageIcon bgIcon = new ImageIcon(Main.class.getResource("/resources/bg.jpg")); // load the image to a imageIcon
		Image bgImage = bgIcon.getImage(); // transform it 
		Image bgImg = bgImage.getScaledInstance(380, 700,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		bgIcon = new ImageIcon(bgImg);  // transform it back
		JLabel background;
		background = new JLabel(bgIcon);
		this.setContentPane(background);

		
		JLabel lable1 = new JLabel("");
		lable1.setIcon(imageIcon);
		lable1.setBounds(60, 79, 250, 250);
		this.getContentPane().add(lable1);

		
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
			SFREG = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream ("/resources/SFTHIN.ttf")).deriveFont(12f);
			
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream ("/resources/SFBOLD.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream ("/resources/SFTHIN.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream ("/resources/SFREG.ttf")));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
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

		getContentPane().add(textPane);
		getContentPane().add(textPane_2);

		
		JButton btnTimePlayed = new JButton("Time Played - Time Left");
		btnTimePlayed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTimePlayed.setBounds(10, 357, 354, 23);
		getContentPane().add(btnTimePlayed);
		
		JButton button_1 = new JButton("Back");
		button_1.setBounds(10, 427, 86, 36);
		getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Main.goBack();
			}
		});
		
		JButton button_play = new JButton("Play");
		button_play.setVisible(true);
		
		JButton button_2 = new JButton("Pause");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button_2.setVisible(false);
				button_play.setVisible(true);
				Main.playAndPause();
			}
		});
		button_2.setBounds(145, 411, 86, 68);
		getContentPane().add(button_2);
		
		button_play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button_2.setVisible(true);
				button_play.setVisible(false);
				Main.playAndPause();

			}
		});
		button_play.setBounds(145, 411, 86, 68);
		getContentPane().add(button_play);

		
		JButton button_3 = new JButton("Skip");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.skip();
				if (button_2.isVisible() == false){
					button_2.setVisible(true);
					button_play.setVisible(false);
				}
			}
		});
		button_3.setBounds(278, 427, 86, 36);
		getContentPane().add(button_3);
		
        JSlider slider = new JSlider(0, 100, 50);
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
            	if (Main.Playing == true){
            		int newVolume = slider.getValue();
            		System.out.println("volume updated to " + newVolume);
            		Main.vol(newVolume);
            	}else{
            		slider.setValue(50);
            	}
            }
        	
        });
		getContentPane().add(slider);
	}
}
