package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * GUI aspect for Preferences Window.
 * 
 * @author Nicole Kim
 *
 */
public class PreferencesWindow extends JFrame implements ActionListener
{

  private static final long serialVersionUID = 1L;

  private Container contentPane;
  private JButton selectDirectory;
  private JFrame frame;
  private JLabel currentDirectory;
  private JTextField path;
  private JPanel directory;
  private File lastDirectory;

  /**
   * Constructor to call the initialization.
   */
  public PreferencesWindow()
  {
    initialize();
  }

  /**
   * Set up window.
   */
  public void initialize()
  {
    File chosenDirectory;
    
    frame = new JFrame();
    frame.setTitle("KiLowBites Preferences");
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    frame.setSize(750, 100);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
    
    contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    contentPane.setBackground(Color.WHITE);
    // Setup panel for directory.
    directory = new JPanel();
    directory.setLayout(new BoxLayout(directory, BoxLayout.X_AXIS));
    directory.setBackground(Color.WHITE);
    directory.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    currentDirectory = new JLabel("Current Directory: ");
    currentDirectory.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

    // Create text field to show current directory chosen.
    lastDirectory = DirectoryPreference.getLastDirectory();
    if (lastDirectory != null && lastDirectory.isDirectory()) 
      chosenDirectory = lastDirectory;
    else chosenDirectory = new File(System.getProperty("user.home"));
    path = new JTextField(chosenDirectory.getAbsolutePath());
    path.setMaximumSize(new Dimension(Integer.MAX_VALUE, path.getPreferredSize().height)); 
    path.setEditable(false);
    
    // Create a button for file chooser dialog.
    selectDirectory = new JButton("Select Directory");
    selectDirectory.addActionListener(this);
    
    // Add contents to panel.
    directory.add(currentDirectory);
    directory.add(path);
    directory.add(Box.createHorizontalStrut(10));
    directory.add(selectDirectory);
    
    // Add panel to content pane.
    contentPane.add(directory);
  }

  /**
   * Determine what happens when directory button is clicked.
   * 
   * @param e The action event.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    int result;
    JFileChooser fileChooser;
    
    // Create a file chooser dialog
    fileChooser = new JFileChooser(lastDirectory);
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    result = fileChooser.showOpenDialog(PreferencesWindow.this);
    if (result == JFileChooser.APPROVE_OPTION)
    {
      // Show chosen directory in text field.
      lastDirectory = fileChooser.getSelectedFile();
      DirectoryPreference.setLastDirectory(lastDirectory);
      path.setText(lastDirectory.getAbsolutePath());
      path.setMaximumSize(new Dimension(Integer.MAX_VALUE, path.getPreferredSize().height)); 
    }
  }
}
