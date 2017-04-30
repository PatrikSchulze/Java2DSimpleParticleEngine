package utils;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Artificial Zero Media
 */
public class InputInterface
{
    /** Flag which indicates usage of the Keyboard as the current input device. */
    public static final int DEVICE_KEYBOARD = 0;

    private KeyboardHandler keyboard = null;
    private int device = DEVICE_KEYBOARD;
    private HashMap<Integer, Integer> keyboardMap = new HashMap<Integer, Integer>(); // Button 1-10, KeyBoardKey


    /**
     * @param in The Component that the KeyListener shall listen to.
     * <br>So it's actually just your GameWindow which should be a Component of some sorts.
     * @param inputType GamePad or KeyBoard
     */
    public InputInterface(Component in, int inputType)
    {
        keyboard = new KeyboardHandler(in);
        device = DEVICE_KEYBOARD;

        keyboardMap.put(0,KeyEvent.VK_E);
        keyboardMap.put(1,KeyEvent.VK_F);
        keyboardMap.put(2,KeyEvent.VK_R);
        keyboardMap.put(3,KeyEvent.VK_D);
        keyboardMap.put(4,KeyEvent.VK_A);
        keyboardMap.put(5,KeyEvent.VK_S);
        keyboardMap.put(6,KeyEvent.VK_ENTER);
        keyboardMap.put(7,KeyEvent.VK_W);
        keyboardMap.put(8,KeyEvent.VK_BACK_SPACE);
        keyboardMap.put(9,KeyEvent.VK_ESCAPE);
    }

    public String getKeyboardButtonMappingText(int which)
    {
        return KeyEvent.getKeyText(keyboardMap.get(which));
    }

    /**
     * Check if direction LEFT is pressed.
     * @return returns in a boolean
     */
    public boolean getLeft()
    {
    	return keyboard.getKey(KeyEvent.VK_LEFT);
    }

   /**
     * Check if direction UP is pressed.
     * @return returns in a boolean
     */
    public boolean getUp()
    {
    	return keyboard.getKey(KeyEvent.VK_UP);
    }

    /**
     * Check if direction RIGHT is pressed.
     * @return returns in a boolean
     */
    public boolean getRight()
    {
    	return keyboard.getKey(KeyEvent.VK_RIGHT);
    }

    /**
     * Check if direction DOWN is pressed.
     * @return returns in a boolean
     */
    public boolean getDown()
    {
    	return keyboard.getKey(KeyEvent.VK_DOWN);
    }

    /**
     * Check if direction LEFT is pressed EX.<br>
     * Which means it is only counted once and not continously.
     * @return returns in a boolean
     */
    public boolean getExLeft()
    {
    	return keyboard.getExKey(KeyEvent.VK_LEFT);
    }

    /**
     * Check if direction UP is pressed EX.<br>
     * Which means it is only counted once and not continously.
     * @return returns in a boolean
     */
    public boolean getExUp()
    {
    	return keyboard.getExKey(KeyEvent.VK_UP);
    }

    /**
     * Check if direction RIGHT is pressed EX.<br>
     * Which means it is only counted once and not continously.
     * @return returns in a boolean
     */
    public boolean getExRight()
    {
    	return keyboard.getExKey(KeyEvent.VK_RIGHT);
    }

    /**
     * Check if direction DOWN is pressed EX.<br>
     * Which means it is only counted once and not continously.
     * @return returns in a boolean
     */
    public boolean getExDown()
    {
    	return keyboard.getExKey(KeyEvent.VK_DOWN);
    }

    /**
     * Quick Escape is a key used in programming/debug to exit the game immediately.
     * @return key boolean
     */
    public boolean getQuickEscape()
    {
        if (keyboard.getExKey(KeyEvent.VK_DELETE))
        {
            return true;
        }

        return false;
    }


    public boolean getDebugExKey(int in)
    {
        if (keyboard.getExKey(in))
        {
            return true;
        }

        return false;
    }

    public boolean getDebugKey(int in)
    {
        if (keyboard.getKey(in))
        {
            return true;
        }

        return false;
    }

    /**
     * Print Screen key. For ScreenShots.
     * @return key boolean
     */
    public boolean getPrintScreen()
    {
        if (keyboard.getExKey(KeyEvent.VK_F5) || keyboard.getExKey(KeyEvent.VK_PRINTSCREEN))
        {
            return true;
        }

        return false;
    }


    /**
     * Checks if a KeyButton is pressed EX.<br>
     * Which means it is only counted once, and then you have to let go of the button.<br>
     * It is not counted continously.
     * @param which Which KeyButton
     * @return returns in a boolean
     */
    public boolean getExButton(int which)
    {
    	return keyboard.getExKey(keyboardMap.get(which));
    }

    /**
     * Checks if a KeyButton is pressed.<br>
     * This method in fact does count continously.<br>
     * Which means a given action would result to happen every single frame.<br>
     * May only be desired rarely. Like walking.
     * @param which Which KeyButton
     * @return returns in a boolean
     */
    public boolean getButton(int which)
    {
    	 return keyboard.getKey(keyboardMap.get(which));
    }
}
