/**
 * 
 */
package math;

/**
 * Holds supported list of measurement units.
 * 
 * @author Gillian Kelly
 *
 */
public enum MeasurementUnits
{
 DRAM("drams (dr)", Constants.WEIGHT), 
 GRAMS("grams (g)", Constants.WEIGHT), 
 POUNDS("pounds (lb)",Constants.WEIGHT), 
 OUNCES("ounces (oz)", Constants.WEIGHT), 
 PINCHES("pinches (p)",Constants.VOLUME), 
 TSP("teaspoons (tsp)", Constants.VOLUME), 
 TBS("tablespoon (tbs)", Constants.VOLUME),
 FL_OZ("fluid ounces (floz)", Constants.VOLUME), 
 CUPS("cups (cup)",Constants.VOLUME),
 PINTS("pints (pt)", Constants.VOLUME),
 QUARTS("quarts (qt)",Constants.VOLUME), 
 GAL("gallons (gal)", Constants.VOLUME), 
 miliL("milliliters (ml)", Constants.VOLUME),
 COUNT("individual", Constants.WEIGHT); 

  private String unit;
  private String type;

  private static class Constants
  {
    public static final String WEIGHT = "weight";
    public static final String VOLUME = "volume";
  }

  private MeasurementUnits(final String unit, final String type)
  {
    this.unit = unit;
    this.type = type;
  }

  /**
   * @return unit name
   */
  public String getUnit()
  {
    return unit;
  }
  
  /**
   * @return  type
   */
  public String getType()
  {
    return type;
  }

  /**
   * @param unit
   * @return if given unit is a weight measurement unit
   */
  public static boolean isWeightUnit(final MeasurementUnits unit)
  {
    if (unit.getType().equals(Constants.WEIGHT))
    {
      return true;
    }
    return false;
  }

  /**
   * @param unit
   * @return true if given Measurement unit is a Volume unit
   */
  public static boolean isVolumeUnit(final MeasurementUnits unit)
  {
    if (unit.getType().equals(Constants.VOLUME))
    {
      return true;
    }
    return false;
  }
  
  /**
   * @return an array containing the units of all 
   */
  public static String[] getAllUnits()
  {
    MeasurementUnits[] bs= MeasurementUnits.values();
    String[] allUnits= new String[bs.length];
    
    for (int i=0; i<bs.length; i++)
    {
      allUnits[i]= bs[i].getUnit();
    }
    return allUnits;
  }
  
  /**
   * This method retrieves the MeasurementUnits value from the unit String.
   * 
   * @param unit The unit selected from the dropdown
   *          
   * @return mu MeasurementUnit value from enum
   */
  // method for converting unit aspect to MeasurementUnit
  public static MeasurementUnits fromUnit(final String unit)
  {
    for (MeasurementUnits mu : MeasurementUnits.values())
    {
      if (mu.getUnit().equalsIgnoreCase(unit))
      { 
        return mu;
      } 
    }
    throw new IllegalArgumentException("Invalid unit: " + unit);
  }
  
  
  
}
