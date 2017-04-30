
package utils;

import java.awt.geom.Rectangle2D;
import java.awt.Color;

/**
 *
 * @author Patrik Schulze
 */
public class Particle
{
    private Rectangle2D.Float rect = new Rectangle2D.Float();
    private float speedX;
    private float speedY;
    private int age;
    private Color color;

    public Particle(float x, float y, Color c, int initialScale)
    {
        rect = new Rectangle2D.Float(x,y,initialScale,initialScale);
        color = c;
        age = 0;
    }

    public void compute()
    {
        rect.x+=speedX;
        rect.y+=speedY;
        age++;
        int a;
        a = color.getAlpha();
        if (a > 0) a--;
        color = new Color(color.getRed(),color.getGreen(),color.getBlue(),a);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setRGBColor(int rgb) {
		this.color = new Color(rgb);
	}
	
	public void setARGBColor(int argb) {
		this.color = new Color(argb, true);
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
