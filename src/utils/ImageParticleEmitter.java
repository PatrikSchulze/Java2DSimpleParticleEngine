package utils;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageParticleEmitter
{
	private int rate;
	private int lifespan_p;
	private ImageParticle p[];
	private float x;
	private float y;
	private BufferedImage img;
	
	public ImageParticleEmitter(int _x, int _y, int _rate, int lifespan_of_particles, int maxParticles, BufferedImage _img)
	{
		img = _img;
		rate = _rate;
		lifespan_p = lifespan_of_particles;
		p = new ImageParticle[maxParticles];
		x = _x;
		y = _y;
	}
	
	public void setLocation(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	
	public void setRate(int in)
	{
		rate = in;
	}
	
	public void setLifespan(int in)
	{
		lifespan_p = in;
	}
	
	public void setMaxParticles(int in)
	{
		p = new ImageParticle[in];
	}
	
	public int update_and_render(Graphics2D g, Frame fr)
	{
		int addedParticles = 0;
		int alivePs = 0;
		for (int i=0;i<p.length;i++)
		{
			if (p[i] != null)
			{
				alivePs++;
				g.drawImage(img, p[i].getX(), p[i].getY(), fr);
				
				if (p[i].getX() >= fr.getWidth())
				{
					p[i].setSpeedX(-p[i].getSpeedX());
				}
				else if (p[i].getX() <= 0)
				{
					p[i].setSpeedX(-p[i].getSpeedX());
				}
				
				if (p[i].getY() >= fr.getHeight())
				{
					p[i].setSpeedY(-p[i].getSpeedY());
				}
				else if (p[i].getY() <= 0)
				{
					p[i].setSpeedY(-p[i].getSpeedY());
				}
				
				p[i].compute();
				if (p[i].getAge() >= lifespan_p)
				{
					p[i] = null;
					alivePs--;
				}
			}
			else if (addedParticles < rate)
			{
				//add particle
				p[i] = new ImageParticle(x,y,img);
				p[i].setSpeed(getRandom(-3.0f,3.0f), getRandom(-3.0f,3.0f));
				addedParticles++;
				alivePs++;
			}
		}
		return alivePs;
	}
	
	public static int getRandom(int minimum, int maximum)
    {
        return (int)(java.lang.Math.random()*((maximum+1)-minimum)+minimum);
    }

    public static float getRandom(float minimum, float maximum)
    {
        return (float)(java.lang.Math.random()*((maximum+1)-minimum)+minimum);
    }
}
