package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import lookandfeel.JMUTheme;

/**
 * GUI configuration for MainWindow of KILowBites.
 * 
 * @author Gillian Kelly
 *
 */
public class MainWindow extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final String EXIT = "Exit";
  private static final String RECIPE = "Recipes";
  private static final String MEAL = "Meals";
  private static final String CAL_CALC = "Calorie Calculator";
  private static final String UNIT_CALC = "Units Calculator";
  private static final String SHOP_LIST = "Shopping List for Recipe";
  private static final String SHOP_LIST_MEAL = "Shopping List for Meal";
  private static final String PROCESS_RCP = "Process for Recipe";
  private static final String PROCESS_MEL = "Process for Meal";
  private static final String PREFERENCES = "Preferences";
  private static final String ABOUT = "About";
  private static final String SEARCH_RECIPE = "Search Recipe";
  private static final String SEARCH_MEAL = "Search Meal";
  private boolean calorieCalcOpen = false;
  //private boolean unitConvertOpen = false;

  // private static boolean UnitconverterOpen = false;
  // private static boolean CalorieCalcOpen = false;

  private JFrame frame;
  private JMenuBar menuBar;
  private JPanel panel;
  private JLabel label;
  private JMenu fileMenu, editMenu, toolsMenu, viewMenu, configMenu, helpMenu, searchMenu, filler;
  private JMUTheme theme;
  

  // OTHER ICON !!!!
   ImageIcon logo2 = new
   ImageIcon(getClass().getClassLoader().getResource("icons/Kroger_Logo.png"));
   ImageIcon logoReal = new
       ImageIcon(getClass().getClassLoader().getResource("icons/KILowBites_Logo.png"));    

  /**
   * Constructor.
   * 
   * @throws IOException
   */
  public MainWindow()
  {
    initialize();
  }

  /**
   * Set up window.
   * 
   * @throws IOException
   */
  public void initialize()
  {
    
    
    
    
    frame = new JFrame();
    frame.setTitle("KiLowBites Main Window");
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(500, 300);
   
    frame.setLocationRelativeTo(null); // center window
    frame.setVisible(true);

    // set up menubar
    menuBar = new JMenuBar();
    menuBar.setBackground(Color.LIGHT_GRAY);
    menuBar.setBorder(new LineBorder(Color.BLACK));
    menuBar.setVisible(true);

    // set up fileMenu
    fileMenu = new JMenu("File");
    JMenuItem exit = new JMenuItem(EXIT);
    exit.addActionListener(this);
    fileMenu.add(exit);

    // set up editMenu
    editMenu = new JMenu("Edit");
    JMenuItem recipe = new JMenuItem(RECIPE);
    JMenuItem meal = new JMenuItem(MEAL);
    meal.addActionListener(this);
    recipe.addActionListener(this);
    editMenu.add(recipe);
    editMenu.add(meal);

    // set up toolsMenu
    toolsMenu = new JMenu("Tools");
    JMenuItem calCalc = new JMenuItem(CAL_CALC);
    JMenuItem unitCalc = new JMenuItem(UNIT_CALC);
    calCalc.addActionListener(this);
    unitCalc.addActionListener(this);
    toolsMenu.add(calCalc);
    toolsMenu.add(unitCalc);

    // set up viewMenu
    viewMenu = new JMenu("View");
    JMenuItem shop = new JMenuItem(SHOP_LIST);
    JMenuItem shopMeal = new JMenuItem(SHOP_LIST_MEAL);
    JMenuItem process = new JMenuItem(PROCESS_RCP);
    JMenuItem processMeal = new JMenuItem(PROCESS_MEL);
    shop.addActionListener(this);
    shopMeal.addActionListener(this);
    process.addActionListener(this);
    processMeal.addActionListener(this);
    viewMenu.add(shop);
    viewMenu.add(shopMeal);
    viewMenu.add(process);
    viewMenu.add(processMeal);
    
    // set up ConfigureMenu
    configMenu = new JMenu("Configure");
    JMenuItem pref = new JMenuItem(PREFERENCES);
    pref.addActionListener(this);
    configMenu.add(pref);
    menuBar.add(configMenu);

    // set up HelpMenu
    helpMenu = new JMenu("Help");
    JMenuItem about = new JMenuItem(ABOUT);
    about.addActionListener(this);
    helpMenu.add(about);

    // setup SearchMenu
    searchMenu = new JMenu("Search");
    JMenuItem searchRecipe = new JMenuItem(SEARCH_RECIPE);
    JMenuItem searchMeal = new JMenuItem(SEARCH_MEAL);
    searchRecipe.addActionListener(this);
    searchMeal.addActionListener(this);
    searchMenu.add(searchRecipe);
    searchMenu.add(searchMeal);
    
    //add filler for appearence
    filler = new JMenu("                                            ");

    // add all menus to menuBar
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(toolsMenu);
    menuBar.add(viewMenu);
    menuBar.add(searchMenu);
    menuBar.add(configMenu);
    menuBar.add(helpMenu);
    menuBar.add(filler);

    // add menuBar to frame
    frame.setJMenuBar(menuBar);

    // set up the logo
    panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
    frame.add(panel, null);

    label = new JLabel("");
    label.setHorizontalAlignment(JLabel.LEFT);
    label.setVerticalAlignment(JLabel.CENTER);
    panel.add(label);
    panel.setBackground(Color.WHITE);

    ImageIcon logo = logoReal; // assuming logo2 is the original image icon
    Image originalImage = logo.getImage();
    Image scaledImage = originalImage.getScaledInstance
        (300, 300, Image.SCALE_SMOOTH); // resize the image to 100x100 pixels
    ImageIcon smallerLogo = new ImageIcon(scaledImage);
    label.setIcon(logoReal); // set the new smaller image as the icon of the label

  }

  /**
   * Determine which menuBarItem was selected.
   * 
   * @param e
   */
  public void actionPerformed(final ActionEvent e)
  {
    if (EXIT.equals(e.getActionCommand()))
    {
      System.exit(EXIT_ON_CLOSE);

    }
    else if (RECIPE.equals(e.getActionCommand()))
    {
      new RecipeEditor();
    }
    else if (MEAL.equals(e.getActionCommand()))
    {
      new MealEditor();
    }
    else if (CAL_CALC.equals(e.getActionCommand()))
    {
      // only allow one instance of the CalorieCalculatorWindow at a time
      if (!calorieCalcOpen)
      {
        calorieCalcOpen = true;
        CalorieCalculatorWindow calWindow = new CalorieCalculatorWindow();
        calWindow.addWindowListener(new WindowAdapter()
        {
          @Override
          public void windowClosing(final WindowEvent e)
          {
            calorieCalcOpen = false;
          }
        });
      }

    }
    else if (UNIT_CALC.equals(e.getActionCommand()))
    {
      // only allow one instance of the UnitConverterWindow at a time
      new UnitConverterWindow();
//      if (!unitConvertOpen)
//      {
//        unitConvertOpen = true;
//        UnitConverterWindow convWindow = new UnitConverterWindow();
//        convWindow.addWindowListener(new WindowAdapter()
//        {
//          @Override
//          public void windowClosing(final WindowEvent e)
//          {
//            unitConvertOpen = false;
//          }
//        });
//      }

    }
    else if (SHOP_LIST.equals(e.getActionCommand()))
    {
      try
      {
        new ShoppingListViewer();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    else if (PROCESS_RCP.equals(e.getActionCommand()))
    {
      try
      {
        new ProcessViewer();
      }
      catch (ClassNotFoundException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    else if (PROCESS_MEL.equals(e.getActionCommand()))
    {
      try
      {
        new ProcessViewerMeal();
      }
      catch (ClassNotFoundException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    else if (PREFERENCES.equals(e.getActionCommand()))
    {
      new PreferencesWindow();
    }
    else if (ABOUT.equals(e.getActionCommand()))
    {
      String aboutMessage = "KILowBites is a recipe management and meal planning system.\n\n"
          + "KILowBites is a product of KitchIntel.\n"
          + "KicthenIntel designs and develops inteligent computing and communications system"
          + " for the kitchen that connects appliances, cabinets, etc...; use RFID tags "
          + "and barcodes to track \"inventories\"; "
          + "allow appliances to be programmed; etc... \n\n"
          + "Authors: Hiba Akbi, Gillian Kelly, Emil Hofstetter, Nicole Kim, Terry Johnson\n\n"
          + "Version 1.3\n\n" + "03-05-2023 \n\n";
      JTextArea aboutText = new JTextArea(20, 50);
      aboutText.setText(aboutMessage);
      aboutText.setLineWrap(true);
      aboutText.setWrapStyleWord(true);
      aboutText.setEditable(false);
      JScrollPane scroll = new JScrollPane(aboutText);

      JOptionPane.showMessageDialog(frame, scroll, "About Dialog", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (SEARCH_RECIPE.equals(e.getActionCommand()))
    {
      new SearchWindow<>(SearchWindow.findRecipes(), "Recipe Search", RECIPE);
    }
    else if (SEARCH_MEAL.equals(e.getActionCommand()))
    {
      new SearchWindow<>(SearchWindow.findMeals(), "Meal Search", MEAL);
    }
    else if (SHOP_LIST_MEAL.equals(e.getActionCommand()))
    {
      try
      {
        new ShoppingListViewerMeal();
      }
      catch (ClassNotFoundException e1)
      {
        e1.printStackTrace();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
  }

}
