/**
 * 
 */
package math;

/**
 * Holds supported conversion factors.
 * 
 * @author Gillian Kelly
 *
 */
public enum ConversionFactor
{
  // Weight Conversions
  LB_TO_OZ(MeasurementUnits.POUNDS, MeasurementUnits.OUNCES, 16.0), 
  OZ_TO_DR(MeasurementUnits.OUNCES, MeasurementUnits.DRAM, 16.0), 
  OZ_TO_GR(MeasurementUnits.OUNCES, MeasurementUnits.GRAMS, 28.34952),
  // Volume Conversions
  TSP_TO_P(MeasurementUnits.TSP, MeasurementUnits.PINCHES, 16.0), 
  TBS_TO_TSP(MeasurementUnits.TBS, MeasurementUnits.TSP,3.0),
  FlOZ_TO_TBS(MeasurementUnits.FL_OZ, MeasurementUnits.TBS, 2.0), 
  CUP_TO_FlOZ(MeasurementUnits.CUPS, MeasurementUnits.FL_OZ, 8.0), 
  PT_TO_CUP(MeasurementUnits.PINTS, MeasurementUnits.CUPS, 2.0), 
  QT_TO_PT(MeasurementUnits.QUARTS, MeasurementUnits.PINTS, 2.0), 
  GL_TO_QT(MeasurementUnits.GAL, MeasurementUnits.QUARTS,4.0), 
  TBS_TO_Ml(MeasurementUnits.TBS,MeasurementUnits.miliL, 14.7867648), 
  CUP_TO_Ml(MeasurementUnits.CUPS, MeasurementUnits.miliL, 236.58824), 
  FlOZ_TO_Ml(MeasurementUnits.FL_OZ,MeasurementUnits.miliL, 29.57353),
  
  //Additional ConversionFactors (for my sanity)
  PT_TO_Ml(MeasurementUnits.PINTS, MeasurementUnits.miliL, 473.176),
  GL_TO_Ml(MeasurementUnits.GAL, MeasurementUnits.miliL, 3785.41),
  TSP_TO_Ml(MeasurementUnits.TSP, MeasurementUnits.miliL, 4.92892),
  OZ_TO_Ml(MeasurementUnits.OUNCES, MeasurementUnits.miliL, 0.033814),
  QT_TO_Ml(MeasurementUnits.QUARTS, MeasurementUnits.miliL, 0.00105669);
  
  

  private MeasurementUnits fromUnits, toUnits;
  private Double amount;

  private ConversionFactor(final MeasurementUnits fromUnits, final MeasurementUnits toUnits, 
      final Double amount)
  {
    this.fromUnits = fromUnits;
    this.toUnits = toUnits;
    this.amount = amount;
  }

  /**
   * @return fromUnits
   */
  public MeasurementUnits getFromUnits()
  {
    return fromUnits;
  }

  /**
   * @return toUnits
   */
  public MeasurementUnits getToUnits()
  {
    return toUnits;
  }

  /**
   * @return amount
   */
  public Double getAmounts()
  {
    return amount;
  }

  /**
   * Method to find toUnits associated with fromUnits.
   * 
   * @param fromUnit
   * @return associated toUnits from the fromUnits
   */
  public static MeasurementUnits findToUnits(final MeasurementUnits fromUnit)
  {
    MeasurementUnits result = null;
 
    // loop thru all conversion factors
    for (ConversionFactor f : ConversionFactor.values())
    {
      // find conversion factor with desired fromUnit
      if (f.getFromUnits().equals(fromUnit)) 
      {
        result = f.getToUnits(); 
      }
    }

    // unsupported conversion
    if (result == null)
    {
      throw new IllegalArgumentException("Invalid fromUnits: " + fromUnit.getUnit());
    }
    return result;
  }
  
}
