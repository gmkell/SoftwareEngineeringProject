package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import recipe.DetailedUtensil;
import recipe.Meal;
import recipe.MeasuredIngredient;
import recipe.Recipe;
import recipe.Step;

// TODO: coverage for exceptions in deserialize and serialize 
class MealTest
{
  Meal meal;
  Recipe bananasFoster, macNcheese;
  String name = "Easter";
  MeasuredIngredient[] ingredients;
  ArrayList<MeasuredIngredient> ingList = new ArrayList<>();
  DetailedUtensil[] utensils;
  ArrayList<DetailedUtensil> utlList = new ArrayList<>();
  Step[] steps;
  ArrayList<Step> stepList = new ArrayList<>();
  ArrayList<Recipe> recipes;
  int servings;
 
  @BeforeEach
  void setUp() throws Exception
  {
    recipes = new ArrayList<>();
    bananasFoster = Recipe.deserialize(new File("Bananas_Foster.rcp"));
    macNcheese = Recipe.deserialize(new File("Macaroni_And_Cheese.rcp"));
    recipes.add(bananasFoster);
    recipes.add(macNcheese);
    // initialize fields
    for (Recipe r : recipes)
    {
      for (MeasuredIngredient ing : r.getIngredients())
      {
        ingList.add(ing);
      }
      for (DetailedUtensil utl : r.getUtensils())
      {
        utlList.add(utl);
      }
      for (Step stp : r.getSteps())
      {
        stepList.add(stp);
      }
    }

    ingredients = new MeasuredIngredient[ingList.size()];
    utensils = new DetailedUtensil[utlList.size()];
    steps = new Step[stepList.size()];

    for (int i = 0; i < ingredients.length; i++)
    {
      ingredients[i] = ingList.get(i);
    }

    for (int u = 0; u < utensils.length; u++)
    {
      utensils[u] = utlList.get(u);
    }

    for (int s = 0; s < steps.length; s++)
    {
      steps[s] = stepList.get(s);
    }

  }

  @Test
  void testConstructors()
  {
    new Meal();
    new Meal(name, 12);

  }

  @Test
  void testSimpleGettersAndSetters()
  {
    meal = new Meal();
    meal.setName(name);
    assertEquals(name, meal.getName());

    meal.setIngredients(macNcheese.getIngredients());
    assertEquals(macNcheese.getIngredients().length, meal.getIngredients().length);

    meal.setSteps(macNcheese.getSteps());
    assertEquals(macNcheese.getSteps().length, meal.getSteps().length);

    meal.setUtensils(macNcheese.getUtensils());
    assertEquals(macNcheese.getUtensils().length, meal.getUtensils().length);

    meal.setServings(5);
    assertEquals(5, meal.getServings());

    meal.setDuration(10);
    assertEquals(10, meal.getDuration());
  }

  @Test
  void testGetRecipes()
  {
    meal = new Meal(name, 12, utensils, ingredients, steps, recipes);
    assertEquals(2, meal.getRecipes().size());

  }

  @Test
  void testGetIngredients()
  {
    meal = new Meal();
    meal.setName(name);
    meal.setIngredients(ingredients);
    meal.setSteps(steps);
    meal.setUtensils(utensils);

    MeasuredIngredient[] expected = meal.getIngredients();

    assertEquals(expected.length, ingredients.length);
  }

  @Test
  void testGetUtensils()
  {
    meal = new Meal(name, 12, utensils, ingredients, steps, recipes);
    DetailedUtensil[] expected = meal.getUtensils();
    assertEquals(expected, utensils);
  }

  @Test
  void testGetSteps()
  {
    meal = new Meal(name, 12, utensils, ingredients, steps, recipes);
    Step[] expected = meal.getSteps();
    assertEquals(expected, steps);
  }

  @Test
  void testConvertIngredients()
  {
    // Banans Foster serves 3
    // Mac And Cheese serves 4
    meal = new Meal(name, 12, utensils, ingredients, steps, recipes);

    // when servings are different
    meal.setIngredients(ingredients); // only way to get ingredients to meal
    MeasuredIngredient[] expected = meal.convertIngredients(meal.getServings(),
        meal.getIngredients());

    // for (MeasuredIngredient ing : expected)
    // {
    // System.out.println(ing.getAmount() + " " + ing.getIngredient());
    // }
    //
    // check to make sure amounts were properly updated
    for (MeasuredIngredient ing : expected)
    {
      if (ing.getIngredient().equals("Banana"))
      {
        assertEquals(12.0, ing.getAmount());
      }

      if (ing.getIngredient().equals("Rum"))
      {
        assertEquals(1.0, ing.getAmount());
      }
      if (ing.getIngredient().equals("American cheese"))
      {
        assertEquals(4.5, ing.getAmount());
      }

      if (ing.getIngredient().equals("Ice cream"))
      {
        assertEquals(8.0, ing.getAmount());
      }
    }

    // when serving are the same
    recipes.remove(bananasFoster);
    meal = new Meal(name, 4, utensils, ingredients, steps, recipes);
    expected = meal.convertIngredients(meal.getServings(), meal.getIngredients());

    for (MeasuredIngredient ing : expected)
    {
      if (ing.getIngredient().equals("American cheese"))
      {
        assertEquals(1.5, ing.getAmount());
      }
      if (ing.getIngredient().equals("Butter"))
      {
        assertEquals(1.0, ing.getAmount());
      }
      if (ing.getIngredient().equals("Onion"))
      {
        assertEquals(0.25, ing.getAmount());
      }
    }
  }

  @Test
  void testSerializeAndDeserialize()
      throws IOException, ClassNotFoundException
  {
    meal = new Meal(name, 12, utensils, ingredients, steps, recipes);
    meal.setIngredients(ingredients);
    meal.serialize();
    Meal deserializeMeal = Meal.deserialize("Easter.mel");
    assertEquals(12, deserializeMeal.getServings());
    assertEquals(name, deserializeMeal.getName());
    // make sure all ingredients were copied correctly
    for (int i = 0; i < deserializeMeal.getIngredients().length; i++)
    {
      assertEquals(ingredients[i].getIngredient(),
          deserializeMeal.getIngredients()[i].getIngredient());
    }
    // make sure utensils were copied correctly
    for (int u = 0; u < deserializeMeal.getUtensils().length; u++)
    {
      assertEquals(utensils[u].toString(), deserializeMeal.getUtensils()[u].toString());
    }
    // make sure steps were copied correctly
    for (int s = 0; s < deserializeMeal.getSteps().length; s++)
    {
      assertEquals(steps[s].toString(), deserializeMeal.getSteps()[s].toString());
    }
    
  }
  
  @Test
  void testDeserializeFile() throws IOException, ClassNotFoundException
  {
    Meal deserializeByFile = Meal.deserialize(new File("Easter.mel"));
    assertNotNull(deserializeByFile);
    Meal throwMeal = null;
    try 
    {
      throwMeal = Meal.deserialize(new File("Christmas.mel"));
    } catch (FileNotFoundException fne)
    {
      // throw exception
    } 
    
    assertNull(throwMeal);
    
  }

}
