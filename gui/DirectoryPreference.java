package gui;

import java.io.*;

/**
 * Class for setting and retrieving the user's directory.
 * 
 * @author Nicole Kim
 *
 */
public class DirectoryPreference
{

  private static final String LAST_DIRECTORY_FILE = "lastDirectory.ser";

  /**
   * Default Constructor.
   */
  public DirectoryPreference()
  {
    super();
  }

  /**
   * Retrieve the last selected directory.
   * 
   * @return The directory.
   */
  public static File getLastDirectory()
  {
    File lastDirectory;
    FileInputStream fileIn;
    ObjectInputStream in;
    Object directory;
    String path;
    
    try
    {
      fileIn = new FileInputStream(LAST_DIRECTORY_FILE);
      in = new ObjectInputStream(fileIn);
      directory = in.readObject();
      path = directory.toString();
      lastDirectory = new File(path);
      
      fileIn.close();
      in.close();
      return lastDirectory;
    }
    catch (FileNotFoundException | StreamCorruptedException exc1)
    {
      return null;
    }
    catch (IOException | ClassNotFoundException exc2)
    {
      exc2.printStackTrace();
      return null;
    }
  }

  /**
   * Set the directory.
   * 
   * @param directory The directory that it is being set to.
   */
  public static void setLastDirectory(final File directory)
  {
    FileOutputStream fileOut;
    ObjectOutputStream out;
    
    try
    {
      fileOut = new FileOutputStream(LAST_DIRECTORY_FILE);
      out = new ObjectOutputStream(fileOut);
      out.writeObject(directory);
      
      fileOut.close();
      out.close();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }
}
