package recipe;

import java.util.HashMap;

/**
 * 
 * @author Emil
 *
 */
public class GenericIngredients extends Ingredients
{

  private static final long serialVersionUID = 1L;
  
  private GenericIngredients()
  {
    super("", 0.0, 0.0, 0.0);
  }

  /**
   * Get the default ingredients supported by the application.
   * 
   * @return A map of default ingredients.
   */
  public static HashMap<String, Ingredients> getDefaultIngredients()
  {
    HashMap<String, Ingredients> defaults = new HashMap<>();
    
    defaults.put("alcohol", new Ingredients("Alcohol", 275.0, 0.79, null));
    defaults.put("almond", new Ingredients("Almond", 601.0, 0.46, 1.3));
    defaults.put("american cheese", new Ingredients("American cheese", 440.0, 0.34, 21.0));
    defaults.put("apple", new Ingredients("Apple", 44.0, 0.56, 182.0));
    defaults.put("apple juice", new Ingredients("Apple juice", 48.0, 1.04, null));
    defaults.put("banana", new Ingredients("Banana", 65.0, 0.56, 118.0));
    defaults.put("bean", new Ingredients("Bean", 130.0, 0.77, null));
    defaults.put("beef", new Ingredients("Beef", 280.0, 1.05, null));
    defaults.put("blackberry", new Ingredients("Blackberry", 25.0, 0.53, 6.5));
    defaults.put("black pepper", new Ingredients("Black pepper", 255.0, 1.01, null));
    defaults.put("bread", new Ingredients("Bread", 240.0, 0.42, 29.0));
    defaults.put("broccoli", new Ingredients("Broccoli", 32.0, 0.37, 10.0));
    defaults.put("brown sugar", new Ingredients("Brown sugar", 380.0, 1.50, null));
    defaults.put("butter", new Ingredients("Butter", 750.0, 0.91, 113.0));
    defaults.put("cabbage", new Ingredients("Cabbage", 28.0, 0.36, 907.2));
    defaults.put("carrot", new Ingredients("Carrot", 41.0, 0.64, 46.0));
    defaults.put("cashew", new Ingredients("Cashew", 553.0, 0.50, 1.6));
    defaults.put("cauliflower", new Ingredients("Cauliflower", 25.0, 0.27, 575.0));
    defaults.put("celery", new Ingredients("Celery", 14.0, 0.61, 40.0));
    defaults.put("cheddar cheese", new Ingredients("Cheddar cheese", 440.0, 0.34, 28.0));
    defaults.put("cherry", new Ingredients("Cherry", 50.0, 1.02, 8.2));
    defaults.put("chicken", new Ingredients("Chicken", 200.0, 1.04, 1400.0));
    defaults.put("chocolate", new Ingredients("Chocolate", 500.0, 1.33, 7.0));
    defaults.put("cinnamon", new Ingredients("Cinnamon", 261.0, 0.45, null));
    defaults.put("cod", new Ingredients("Cod", 100.0, 0.58, 180.0));
    defaults.put("corn", new Ingredients("Corn", 130.0, 0.72, 103.0));
    defaults.put("cornflake", new Ingredients("Cornflake", 370.0, 0.12, null));
    defaults.put("cottage cheese", new Ingredients("Cottage cheese", 98.0, 0.96, null));
    defaults.put("crab", new Ingredients("Crab", 110.0, 0.61, 151.2));
    defaults.put("creme de cacao", new Ingredients("Creme de cacao", 275.0, 0.79, null));
    defaults.put("cucumber", new Ingredients("Cucumber", 10.0, 0.67, 201.0));
    defaults.put("egg", new Ingredients("Egg", 150.0, 0.60, 50.0));
    defaults.put("flour", new Ingredients("Flour", 364.0, 0.45, null));
    defaults.put("garlic", new Ingredients("Garlic", 111.0, 0.32, 3.0));
    defaults.put("grapefruit", new Ingredients("Grapefruit", 32.0, 0.33, 330.0));
    defaults.put("grape", new Ingredients("Grape", 62.0, 0.37, 4.9));
    defaults.put("grape juice", new Ingredients("Grape juice", 60.0, 1.04, null));
    defaults.put("green bean", new Ingredients("Green bean", 31.0, 0.53, 5.0));
    defaults.put("haddock", new Ingredients("Haddock", 110.0, 0.58, 150.0));
    defaults.put("ham", new Ingredients("Ham", 240.0, 1.40, null));
    defaults.put("honey", new Ingredients("Honey", 280.0, 1.50, null));
    defaults.put("ice cream", new Ingredients("Ice cream", 180.0, 0.55, null));
    defaults.put("kidney bean", new Ingredients("Kidney bean", 333.0, 0.79, null));
    defaults.put("lamb", new Ingredients("Lamb", 200.0, 1.30, null));
    defaults.put("lemon", new Ingredients("Lemon", 29.0, 0.77, 84.0));
    defaults.put("lentil", new Ingredients("Lentil", 116.0, 0.85, null));
    defaults.put("lettuce", new Ingredients("Lettuce", 15.0, 0.06, 626.0));
    defaults.put("macaroni", new Ingredients("Macaroni", 371.0, 1.31, null));
    defaults.put("milk", new Ingredients("Milk", 70.0, 1.04, null));
    defaults.put("mushroom", new Ingredients("Mushroom", 15.0, 1.17, 12.0));
    defaults.put("oil", new Ingredients("Oil", 900.0, 0.88, null));
    defaults.put("olive", new Ingredients("Olive", 80.0, 0.65, 3.8));
    defaults.put("onion", new Ingredients("Onion", 22.0, 0.74, 94.0));
    defaults.put("orange", new Ingredients("Orange", 30.0, 0.77, 140.0));
    defaults.put("paprika", new Ingredients("Paprika", 282.0, 0.46, null));
    defaults.put("pasta", new Ingredients("Pasta", 371.0, 1.31, null));
    defaults.put("peach", new Ingredients("Peach", 30.0, 0.61, 175.0));
    defaults.put("peanut", new Ingredients("Peanut", 567.0, 0.53, 1.0));
    defaults.put("pear", new Ingredients("Pear", 16.0, 0.61, 178.0));
    defaults.put("peas", new Ingredients("Peas", 148.0, 0.73, 0.2));
    defaults.put("pepper", new Ingredients("Pepper", 20.0, 0.51, 114.0));
    defaults.put("pineapple", new Ingredients("Pineapple", 40.0, 0.54, 1000.0));
    defaults.put("plum", new Ingredients("Plum", 39.0, 0.58, 66.0));
    defaults.put("pork", new Ingredients("Pork", 290.0, 0.70, null));
    defaults.put("rum", new Ingredients("Rum", 275.0, 0.79, null));
    defaults.put("salmon", new Ingredients("Salmon", 180.0, 0.58, 227.0));
    defaults.put("salt", new Ingredients("Salt", 0.0, 1.38, null));
    defaults.put("saltine crackers", new Ingredients("Saltine crackers", 421.0, 0.43, 3.0));
    defaults.put("spaghetti", new Ingredients("Spaghetti", 371.0, 1.31, null));
    defaults.put("spinach", new Ingredients("Spinach", 8.0, 0.08, null));
    defaults.put("strawberries", new Ingredients("Strawberries", 30.0, 0.58, 12.0));
    defaults.put("sugar", new Ingredients("Sugar", 400.0, 0.95, null));
    defaults.put("sweet potato", new Ingredients("Sweet potato", 86.0, 0.65, 114.0));
    defaults.put("syrup", new Ingredients("Syrup", 260.0, 1.38, null));
    defaults.put("thyme", new Ingredients("Thyme", 101.0, 0.46, 0.5));
    defaults.put("tomato", new Ingredients("Tomato", 20.0, 0.67, 123.0));
    defaults.put("wine", new Ingredients("Wine", 83.0, 0.99, null));
    
    return defaults;
  }
  
  public static String[] toArrayOfKeys(HashMap<String, ?> map) {
    String[] keys = new String[map.size()];
    int i = 0;
    for (String key : map.keySet()) {
        keys[i++] = key;
    }
    return keys;
}

}
