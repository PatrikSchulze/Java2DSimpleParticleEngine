package tests;

import static java.lang.System.out;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import utils.*;

 /**
  *
  * @author Patrik Schulze, Artificial Zero Media
  */
 public class ParticleTests extends JFrame
 {
    private boolean gamerun = true;

    private Graphics2D gg = null;
    private DisplayManager displayMan = null;
    private InputInterface input = null;
    private MouseHandler debugMouse = null;
    private FPS myfps = null;
    private FrameSkipper skip;
    
    private ImageParticleEmitter emitter;
    private int rateHere = 42;
    private int lifespanHere = 254;
    private int maxPartsHere = 20000;
    
    private BufferedImage partImg;

    public ParticleTests(int screenWidth, int screenHeight)
    {
    	myfps = new FPS();
      	setTitle("White Alchemy Test Chamber");
      	setBackground(Color.white);
      	input = new InputInterface(this, InputInterface.DEVICE_KEYBOARD);
        debugMouse = new MouseHandler(this);
        setUndecorated(true);
        displayMan = new DisplayManager(this, "cfg/testchamber_display.cfg");
        partImg = MethodLibrary.loadImage("smokeParticle.png");
      	//setCursor(getToolkit().createCustomCursor(new ImageIcon("").getImage(),new Point(0,0),"Cursor"));
        this.addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent we){gamerun = false;}});
        try{setIconImage(MethodLibrary.loadImage("content/grafx/icon6.png"));}catch(Exception e){e.printStackTrace();}
        
        emitter = new ImageParticleEmitter(400, 400, rateHere, lifespanHere, maxPartsHere, partImg);

        
        skip = new FrameSkipper(60);
        myfps.setOldtime(java.lang.System.nanoTime());

        setIgnoreRepaint(true);
        displayMan.createBuffers();
    }

    public static void main(String[] args)
    {
        ParticleTests myGameWindow = new ParticleTests(1280,720);
        myGameWindow.setVisible(true);
        myGameWindow.gameloop();
        myGameWindow.removeAll();
        myGameWindow = null;

        int ramFreeLeft = (int)(Runtime.getRuntime().freeMemory()/1024/1024);
        int ramMax      = (int)(Runtime.getRuntime().maxMemory()/1024/1024);

        out.println("RAM usage at termination:   "+(ramMax-ramFreeLeft)+"MB / "+ramMax+" MB");

        java.lang.System.exit(0);
    }

    private String getRAMString()
    {
        int ramFreeLeft = (int)(Runtime.getRuntime().freeMemory()/1024/1024);
        int ramMax      = (int)(Runtime.getRuntime().maxMemory()/1024/1024);

        return "RAM usage:   "+(ramMax-ramFreeLeft)+"MB / "+ramMax+" MB";
    }

    private void checkInputs()
    {
        if (input.getDebugExKey(KeyEvent.VK_ESCAPE)) // DELETE
    	{
            gamerun = false;
    	}
        
        if (input.getDebugExKey(KeyEvent.VK_W))
        {
        	maxPartsHere+=1000;
        	emitter.setMaxParticles(maxPartsHere);
        }
        
        if (input.getDebugExKey(KeyEvent.VK_S))
        {
        	if (maxPartsHere >= 2000)
        	{
        		maxPartsHere-=1000;
        		emitter.setMaxParticles(maxPartsHere);
        	}
        }
        
        if (input.getDebugKey(KeyEvent.VK_PAGE_UP))
        {
        	lifespanHere++;
        	emitter.setLifespan(lifespanHere);
        }
        
        if (input.getDebugKey(KeyEvent.VK_PAGE_DOWN))
        {
        	if (lifespanHere > 0)
        	{
        		lifespanHere--;
        		emitter.setLifespan(lifespanHere);
        	}
        }

        if (input.getUp())
        {
        	rateHere++;
        	emitter.setRate(rateHere);
        }

        if (input.getDown())
        {
        	if (rateHere > 0)
    		{
        		rateHere--;
        		emitter.setRate(rateHere);
    		}
        }
    }

    private void reinitializeGraphics()
    {
        gg = displayMan.getGraphics();

        gg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gg.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        gg.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);

        gg.clearRect (0, 0, getWidth(), getHeight());
    }

    private void runNormal()
    {
        compute();
        reinitializeGraphics();
        renderMainGame (gg);
    }

    private void compute()
    {
    	emitter.setLocation(debugMouse.getX(), debugMouse.getY());
    }

    public void gameloop()
    {
        while(gamerun)
        {
            myfps.manage();
            checkInputs();
            runNormal();

            gg.dispose();

            displayMan.getStrategy().show();
            skip.sync(); //frameskipper maintainence
            myfps.incFramecount(); // FPS show
      	}
    }

    /**
	 * Random INCLUDING minimum value AND INCLUDING maximum value
	 */
	public static final int getRandom(int minimum, int maximum)
    {
        return (int)(java.lang.Math.random()*((maximum+1)-minimum)+minimum);
    }

    public static final float getRandom(float minimum, float maximum)
    {
        return (float)(java.lang.Math.random()*((maximum+Math.ulp(maximum))-minimum)+minimum);
    }

    private void renderMainGame(Graphics2D g)
    {
    	int r = emitter.update_and_render(g, this);
    	
        g.setColor(Color.black);
        g.drawString("FPS: "+myfps.getFPS(),10+1,getInsets().top+1+g.getFontMetrics().getHeight());
        g.drawString(getRAMString(),10+1, getInsets().top+1+(2*g.getFontMetrics().getHeight()));

        g.drawString("         Rate: "+rateHere,10+1, getInsets().top+1+(6*g.getFontMetrics().getHeight()));
        g.drawString("     Lifespan: "+lifespanHere,10+1, getInsets().top+1+(7*g.getFontMetrics().getHeight()));
        g.drawString("PARTICLES: "+r+" / "+maxPartsHere,10+1,getInsets().top+1+(8*g.getFontMetrics().getHeight()));
    	
        g.setColor(Color.white);
        g.drawString("FPS: "+myfps.getFPS(),10,getInsets().top+g.getFontMetrics().getHeight());
        g.drawString(getRAMString(),10, getInsets().top+(2*g.getFontMetrics().getHeight()));

        g.drawString("         Rate: "+rateHere,10, getInsets().top+(6*g.getFontMetrics().getHeight()));
        g.drawString("     Lifespan: "+lifespanHere,10, getInsets().top+(7*g.getFontMetrics().getHeight()));
        g.drawString("PARTICLES: "+r+" / "+maxPartsHere,10,getInsets().top+(8*g.getFontMetrics().getHeight()));
        
        

    }
}
