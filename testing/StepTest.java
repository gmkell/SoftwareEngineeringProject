package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import math.MeasurementUnits;
import math.Time;
import recipe.DetailedUtensil;
import recipe.MeasuredIngredient;
import recipe.Step;

class StepTest
{
  DetailedUtensil u= new DetailedUtensil("pot","");
  DetailedUtensil u1= new DetailedUtensil("casserole","small");
  DetailedUtensil u2= new DetailedUtensil("saucepan","");
  DetailedUtensil u3= new DetailedUtensil("strainer","");
  MeasuredIngredient mg = MeasuredIngredient.fromStringParams(
      "Macaroni",
      "1.0",
      MeasurementUnits.CUPS.getUnit(),
      ""
  );
  
  MeasuredIngredient mg2 = MeasuredIngredient.fromStringParams(
      "Milk",
      "2.0",
      MeasurementUnits.CUPS.getUnit(),
      ""
  );
  @Test
  void accessorsTest()
  {
    Time t= new Time(0,10);
    Step s= new Step("cut",mg,u,"in dices",t);
    assertEquals(s.getAction(),"cut");
    assertEquals(((MeasuredIngredient)s.getOn()).toString(true),"1.0 cups (cup) of Macaroni");
    assertEquals(s.getUtensil().toString(),"pot");
    assertEquals(s.getDetail(),"in dices");
    assertEquals(s.getDuration(),t);
    assertNull(s.getStartTime());
    s.setAction("pour");
    s.setDetail("gently");
    s.setOn(mg2);
    s.setUtensil(u1);
    Time t2= new Time(0,30);
    s.setDuration(t2);
    assertEquals(s.getAction(),"pour");
    assertEquals(((MeasuredIngredient)s.getOn()).toString(true),"2.0 cups (cup) of Milk");
    assertEquals(s.getUtensil().toString(),"small casserole");
    assertEquals(s.getDetail(),"gently");
    assertEquals(s.getDuration(),t2);
    
  }

  @Test
  void toStringTest()
  {
    Time t= new Time(0,10);
    
    Step s= new Step("boil",mg,u,"",t);
    assertEquals(s.toString(),"boil the 1.0 cups (cup) of Macaroni in the pot for 10 minute(s)");
   
    s.setDuration(null);
    assertEquals(s.toString(),"boil the 1.0 cups (cup) of Macaroni in the pot ");
    
    t= new Time(1,10);
    s.setDuration(t);
    assertEquals(s.toString(),"boil the 1.0 cups (cup) of Macaroni in the pot for"
        + " 1 hour(s) and 10 minute(s)");
    
    t= new Time(1,00);
    s.setDuration(t);
    assertEquals(s.toString(),"boil the 1.0 cups (cup) of Macaroni in the pot for 1 hour(s)");
    
    t= new Time(0,0);
    s.setDuration(t);
    assertEquals(s.toString(),"boil the 1.0 cups (cup) of Macaroni in the pot ");

    s= new Step("put",u3,u1,"",t);
    assertEquals(s.toString(),"put the contents of the strainer in the small casserole ");
    
    s= new Step("saute",u2,u2,"until tender but not brown",t);
    assertEquals(s.toString(),"saute the contents of the saucepan until tender but not brown");
  }

}
