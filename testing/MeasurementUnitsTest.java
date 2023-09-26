package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import math.MeasurementUnits;

class MeasurementUnitsTest
{

  @Test
  void testGetUnit() 
  {
    assertEquals("cups (cup)", MeasurementUnits.CUPS.getUnit());
  }

  @Test
  void testStupidList()
  {
    assertEquals(14, MeasurementUnits.getAllUnits().length); 
  }

  @Test
  void testGetType()
  {
    assertEquals("weight", MeasurementUnits.DRAM.getType());
  }

  @Test
  void testisVolumeAndIsWeight()
  {
    assertEquals(true, MeasurementUnits.isWeightUnit(MeasurementUnits.DRAM));
    assertEquals(false, MeasurementUnits.isWeightUnit(MeasurementUnits.FL_OZ));
    assertEquals(true, MeasurementUnits.isVolumeUnit(MeasurementUnits.CUPS));
    assertEquals(false, MeasurementUnits.isVolumeUnit(MeasurementUnits.GRAMS));
  }
  
  @Test
  void testFromUnits()
  {
    assertEquals(MeasurementUnits.COUNT, MeasurementUnits.fromUnit("individual"));
    assertEquals(MeasurementUnits.GAL, MeasurementUnits.fromUnit("gallons (gal)"));
    try
    {
      MeasurementUnits.fromUnit("kilograms");
    } catch (IllegalArgumentException e)
    {
      
    }
  }

}
