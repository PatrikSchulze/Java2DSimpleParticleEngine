
package utils;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 *
 * @author Patrik Schulze
 */
public class ImageParticle
{
    private Rectangle2D.Float rect = new Rectangle2D.Float();
    private float speedX;
    private float speedY;
    private int age;

    public ImageParticle(float x, float y, BufferedImage _img)
    {
        rect = new Rectangle2D.Float(x,y,_img.getWidth(),_img.getHeight());
        age = 0;
    }

    public void compute()
    {
        rect.x+=speedX;
        rect.y+=speedY;
        age++;
    }

    public int getAge()
    {
        return age;
    }

    public void setSpeed(float fx, float fy)
    {
        setSpeedX(fx);
        setSpeedY(fy);
    }

    public void setSpeedX(float fX)
    {
        speedX = fX;
    }

    public void setSpeedY(float fY)
    {
        speedY = fY;
    }
    
    public void setWidth(float in)
    {
    	rect.width = in;
    }
    
    public void setHeight(float in)
    {
    	rect.height = in;
    }
    
    public int getX()
    {
    	return (int)rect.x;
    }
    
    public int getY()
    {
    	return (int)rect.y;
    }
    
    public void setX(float in)
    {
    	rect.x = in;
    }
    
    public void setY(float in)
    {
    	rect.y = in;
    }
    
    public void addX(float in)
    {
    	rect.x+=in;
    }
    
    public void addY(float in)
    {
    	rect.y+=in;
    }

	public Rectangle2D.Float getRect() {
		return rect;
	}

	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setAge(int age) {
		this.age = age;
	}
    
}