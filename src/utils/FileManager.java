package utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager
{
	public static void writeToFile(String msg, String path)
    {
        try
    	{
            FileWriter file = new FileWriter(""+path);
            file.write(msg, 0 , msg.length());
            file.close();
    	}
    	catch (IOException ex)
    	{
            ex.printStackTrace();
    	}
    }

	public static String readFromFile(String path)
    {
    	try
    	{
            FileReader file = new FileReader(""+path);
            StringBuilder message = new StringBuilder();
            char c;
            while (file.ready())
            {
                c = (char)file.read();
                message.append(c);
            }
            file.close();
            return message.toString();
    	}
    	catch (IOException e)
    	{
            e.printStackTrace();
            return "";
    	}
    }
}
