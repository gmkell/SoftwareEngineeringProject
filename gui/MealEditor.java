
package gui;

// TODO: fix expand array methods so they can confirm to checkstyle and still work
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import recipe.DetailedUtensil;
import recipe.Meal;
import recipe.MeasuredIngredient;
import recipe.Recipe;
import recipe.Step;

/**
 * GUI for MealEditor.
 * 
 * @author Gillian Kelly
 *
 */
public class MealEditor extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final String FILE_DST = "user.home";
  private static final String MEAL_FILES = "Meal Files";
  private static final String MEL = "mel";
  private static final String CLOSE = "Close";
  private static final String OPEN = "Open";
  private static final String SAVE_AS = "SaveAs";
  private static final String SAVE = "Save";
  private static final String NEW = "New";
  private static final String ADD_RCP = "Add Recipe";
  private static final String DELETE = "Delete";
  private static Meal meal;
  private static JFrame frame;
  private static File currentMealFile = null; // for SAVE functionality
  // new button stuff
  ImageIcon closeImg = new ImageIcon(getClass().getClassLoader().getResource("icons/close_sm.png"));
  ImageIcon openImg = new ImageIcon(getClass().getClassLoader().getResource("icons/open_sm.png"));
  ImageIcon addImg = new ImageIcon(getClass().getClassLoader().getResource("icons/add_sm.png"));
  ImageIcon saveAsImg = new ImageIcon(
      getClass().getClassLoader().getResource("icons/save_as_sm.png"));
  ImageIcon saveImg = new ImageIcon(getClass().getClassLoader().getResource("icons/save_sm.png"));
  private Container contentPane;
  private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
  private DefaultListModel<String> recipeModel; // for adding/deleting recipes
  private JList<String> recipeList;
  private JTextField textField; // for user to enter Meal name
  private JScrollPane scrollPane;
  private JPanel buttonPanel, namePanel, displayPanel, mainPanel;
  private JButton newButton, saveButton, saveAsButton, closeButton, openButton, addRecipeButton,
      deleteButton;
  private JLabel textFieldLabel;

  /**
   * Constructor.
   */
  public MealEditor()
  {
    initialize();
  }

  /**
   * setup the frame and its components.
   */
  public void initialize()
  {
    // SETUP JFRAME
    frame = new JFrame();
    
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    frame.setVisible(true);
    frame.setSize(800, 500);
    frame.setLocationRelativeTo(null);
    frame.setTitle("KiLowBites Meal Editor");
    contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    // SETUP BUTTONPANEL
    setupButtonPanel();

    // SETUP JTextField for holding Meal name
    textField = new JTextField(50);
    textFieldLabel = new JLabel("Name: ");
    namePanel = new JPanel();

    namePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    namePanel.add(textFieldLabel);
    namePanel.add(textField);

    // TODO: create document listener to enable/disable save button if no name entered
    textField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        if (!textField.getText().equals(""))
        {
          saveButton.setEnabled(true);
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

    // TODO: (Optional) create a box layout to fix placement
    // of buttons in displayPanel!

    JPanel recipePanel = setupDisplayPanel();

    // Set-up main Panel
    mainPanel = new JPanel();

    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(namePanel, BorderLayout.NORTH);
    mainPanel.add(recipePanel, BorderLayout.CENTER);
    contentPane.add(mainPanel);
    disableFields(); // start document at null state

  }

  /**
   * @return JPanel containing the recipeList
   */
  public JPanel setupDisplayPanel()
  {
    // SETUP DISPLAY panel to hold text area, delete and add recipes buttons
    displayPanel = new JPanel();

    displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
    displayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // add recipeList to display added recipes in a Meal
    recipeModel = new DefaultListModel<>();
    recipeList = new JList<>(recipeModel);

    // add scrollbar to textArea
    scrollPane = new JScrollPane(recipeList);
 
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
    scrollPane.setVisible(true);

    // add delete and add reicpe buttons to the display panel
    addRecipeButton = new JButton(" Add Recipe ");
   
    deleteButton = new JButton(" Delete ");
   

    // set up Action cmd string and action listeners
    addRecipeButton.setActionCommand(ADD_RCP);
    deleteButton.setActionCommand(DELETE);
    addRecipeButton.addActionListener(this);
    deleteButton.addActionListener(this);

    // add components to panel
    displayPanel.add(addRecipeButton);
    displayPanel.add(scrollPane);
    displayPanel.add(deleteButton);

    return displayPanel;
  }

  /**
   * Setup the button panel.
   */
  public void setupButtonPanel()
  {
    // SETUP BUTTONPANEL
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    newButton = new JButton(addImg);
    saveButton = new JButton(saveImg);
    saveAsButton = new JButton(saveAsImg);
    closeButton = new JButton(closeImg);
    openButton = new JButton(openImg);

    // change display of buttons
  


    // set up action command strings for all buttons
    newButton.setActionCommand(NEW);
    saveButton.setActionCommand(SAVE);
    saveAsButton.setActionCommand(SAVE_AS);
    openButton.setActionCommand(OPEN);
    closeButton.setActionCommand(CLOSE);

    // add action listeners for all buttons
    newButton.addActionListener(this);
    saveButton.addActionListener(this);
    closeButton.addActionListener(this);
    saveAsButton.addActionListener(this);
    openButton.addActionListener(this);

    buttonPanel.add(newButton);
    buttonPanel.add(openButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(saveAsButton);
    buttonPanel.add(closeButton);
    contentPane.add(buttonPanel, BorderLayout.NORTH);
  }

  /**
   * Disables all fields until "New" is chosen.
   */
  private void disableFields()
  {
    textField.setEnabled(false);
    saveButton.setEnabled(false);
    saveAsButton.setEnabled(false);
    closeButton.setEnabled(false);
    openButton.setEnabled(false);
    addRecipeButton.setEnabled(false);
    deleteButton.setEnabled(false);
  }

  /**
   * Enable all fields after "New" is chosen.
   */
  private void enableFields()
  {
    textField.setEnabled(true);
    saveButton.setEnabled(true);
    saveAsButton.setEnabled(true);
    closeButton.setEnabled(true);
    openButton.setEnabled(true);
    addRecipeButton.setEnabled(true);
    deleteButton.setEnabled(true);
  }

  /**
   * Functionality of the "Close" button.
   */
  private void clearFields()
  {
    textField.setText("");
    recipeModel.removeAllElements();
    recipeList.removeAll();
  }

  /**
   * Helper methods, doubles the size of an array.
   * 
   * @param array
   *          the array to expand
   * @return expanded array
   */
  public Step[] expandStepArray(final Step[] array)
  {

    // initialize new empty array double the size of old one
    Step[] newArray = new Step[array.length * 2];
    for (int i = 0; i < array.length; i++)
    {
      newArray[i] = array[i];
    }
    // make old array point to new one
    // array = newArray;

    // return array;
    return newArray;
  }

  /**
   * Helper methods, doubles the size of an array.
   * 
   * @param array
   *          the array to expand
   * @return expanded array
   */
  public MeasuredIngredient[] expandIngArray(final MeasuredIngredient[] array)
  {

    // initialize new empty array double the size of old one
    MeasuredIngredient[] newArray = new MeasuredIngredient[array.length * 2];
    for (int i = 0; i < array.length; i++)
    {
      newArray[i] = array[i];
    }
    // make old array point to new one
    // array = newArray;
    // System.out.println("Ingredient, new length is: " + array.length);
    // return array;
    return newArray;

  }

  /**
   * Helper methods, doubles the size of an array.
   * 
   * @param array
   *          the array to expand
   * @return expanded array
   */
  public DetailedUtensil[] expandUtensilArray(final DetailedUtensil[] array)
  {
    // initialize new empty array double the size of old one
    DetailedUtensil[] newArray = new DetailedUtensil[array.length * 2];
    for (int i = 0; i < array.length; i++)
    {
      newArray[i] = array[i];
    }
    // make old array point to new one

    // array = newArray;
    // System.out.println("Utensil, new length is: " + array.length);
    // return array;
    return newArray;
  }

  /**
   * Functionality of the "Save" button.
   * 
   * @param filePath
   * @throws IOException
   */
  private void saveMealAs(final File filePath) throws IOException
  {
    // otherwise, save the fields to a meal object
    meal = new Meal();
    meal.setName(textField.getText());

    // initialize empty arrays of size 15
    MeasuredIngredient[] ingredients = new MeasuredIngredient[15];
    int ingLastUsedIndex = -1;
    DetailedUtensil[] utensils = new DetailedUtensil[15];
    int utsLastUsedIndex = -1;
    Step[] steps = new Step[15];
    int stepLastUsedIndex = -1;

    for (Recipe r : recipes)
    {
      // add all ingredients from containing recipes to Meal
      for (int i = 0; i < r.getIngredients().length; i++)
      {
        
        ingLastUsedIndex += 1; // increment index
        
        // ensure index still within bounds, else expand
        if (ingLastUsedIndex >= ingredients.length)
          ingredients = expandIngArray(ingredients);
        ingredients[ingLastUsedIndex] = r.getIngredients()[i];
      }
      
      // add all utensils from containing recipes to Meal

      for (int i = 0; i < r.getUtensils().length; i++)
      {
        utsLastUsedIndex += 1; // increment index
        // ensure index still within bounds, else expand
        if (utsLastUsedIndex >= utensils.length)
          utensils = expandUtensilArray(utensils);
        utensils[utsLastUsedIndex] = r.getUtensils()[i];
      }
      // add all steps from containing recipes to Meal

      for (int i = 0; i < r.getSteps().length; i++)
      {
        stepLastUsedIndex += 1; // increment index
        // ensure index still within bounds, else expand
        if (stepLastUsedIndex >= steps.length)
          steps = (Step[]) expandStepArray(steps);
        steps[stepLastUsedIndex] = r.getSteps()[i];
      }
    }

    meal.setIngredients(ingredients);
    meal.setUtensils(utensils);
    meal.setSteps(steps);
    meal.setRecipes(recipes);

    try
    {
      meal.serialize(filePath);
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(frame, "Failed to Save the Meal", e.toString(),
          JOptionPane.ERROR_MESSAGE);
    }

    JOptionPane.showConfirmDialog(null, "Meal Saved!", "SUCCESSFUL", JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE);

  }

  /**
   * File Dialog to open if "Open" button is chosen.
   * 
   * @param recipeModel
   * @param recipes 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static void openMeal(final DefaultListModel<String> recipeModel,
      final ArrayList<Recipe> recipes) throws IOException
  {
    int retval;
    JFileChooser fileChooser;
    fileChooser = new JFileChooser(System.getProperty(FILE_DST));
    FileNameExtensionFilter filter = new FileNameExtensionFilter(MEAL_FILES, MEL);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(filter);
    retval = fileChooser.showOpenDialog(frame);

    if (retval == JFileChooser.APPROVE_OPTION)
    {
      if (fileChooser.getSelectedFile().length() == 0)
      {
        System.out.println("Error: the selected file is empty.");
      }
      else
      {
        try
        {
          currentMealFile = fileChooser.getSelectedFile();
          meal = Meal.deserialize(currentMealFile);

          // clear the current recipeModel
          recipeModel.clear();
          recipes.clear();

          // Add the opened meal's recipes to the recipeModel
          for (Recipe r : meal.getRecipes())
          {
            recipeModel.addElement(r.getName());
            recipes.add(r);
          }

        }
        catch (IOException e)
        {
          System.out.println("Error: the selected file may be corrupted.");
          e.printStackTrace();
        }
      }
    }
    else
    {
      System.exit(JFrame.DISPOSE_ON_CLOSE);
    }
  }

  /**
   * Opens a file dialog that allows user to chooser a RCP file to add to the meal then adds that
   * recipe (if valid) to the textArea.
   */
  private void addRecipe()
  {
    int retval;
    JFileChooser fChooser = new JFileChooser(System.getProperty(FILE_DST));
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Recipe Files", "rcp");
    fChooser.setAcceptAllFileFilterUsed(false);
    fChooser.addChoosableFileFilter(filter);
    retval = fChooser.showOpenDialog(frame);

    if (retval == JFileChooser.APPROVE_OPTION)
    {
      Recipe recipe = null;
      try
      {
        recipe = Recipe.deserialize(fChooser.getSelectedFile());
        recipes.add(recipe);

      }
      catch (IOException ie)
      {
        ie.printStackTrace();
      }

      // check if recipe has already in the list
      if (!recipeModel.contains(recipe.getName()))
      {
        recipeModel.addElement(recipe.getName());
      }
    }
  }

  /**
   * Checks if document is unchagned or changed state.
   * 
   * @param textField
   * @param list
   * @param listModel
   * @param button
   */
  public static void changed(final JTextField textField, final DefaultListModel<String> listModel,
      final JList<String> list, final JButton button)
  {
    // boolean result = false;
    button.setEnabled(false);
    DocumentListener textFieldListener;
    ListDataListener listListener;

    // listen for changes in textField
    textFieldListener = new DocumentListener()
    {
      void updateButton()
      {
        button.setEnabled(true);
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        updateButton();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        updateButton();
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        updateButton();
      }

    };

    textField.getDocument().addDocumentListener(textFieldListener);

    // listen for changes in recipeList
    listListener = new ListDataListener()
    {

      @Override
      public void intervalAdded(final ListDataEvent e)
      {
        button.setEnabled(true);

      }

      @Override
      public void intervalRemoved(final ListDataEvent e)
      {
        button.setEnabled(true);
      }

      @Override
      public void contentsChanged(final ListDataEvent e)
      {
        button.setEnabled(true);
      }

    };

    listModel.addListDataListener(listListener);

  }

  /**
   * The functionality of the "Save" button.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    switch (e.getActionCommand())
    {
      case NEW:
        enableFields();
        saveButton.setEnabled(false);
        break;

      case OPEN:
        try
        {
          openMeal(recipeModel, recipes);
          if (meal.equals(null))
          {
            System.out.println("the meal was not properly loaded");
          }
        }
        catch (IOException e3)
        {
          e3.printStackTrace();
        }

        // set the fields based on the opened meal
        textField.setText(meal.getName());
        changed(textField, recipeModel, recipeList, saveButton);
        break;

      case SAVE:
        // TODO: implement functionality for the save button
        if (!textField.getText().equals(""))
        {
          try
          {
            if (currentMealFile != null)
            {
              saveMealAs(currentMealFile);
            }
            // saveButton.setEnabled(false);
          }
          catch (IOException ioe)
          {
            ioe.printStackTrace();
          }
        }
        break;

      case SAVE_AS:
        JFileChooser chooseMeal = new JFileChooser(System.getProperty(FILE_DST));
        chooseMeal.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter melFiles = new FileNameExtensionFilter(MEAL_FILES, MEL);
        chooseMeal.setFileFilter(melFiles);

        int option = chooseMeal.showDialog(new JFrame(), "Save Meal (use .mel extension!)");
        if (option == JFileChooser.APPROVE_OPTION)
        {
          try
          {
            saveMealAs(chooseMeal.getSelectedFile());
          }
          catch (IOException e1)
          {
            e1.printStackTrace();
          }
        }
        break;

      case ADD_RCP:
        addRecipe();
        saveButton.setEnabled(true);
        break;

      case DELETE:
        // TODO: enable multiple selection so user can delete multiple recipes at once
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex >= 0)
        {
          recipeModel.remove(selectedIndex);
          recipes.remove(selectedIndex);
          saveButton.setEnabled(true);
        }
        break;

      case CLOSE:
        clearFields();
        disableFields();
        break;

      default:
        break;
    }
  }

}
