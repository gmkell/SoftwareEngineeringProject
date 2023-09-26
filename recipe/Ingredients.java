package recipe;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import java.util.HashMap;

/**
 * Enumeration of all available ingredients that the user can search.
 * 
 * @author Nicole Kim
 *
 */
public class Ingredients implements Serializable
{
  protected static Map<String, Ingredients> USER_INGREDIENTS = 
      new HashMap<String, Ingredients>();
  private static Map<String, Ingredients> DEFAULT_INGREDIENTS = 
      GenericIngredients.getDefaultIngredients();
  private static final String USER_INGS_FN = "user.ings";
  private static final long serialVersionUID = 
      5097875747000654427L;
  private final Double calories, density, avgWeight;
  private final String item;
  
  
  /**
   * The explicit value constructor of an Ingredient.
   * 
   * @param item The name of the ingredient.
   * @param calories The calories in 100 grams.
   * @param density The density in grams per milliliter.
   * @param avgWeight The average weight in grams for an individual unit.
   */
  protected Ingredients(
      final String item, final Double calories,
      final Double density, final Double avgWeight)
  {
    this.item  = item;
    this.calories = calories;
    this.density = density;
    this.avgWeight = avgWeight;
  }

  private static void loadIngredients() 
  {
    USER_INGREDIENTS = loadMapFromFP(USER_INGS_FN);
  }
  
  private static Map<String, Ingredients> loadMapFromFP(
      final String filePath
  ) 
  {
    try 
    {
      FileInputStream fis = new FileInputStream(filePath);

      @SuppressWarnings("unchecked")
      Map<String, Ingredients> map =
          (HashMap<String, Ingredients>) (
            new ObjectInputStream(fis)
          ).readObject();

      fis.close();

      return map;
    } 
    catch (ClassNotFoundException cnfe) 
    {
      System.out.println(cnfe);
    }
    catch (FileNotFoundException fnfe) 
    {
      System.out.println(fnfe);
    }
    catch (IOException ioe) 
    {
      System.out.println(ioe);
    }
    return new HashMap<String, Ingredients>();
  }

  private static void saveIngredients() 
  {

    try 
    {
      FileOutputStream fos = new FileOutputStream(USER_INGS_FN, false);
      
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      
      oos.writeObject(USER_INGREDIENTS);
      
      fos.close();
    }
    catch (IOException ioe) 
    {
      System.out.println(ioe);
    }
  }
  
  /**
   * Returns a Ingredient if it exists within the application.
   * @param name
   * @return An Ingredient with the given name if it exists.
   */
  public static Ingredients getIngredient(
      final String name
  )
  {
    if (USER_INGREDIENTS == null) 
    {
      loadIngredients();
    }
    return DEFAULT_INGREDIENTS.getOrDefault(
        name.toLowerCase(), 
        USER_INGREDIENTS.get(name.toLowerCase())
    );
  }

  /**
   * Gets an ingredient with a given name if it exists, and creates a
   * user defined ingredient otherwise.
   * @param name
   * @param calories
   * @param density
   * @param avgWeight
   * @return An Ingredient, existing or new.
   */
  public static Ingredients getOrCreateIngredient(
      final String name,
      final Double calories,
      final Double density,
      final Double avgWeight
  )
  {
    if (getIngredient(name) == null)
    {
      USER_INGREDIENTS.put(
          name.toLowerCase(), 
          new Ingredients(name, calories, density, avgWeight)
      );
      saveIngredients();
    }
    
    // Should no longer be null at this point. 
    // Something is likely wrong if it is.
    assert(getIngredient(name) != null);
    
    return getIngredient(name);
  }

  /**
   * Gets all available Ingredients within the application.
   * 
   * @return A Map of Ingredient names to Ingredients.
   */
  public static Map<String, Ingredients> getAvailableIngredients() 
  {
    Map<String, Ingredients> ings = new HashMap<>();

    loadIngredients();

    ings.putAll(DEFAULT_INGREDIENTS);
    ings.putAll(USER_INGREDIENTS);

    return ings;
  }

  /** Methods gets a list of all ingredient names as strings.
   * @return string array containing all ingredient names*/
  public static String[] getAvailableIngredientsNames() 
  {
    Map<String, Ingredients> ings = new HashMap<>();

    loadIngredients();

    ings.putAll(DEFAULT_INGREDIENTS);
    ings.putAll(USER_INGREDIENTS);

    return ings.keySet().toArray(
        new String[DEFAULT_INGREDIENTS.size() + USER_INGREDIENTS.size()]
    );
  }
  
  /**Methods checks if given string matches an existing ingredient.
   * @param ing is the string to check
   * @return true if an ingredient with the same name exists, else returns false.*/
  public static boolean isValidIngredient(final String ing)
  {
    String[] allIngNames = getAvailableIngredientsNames();
    for (String s: allIngNames) 
    {
      if (s.equalsIgnoreCase(ing)) return true;
    }
              
    return false;
  }
  
  /**
   * Return the average weight in grams for an individual unit of the ingredient.
   * If there is no known average weight, null will be returned.
   * 
   * @return THe average weight.
   */
  public Double getAvgWeight()
  {
    return avgWeight;
  }
  /**
   * Return the ingredient's calories per 100 grams.
   * 
   * @return The calories.
   */
  public Double getCalories()
  {
    return calories;
  }

  /**
   * Return the ingredient's density (in grams per milliliter).
   * 
   * @return The density.
   */
  public Double getDensity()
  { 
    return density;
  }

  /**
   * Return the item name of the ingredient.
   * 
   * @return The item.
   */
  public String getItem()
  {
    return item;
  }
  
  @Override
  public String toString()
  {
    return this.getItem();
  }
}
