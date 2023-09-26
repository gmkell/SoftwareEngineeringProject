package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import math.ConversionFactor;
import math.MeasurementUnits;

/**
 * @author Gillian Kelly
 *
 */
public class ConversionFactorTest
{

  @Test
  void allTests()  
  {
    ConversionFactor tbsToTsp = ConversionFactor.TBS_TO_TSP;
    assertEquals(3.0, tbsToTsp.getAmounts());
    assertEquals(MeasurementUnits.TBS, tbsToTsp.getFromUnits());
    assertEquals(MeasurementUnits.TSP, tbsToTsp.getToUnits());
    // findToUnits
    MeasurementUnits result = ConversionFactor.findToUnits(MeasurementUnits.QUARTS);
    assertEquals(MeasurementUnits.miliL, result);

    result = ConversionFactor.findToUnits(MeasurementUnits.POUNDS);
    assertEquals(MeasurementUnits.OUNCES, result);
    
    
    result = ConversionFactor.findToUnits(MeasurementUnits.FL_OZ);
    assertEquals(MeasurementUnits.miliL, result); 
    
    try 
    {
      result = ConversionFactor.findToUnits(MeasurementUnits.COUNT);
    } catch (IllegalArgumentException e)
    {
      // should throw
    }
  }

}
