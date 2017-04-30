package utils;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.*;
//import java.util.Locale;
import java.util.ArrayList;

/**
 * 
 * @author Me
 * @version 2.1
 */
public class DisplayManager
{
	public static final int FAILSAFE_WIDTH  = 800;
	public static final int FAILSAFE_HEIGHT = 600;
    private BufferStrategy strategy = null;
    private Frame frame;
    private static GraphicsDevice ourDevice;
    private boolean fullscreen;
    private int width, height;
	
    public DisplayManager(Frame fr, String cfg)
    {
      frame = fr;
      
      int w = FAILSAFE_WIDTH; //failsafe
      int h = FAILSAFE_HEIGHT;  //failsafe
      
      try
      {
    	  String[] r = FileManager.readFromFile(cfg).split(",");
          fullscreen = r[0].toLowerCase().startsWith("full");
          w = Integer.parseInt(r[1]);
          h = Integer.parseInt(r[2]);
      }
      catch(Exception e)
      {
    	System.out.println(e);
    	fullscreen = false;
    	w = FAILSAFE_WIDTH; //failsafe
        h = FAILSAFE_HEIGHT;  //failsafe
      }
      
      ourDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]; //failsafe
      
      //find primary screen device
      GraphicsDevice[] allDevices;
      allDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
      for (int i=0;i<allDevices.length;i++)
	  {
    	  	java.awt.Rectangle bounds = allDevices[i].getDefaultConfiguration().getBounds();
			java.awt.Point origin = bounds.getLocation();
			if (origin.x == 0 && origin.y == 0 && allDevices[i].getType() == GraphicsDevice.TYPE_RASTER_SCREEN)
			{
				ourDevice = allDevices[i];
				break;
			}
	  }

      if (fullscreen)
      {
          fr.setUndecorated(true); // Sun Java Bug ID: 5034393
	  			   				   // Undecorated BEFORE display sets
          ourDevice.setFullScreenWindow(fr);
          int colordepth = ourDevice.getDisplayMode().getBitDepth();
          try
          {
                ourDevice.setDisplayMode(new DisplayMode(w,h,colordepth,0));
          }
          catch(IllegalArgumentException k) // failsafe
          {
        	  System.err.println("-----------------------\nCouldn't set "+w+" x "+h+" x "+colordepth+" resolution. FAILSAFE 1024x768 used.");
        	  System.err.println(k);
        	  System.err.println(k.getMessage());
        	  k.printStackTrace();
        	  System.err.println("-----------------------");
              w = FAILSAFE_WIDTH;
              h = FAILSAFE_HEIGHT;
              ourDevice.setDisplayMode(new DisplayMode(w,h,colordepth,0));
          }
          System.out.print("Fullscreen Mode: ");
      }
      else
      {
          fr.setVisible(true); //otherwise bufferStrategy throws 'Component must have a valid peer' Exception.
          fr.setBounds(0,0,w,h);
          fr.setLocationRelativeTo(null); // middle of screen
          System.out.print("Window Mode: ");
      }
      width = w;
      height= h;

      System.out.println(""+w+" x "+h);

      fr.setResizable(false);	// no Resize and no maximaze
      fr.setLayout(null);	// layout
    }
    
    /**
     * @deprecated
     * Doesn't currently work.
     */
    @Deprecated
    public void toggleFullscreen()
    {
      if (!fullscreen)
      {
    	  fullscreen = true;
    	  //frame.setVisible(false);
    	  frame.removeNotify();
          frame.setUndecorated(true); // Sun Java Bug ID: 5034393
	  			   				   // Undecorated BEFORE display sets
          ourDevice.setFullScreenWindow(frame);
          int colordepth = ourDevice.getDisplayMode().getBitDepth();
          try
          {
                ourDevice.setDisplayMode(new DisplayMode(width,height,colordepth,0));
          }
          catch(IllegalArgumentException k) // failsafe
          {
        	  System.err.println("-----------------------\nCouldn't set "+width+" x "+height+" x "+colordepth+" resolution.");
        	  System.err.println(k);
        	  System.err.println(k.getMessage());
        	  k.printStackTrace();
          }
      }
      else
      {
    	  fullscreen = false;
          frame.setVisible(true); // You have to set this specifically,
                               // otherwise getting bufferStrategy will result
                               // in a 'Component must have a valid peer' Exception.
          frame.setBounds(0,0,width,height);
          frame.setLocationRelativeTo(null); // middle of screen
      }

      frame.setResizable(false);	// no Resize and no maximaze
      frame.setLayout(null);	// layout
      
      createBuffers();
    }
    
    public DisplayMode[] getLimitedDisplayModes(int widthMin, int heightMin, int widthLimit, int heightLimit)
	{
		ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
		DisplayMode[] itdm = ourDevice.getDisplayModes();
		
		for (int d = 0;d<itdm.length;d++)
		{
			if (itdm[d].getRefreshRate() == ourDevice.getDisplayMode().getRefreshRate() &&
				itdm[d].getBitDepth() == ourDevice.getDisplayMode().getBitDepth() &&
				itdm[d].getWidth() <= widthLimit && itdm[d].getHeight() <= heightLimit &&
				itdm[d].getWidth() >= widthMin && itdm[d].getHeight() >= heightMin				)
			{
					list.add(itdm[d]);
			}
		}
		
		DisplayMode[] out = new DisplayMode[list.size()];
		out = list.toArray(out);
		
		return out;
	}
    
    public DisplayMode[] getLimitedDisplayModes(GraphicsDevice gdev, int widthMin, int heightMin, int widthLimit, int heightLimit)
	{
		ArrayList<DisplayMode> list = new ArrayList<DisplayMode>();
		DisplayMode[] itdm = gdev.getDisplayModes();
		
		for (int d = 0;d<itdm.length;d++)
		{
			if (itdm[d].getRefreshRate() == gdev.getDisplayMode().getRefreshRate() &&
				itdm[d].getBitDepth() == gdev.getDisplayMode().getBitDepth() &&
				itdm[d].getWidth() <= widthLimit && itdm[d].getHeight() <= heightLimit &&
				itdm[d].getWidth() >= widthMin && itdm[d].getHeight() >= heightMin				)
			{
					list.add(itdm[d]);
			}
		}
		
		DisplayMode[] out = new DisplayMode[list.size()];
		out = list.toArray(out);
		
		return out;
	}
    
    public static GraphicsConfiguration getGraphicsConfiguration() { return ourDevice.getDefaultConfiguration(); }
    public static GraphicsDevice        getGraphicsDevice()        { return ourDevice; }
    
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}
	
    public void createBuffers()
    {
        frame.createBufferStrategy(2);
        strategy = frame.getBufferStrategy();
        frame.requestFocus();
        frame.requestFocusInWindow();
    }

    public boolean isFullscreen()      { return fullscreen; }
    public Graphics2D getGraphics()     { return (Graphics2D)strategy.getDrawGraphics(); }
    public BufferStrategy getStrategy() { return strategy; }
}