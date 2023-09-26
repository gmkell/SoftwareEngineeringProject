package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import recipe.Ingredients;
import recipe.MeasuredIngredient;
import math.MeasurementUnits;

import org.junit.jupiter.api.Test;

class TestMeasuredIngredient
{

  @Test
  void testSerialization() throws IOException
  {
    String file = "recipe.ser";
    MeasuredIngredient mg = new MeasuredIngredient(
        Ingredients.getIngredient("Milk"),
        1.0,
        MeasurementUnits.CUPS,
        "whole"
    );
    
    FileOutputStream fos = new FileOutputStream(file);
    
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    
    oos.writeObject(mg);
    
    fos.close();
  }
  
  @Test
  void testConstructor()
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.CUPS.getUnit(),
        "skim"
    );

    assertNotNull(mg);

    // Invalid, negative amount
    mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "-1.0",
        "cups",
        "skim"
    );
    assertNull(mg);

    // Invalid, invalid amount
    mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "faew",
        "cups",
        "skim"
    );
    assertNull(mg);

    // Invalid, invalid unit
    mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        "blurb",
        "skim"
    );
    assertNull(mg);

    // Invalid, invalid ingredient
    mg = MeasuredIngredient.fromStringParams(
        "broth",
        "1.0",
        "blurb",
        "skim"
    );
    assertNull(mg);

    // Create a new ingredient
    mg = MeasuredIngredient.fromStringParams(
        "Cicken Broth",
        "1.0",
        "cups",
        "light"
    );
    assertNull(mg);
  }
  
  @Test
  void testGetters() 
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.CUPS.getUnit(),
        "skim"
    );
    
    assertEquals("Milk", mg.getIngredient());
    assertEquals(1.0, mg.getAmount());
    assertEquals("skim", mg.getDetails());
    assertEquals(MeasurementUnits.CUPS, mg.getUnit());
  }

  @Test
  void testCompare() 
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.CUPS.getUnit(),
        "skim"
    );

    MeasuredIngredient o = MeasuredIngredient.fromStringParams(
        "Bean",
        "1.0",
        MeasurementUnits.CUPS.getUnit(),
        ""
    );
    
    assertTrue(mg.compareTo(o) > 1);
  }

  @Test
  void testToString() 
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.GRAMS.getUnit(),
        "skim"
    );

    assertEquals("Milk", mg.toString());
    assertEquals("1.0 grams (g) of skim Milk", mg.toString(true));
    assertEquals("Milk", mg.toString(false));

    mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.GRAMS.getUnit(),
        ""
    );

    assertEquals("Milk", mg.toString());
    assertEquals("1.0 grams (g) of Milk", mg.toString(true));
    assertEquals("Milk", mg.toString(false));
  }

  @Test
  void testSetUnits() 
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.GRAMS.getUnit(),
        "skim"
    );
    
    mg.setUnits(MeasurementUnits.CUPS);

    assertEquals(MeasurementUnits.CUPS, mg.getUnit());
  }

  @Test
  void testSetDoubleAmount() 
  {
    // Valid
    MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
        "Milk",
        "1.0",
        MeasurementUnits.GRAMS.getUnit(),
        "skim"
    );
    
    mg.setAmount(20.0);

    assertEquals(20.0, mg.getAmount(), 0.01);
  }
}
