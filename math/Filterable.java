package math;

import recipe.Ingredients;

/**
 * 
 * @author Emil Hofstetter
 *
 */
public interface Filterable
{
  /**
   * 
   * @param ings
   * @return True if all Ingredients are present.
   */
  public boolean all(final Ingredients[] ings);

  /**
   * 
   * @param ings
   * @return True if any of the Ingredients are present.
   */
  public boolean any(final Ingredients[] ings);
}
