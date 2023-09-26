/**
 * 
 */
package math;

import java.util.HashMap;

import recipe.Ingredients;

/**
 * Class for calcualting conversions between MeasurementUnits.
 * 
 * @author Gillian Kelly
 *
 */
public class UnitConversions
{

  /**
   * Holds the number of a certain MeasurementUnit in a GRAM.
   */
  private static HashMap<MeasurementUnits, Double> conversionFactors = new HashMap<>();
  static
  {
    conversionFactors.put(MeasurementUnits.GRAMS, 1.0);
    conversionFactors.put(MeasurementUnits.TSP, 4.92892);
    conversionFactors.put(MeasurementUnits.TBS, 14.7868);
    conversionFactors.put(MeasurementUnits.CUPS, 236.588);
    conversionFactors.put(MeasurementUnits.miliL, 1.0);
    conversionFactors.put(MeasurementUnits.OUNCES, 28.3495);
    conversionFactors.put(MeasurementUnits.POUNDS, 453.592);
    conversionFactors.put(MeasurementUnits.DRAM, 1.77185);
    conversionFactors.put(MeasurementUnits.PINCHES, 0.3125);
    conversionFactors.put(MeasurementUnits.FL_OZ, ConversionFactor.FlOZ_TO_Ml.getAmounts());
    conversionFactors.put(MeasurementUnits.PINTS, ConversionFactor.PT_TO_Ml.getAmounts());
    conversionFactors.put(MeasurementUnits.QUARTS, ConversionFactor.QT_TO_Ml.getAmounts());
    conversionFactors.put(MeasurementUnits.GAL, ConversionFactor.GL_TO_Ml.getAmounts());
    conversionFactors.put(MeasurementUnits.COUNT, 1.0);
  }

  /**
   * Constructor.
   */
  public UnitConversions()
  {
    super();
  }

  /**
   * Method to convert from one MeasurementUnit to another, using the supported ConversionFactors.
   * 
   * @param amount
   * @param fromUnit
   * @param toUnit
   * @param ingredient
   * 
   * @return conversion from fromUnit to toUnit
   */
  public static double convert(final double amount, final MeasurementUnits fromUnit,
      final MeasurementUnits toUnit, final Ingredients ingredient)
  {
    double density = 1.0;
    double baseAmount;
    double convertedValue;

    if (ingredient != null)
    {
      if (ingredient.getDensity() == null)
      {
        density = 1.0;
      }
      else {
        density = ingredient.getDensity();
      }
     
    }

    // conversion btw WEIGHT units
    if (MeasurementUnits.isWeightUnit(fromUnit) && MeasurementUnits.isWeightUnit(toUnit))
    {
      baseAmount = amount * getConversionFactor(fromUnit);
      convertedValue = baseAmount / getConversionFactor(toUnit);
    }
    // conversion btw VOLUME units
    if (MeasurementUnits.isVolumeUnit(fromUnit) && MeasurementUnits.isVolumeUnit(toUnit))
    {
      baseAmount = amount * getConversionFactor(fromUnit);
      convertedValue = baseAmount / getConversionFactor(toUnit);
    }

    // Convert to a common base unit (grams or milliliters)
    baseAmount = amount * getConversionFactor(fromUnit);
    if (MeasurementUnits.isVolumeUnit(fromUnit))
    {
      baseAmount *= density;
    }

    // Convert from the base unit (grams or milliliters) to the target unit
    convertedValue = baseAmount / getConversionFactor(toUnit);
    if (MeasurementUnits.isVolumeUnit(toUnit))
    {
      convertedValue /= density;
    }

    return CalPerGramCalculator.roundToSignificantDigits(convertedValue);

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

  private static double getConversionFactor(final MeasurementUnits u)
  {
    return conversionFactors.get(u);
  }

  /**
   * @param fromUnits
   * @param ingredient
   * @return  associated conversion factor related to GRAMS 
   */
  public static double getConversionFactor(final MeasurementUnits fromUnits,
      final Ingredients ingredient)
  {
    double factor = conversionFactors.get(fromUnits);
    if (MeasurementUnits.isVolumeUnit(fromUnits) && ingredient != null)
    {
      factor *= ingredient.getDensity();
    }
    return factor;
  }

}
