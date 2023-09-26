package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import gui.RecipeEditor;
import math.MeasurementUnits;
import math.Time;
import recipe.Ingredients;
import recipe.MeasuredIngredient;
import recipe.DetailedUtensil;
import recipe.Recipe;
import recipe.Step;

/**
 * Testing for Recipe Class.*/
public class RecipeTest
{
  private int s= 10;
  private String n= "Banana Foster";
  private RecipeEditor.RC<MeasuredIngredient>[] iList= Recipe.newArray(0);
  private RecipeEditor.WithDependencies<Step>[] sList= Recipe.newArray(0);
 
  MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
      "butter",
      "1.0",
      MeasurementUnits.CUPS.getUnit(),
      ""
  );
  

  MeasuredIngredient mg2 = MeasuredIngredient.fromStringParams(
      "brown sugar",
      "2.0",
      MeasurementUnits.CUPS.getUnit(),
      ""
  );
 
  MeasuredIngredient mg3 = MeasuredIngredient.fromStringParams(
      "beurre de cacao",
      "10.0",
      MeasurementUnits.GRAMS.getUnit(),
      ""
  );
  

  @Test
  void constructorTest()
  {
    
    Recipe r= new Recipe();
    assertEquals(r.getName(),"");
    assertEquals(r.getServings(),0);
  }
  
  @Test
  void paramConstructorTest()
  {
    DetailedUtensil u1 = new DetailedUtensil("Whisk", "tiny");
    RecipeEditor.RC<DetailedUtensil>[] uList = Recipe.newArray(1);
    uList[0] = new RecipeEditor.RC<>(u1);
    Recipe r = new Recipe(n,s,uList,iList,sList);
    assertEquals(r.getName(),n);
    assertEquals(r.getServings(),10);
    assertEquals(uList[0].inner(), u1);
  }
  
  @Test
  void addUtensilTest()
  {
    DetailedUtensil u1 = new DetailedUtensil("Bowl", "huge");
    RecipeEditor.RC<DetailedUtensil>[] uList = Recipe.newArray(1);
    uList[0] = new RecipeEditor.RC<>(u1);
    Recipe r = new Recipe(n,s,uList,iList,sList);
    assertEquals(r.getName(),n);
    assertEquals(r.getServings(),10);
    assertEquals(uList[0].inner(), u1);
    
  }
  
  @Test
  void printAllUtensilsTest()
  {
    DetailedUtensil u1 = new DetailedUtensil("saucepan", "");
    DetailedUtensil u2 = new DetailedUtensil("plate", "");
    DetailedUtensil u3 = new DetailedUtensil("Skillet", "large");
    RecipeEditor.RC<DetailedUtensil>[] uList = Recipe.newArray(3);
    uList[0] = new RecipeEditor.RC<>(u1);
    uList[1] = new RecipeEditor.RC<>(u2);
    uList[2] = new RecipeEditor.RC<>(u3);

    Recipe r = new Recipe(n,s,uList,iList,sList);
    
    String ss= "saucepan\nplate\nlarge Skillet\n";
    assertEquals(r.printAllUtensils(),ss);
    
  }
  
  @Test
  void printStepsTest()
  {
    DetailedUtensil u1 = new DetailedUtensil("skillet", "");
    DetailedUtensil u2 = new DetailedUtensil("saucepan", "");
    Step s1= new Step("put",mg,u1,"",new Time(0,0));
    Step s2= new Step("melt",u1,u1,"",new Time(1,20));
    Step s3= new Step("put",mg,u1,"",new Time(1,20));
    Step s4= new Step("pour",mg2,u2,"",null);
    Step[] steps= {s1,s2,s3};
    Recipe r = new Recipe();
    r.setSteps(steps);
    
    String result= "put the 1.0 cups (cup) of Butter in the skillet \n"
        + "melt the contents of the skillet for 20 minute(s)\n"
        + "put the 1.0 cups (cup) of Butter in the skillet \n";
    //assertEquals(r.printSteps(),result);
    r.calculateDuration();
    assertEquals(r.getDuration(),160);
    
  }
  
  @Test
  void setStartTimeTest()
  {
    DetailedUtensil u1 = new DetailedUtensil("skillet", "");
    DetailedUtensil u2 = new DetailedUtensil("saucepan", "");
    Step s1= new Step("put",mg,u1,"",new Time(0,0));
    Step s2= new Step("melt",u1,u1,"",new Time(0,20));
    Step s3= new Step("put",mg,u1,"",null);
    Step s4= new Step("pour",mg2,u2,"",new Time(0,20));
    Step[] steps= {s1,s2,s3,s4};
    Recipe r = new Recipe();
    r.setSteps(steps);
    r.setStartTime(new Time(9,00));
    
    assertEquals(s1.getStartTime().toString(),"08:20 (a.m)");
    assertEquals(s2.getStartTime().toString(),"08:20 (a.m)");
    assertEquals(s3.getStartTime().toString(),"08:40 (a.m)");
    assertEquals(s4.getStartTime().toString(),"08:40 (a.m)");
    
  }
  
  
  @Test
  void printStepStartTimeTest()
  {

    DetailedUtensil u1 = new DetailedUtensil("skillet", "");
    DetailedUtensil u2 = new DetailedUtensil("saucepan", "");
    Step s1= new Step("put",mg,u1,"",new Time(0,0));
    Step s2= new Step("melt",u1,u1,"",new Time(0,20));
    Step s3= new Step("put",mg,u1,"",null);
    Step s4= new Step("pour",mg2,u2,"",null);
   
    Step[] steps= {s1,s2,s3,s4};
    Recipe r = new Recipe();
    r.setSteps(steps);
    String result= "put the 1.0 cups (cup) of Butter in the skillet  \t 08:00 (a.m)\n"
        + "melt the contents of the skillet for 20 minute(s) \t 08:00 (a.m)\n"
        + "put the 1.0 cups (cup) of Butter in the skillet  \t 08:20 (a.m)\n"
        + "pour the 2.0 cups (cup) of Brown sugar in the saucepan  \t approximately 08:20 (a.m)\n";
    
    assertEquals(r.printStepsStartTime(new Time(8,20)),result);
    
    
  }
  
  

}