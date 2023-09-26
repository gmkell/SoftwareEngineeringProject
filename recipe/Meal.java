/**
 * 
 */
package recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import math.Filterable;
import math.Time;

/**
 * Class that builds a Meal.
 * 
 * @author Gillian Kelly, Hiba Akbi
 *
 */
public class Meal implements Serializable, Filterable
{
  
  public static final String MEL = ".mel";
  private static final String UTENSIL_PRINT_FORMAT = "%s\n";
  private static final long serialVersionUID = 1L;
  private ArrayList<Recipe> recipes;
  private DetailedUtensil[] utensils;
  private Step[] steps;
  private MeasuredIngredient[] ingredients;
  private String name;
  private int servings;
  private int duration;

  /**
   * Default Constructor.
   */
  public Meal()
  {
    this("", 0);
  }

  /**
   * Explicit name and servings.
   * 
   * @param name
   * @param servings
   */
  public Meal(final String name, final int servings)
  {
    this(name, servings, new DetailedUtensil[] {}, new MeasuredIngredient[] {}, new Step[] {},
        new ArrayList<>());
  }

  /**
   * Explicit value constructor.
   * 
   * @param name
   * @param servings
   * @param utensils
   * @param ingredients
   * @param steps
   * @param recipes
   */
  public Meal(final String name, final int servings, final DetailedUtensil[] utensils,
      final MeasuredIngredient[] ingredients, final Step[] steps, final ArrayList<Recipe> recipes)
  {
    this.name = name;
    this.duration = 0;
    this.servings = servings;
    this.utensils = utensils;
    this.steps = steps;
    this.recipes = recipes;
  }

  /**
   * @return recipes in a Meal
   */
  public ArrayList<Recipe> getRecipes()
  {
    return recipes;

  }

  /**
   * @return name of the Meal
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  /**
   * @return how many people the meal should serve
   */
  public int getServings()
  {
    return servings;
  }

  /**
   * @param servings
   */
  public void setServings(final int servings)
  {
    this.servings = servings;
  }

  /**
   * @return how long it will take to make the meal
   */
  public int getDuration()
  {
    return this.duration;
  }

  /**
   * @param duration
   */
  public void setDuration(final int duration)
  {
    this.duration = duration;
  }
  
  /**
   * @param recipes
   */
  public void setRecipes(final ArrayList<Recipe> recipes)
  {
    this.recipes = recipes;
  }

  /**
   * @return all the ingredients in a meal (from its recipes)
   */
  public MeasuredIngredient[] getIngredients()
  {
    return this.ingredients;
  }

  /**
   * @param ingredients
   */
  public void setIngredients(final MeasuredIngredient[] ingredients)
  {
    this.ingredients = ingredients;
  }

  /**
   * @return all the utensils in a meal (from its recipes)
   */
  public DetailedUtensil[] getUtensils()
  {
    return this.utensils;
  }

  /**
   * @param utensils
   */
  public void setUtensils(final DetailedUtensil[] utensils)
  {
    this.utensils = utensils;
  }

  /**
   * @return all the steps in a meal (from its recipes)
   */
  public Step[] getSteps()
  {
    return steps;
  }

  /**
   * @param steps
   */
  public void setSteps(final Step[] steps)
  {
    this.steps = steps;
  }

  /**
   * Convert all recipes so that the amounts of the ingredients is equal to the serving for a Meal.
   * 
   * @param ingredients the original list of ingredients for meal
   * @param servings the servings for a Meal
   * @return all the recipes with their ingredient amounts adjusted
   */
  public MeasuredIngredient[] convertIngredients(final int servings,
      final MeasuredIngredient[] ingredients)
  {

    ArrayList<MeasuredIngredient> ingList = new ArrayList<>();
    MeasuredIngredient[] result;
    double adjust;
    double updatedAmount;
    for (Recipe r : recipes)
    {
      for (MeasuredIngredient ing : r.getIngredients())
      {
        if (r.getServings() != servings && ing != null)
        {
          adjust = (double) servings / r.getServings();
          updatedAmount = adjust * ing.getAmount();
          Ingredients ingredient = Ingredients.getIngredient(ing.getIngredient());
          MeasuredIngredient updatedIng = new MeasuredIngredient(ingredient, updatedAmount,
              ing.getUnit(), ing.getDetails());
          if (updatedIng != null)
          {
            ingList.add(updatedIng);
          }
          // ingList.add(updatedIng);
          
        } else 
        {
          if (ing != null)
          {
            ingList.add(ing);
          }
          // ingList.add(ing);
        }
      }
    }
    
    result = new MeasuredIngredient[ingList.size()];
    for (int i = 0; i < result.length; i++)
    {
      result[i] = ingList.get(i);
    }
    return result;
  }

  // TODO: @Emil I'm not sure if your version for getting all the contents in a recipe
  // is going to work for me since I have to sort of remove ingredients that appear
  // in multiple recipse for the ShoppingListViewer

  /**
   * Write a meal to a file.
   * 
   * @throws IOException
   */
  public void serialize() throws IOException
  {
    String fileName = this.getName() + MEL;
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(fileName));
    outStream.writeObject(this);
    outStream.flush();
    outStream.close();
  }
  
  /**
   * Write a meal to specific file. 
   * 
   * @param file
   * @throws IOException
   */
  public void serialize(final File file) throws IOException
  {
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
    outStream.writeObject(this);
    outStream.flush();
    outStream.close();
  }

  /**
   * Deserialize a Meal object.
   * 
   * @param file
   *          the name of the meal
   * @return a Meal object corresponding to the file
   * @throws IOException
   * @throws ClassNotFoundException 
   */
  public static Meal deserialize(final File file) throws IOException
  {
    if (file == null)
    {
      System.out.println("The file was empty.");
    }
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
    Meal meal;
    try
    {
      meal = (Meal) in.readObject();
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("file " + file + " not found");
      meal = null;
    }
    in.close();
    return meal;
  }

  /**
   * Deserialize a meal object from a filepath.
   * 
   * @param fileName
   * @return a Meal corresponding to the file
   * @throws IOException
   */
  public static Meal deserialize(final String fileName) throws IOException
  {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
    Meal meal;
    try
    {
      meal = (Meal) in.readObject();
    } 
    catch (ClassNotFoundException e)
    {
      System.out.println("the file: " + fileName + " was not found");
      meal = null;
    }

    in.close();
    return meal;
  }
  
  /**
   * Calculate the duration to complete the recipe an dpdate the duration field accordingly .
   * Will go over every step in the recipe and sum their durations
   */
  public void calculateDuration()
  {
    int totalD=0; //total duration in minutes
    for (int i=0; i <steps.length; i++)
    {
      if(steps[i]!=null && steps[i].getDuration()!=null)
      {
        System.out.println(steps[i].getDuration()+"min");
        totalD+=Time.toMinutes(steps[i].getDuration());
        
      }
    }
    
    setDuration(totalD);
  
  }
  
  /**
   * @param t the plating time
   * @return a String of all steps with their start time
   */
  public String printStepsStartTime(final Time t)
  {
    //first calculate starts times of all steps accordingly
    setStartTime(t);
    String r = "";
    boolean isApproximate= false; //boolean that represents whether a step with null time exist
    for (int i = 0; i < steps.length; i++)
    {
      if(steps[i]!=null)
      {
        //for first step, just print tthe string equivalent of the step
        if(i==0) 
        {
          r = String.format("%s \t %s\n", 
              steps[i].toString(), steps[i].getStartTime().toString());
        }

        else
        {
          //for other steps, check if previous duration was null
          if(steps[i-1].getDuration()==null) isApproximate = true; 
          //if one of the steps has a null duration, then all subsequent steps add "approximately"
          if(isApproximate)
          {
            r += String.format("%s \t approximately %s\n", steps[i].toString(), 
                steps[i].getStartTime().toString()
              );
          }else
          {
            r += String.format("%s \t %s\n", 
                steps[i].toString(), steps[i].getStartTime().toString()
              );
          }
        }
      }
      
    }
    return r;
  }
  
  /**
   * set start time for steps in the recipe .
   * 
   * @param t is the intial plating time
   */
  public void setStartTime(final Time t)
  {
    //calculate start time for first step by deducting total duration from final plating time
    calculateDuration();
    Time ti= Time.subDuration(t, Time.toTime(this.getDuration()));
    Time durationOfPrevious= new Time(0,0);
    Time startTimeOfPrevious= new Time(0,0);
    // traverses the array of steps and calculates start time for each
    for (int i =0; i<steps.length; i++)
    {
      //for all non null steps
      if(steps[i]!=null) 
      {
        //for first step only, set the start time to plating time- total duration of recipe
        if(i==0) steps[0].setStartTime(ti);
        else 
        {
          //for other steps
          durationOfPrevious= steps[i-1].getDuration();
          startTimeOfPrevious= steps[i-1].getStartTime();
          Time currentStartTime= 
              new Time(startTimeOfPrevious.getHours(), startTimeOfPrevious.getMinutes());
          
          //if duration of previous step=null,start time of current step=start time of previous step
          //else, start time of current step= start time of previous step+ duration of previous step
          if(durationOfPrevious!=null)
          {
            currentStartTime= Time.sumDuration(startTimeOfPrevious, durationOfPrevious);
          }
          steps[i].setStartTime(currentStartTime);
        }
        
       
      }
      
    }
  }
  /**
   * @return a String of all steps.
   */
  public String printSteps()
  {
    String r = "";
    for (int i = 0; i < steps.length; i++)
    {
      if(steps[i]!=null) r += String.format(UTENSIL_PRINT_FORMAT, steps[i]);
    }
    return r;
  }
  
  /**
   * @return a String of all utensils
   */
  public String printAllUtensils()
  {
    String r = "";
    for (int i = 0; i < utensils.length; i++)
    {
      if(utensils[i]!=null) r += String.format(UTENSIL_PRINT_FORMAT, utensils[i]);
    }
    return r;
  }

  @Override
  public boolean all(final Ingredients[] ings)
  {
    return Arrays.stream(ings)
        .filter((ing) -> ing != null)
        .allMatch((ing) -> Arrays.stream(getIngredients())
        .anyMatch((other) -> ing.getItem().equals(other.getIngredient()))
    );
  }

  @Override
  public boolean any(final Ingredients[] ings)
  {
    return Arrays.stream(ings)
        .filter((ing) -> ing != null)
        .anyMatch((ing) -> Arrays.stream(getIngredients())
        .anyMatch((other) -> ing.getItem().equals(other.getIngredient()))
    );
  }

  @Override
  public String toString()
  {
    return String.format("%s", getName());
  }
}
