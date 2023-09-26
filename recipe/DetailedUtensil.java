package recipe;

import java.io.Serializable;

/**
 * Detailed Utensil represent the Utensil added to a recipe.
 * a name and details attribute
 * @author Emil Hofstetter
 *
 */
public class DetailedUtensil 
    implements Comparable<DetailedUtensil>, Serializable, gui.SortedJList.Verbose
{
  private static final long serialVersionUID = 6752999285234983756L;
  private final String utensil;
  private final String details;
  
  /** Parametrized Constructor.
   * @param utensil is the name of the utensil
   * @param details is the detail of the utensil*/
  public DetailedUtensil(final String utensil, final String details)
  {
    this.utensil = utensil;
    this.details = details;
  }

  /** Compares a utensil to another.*/
  @Override
  public int compareTo(final DetailedUtensil o)
  {
    return this.utensil.compareTo(o.utensil);
  }
  
  /**
   * @return the string equivalent of the utensil.*/
  @Override
  public String toString()
  {
    return this.toString(true);
  }

  @Override
  public String toString(final boolean verbose)
  {
    if (verbose) 
    {
      if(this.details == null || this.details.equals("")) return String.format("%s", this.utensil);
      else return String.format("%s %s", this.details, this.utensil);
    } 
    else 
    {
      return this.toString();
    }
  }
}
