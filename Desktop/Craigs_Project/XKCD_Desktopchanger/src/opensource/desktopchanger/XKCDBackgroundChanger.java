package opensource.desktopchanger;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * This program interacts with the web-site xkcd 
 * to get the current comic and set it as the 
 * background on my computer(as in need heavy editing for use on anyone else's computer)
 * @author Curran B. Hamilton
 * @author Contributions by Everett Williams
 */
public class XKCDBackgroundChanger
{
	public static void main(String[] args)
	{
		try
		{
			xkcdURL = new URL(xkcdURLString);
		}
		catch(MalformedURLException ex)
		{
			JOptionPane.showMessageDialog(null, "Could not create Url instance.");
		}
		try
		{
			setComicURL();
			saveImage();
			createBatchFile();
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Could not use the two methods.");
		}
//		try
//		{
//			String myCommand;
//			Runtime.getRuntime().exec("BackgroundChanger.bat");
//			myCommand = "RUNDLL32.EXE user32.dll,UpdatePerUserSystemParameters";
//			Runtime.getRuntime().exec(myCommand);
//		}
//		catch(IOException ex)
//		{
//			JOptionPane.showMessageDialog(null, "Could not set the desktop");
//		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public static void createBatchFile() throws IOException
	{
		FileWriter os = new FileWriter("BackgroundChanger.bat", false);
		os.write("\"" + System.getProperty("user.name") + "\\Control Panel\\Desktop\" /v Wallpaper /t REG_SZ /f /d " + destinationFile.getPath());
		os.close();
	}
	/**
	 * sets the comicURL field for use in the saveImage method
	 * @throws IOException
	 */
	public static void setComicURL() throws IOException
	{
		URLConnection xkcdConnection = xkcdURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(xkcdConnection.getInputStream()));
		String[] xkcdComicString = new String[10];
		
		try
		{
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				if(inputLine.contains("imgs.xkcd.com/comics/"))
				{
					xkcdComicString = inputLine.split("\"");
					if(xkcdComicString.length >= 3)
					{
						comicURL = new URL(xkcdComicString[1]);
					}
				}
			}
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "File did not open!");
		}
	}
	
	/**
	 * saves the image from the url to the file
	 * @throws IOException
	 */
	public static void saveImage() throws IOException
	{
		
		InputStream is = comicURL.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1)
		{
				os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	/**
	 * Data Fields
	 */
	
	private static URL comicURL;
	private static URL xkcdURL;
	private static final String xkcdURLString = new String("http://www.xkcd.com/");
	private static final File destinationFile = new File("D:\\Users\\Curran\\Desktop\\Craigs Project\\XKCD Desktopchanger\\xkcdComic.bmp");
}

