package uk.co.ElmHoe;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class ExtraEvents {

	public static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    return bimage;
	}
	
	public static void pullImageArtwork(File file) throws UnsupportedTagException, InvalidDataException, IOException
	{
		Mp3File song = new Mp3File(file);
		BufferedImage img = null;
		if (!(song.getId3v2Tag().getAlbumImage().equals(null)))
		{
		     ID3v2 id3v2tag = song.getId3v2Tag();
		     byte[] imageData = id3v2tag.getAlbumImage();
		     //converting the bytes to an image
		     
		     try
		     {
		    	 img = ImageIO.read(new ByteArrayInputStream(imageData));
			     LoadingIcons.updateIcons(img);
		     }catch(Exception e)
		     {
		     }
		}
	}
}

