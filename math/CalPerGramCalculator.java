package math;

import recipe.Ingredients;

/**
 * Calculates the total calories of x grams of an ingredient.
 * 
 * @author Terry Johnson
 * @version 345 Project
 *
 */
public class CalPerGramCalculator
{

  double calPerGram;
  Ingredients ingredient;

  /**
   * Constructor for class.
   * 
   * @param ingredient
   *          - Ingredient calculating calories for
   * 
   */
  public CalPerGramCalculator(final Ingredients ingredient)

  {
    this.ingredient = ingredient;
  }

  /**
   * This method calculates the total calories in an amount of grams.
   * 
   * @param grams
   *          amount of grams to calculate calories from
   * 
   * @return amount of calories from grams
   */
  public double calculateCalFromGrams(final double grams)
  {
    calPerGram = ingredient.getCalories() / 100.0;
    double calories = grams * calPerGram;
    return roundToSignificantDigits(calories);

  }

  public static double roundToSignificantDigits(double num)
  {
    double roundedNum;
    double decimalPart = num - Math.floor(num); // Get the decimal part of the number

    if (decimalPart < 0.1 && decimalPart > 0.0)
    { // Check if the first two digits after the decimal point are less than 10
      int significantDigitIndex = 0;
      String numStr = Double.toString(num);

      for (int i = 0; i < numStr.length(); i++)
      {
        if (numStr.charAt(i) == '.')
        {
          significantDigitIndex = i + 1;
          break;
        }
      }

      if (significantDigitIndex == 0)
      { // The number has no decimal point
        return num;
      }

      for (int i = significantDigitIndex; i < numStr.length(); i++)
      {
        char digitChar = numStr.charAt(i);
        if (digitChar != '0' && digitChar != '.')
        {
          int digitValue = Character.getNumericValue(digitChar);
          int roundingFactor = (int) Math.pow(10, i - significantDigitIndex + 1);
          roundedNum = Math.round(num * roundingFactor) / (double) roundingFactor;
          return roundedNum;
        }
      }

      // If all digits after the decimal are 0, return the original number
      return num;

    }
    else
    { // If the first two digits after the decimal are 10 or greater
      roundedNum = Math.round(num * 10) / 10.0;
      return roundedNum;
    }
  }

}
