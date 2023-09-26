/**
 * 
 */
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import math.CalPerGramCalculator;
import math.MeasurementUnits;
import math.UnitConversions;
import recipe.Ingredients;
import recipe.Meal;
import recipe.MeasuredIngredient;
import recipe.Recipe;

/**
 * @author Gillian Kelly
 *
 */
public class ShoppingListViewerMeal extends JFrame implements ActionListener
{

  private static final long serialVersionUID = 1L;
  private static final String MEL = "mel";
  private static String MEAL_NAME = "";
  private static JFrame frame;
  private static Meal meal;
  // new button stuff
  ImageIcon print = new ImageIcon(
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
   * @return
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ShoppingListViewerMeal() throws IOException, ClassNotFoundException
  {
    fileDialog();
    if (meal != null) initialize();
  }

  /**
   * The file dialog window to display for user to choose a mel file.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static void fileDialog() throws IOException, ClassNotFoundException
  {
    int retval;
    JFileChooser fileChooser;
    File chosenDirectory, lastDirectory;
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // set-up approved options
    lastDirectory = DirectoryPreference.getLastDirectory();
    if (lastDirectory != null && lastDirectory.isDirectory())
      chosenDirectory = lastDirectory;
    else
      chosenDirectory = new File(System.getProperty("user.home"));

    fileChooser = new JFileChooser(chosenDirectory);
    // fileChooser = new JFileChooser(System.getProperty("user.home"));
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Meal Files", MEL);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(filter);

    retval = fileChooser.showOpenDialog(frame);

    if (retval == JFileChooser.APPROVE_OPTION)
    {
      File file = fileChooser.getSelectedFile();
      String fileName = file.getName();
      // String fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
      MEAL_NAME = fileName.substring(0, fileName.length() - 4);
      // create a meal object from the file
      try
      {
        meal = Meal.deserialize(file);
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
    frame.setBackground(Color.WHITE);
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    contentPane = frame.getContentPane();
    frame.setTitle("KiLowBites Shopping List Viewer\t" + MEAL_NAME);

    // ADD PRINT BUTTON
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    printButton = new JButton(print);
    printButton.setBackground(Color.WHITE);
    printButton.setVisible(true);
    printButton.addActionListener(this);
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
//    System.out.println(meal.getIngredients().length);
//    System.out.println(meal.getRecipes().size());
//    System.out.println(meal.getSteps().length);

    //String ingName = meal.getIngredients()[0].getIngredient();

    //Ingredients ingVar = Ingredients.getIngredient(ingName);

    // MeasuredIngredient prevIngredient = new MeasuredIngredient(ingVar,
    // meal.getIngredients()[0].getAmount(), meal.getIngredients()[0].getUnit(),
    // meal.getIngredients()[0].getDetails());
 
    Ingredients ingV = Ingredients.getIngredient("wine");
    MeasuredIngredient prevIngredient = new MeasuredIngredient(ingV, 0.0, MeasurementUnits.GRAMS,
        "ssdff");
    
    
    // HERE IS THE PROBLEM
    
    // remove null elements 
//    MeasuredIngredient[] noNull = new MeasuredIngredient[meal.getIngredients().length];
//    for (int i = 0; i < meal.getIngredients().length; i++)
//    {
//      if (meal.getIngredients()[i] != null)
//      {
//        noNull[i] = meal.getIngredients()[i];
//      }
//    }
    
    
//    MeasuredIngredient[] ingMeals = meal.convertIngredients(meal.getServings(),
//        noNull);
    // MeasuredIngredient[] ingMeals = meal.getIngredients();
    //Arrays.sort(ingMeals); // alpabetize list
    
    MeasuredIngredient[] ingMeals;
    ArrayList<MeasuredIngredient> mealIngredients = new ArrayList<>();
    ArrayList<MeasuredIngredient> ings = new ArrayList<>();
    //int count = 0;
    
    
    for (Recipe r : meal.getRecipes())
    {
      for (MeasuredIngredient i : r.getIngredients())
      {
        if (i != null)
        {
          ings.add(i);
        }
      }
    }
    
    ingMeals = new MeasuredIngredient[ings.size()];
    
    for (int i = 0; i < ings.size(); i++)
    {
      ingMeals[i] = ings.get(i);
    }
    
    
    // get all the ingredients
    for (MeasuredIngredient ing : ingMeals)
    {
      // look for duplicate ingredients
      if (ing != null && prevIngredient.getIngredient().equals(ing.getIngredient()))
      {
        // convert duplicate ingredients to the same unit if different
        if (!prevIngredient.getUnit().equals(ing.getUnit()))
        {
          Double convertedValue = UnitConversions.convert(ing.getAmount(), ing.getUnit(),
              prevIngredient.getUnit(), Ingredients.getIngredient(ing.getIngredient()));
          ing.setAmount(convertedValue);
          ing.setUnits(prevIngredient.getUnit());
        }
//        else
//        {
//          if (ing != null)
//          {
//            double amount1 = ing.getAmount();
//            ingMeals[count - 1].setAmount(amount1 + ingMeals[count - 1].getAmount());
//          }
//          // double amount1 = ing.getAmount();
//          // ingMeals[count - 1].setAmount(amount1 + ingMeals[count - 1].getAmount());
//        }

        // mealIngredients.add(ing);
      }
      else
      {
        //count++;
        prevIngredient = ing;
        if (prevIngredient != null)
        {
          mealIngredients.add(prevIngredient);
        }
        // mealIngredients.add(prevIngredient);
      }

    }
    /*------------------------------------------------------------------------------------------- */

    // ADD DOCUMENT LISTENERS!
    String display = "Ingredients\n";
    String space = " ";
    String newL = "\n";
    shoppingListArea.append(display);

    for (MeasuredIngredient mIng : mealIngredients)
    {
      System.out.println(mIng.getAmount() + space + mIng.getIngredient());
      shoppingListArea.append(
          mIng.getAmount() + space + mIng.getUnit().getUnit() + space + mIng.getIngredient());
      shoppingListArea.append(newL);
    }

    textField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        shoppingListArea.setText("");
        try
        {
          int num = Integer.parseInt(textField.getText());
          if (num <= 0)
          {
            JOptionPane.showMessageDialog(frame, "Please Enter a positive number of servings");
          }

          Double adjust;
          Double updateAmount;
          String ingName;
          String unit;
          shoppingListArea.append("Ingredients:\n");

          for (MeasuredIngredient ing : mealIngredients)
          {
            ingName = ing.getIngredient();
            adjust = (double) num;
            // adjust = (double) num / meal.getServings();
            updateAmount = adjust * ing.getAmount();
            unit = ing.getUnit().getUnit();
            updateAmount = CalPerGramCalculator.roundToSignificantDigits(updateAmount);
            shoppingListArea.append(updateAmount + space + unit + space + ingName + space);
            shoppingListArea.append(newL);
          }

        }
        catch (NumberFormatException nfe)
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

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // print button selected
    if (e.getSource() == printButton)
    {
      printPage(listPanel);
    }

  }

}
