package recipe;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import math.MeasurementUnits;

/**
 * MeasuredIngredient represent the Ingredient added to a recipe. It has an Ingredient (name and
 * corresponding generic ingredient details) an amount, a MeasurementUnits, and details.
 * 
 * @author Emil Hofstetter
 *
 */
public class MeasuredIngredient 
    implements Comparable<MeasuredIngredient>, Serializable, gui.SortedJList.Verbose
{
  private static final long serialVersionUID = 1L;
  private final Ingredients ing;
  private Double amount; // removed final modifier to allow it to change for meals 
  private MeasurementUnits unit; // removed final modifier to allow modification in meals 
  private final String details;

  /**
   * Constructor.
   * 
   * @param ing
   *          is the Ingredient
   * @param amount
   *          is the amount of said ingredient
   * @param unit
   *          is the measurement unit for the amount
   * @param details
   *          is the additional details to an ingredient
   */
  public MeasuredIngredient(final Ingredients ing, final Double amount, final MeasurementUnits unit,
      final String details)
  {
    this.ing = ing;
    this.amount = amount;
    this.unit = unit;
    this.details = details;
  }

  /**
   * Try to add a Ingredient, Amount, Unit, and Details from string.
   * 
   * 
   * @param ingredient
   * @param amount
   * @param unit
   * @param details
   * @return Null if: The amount entered was not a valid number or double amount.
   */
  public static MeasuredIngredient fromStringParams(final String ingredient, final String amount,
      final String unit, final String details)
  {
    Double parsedAmount;
    try
    {
      parsedAmount = Double.parseDouble(amount);
    }
    catch (NumberFormatException nfe)
    {
      return null;
    }

    Ingredients parsedIngredient = stringToIngredient(ingredient);
    MeasurementUnits parsedUnit = stringToUnit(unit);

    if (parsedIngredient == null || parsedAmount <= 0 || parsedUnit == null)
    {
      return null;
    }

    return new MeasuredIngredient(parsedIngredient, parsedAmount, parsedUnit, details);
  }

  /**
   * @return the string equivalent of the ingredient.
   */
  @Override
  public String toString()
  {
    return String.format("%s", this.ing.getItem());
  }

  /**
   * Get a verbose String version of the Measured ingredient.
   * 
   * @param verbose To indicate verboseness.
   * @return A verbose String if true is passed.
   */
  public String toString(final boolean verbose)
  {
    if (verbose) 
    {
      if(this.details == null || this.details.equals("")) 
        return String.format("%.1f %s of %s", this.amount, this.unit.getUnit(), this.ing.getItem());
      else return String.format("%.1f %s of %s %s", this.amount, this.unit.getUnit(), this.details,
          this.ing.getItem());
    } 
    else 
    {
      return this.toString();
    }
  }

  /** Compares an ingredient to another. */
  @Override
  public int compareTo(final MeasuredIngredient o)
  {
    return this.ing.getItem().compareTo(o.ing.getItem());
  }

  /**
   * Helper to parse a String to an Ingredient.
   * 
   * @param ingredient
   * @return Null if the Ingredients is invalid.
   * @throws IngredientsLoadingException
   */
  private static Ingredients stringToIngredient(final String ingredient)
  {
    if (Ingredients.isValidIngredient(ingredient.toLowerCase()))
    {
      return Ingredients.getIngredient(ingredient);
    }

    int choice = JOptionPane.showConfirmDialog(new JFrame(),
        "The ingredient you specified was not found. Would you like to create one?");

    if (choice != JOptionPane.YES_OPTION)
    {
      return null;
    }

    JPanel createDialogPanel = new JPanel();
    // createDialog.add(createDialogPanel);
    JTextField newIngredientNamePanel = new JTextField(10);
    createDialogPanel.add(new JLabel("Ingredient"));
    createDialogPanel.add(newIngredientNamePanel);
    JTextField rawCaloriesPanel = new JTextField(10);
    createDialogPanel.add(new JLabel("Calories (cal/100g)"));
    createDialogPanel.add(rawCaloriesPanel);
    JTextField rawDensityPanel = new JTextField(10);
    createDialogPanel.add(new JLabel("Density (g/ml)"));
    createDialogPanel.add(rawDensityPanel)
    ;
    JTextField rawAverageWeightPanel = new JTextField(10);
    createDialogPanel.add(new JLabel("Average Weight (g)"));
    createDialogPanel.add(rawAverageWeightPanel);
    
    int confirmIng = JOptionPane.showConfirmDialog(
        new JFrame(), 
        createDialogPanel,
        "Please define your ingredient.", 
        JOptionPane.OK_CANCEL_OPTION
    );
    
    if (confirmIng != JOptionPane.OK_OPTION) 
    {
      return null;
    }
    
    String newIngredientName = newIngredientNamePanel.getText();
    String rawCalories = rawCaloriesPanel.getText();
    String rawDensity = rawDensityPanel.getText();
    String rawAverageWeight = rawAverageWeightPanel.getText();
    
    if (
        newIngredientName.equals("") 
    ) 
    {
      JOptionPane.showConfirmDialog(new JFrame(), "Please enter an ingredient name.");

      return null;
    }

    Double calories, density, avgWeight;
    try
    {
      calories = rawCalories.equals("") ? null : Double.parseDouble(rawCalories);

      density = rawDensity.equals("") ? null : Double.parseDouble(rawDensity);
      
      avgWeight = rawAverageWeight.equals("") ? null : Double.parseDouble(rawAverageWeight);

      if (
          (calories != null && calories < 0) 
          || (density != null && density <= 0) 
          || (avgWeight != null && avgWeight <= 0)
      )
      {
        throw new NumberFormatException();
      }

    }
    catch (NumberFormatException nfe)
    {
      JOptionPane.showConfirmDialog(new JFrame(), "Please enter a valid number.");

      return null;
    }

    return Ingredients.getOrCreateIngredient(newIngredientName, calories, density, avgWeight);
  }

  /**
   * Helper to parse a String to a MeasurementUnits.
   * 
   * @param unit
   * @return Null if the MeasurementUnits is invalid.
   */
  private static MeasurementUnits stringToUnit(final String unit)
  {
    for (MeasurementUnits variant : MeasurementUnits.values())
    {
      if (variant.getUnit().equalsIgnoreCase(unit))
      {
        return variant;
      }
    }
    return null;
  }

  /**
   * (Gillian added this).
   * @param amount
   */
  public void setAmount(final Double amount)
  {
    this.amount = amount;
  }
  
  /**
   * (Gillian added this). 
   * @param units
   */
  public void setUnits(final MeasurementUnits units)
  {
    this.unit = units;
  }

  /**
   * 
   * @return The ingredient name.
   */
  public String getIngredient()
  {
    return this.ing.getItem();
  }

  /**
   * 
   * @return The double amount.
   */
  public Double getAmount()
  {
    return this.amount;
  }

  /**
   * 
   * @return The measurement unit.
   */
  public MeasurementUnits getUnit()
  {
    return this.unit;
  }

  /**
   * 
   * @return The ingredient details.
   */
  public String getDetails()
  {
    return this.details;
  }
}
