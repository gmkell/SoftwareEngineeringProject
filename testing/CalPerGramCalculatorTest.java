package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

import math.CalPerGramCalculator;
import recipe.Ingredients;
class CalPerGramCalculatorTest
{

  
  
  @Test
  void constructorTest()
  {
    Ingredients almond =Ingredients.getIngredient("almond");
    CalPerGramCalculator calculator = new CalPerGramCalculator(almond);
  }
  
  @Test
  void calculateCalFromGramsLessThan100Test(){
    Ingredients almond = Ingredients.getIngredient("almond");
    CalPerGramCalculator calculator = new CalPerGramCalculator(almond);
    double grams = calculator.calculateCalFromGrams(2.0);
    assertEquals(grams,12.02, 0.0);
    
   
    
  }
  
  @Test
  void calculateCalFromGramsMoreThan100Test() {
    Ingredients banana = Ingredients.getIngredient("banana");
    CalPerGramCalculator calculator = new CalPerGramCalculator(banana);
    int gramsround = 0;
    double grams = calculator.calculateCalFromGrams(120.0);
    if (grams == Math.floor(grams)) {
       gramsround = (int) grams;
    }
    assertEquals(String.valueOf(gramsround),"78");
  }
  
  @Test
  void calculateCalFromGramsNonWholeNumber() {
    Ingredients banana = Ingredients.getIngredient("banana");
    CalPerGramCalculator calculator = new CalPerGramCalculator(banana);
    double grams = calculator.calculateCalFromGrams(7.5);
    assertEquals(grams,4.9, 0.0);
  }
  
  @Test
  void calculateCalFromGramsNonWholeNumberTwoAfterDecimal() {
    Ingredients blackberry = Ingredients.getIngredient("blackberry");   
    CalPerGramCalculator calculator = new CalPerGramCalculator(blackberry);
    double grams = calculator.calculateCalFromGrams(109.56);
    assertEquals(grams,27.4, 0.0);
    
  }
  
  

}
