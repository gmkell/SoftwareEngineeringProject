package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import math.MeasurementUnits;
import math.UnitConversions;
import recipe.GenericIngredients;
import recipe.Ingredients;

class UnitConversionsTest
{

  HashMap<String, Ingredients> list = GenericIngredients.getDefaultIngredients(); 
  Ingredients flour = list.get("flour");
  Ingredients chicken = list.get("chicken"); 
  
  @Test
  void testUnitConversions()
  { 
    new UnitConversions(); 
  }

  @Test 
  void testConvertSimple() 
  {
    new UnitConversions();  
    Double amount = 20.0;  
    MeasurementUnits toUnit = MeasurementUnits.TSP;
    MeasurementUnits fromUnit = MeasurementUnits.TBS;

    assertEquals(60.0, UnitConversions.convert(amount, fromUnit, toUnit, flour));
    fromUnit = MeasurementUnits.TSP;
    assertEquals(amount, UnitConversions.convert(amount, fromUnit, toUnit, flour));
    
    toUnit = MeasurementUnits.POUNDS; 
    fromUnit = MeasurementUnits.OUNCES; 
    
    amount = 60.0; 
    assertEquals(3.8, UnitConversions.convert(amount, fromUnit, toUnit, chicken));
    
  }
   
  
  @Test
  void testConvertComplex()   
  {        

    Double amount = 10.0; 
    MeasurementUnits fromUnit = MeasurementUnits.GRAMS;   
    MeasurementUnits toUnit = MeasurementUnits.miliL;
    
    assertEquals(9.6, UnitConversions.convert(amount, fromUnit, toUnit, chicken));
    
    // when ingredient is null
    assertEquals(10.0, UnitConversions.convert(amount, fromUnit, toUnit, null));
    
    amount = 1.0;  
    fromUnit = MeasurementUnits.CUPS;
    toUnit = MeasurementUnits.OUNCES; 
    assertEquals(8.7, UnitConversions.convert(amount, fromUnit, toUnit, chicken));
    
    
    // POUNDS to GRAMS
    amount = 1.0; 
    fromUnit = MeasurementUnits.POUNDS;
    toUnit = MeasurementUnits.GRAMS;
    assertEquals(453.6, UnitConversions.convert(amount, fromUnit, toUnit, chicken)); 
   
    
    // TSP to CUPS
    amount = 48.0;
    fromUnit = MeasurementUnits.TSP;
    toUnit = MeasurementUnits.CUPS;     
    assertEquals(1.0, UnitConversions.convert(amount, fromUnit, toUnit, flour)); 
    
    amount = 96.0;
    assertEquals(2.0, UnitConversions.convert(amount, fromUnit, toUnit, flour)); 

    // MiliL to OZ 
    amount = 100.0;
    fromUnit = MeasurementUnits.miliL;
    toUnit = MeasurementUnits.OUNCES;
    
    assertEquals(3.7, UnitConversions.convert(amount, fromUnit, toUnit, chicken));
    
    amount = 200.0;
    assertEquals(7.3, UnitConversions.convert(amount, fromUnit, toUnit, chicken), 0.001);
           
  }
  
  @Test 
  void testConvertToGRAMS()
  {
    double amount;
    MeasurementUnits fromUnit, toUnit;
    
    // DRAMS to GRAMS
    amount = 10.0;
    fromUnit = MeasurementUnits.DRAM;
    toUnit = MeasurementUnits.GRAMS;
    assertEquals(17.7, UnitConversions.convert(amount, fromUnit, toUnit, null), 0.01);
    
    // PINCHES TO GRAMS
    amount = 7.0;
    fromUnit = MeasurementUnits.PINCHES;
    toUnit = MeasurementUnits.GRAMS;
    assertEquals(2.2, UnitConversions.convert(amount, fromUnit, toUnit, null));    
    
    // CUPS to GRAMS 
    amount = 3.0;
    fromUnit = MeasurementUnits.CUPS;
    assertEquals(709.8, UnitConversions.convert(amount, fromUnit, toUnit, null), 0.01);    
    
    // QUARTS to GRAMS
    amount = 8.0;
    fromUnit = MeasurementUnits.QUARTS;        
  }
  
  @Test 
  void testConvertMILI()
  {
    double amount;
    MeasurementUnits fromUnit, toUnit;
    
    // TSP to MIL
    amount = 150.0;
    fromUnit = MeasurementUnits.TSP;
    toUnit = MeasurementUnits.miliL;
    assertEquals(739.3, UnitConversions.convert(amount, fromUnit, toUnit, null));
    
    // PINT to MIL
    amount = 1.5;
    fromUnit = MeasurementUnits.PINTS;
    assertEquals(709.8, UnitConversions.convert(amount, fromUnit, toUnit, null));
    
    // MIL to PINT
    amount = 1000.0;
    fromUnit = MeasurementUnits.miliL;
    toUnit = MeasurementUnits.PINTS;
    assertEquals(2.1, UnitConversions.convert(amount, fromUnit, toUnit, chicken));
  }

  @Test
  void testGetConversionFactor()
  {
    double expected = UnitConversions.getConversionFactor(MeasurementUnits.CUPS, chicken);
    assertEquals(246.05152, expected); 
    expected = UnitConversions.getConversionFactor(MeasurementUnits.GRAMS, null);
    assertEquals(1.0, expected);
    expected = UnitConversions.getConversionFactor(MeasurementUnits.CUPS, null);
    assertEquals(236.588, expected);
  }
}
