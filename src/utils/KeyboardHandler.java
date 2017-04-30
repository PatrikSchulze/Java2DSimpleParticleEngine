package utils;

import java.awt.Component;
import java.awt.event.*;
import java.awt.KeyboardFocusManager;
import java.util.Collections;
import java.util.BitSet;

/**
 * KeyboardHandler handles Keyboard inputs. DUH.<br>
 * You need to implement it to react to KeyboardEvents.
 * @author Artificial Zero Media
 */
public class KeyboardHandler implements KeyListener
{
    private BitSet keys = new BitSet(256);
    private BitSet exKeys = new BitSet(256);
    private BitSet taken = new BitSet(256);

    /**
     * Constructor.
     * @param comp The Component that the KeyListener shall listen to.
     * <br>So it's actually just your GameWindow which should be a Component of some sorts.
     */
    @SuppressWarnings( "unchecked" ) public KeyboardHandler(Component comp)
    {
      comp.addKeyListener(this);

      /*
          by default, AWT intercepts tab presses, so it can move focus between components.
          You can however disable this feature with java.awt.KeyboardFocusManager,
          now obviously this is an unchecked unsafe operation, naturally
      */
      KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
      kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
      kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
      //
    }
	
    @Override public void keyReleased(KeyEvent e)
    {
        keys.set(e.getKeyCode(), false);
        taken.set(e.getKeyCode(), false);
        exKeys.set(e.getKeyCode(), false);
    }
  	
    @Override public void keyPressed(KeyEvent e)
    {
        keys.set(e.getKeyCode(), true);
    }

    @Override public void keyTyped(KeyEvent e){}

    /**
     * Checks if the Key Button is pushed down.
     * @param which which Key
     * @return the boolean
     */
    public boolean getKey(int which)
    {
        return keys.get(which);
    }

    /**
     * Checks if the Key Button is pushed down, but only one frame.<br>
     * This can only be reactivated if the button was released again.<br>
     * The idea is not to have an event happening EVERY frame since pushing down the button, but only once per press.
     * @param which which Key
     * @return the boolean
     */
    public boolean getExKey(int which)
    {
        if (keys.get(which))
        {
            if (taken.get(which))
            {
                exKeys.set(which, false);
            }
            else
            {
                exKeys.set(which, true);
                taken.set(which, true);
            }
        }
        else
        {
            exKeys.set(which, false);
        }

        return exKeys.get(which);
    }
	
}