package utils;

import java.awt.Component;
import java.awt.event.*;

/**
 * This is only for Debug purposes.
 * @author Patrik Schulze, Artificial Zero Media
 */
public class MouseHandler implements MouseListener, MouseMotionListener
{
    private int mx;
    private int my;
    private boolean button = false;

    public MouseHandler(Component comp)
    {
      comp.addMouseListener(this);
      comp.addMouseMotionListener(this);
    }

    @Override public void mouseExited(MouseEvent me) {}
    @Override public void mouseEntered(MouseEvent me) {}

    @Override public void mouseReleased(MouseEvent me)
    {
        button = false;
    }

    @Override public void mousePressed(MouseEvent me)
    {
        button = true;
    }

    @Override public void mouseClicked(MouseEvent me) {}

    @Override public void mouseMoved(MouseEvent me)
    {
        mx = me.getX();
        my = me.getY();
    }

    @Override public void mouseDragged(MouseEvent me)
    {
        mx = me.getX();
        my = me.getY();
    }

    public boolean getButton()
    {
        return button;
    }

    public int getX()
    {
        return mx;
    }
    
    public int getY()
    {
        return my;
    }

}
