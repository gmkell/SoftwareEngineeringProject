package recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import gui.RecipeEditor;
import math.Filterable;
import math.Time;

/**
 * This class represents a Recipe. It has a name, number of servings, and a total time It also has a
 * list of used ingredients, necessary utensils, and required steps.
 * 
 * @author Hiba Akbi
 *
 */
public class Recipe implements Serializable, Filterable
{
  public static final String FILE_EXTENSION = ".rcp";
  private static final String UTENSIL_PRINT_FORMAT = "%s\n";
  private static final String FNF_PRINT_FORMAT = "File %s not found!\n";
  private static final long serialVersionUID = 1L;
  private String name;
  private int duration;
  private int servings;
  private RecipeEditor.RC<DetailedUtensil>[] utensils;
  private RecipeEditor.RC<MeasuredIngredient>[] ingredients;
  private RecipeEditor.WithDependencies<Step>[] steps;

  /**
   * Default constructor.
   */
  public Recipe()
  {
    this("", 0);
  }

  /**
   * Explicit name and serving amount.
   * 
   * @param name
   * @param servings
   */
  public Recipe(final String name, final int servings)
  {
    this(name, servings, newArray(0), newArray(0), newArray(0));
  }


  /**
   * Constructor for a recipe.
   * 
   * @param name
   *          is the name of the recipe
   * @param utensils
   *          is the list of utensils used in the recipe
   * @param servings
   *          is the number of servings of the recipe
   * @param ingredients
   *          is the list of ingredients.
   * @param steps
   *          is the list of steps in a recipe.
   */
  public Recipe(
      final String name, 
      final int servings, 
      final RecipeEditor.RC<DetailedUtensil>[] utensils,
      final RecipeEditor.RC<MeasuredIngredient>[] ingredients,
      final RecipeEditor.WithDependencies<Step>[] steps
  )
  {

    this.name = name;
    this.duration = 0;
    this.servings = servings;
    this.utensils = utensils;
    this.ingredients = ingredients;
    this.steps = steps;
  }

  /**
   * Helper to create new generic array.
   * 
   * @param <E>
   * @param length
   * @param array
   * @return A new array of type <E>[] of length `length`.
   */
  @SafeVarargs
  public static <E> E[] newArray(final int length, final E...array) 
  {
    return Arrays.copyOf(array, length);
  }

  /**
   * @return recipe name
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * @param name
   *          is the recipe name
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  /**
   * @return number of servings
   */
  public int getServings()
  {
    return this.servings;
  }

  /**
   * @param servings
   *          is the number of servings in the recipe.
   */
  public void setServings(final int servings)
  {
    this.servings = servings;
  }

  /**
   * @return the duration to complete the recipe.
   */
  public int getDuration()
  {
    return this.duration;
  }

  /**
   * @param d is the total duration of the recipe.
   */
  public void setDuration(int d)
  {
    this.duration=d;
  }

  /**
   * @return the array of ref counted utensils used in the recipe.
   */
  public RecipeEditor.RC<DetailedUtensil>[] getRCUtensils()
  {
    return this.utensils;
  }

  /**
   * @return the array of utensils used in the recipe.
   */
  public DetailedUtensil[] getUtensils()
  {
    return Arrays.stream(this.utensils)
        .map((utn) -> utn.inner())
        .toList()
        .toArray(newArray(this.utensils.length));
  }

  /**
   * @return a String of all utensils
   */
  public String printAllUtensils()
  {
    String r = "";
    for (int i = 0; i < utensils.length; i++)
    {
      r += String.format(UTENSIL_PRINT_FORMAT, utensils[i]);
    }
    return r;
  }

  /**
   * @return a String of all steps
   */
  public String printAllSteps()
  {
    String r = "";
    for (int i = 0; i < steps.length; i++)
    {
      r += String.format(UTENSIL_PRINT_FORMAT, steps[i]);
    }
    return r;
  }

  /**
   * @return the array of steps in the recipe.
   */
  public Step[] getSteps()
  {
    return Arrays.stream(this.steps)
        .map((step) -> step.inner())
        .toList()
        .toArray(newArray(this.steps.length));
  }

  /**
   * @return the array of steps in the recipe.
   */
  public RecipeEditor.WithDependencies<Step>[] getStepsWithDeps()
  {
    return this.steps;
  }

  /**
   * @return the array of ref counted ingredients used in the recipe.
   */
  public RecipeEditor.RC<MeasuredIngredient>[] getRCIngredients()
  {
    return this.ingredients;
  }

  /**
   * @return the array of ingredients used in the recipe.
   */
  public MeasuredIngredient[] getIngredients()
  {
    return Arrays.stream(this.ingredients)
        .map((ing) -> ing.inner())
        .toList()
        .toArray(newArray(this.ingredients.length));
  }

  /**
   * Sets the list of MeasuredUtensils for the recipe.
   * @param utensils
   */
  public void setUtensils(final DetailedUtensil[] utensils)
  {
    this.utensils = Arrays.stream(utensils)
        .map((utn) -> new RecipeEditor.RC<>(utn))
        .toList()
        .toArray(newArray(utensils.length));
  }

  /**
   * Set the list of Steps for the recipe.
   * 
   * @param steps
   */
  public void setSteps(final Step[] steps)
  {
    this.steps = Arrays.stream(steps)
        .map((step) -> new RecipeEditor.WithDependencies<>(step))
        .toList()
        .toArray(newArray(steps.length));
  }

  /**
   * Sets the list of MeasuredIngredients of the recipe.
   * 
   * @param ings
   */
  public void setIngredienst(final RecipeEditor.RC<MeasuredIngredient>[] ings)
  {
    this.ingredients = ings;
  }

  /**
   * Sets the list of MeasuredUtensils for the recipe.
   * @param utensils
   */
  public void setUtensils(final RecipeEditor.RC<DetailedUtensil>[] utensils)
  {
    this.utensils = utensils;
  }

  /**
   * Set the list of Steps for the recipe.
   * 
   * @param steps
   */
  public void setSteps(final RecipeEditor.WithDependencies<Step>[] steps)
  {
    this.steps = steps;
  }

  /**
   * Sets the list of MeasuredIngredients of the recipe.
   * 
   * @param ings
   */
  public void setIngredienst(final MeasuredIngredient[] ings)
  {
    this.ingredients = Arrays.stream(ings)
        .map((ing) -> new RecipeEditor.RC<>(ing))
        .toList()
        .toArray(newArray(ings.length));
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
      if(steps[i].inner()!=null && steps[i].inner().getDuration()!=null)
      {
        System.out.println(steps[i].inner().getDuration()+"min");
        totalD+=Time.toMinutes(steps[i].inner().getDuration());
        
      }
    }
    
    setDuration(totalD);
  
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
      if(steps[i].inner()!=null) 
      {
        //for first step only, set the start time to plating time- total duration of recipe
        if(i==0) steps[0].inner().setStartTime(ti);
        else 
        {
          //for other steps
          durationOfPrevious= steps[i-1].inner().getDuration();
          startTimeOfPrevious= steps[i-1].inner().getStartTime();
          Time currentStartTime= 
              new Time(startTimeOfPrevious.getHours(), startTimeOfPrevious.getMinutes());
          
          //if duration of previous step=null,start time of current step=start time of previous step
          //else, start time of current step= start time of previous step+ duration of previous step
          if(durationOfPrevious!=null)
          {
            currentStartTime= Time.sumDuration(startTimeOfPrevious, durationOfPrevious);
          }
          steps[i].inner().setStartTime(currentStartTime);
        }
        
       
      }
      
    }
  }
  
//  /**ORIGINAL VERSION THAT WORKS!!!!! BEFORE NULLS UPDATE
//   * set start time for steps in the recipe .
//   * 
//   * @param t is the intial plating time
//   */
//  public void setStartTime(final Time t)
//  {
//    Time temp = new Time(t.getHours(), t.getMinutes());
//    // traverses the array of steps inversely and calculates start time for each
//    for (int i = steps.length - 1; i >= 0; i--)
//    {
//      steps[i].setStartTime(temp);
//      System.out.println(steps[i].getStartTime().toString());
//      temp = Time.subDuration(temp, steps[i].getDuration());
//    }
//  }

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
      if(steps[i].inner()!=null)
      {
        //for first step, just print tthe string equivalent of the step
        if(i==0) 
        {
          r = String.format("%s \t %s\n", 
              steps[i].toString(), steps[i].inner().getStartTime().toString());
        }

        else
        {
          //for other steps, check if previous duration was null
          if(steps[i-1].inner().getDuration()==null) isApproximate = true; 
          //if one of the steps has a null duration, then all subsequent steps add "approximately"
          if(isApproximate)
          {
            r += String.format("%s \t approximately %s\n", steps[i].toString(), 
                steps[i].inner().getStartTime().toString()
              );
          }else
          {
            r += String.format("%s \t %s\n", 
                steps[i].toString(), steps[i].inner().getStartTime().toString()
              );
          }
        }
      }
      
    }
    return r;
  }
  
  
//  /**ORIGINAL VERSION THAT WORKS !!!!! BEFORE NULLS UPDATE
//   * @param t the plating time
//   * @return a String of all steps with their start time
//   */
//  public String printStepsStartTime(final Time t)
//  {
//    setStartTime(t);
//    String r = "";
//    for (int i = 0; i < steps.length; i++)
//    {
//      r += String.format("%s \t %s\n", steps[i].toString(), steps[i].getStartTime().toString());
//    }
//    return r;
//  }

  /**
   * @return a String of all steps.
   */
  public String printSteps()
  {
    String r = "";
    for (int i = 0; i < steps.length; i++)
    {
      r += String.format(UTENSIL_PRINT_FORMAT, steps[i]);
    }
    return r;
  }

  // might be useless and need to be deleted later, I (Hiba) noticed you (Emil) basically wrote this
  // in a different way to account for different errors and display detailed dialog boxes
  // or each error. Which is is much better but we will need to check if that's what the PO wants
  /** Serialize the recipe object. */
  public void serialize() throws IOException
  {
    String fileName = this.getName() + FILE_EXTENSION;
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
    out.writeObject(this);
    out.flush();
    out.close();
  }
  /**
   * Deserialize the recipe object.
   * 
   * @param file
   *          is the name recipe
   * @return the recipe corresponding to the file
   * @throws ClassNotFoundException
   */
  public static Recipe deserialize(final File file) throws IOException
  {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
    Recipe recipe;
    try
    {
      recipe = (Recipe) in.readObject();
    }
    catch (ClassNotFoundException e)
    {
      System.out.printf(FNF_PRINT_FORMAT, file);
      recipe = null;
    }
    in.close();

    return recipe;
  }

  /**
   * Deserialize the recipe object.
   * 
   * @param file
   *          is the name recipe
   * @return the recipe corresponding to the file
   * @throws ClassNotFoundException
   */
  public static Recipe deserialize(final String file) throws IOException
  {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
    Recipe recipe;
    try
    {
      recipe = (Recipe) in.readObject();
    }
    catch (ClassNotFoundException e)
    {
      System.out.printf(FNF_PRINT_FORMAT, file);
      recipe = null;
    }
    in.close();

    return recipe;
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
    return String.format("%s, serves %d", name, servings);
  }

}
