package utils;

/**
 * FrameSkipper.
 * Initialize with FPS value as parameter.
 * call FrameSkipper.sync() instead of Thread.yield()
 * in situation of no Vsync, ergo window mode
 *
 * if this class proves to work fine. use this all the time
 * because Vsync does not mean 60 FPS by default.
 * For example, A 100 Hz CRT withh sync at 100FPS.
 *
 * @author Artificial Zero Media
 */
public class FrameSkipper
{
    private int fps;
    private long timeThen;

    public FrameSkipper(int frameRate)
    {
        fps = frameRate;
        timeThen = System.nanoTime();
    }

    public void changeFPS(int frameRate)
    {
        fps = frameRate;
    }

    public void sync()
    {
        long gapTo = 1000000000L / fps + timeThen;
        long timeNow = System.nanoTime();

        while (gapTo > timeNow)
        {
            Thread.yield();
            timeNow = System.nanoTime();
        }

        timeThen = timeNow;
    }

    /*
    public static void main(String args[])
    {
            System.out.println("Simple Timer Test: " +
                    "set to 2 (should actually be 60 for most games)");

            FrameSkipper frameSkip = new BasicTimer(2);

            while (true)
            {
                    frameSkip.sync();
                    System.out.println("TICK");
            }
    }*/
}
