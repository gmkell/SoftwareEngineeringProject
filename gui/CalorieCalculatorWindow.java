/**
 * 
 */
package gui;

import javax.swing.filechooser.FileNameExtensionFilter;

import math.MeasurementUnits;
import recipe.Ingredients;
import recipe.Meal;
import recipe.MeasuredIngredient;
import recipe.Recipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import math.CalPerGramCalculator;
import math.UnitConversions;

import javax.swing.*;

/**
 * Class That creates GUI aspect for Calorie Calculator Window.
 * 
 * @author Terry Johnson
 *
 *
 */
public class CalorieCalculatorWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static final String RCP = "rcp";
  private static final String INDIVIDUAL_ERROR= "Error: Ingredient can not be "
      + "measured as an individual component.";
  private static final String RESET = "Reset";
  private static final String MEL = "mel";
  private static final String TOTAL_CAL_RECIPE = "The total calories "
      + "per serving in this recipe is ";
  //private static  String RECIPE_NAME = "";
  private static final String COMMA = ", ";
  private static final String ING_NO_CAL = "The following "
      + "ingredients were not included in the calorie calculation: ";
  //private static  String MEAL_NAME = "";
  private static final String TOTAL_CAL_MEAL = "The total calories per serving in this meal is ";
  private static JFrame frame;
  private static Recipe recipe;
  private static Meal meal;
  ImageIcon calculate = new ImageIcon(
      getClass().getClassLoader().getResource("icons/calculate_button.png"));
  ImageIcon refresh = new ImageIcon(
      getClass().getClassLoader().getResource("icons/refresh_button.png"));
  ImageIcon open = new ImageIcon(getClass().getClassLoader().getResource("icons/open_button.png"));

  /**
   * Function to call the Gui Initialization.
   */
  public CalorieCalculatorWindow()
  {
    initialize();
  }

  /**
   * Constructs GUI.
   */
  public void initialize()
  {
    //
    // Set the title of the JFrame
    setTitle("KiLowBites Calorie Calculator");
    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

    // Create a JPanel to hold the dropdown boxes
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // add some padding
    JPanel panel2 = new JPanel();

    panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

    JPanel panel3 = new JPanel();
    panel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

    // Create a JLabel for the first dropdown box
    JLabel ingredientsLabel = new JLabel("Ingredients: ");

    // Create a dropdown box for the ingredients
    String[] ingredients = Ingredients.getAvailableIngredientsNames();
    Arrays.sort(ingredients);
    JComboBox<String> ingredientsDropdown = new JComboBox<String>(ingredients);
    ingredientsDropdown.setSelectedIndex(-1);

    // Create a JLabel for the second dropdown box
    JLabel amountLabel = new JLabel("Amount: ");

    // Create a dropdown box for the amount
    JTextField amountField = new JTextField(10);
    amountField.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(final KeyEvent e)
      {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') || ((c == '.') && (!amountField.getText().contains(".")))
            || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
        {
          e.consume(); // consume non-numbers
        }
      }
    });

    // Create a JLabel for the third dropdown box
    JLabel unitsLabel = new JLabel("Units: ");

    // Create a dropdown box for the units
    String[] units = MeasurementUnits.getAllUnits();
    JComboBox<String> unitsDropdown = new JComboBox<String>(units);
    unitsDropdown.setSelectedIndex(-1);

    // Create a JLabel for the calorie display
    JLabel calorieLabel = new JLabel("Calories in Ingredient: ");

    // Create a JTextArea for displaying the calorie information
    JTextField calorieArea = new JTextField(10);
    calorieArea.setEditable(false);

    // Create a Box to hold the calorie label and text field
    Box calorieBox = Box.createHorizontalBox();
    calorieBox.add(calorieLabel);
    calorieBox.add(Box.createHorizontalStrut(10)); // add some space between the label and text
                                                   // field
    calorieBox.add(calorieArea);

    // Create Calculate Button

    JButton calculateButton = new JButton(calculate);
    calculateButton.setName("Calculate");
    calculateButton.setPreferredSize(new Dimension(50, 50));
    calculateButton.setContentAreaFilled(false);
    calculateButton.setBorderPainted(false);
    calculateButton.setFocusPainted(false);
    getContentPane().add(calculateButton, BorderLayout.WEST);

    // Create Refresh Button

    JButton resetButton = new JButton(refresh);
    resetButton.setPreferredSize(new Dimension(50, 50));
    resetButton.setContentAreaFilled(false);
    resetButton.setBorderPainted(false);
    resetButton.setFocusPainted(false);
    resetButton.setName(RESET);
    getContentPane().add(resetButton, BorderLayout.WEST);

    // Create open Button
    JButton openButton = new JButton(open);
    openButton.setPreferredSize(new Dimension(50, 50));
    openButton.setContentAreaFilled(false);
    openButton.setBorderPainted(false);
    openButton.setFocusPainted(false);
    openButton.setName(RESET);
    getContentPane().add(openButton, BorderLayout.WEST);

    // Focus listener for amount field
    amountField.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusLost(final FocusEvent e)
      {
        if (ingredientsDropdown.getSelectedIndex() == -1 || amountField.getText().trim().isEmpty()
            || unitsDropdown.getSelectedIndex() == -1
            || Double.parseDouble(amountField.getText()) < 0)
        {
          calculateButton.setEnabled(false);
          calorieArea.setText("");
        }
        else
        {
          calculateButton.setEnabled(true);
        }
      }
    });

    // Action listener for ingredients dropdown
    ingredientsDropdown.addActionListener(e -> 
    {
      if (ingredientsDropdown.getSelectedIndex() == -1 || amountField.getText().trim().isEmpty()
          || unitsDropdown.getSelectedIndex() == -1
          || Double.parseDouble(amountField.getText()) < 0)
      {
        calculateButton.setEnabled(false);
        calorieArea.setText("");
      }
      else
      {
        calculateButton.setEnabled(true);
      }
    });

    // Action listener for units dropdown
    unitsDropdown.addActionListener(e -> 
    {
      if (ingredientsDropdown.getSelectedIndex() == -1 || amountField.getText().trim().isEmpty()
          || unitsDropdown.getSelectedIndex() == -1
          || Double.parseDouble(amountField.getText()) < 0)
      {
        calculateButton.setEnabled(false);
        calorieArea.setText("");
      }
      else
      {
        calculateButton.setEnabled(true);
      }
    });

    // Action Listener for Calculate Button
    calculateButton.addActionListener(e -> 
    {
      if (ingredientsDropdown.getSelectedIndex() == -1 || amountField.getText().trim().isEmpty()
          || unitsDropdown.getSelectedIndex() == -1
          || Double.parseDouble(amountField.getText()) < 0)
      {
        calculateButton.setEnabled(false);
        calorieArea.setText("");
      }
      else
      {
        calculateButton.setEnabled(true);
        // Get the selected item from the ingredients dropdown
        String selectedIngredient = (String) ingredientsDropdown.getSelectedItem();
        String selectedUnits = (String) unitsDropdown.getSelectedItem();

        // Create an Ingredients object from the selected item
        Ingredients matchingIngredient = null;

        for (Ingredients ingredient : Ingredients.getAvailableIngredients().values())
        {
          if (ingredient.getItem().equalsIgnoreCase(selectedIngredient))
          {
            matchingIngredient = ingredient;
            break;
          }
        }

        if (matchingIngredient.getCalories() == null)
        {
          JOptionPane.showMessageDialog(null,
              "Error: Ingredient does not contain calorie information.");
        }

        else if (matchingIngredient != null)
        {
          int truncated = 0;
          // Create a CalPerGramCalculator object with the selected ingredient
          CalPerGramCalculator calculator = new CalPerGramCalculator(matchingIngredient);
          // Get the grams from the amount field
          double amount1 = Double.parseDouble(amountField.getText());
          MeasurementUnits mu = MeasurementUnits.fromUnit(selectedUnits);
          System.out.println(mu.getUnit());
          // Convert user chosen units to grams
          double grams = UnitConversions.convert(amount1, mu, MeasurementUnits.GRAMS,
              matchingIngredient);

          // Calculate the calories per gram and total calories
          double totalCalories = calculator.calculateCalFromGrams(grams);
          if (totalCalories == Math.floor(totalCalories))
          {
            totalCalories = Math.round(totalCalories);
            truncated = (int) totalCalories;
            calorieArea.setText(String.valueOf(truncated));
          }
          else
          {
            calorieArea.setText(String.valueOf(totalCalories));
          }

        }
//        else
//        {
//          // Handle case where selected ingredient is not found
//          calorieArea.setText("Ingredient not found.");
//        }

      }
    });

    // Action listener for reset button
    resetButton.addActionListener(e -> 
    {
      ingredientsDropdown.setSelectedIndex(-1);
      amountField.setText("");
      unitsDropdown.setSelectedIndex(-1);
      calorieArea.setText("");
      if (ingredientsDropdown.getSelectedIndex() == -1 || amountField.getText().trim().isEmpty()
          || unitsDropdown.getSelectedIndex() == -1
          || Double.parseDouble(amountField.getText()) < 0)
      {
        calculateButton.setEnabled(false);
        calorieArea.setText("");
      }
      else
      {
        calculateButton.setEnabled(true);
      }
    });

    // Action listener for open button
    openButton.addActionListener(e -> 
    {
      //match with directory preference if chosen
      File chosenDirectory, lastDirectory;

      lastDirectory = DirectoryPreference.getLastDirectory();
      if (lastDirectory != null && lastDirectory.isDirectory())
        chosenDirectory = lastDirectory;
      else
        chosenDirectory = new File(System.getProperty("user.home"));
      JFileChooser fileChooser = new JFileChooser(chosenDirectory);
      FileNameExtensionFilter recFilter = new FileNameExtensionFilter("Recipe Files", RCP);
      FileNameExtensionFilter mealFilter = new FileNameExtensionFilter("Meal Files", MEL);

      // use the directory user chose or home for default - Nicole

      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.addChoosableFileFilter(recFilter);
      fileChooser.addChoosableFileFilter(mealFilter);

      int retval = fileChooser.showOpenDialog(frame);

      if (retval == JFileChooser.APPROVE_OPTION)
      {
        if (fileChooser.getSelectedFile().getName().endsWith(".rcp"))
        {
          //String name = fileChooser.getSelectedFile().getName();
          //RECIPE_NAME = name.substring(0, name.length() - 4);
          // create a recipe object from the file
          try
          {
            recipe = Recipe.deserialize(fileChooser.getSelectedFile());
          }
          catch (IOException e1)
          {
            e1.printStackTrace();
          }

          // calculate total calories
          MeasuredIngredient[] recipeIngredients = recipe.getIngredients();
          Ingredients realIng;
          ArrayList<String> zeroCalIng = new ArrayList<String>();
          double totalCalories = 0.0;
          double grams;
          int truncated = 0;
          int servings = recipe.getServings();

          //loop through ingredients in recipe
          for (MeasuredIngredient ing : recipeIngredients)
          {
            double amount = ing.getAmount();
            String ingStr = ing.getIngredient();
            MeasurementUnits unit = ing.getUnit();
            
            //loop through ingredients to ensure they exist
            for (Ingredients ingredient : Ingredients.getAvailableIngredients().values())
            {
              if (ingredient.getItem().equalsIgnoreCase(ingStr))
              {
                if (unit == MeasurementUnits.COUNT)
                {
                  if (ingredient.getAvgWeight() == null)
                  {
                    JOptionPane.showMessageDialog(null,
                        INDIVIDUAL_ERROR);
                  }
                  else
                  {
                    grams = amount * ingredient.getAvgWeight();
                    realIng = ingredient;
                    String ingredientStr = realIng.getItem();
                    if (realIng.getCalories() == null)
                    {
                      zeroCalIng.add(ingredientStr);
                    }
                    else
                    {
                      realIng = ingredient;
                      CalPerGramCalculator calculator = new CalPerGramCalculator(realIng);
                      double calories = calculator.calculateCalFromGrams(grams);
                      totalCalories += calories;
                    }
                  }

                }
                else
                {
                  grams = UnitConversions.convert(amount, unit, MeasurementUnits.GRAMS, ingredient);

                  String ingredientStr = ingredient.getItem();
                  if (ingredient.getCalories() == null)
                  {
                    zeroCalIng.add(ingredientStr);
                  }
                  else
                  {
                    CalPerGramCalculator calculator = new CalPerGramCalculator(ingredient);

                    double calories = calculator.calculateCalFromGrams(grams);
                    totalCalories += calories;
                  }
                }
              }
            }
          }

          if (totalCalories / servings == Math.floor(totalCalories / servings))
          {
            totalCalories = Math.round(totalCalories / servings);
            truncated = (int) totalCalories;
            JOptionPane.showMessageDialog(frame,
                TOTAL_CAL_RECIPE + (truncated / servings));
          }
          else
          {
            JOptionPane.showMessageDialog(frame, TOTAL_CAL_RECIPE
                + CalPerGramCalculator.roundToSignificantDigits(totalCalories / servings));
          }

          // display ingredients that didn't contain caloreis
          if (!zeroCalIng.isEmpty())
          {
            String zeroCalories = String.join(COMMA, zeroCalIng);
            JOptionPane.showMessageDialog(frame,
                ING_NO_CAL
                    + zeroCalories);
          }

        }
        else if (fileChooser.getSelectedFile().getName().endsWith(".mel"))
        {
          //String name = fileChooser.getSelectedFile().getName();
          //MEAL_NAME = name.substring(0, name.length() - 4);
          // create a recipe object from the file
          try
          {
            meal = Meal.deserialize(fileChooser.getSelectedFile());
          }
          catch (IOException e1)
          {
            e1.printStackTrace();
          }



          //Set key restrictions for entering servings for a meal
          JTextField servingsField = new JTextField();

          servingsField.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(final KeyEvent e)
            {
              char c = e.getKeyChar();
              if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE)
                  || (c == KeyEvent.VK_DELETE)))
              {
                e.consume();
              }
            }
          });

          
          JOptionPane optionPane = new JOptionPane(servingsField, JOptionPane.PLAIN_MESSAGE,
              JOptionPane.OK_CANCEL_OPTION);
          JDialog dialog = optionPane.createDialog("Enter the number of servings in the meal:");
          dialog.setVisible(true);

          String servingsString = null;
          Object selectedValue = optionPane.getValue();
          if (selectedValue != null && selectedValue instanceof Integer
              && (int) selectedValue == JOptionPane.OK_OPTION)
          {
            servingsString = servingsField.getText();

            int servings = Integer.parseInt(servingsString);

            // calculate total calories
            MeasuredIngredient[] mealIngredients = meal.getIngredients();
            MeasuredIngredient[] noNullIng;
            int index = 0;
            
            ArrayList<MeasuredIngredient> ingList = new ArrayList<>();
            for (Recipe r : meal.getRecipes())
            {
              for (MeasuredIngredient i : r.getIngredients())
              {
                if (i != null)
                {
                  ingList.add(i);
                }
              }
            }
            
            noNullIng = new MeasuredIngredient[ingList.size()];
            
            for (int i = 0; i < ingList.size(); i++)
            {
              noNullIng[i] = ingList.get(i);
            }
            
//            for (MeasuredIngredient noNullIngredients : mealIngredients) {
//              if (noNullIngredients != null) {
//                noNullIng[index] = noNullIngredients;
//                index++;
//              }
//            }
            Ingredients realIng;
            ArrayList<String> zeroCalIng = new ArrayList<String>();

            double totalCalories = 0.0;
            double grams;
            int truncated = 0;

            //loop through all ingredients in meal
            for (MeasuredIngredient ing : noNullIng)
            {
              if (ing != null)
              {
                double amount = ing.getAmount();
                String ingStr = ing.getIngredient();
                MeasurementUnits unit = ing.getUnit();

                //loop through ingredients to nmake sure ingredients exist
                for (Ingredients ingredient : Ingredients.getAvailableIngredients().values())
                {
                  //if ingredient exist
                  if (ingredient.getItem().equalsIgnoreCase(ingStr))
                  {
                    //if its an individual
                    if (unit == MeasurementUnits.COUNT)
                    {
                      //if there is no avgWeight component which we used to measure individual items
                      if (ingredient.getAvgWeight() == null)
                      {
                        JOptionPane.showMessageDialog(null,
                            INDIVIDUAL_ERROR);
                      }
                      else
                      {
                        grams = amount * ingredient.getAvgWeight();
                        realIng = ingredient;
                        String ingredientStr = realIng.getItem();
                        if (realIng.getCalories() == 0.0 || realIng.getCalories() == null)
                        {
                          zeroCalIng.add(ingredientStr);
                        }
                        else
                        {
                          realIng = ingredient;
                          CalPerGramCalculator calculator = new CalPerGramCalculator(realIng);
                          double calories = calculator.calculateCalFromGrams(grams);
                          totalCalories += calories;
                        }
                      }

                    }
                    else
                    {
                      //convert unit to grams
                      grams = UnitConversions.convert(amount, unit, MeasurementUnits.GRAMS,
                          ingredient);
                      realIng = ingredient;
                      String ingredientStr = realIng.getItem();
                      
                      if (realIng.getCalories() == null)
                      {
                        zeroCalIng.add(ingredientStr);
                      }
                      else
                      {
                        CalPerGramCalculator calculator = new CalPerGramCalculator(realIng);

                        double calories = calculator.calculateCalFromGrams(grams);
                        totalCalories += calories;
                      }
                    }
                  }
                }
              }
              else 
              {
                System.exit(JFrame.EXIT_ON_CLOSE);
              }
              
            }

            //if number ends in .0 return the integer of that number
            if ((totalCalories / servings) == Math.floor(totalCalories / servings))
            {
              totalCalories = Math.round(totalCalories / servings);
              truncated = (int) totalCalories;
              JOptionPane.showMessageDialog(frame,
                  TOTAL_CAL_MEAL + (int) truncated / servings); 
            }
            else
            {
              JOptionPane.showMessageDialog(frame, TOTAL_CAL_MEAL
                  + CalPerGramCalculator.roundToSignificantDigits(totalCalories / servings));
            }

            // display ingredients that didn't contain calories
            if (!zeroCalIng.isEmpty())
            {
              String zeroCalories = String.join(COMMA, zeroCalIng);
              JOptionPane.showMessageDialog(frame,
                  ING_NO_CAL
                      + zeroCalories);
            }

          }

        }

        else
        {
          System.exit(JFrame.EXIT_ON_CLOSE);
        }
      }
    });

    // Add the components to the panel
    panel2.add(Box.createVerticalStrut(100)); // add some space at the top
    panel2.add(ingredientsLabel);
    panel2.add(Box.createHorizontalStrut(10));
    panel2.add(ingredientsDropdown);
    panel2.add(Box.createHorizontalStrut(20));
    panel2.add(amountLabel);
    panel2.add(amountField);
    panel2.add(Box.createHorizontalStrut(20));
    panel2.add(unitsLabel);
    panel2.add(unitsDropdown);
    panel2.add(Box.createHorizontalGlue());
    panel3.add(Box.createVerticalStrut(0));
    panel3.add(calorieBox);

    // Add the panel to the JFrame
    add(panel);
    add(panel2);
    add(panel3);

    // Set the size of the JFrame
    setSize(900, 400); // make the JFrame slightly taller

    // Exit the application when the JFrame is closed
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    // Set the JFrame to be visible.
    setVisible(true);



  }
}
