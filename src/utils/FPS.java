package utils;

public class FPS
{
	private int oFPS;
 	private int framecount;
 	
 	private long oldtime;
    private long newtime;
    
    public FPS()
    {
    }
    
    public void incFramecount()
    {
    	framecount++;
    }
    
    public void manage()
    {
    	if ( ( newtime - oldtime ) >= 1000000000) 		//fps measure
			{
				outputFPS();
				oldtime = System.nanoTime();
			}
			newtime = System.nanoTime();
    }
    
    public void setOldtime(long in)
    {
    	oldtime = in;
    }
    
    public void outputFPS()
    {
  		oFPS = framecount;
  		framecount=0;
    }
    
    public int getFPS()
    {
    	return oFPS;
    }
}