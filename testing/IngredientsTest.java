package testing;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import recipe.GenericIngredients;
import recipe.Ingredients;
import java.io.File;

class IngredientsTest
{

  @Test
  public void getterMethodTest()
  {
    Ingredients alcohol, peach, crab;
    alcohol = Ingredients.getIngredient("Alcohol");
    peach = Ingredients.getIngredient("Peach");
    crab = Ingredients.getIngredient("Crab");
    
    assertEquals("Alcohol", alcohol.getItem(), "Get alcohol string.");
    assertEquals("Peach", peach.getItem(), "Get peach string.");
    assertEquals("Crab", crab.getItem(), "Get crab string.");
    
    assertEquals(275, alcohol.getCalories(), "Get alcohol calories.");
    assertEquals(30, peach.getCalories(), "Get peach calories.");
    assertEquals(110, crab.getCalories(), "Get crab calories.");
    
    assertEquals(0.79, alcohol.getDensity(), 0.01, "Get alcohol density.");
    assertEquals(0.61, peach.getDensity(), 0.01, "Get peach density.");
    assertEquals(0.61, crab.getDensity(), 0.01, "Get crab density.");
    
    assertNull(alcohol.getAvgWeight(), "Alcohol average weight is null.");
    assertEquals(175.0, peach.getAvgWeight(), 0.01, "Get peach average weight.");
    assertEquals(151.2, crab.getAvgWeight(), 0.01, "Get crab average weight.");
  }
  
  @Test
  public void testCustomIngrdient()
  {
    File userIngredientFile = new File("user.ings");
    userIngredientFile.delete();
    Ingredients.getOrCreateIngredient("Yuzu", 0.00, 0.12, 12.0);
    assertNotNull(Ingredients.getIngredient("Yuzu"));
  }

  @Test
  public void getAllIngredientsNamesTest()
  {
    File userIngredientFile = new File("user.ings");
    userIngredientFile.delete();

    List<String> actual = Arrays.asList(Ingredients.getAvailableIngredientsNames());
    
    ArrayList<String> expected = 
        new ArrayList<>(GenericIngredients.getDefaultIngredients().keySet());
    
    for (int i = 0; i < expected.size(); i++)
    {
      assertNotEquals(-1, actual.indexOf(expected.get(i)));
    }
    
    // Add a new ingredient.
    Ingredients.getOrCreateIngredient("Yuzu", 0.00, 0.12, 12.0);
    assertNotNull(Ingredients.getIngredient("Yuzu"));

    actual = Arrays.asList(Ingredients.getAvailableIngredientsNames());
    
    assertNotEquals(-1, actual.indexOf("yuzu"));
    userIngredientFile.delete();
  }
  
  @Test
  public void testIsValidIngredient()
  {
    // Normal spelling.
    assertTrue(Ingredients.isValidIngredient("Bean"));

    // Lower-case spelling.
    assertTrue(Ingredients.isValidIngredient("bean"));

    // off-case spelling.
    assertTrue(Ingredients.isValidIngredient("bEaN"));

    // wrong-case spelling.
    assertFalse(Ingredients.isValidIngredient("Baen"));
  }
}
