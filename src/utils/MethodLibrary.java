package utils;

import static java.lang.System.out;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

/**
 * Static methods so we don't have the same methods in every other class - redundancy
 * @author Administrator
 * @version 1.2
 */
public class MethodLibrary
{
	public static String getRAMUsageString()
    {
        int ramFreeLeft = (int)(Runtime.getRuntime().freeMemory()/1024/1024);
        int ramMax =       (int)(Runtime.getRuntime().maxMemory()/1024/1024);

        return "RAM:   "+(ramMax-ramFreeLeft)+"MB / "+ramMax+" MB";
    }
	
	public static int getRandom(int minimum, int maximum)
    {
        return (int)(java.lang.Math.random()*((maximum+1)-minimum)+minimum);
    }

    public static float getRandom(float minimum, float maximum)
    {
        return (float)(java.lang.Math.random()*((maximum+1)-minimum)+minimum);
    }
	
	public static String numToFiveDigits(int in)
    {
        StringBuilder str = new StringBuilder("");

        if (in < 10000) str.append("0");
        if (in < 1000)  str.append("0");
        if (in < 100)   str.append("0");
        if (in < 10)    str.append("0");

        str.append(in);

        return str.toString();
    }
    
    public static void screenshot(Frame fr, DisplayManager displayMan)
    {
          java.awt.Rectangle captureRect = new java.awt.Rectangle(fr.getX(),fr.getY(),fr.getWidth(),fr.getHeight());

          if (!displayMan.isFullscreen())
          {
              captureRect = new java.awt.Rectangle(
            		  fr.getX() + fr.getInsets().left,
            		  fr.getY() + fr.getInsets().top,
            		  fr.getWidth() - fr.getInsets().left - fr.getInsets().right,
            		  fr.getHeight() - fr.getInsets().bottom - fr.getInsets().top);
          }

          Robot robot = null;
          try                     { robot = new Robot(); }
          catch (AWTException e1) { e1.printStackTrace(); }
          
          if (robot == null)
          {
        	  out.println("Screenshot robot is null");
        	  return ;
          }

          BufferedImage image = robot.createScreenCapture(captureRect);

          DateFormat df = new SimpleDateFormat( "HH:mm:ss  -  yyyy-MM-dd" );
          String timeStamp = df.format(Calendar.getInstance().getTime());

          Graphics2D screenG = (Graphics2D)image.getGraphics();
//          screenG.setFont(ContentManager.getFont(CF.FONT_SUPERSMALL));
          screenG.setColor(Color.black);
          screenG.drawString(timeStamp,fr.getWidth()-160+2,fr.getHeight()-45+1);
          screenG.drawString(timeStamp,fr.getWidth()-160-1,fr.getHeight()-45-1);
          screenG.setColor(Color.white);
          screenG.drawString(timeStamp,fr.getWidth()-160,fr.getHeight()-45);
          screenG.dispose();

          int screenNum = 1;
          File saveScreenFile = new File("screenshots/Quantum_Nucleus__"+numToFiveDigits(screenNum)+".png");

          while (saveScreenFile.exists())
          {
              screenNum++;
              saveScreenFile = new File("screenshots/Quantum_Nucleus__"+numToFiveDigits(screenNum)+".png");
          }

          try { ImageIO.write(image, "png", saveScreenFile); }
          catch (IOException e1) { e1.printStackTrace(); }
          out.println("Screenshot \"Quantum_Nucleus__"+numToFiveDigits(screenNum)+".png\" saved.");
    }
    

    public static void renderStringCentered(String in, int offsetX, int y, Graphics2D g, int screenWidth)
    {
        FontMetrics fm = g.getFontMetrics();
        int strWidth = fm.stringWidth(in);

        g.drawString(in,offsetX+(screenWidth/2)-(strWidth/2),y);
    }
    
    public static VolatileImage loadImageVolatile(String in)
    {
    	return loadImageVolatile(new File(in));
    }
    
    public static VolatileImage loadImageVolatile(File f)
    {
      	BufferedImage image = null;
      	try
      	{
      		image = ImageIO.read( f );
      	}catch(Exception e) {return null;}
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = DisplayManager.getGraphicsConfiguration();

        // image is not optimized, so create a new image that is
        VolatileImage new_image = gfx_config.createCompatibleVolatileImage(image.getWidth(),image.getHeight(), VolatileImage.TRANSLUCENT);

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();
        g2d.setComposite(AlphaComposite.Src);
        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        new_image.setAccelerationPriority(1.0f);

        // return the new optimized image
        return new_image;
    }
    
    /**
     * @deprecated
     * Eats double RAM with no visible CPU performance improvement @ blitting or whatever. 
     */
    @Deprecated
    public static BufferedImage loadOptimizedImage(String what)
    {
    	BufferedImage image = null;
        try
        {
            image = ImageIO.read( new File(what));
            image.setAccelerationPriority(1.0F);
            GraphicsConfiguration gfx_config = DisplayManager.getGraphicsConfiguration();
            if (image.getColorModel().equals(gfx_config.getColorModel()))
            {	//already compatible and optimized
                image.setAccelerationPriority(1.0f);
                return image;
            }
            BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
            Graphics2D g2d = (Graphics2D) new_image.getGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            new_image.setAccelerationPriority(1.0f);
            return new_image;
        }
        catch(IOException e)
        {
        	System.err.println("Error: couldn't load: "+what);
            try  { return ImageIO.read( new File("content/grafx/error.gif") ); }
            catch (IOException d ) {return null;}
        }
    }
    
    public static BufferedImage loadImage(String what)
    {
        try
        {
            BufferedImage img = ImageIO.read( new File(what));
            img.setAccelerationPriority(1.0F);
            return img;
        }
        catch(IOException e)
        {
        	System.err.println("Error: couldn't load: "+what);
            try  { return ImageIO.read( new File("content/grafx/error.gif") ); }
            catch (IOException d ) {return null;}
        }
    }

//    private static BufferedImage toCompatibleImage(BufferedImage image)
//    {
//        // obtain the current system graphical settings
//    	GraphicsConfiguration gfx_config = DisplayManager.getGraphicsConfiguration();
//
//        /*
//         * if image is already compatible and optimized for current system
//         * settings, simply return it
//         */
//        if (image.getColorModel().equals(gfx_config.getColorModel()))
//        {
//            image.setAccelerationPriority(1.0f);
//            return image;
//        }
//
//        // image is not optimized, so create a new image that is
//        BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
//
//        // get the graphics context of the new image to draw the old image on
//        Graphics2D g2d = (Graphics2D) new_image.getGraphics();
//
//        // actually draw the image and dispose of context no longer needed
//        g2d.drawImage(image, 0, 0, null);
//        g2d.dispose();
//
//        new_image.setAccelerationPriority(1.0f);
//
//        // return the new optimized image
//        return new_image;
//    }
}
