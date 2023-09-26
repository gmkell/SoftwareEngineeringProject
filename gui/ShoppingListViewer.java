package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import math.CalPerGramCalculator;
import recipe.MeasuredIngredient;
import recipe.Recipe;

/**
 * Builds a ShoppingListViewer GUI and funtionality.
 * 
 * @author Gillian Kelly
 *
 */
public class ShoppingListViewer extends JFrame implements ActionListener
{

  private static final long serialVersionUID = 1L;
  private static final String RCP = "rcp";
  private static String RECIPE_NAME = "";
  private static JFrame frame;
  private static Recipe recipe;
  ImageIcon printButtonPic = new ImageIcon(
      getClass().getClassLoader().getResource("icons/print (smaller).png"));
  private Container contentPane;
  private JPanel mainPanel, numPanel, listPanel;
  private JLabel label;
  private JScrollPane scrollPane;
  private JTextArea shoppingListArea;
  private JTextField textField;
  private JButton printButton;

  /**
   * Constructor.
   * 
   * @throws IOException
   */
  public ShoppingListViewer() throws IOException
  {
    fileDialog();
    if (recipe != null) initialize();
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException
  {

    new ShoppingListViewer();

  }

  /**
   * Open fileDialog window.
   * 
   * @throws IOException
   */
  public static void fileDialog() throws IOException
  {
    int retval;
    JFileChooser fileChooser;
    File chosenDirectory, lastDirectory;
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // use the directory user chose or home for default - Nicole
    lastDirectory = DirectoryPreference.getLastDirectory();
    if (lastDirectory != null && lastDirectory.isDirectory())
    {
      chosenDirectory = lastDirectory;
    }
    else
    {
      chosenDirectory = new File(System.getProperty("user.home"));
    }

    // set-up approved options
    fileChooser = new JFileChooser(chosenDirectory);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Recipe Files", RCP);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(filter);

    // File file = new File("recipe.ser");
    // System.out.println("Loading file from: " + file.getAbsolutePath());
    retval = fileChooser.showOpenDialog(frame);

    File file = fileChooser.getSelectedFile();
    String fileName = file.getName();

    if (retval == JFileChooser.APPROVE_OPTION)
    {
      RECIPE_NAME = fileName.substring(0, fileName.length() - 4);
      // create a recipe object from the file
      try
      {
        recipe = Recipe.deserialize(fileChooser.getSelectedFile());
      }
      catch (IOException ie)
      {
        ie.printStackTrace();
      }
    }
  }

  /**
   * set up display.
   * 
   * @throws IOException
   */
  public void initialize() throws IOException
  {
    // SETUP JFRAME
    frame = new JFrame();
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    contentPane = frame.getContentPane();
    frame.setTitle("KiLowBites Shopping List Viewer\t" + RECIPE_NAME);

    // ADD PRINT BUTTON
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    printButton = new JButton(printButtonPic);
    printButton.addActionListener(this);
    printButton.setBackground(Color.WHITE);
    buttonPanel.add(printButton);
    contentPane.add(buttonPanel, BorderLayout.NORTH);

    // SET UP NUM TEXT FIELD
    textField = new JTextField(10);
    label = new JLabel();
    label.setText("Number of People: ");
    numPanel = new JPanel();
    numPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    numPanel.add(label);
    numPanel.add(textField);

    // SET UP SHOPPINGLIST TEXTFIELD
    listPanel = new JPanel();
    listPanel.setLayout(new BorderLayout());

    // SET UP SHOPPING LIST TEXT AREA
    listPanel = new JPanel();
    listPanel.setLayout(new BorderLayout());

    // add text field to panel shopping list panel
    shoppingListArea = new JTextArea();
    shoppingListArea.setEditable(false);
    listPanel.add(shoppingListArea);

    // add the scrollbar to the shopping list panel
    scrollPane = new JScrollPane(shoppingListArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    listPanel.add(scrollPane, BorderLayout.CENTER);

    listPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(Color.BLACK, getBackground()), "   Shopping List    "));

    // SETUP MAIN PANEL
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    // add attributes to mainPanel
    mainPanel.add(numPanel, BorderLayout.NORTH);
    mainPanel.add(listPanel, BorderLayout.CENTER);

    // finish setting up JFrame
    contentPane.add(mainPanel);
    frame.setSize(800, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);

    /*------------------------------------------------------------------------------------------- */

    // set up Document Listeners
    String display = "Ingredients\n";
    String space = " ";
    String newL = "\n";
    shoppingListArea.append(display);

    for (MeasuredIngredient i : recipe.getIngredients())
    {
      // add new amount to the window
      shoppingListArea
          .append(i.getAmount() + space + i.getUnit().getUnit() + space + i.getIngredient());
      shoppingListArea.append(newL);
    }
    textField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        shoppingListArea.setText(""); // start with empty text area
        try
        {
          int num = Integer.parseInt(textField.getText()); // only accept positive integers
          if (num <= 0)
          {
            shoppingListArea.append("Please enter a positive number");
            return;
          }
          // get the name and amount of each ingredient needed for the recipe
          Double adjust;
          Double updateAmount;
          String ingredientName;
          String unit;
          shoppingListArea.append("Ingredients:\n");
          for (MeasuredIngredient i : recipe.getIngredients())
          {
            ingredientName = i.getIngredient();

            // get the amount of each ingredient in the recipe
            adjust = (double) num / recipe.getServings();
            updateAmount = adjust * i.getAmount();
            unit = i.getUnit().getUnit();
            updateAmount = CalPerGramCalculator.roundToSignificantDigits(updateAmount);
            // add ingredient with new amount to the window
            shoppingListArea.append(updateAmount + space + unit + space + ingredientName);
            shoppingListArea.append(newL);
          }

        }
        catch (NumberFormatException ex)
        {
          // handle exception
        }

      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

    });

  }

  /**
   * @param value
   * @param decimalPlaces
   * @return rounded the convertedValue to one decimal place
   */
  public static double round(final double value, final int decimalPlaces)
  {
    double scale = Math.pow(10, decimalPlaces);
    return Math.round(value * scale) / scale;
  }

  /**
   * @param e
   */
  public void actionPerformed(final ActionEvent e)
  {
    // print button selected
    if (e.getSource() == printButton)
    {
      printPage(listPanel);
    }

  }

  /**
   * Prints the ShoppingListViewer window.
   * 
   * @param viewer
   *          the panel containing the shopping list
   */
  public void printPage(final JPanel viewer)
  {
    PrinterJob printComp = PrinterJob.getPrinterJob();
    printComp.setJobName("Print");
    printComp.setPrintable(new Printable()
    {

      @Override
      public int print(final Graphics graphics, final PageFormat pageFormat, final int pageNum)
          throws PrinterException
      {
        if (pageNum > 0)
        {
          return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        viewer.paint(g2);
        return Printable.PAGE_EXISTS;
      }
    });

    if (!printComp.printDialog())
    {
      return;
    }

    try
    {
      printComp.print();
    }
    catch (PrinterException ex)
    {
      // handle exception
      ex.printStackTrace();
    }

  }

}
