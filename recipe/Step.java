package recipe;

import java.io.Serializable;

import math.Time;

/**
 * This class represents an individual step in a recipe.
 * @author Hiba Akbi*/

public class Step implements Serializable
{
  private static final long serialVersionUID = 1L;

  private DetailedUtensil utensil;
  private Object on;
  private String action;
  private String detail;
  private Time duration;
  private Time startTime;

  
  /**
   * Constructor.
   * @param action is the action performed in the step.
   * @param on is either the content or an ingredient
   * @param utensil is the utensil
   * @param detail is additional detail about the step
   * @param duration is how long the step takes in minutes.
   * */
  public Step(final String action, final Object on, final DetailedUtensil utensil,
      final String detail, final Time duration)
  {
    this.utensil=utensil;
    this.action=action;
    this.on=on;
    this.detail=detail;
    this.duration=duration;
    this.startTime=null;
       
  }
  
  /**
   * @return step utensil*/
  public DetailedUtensil getUtensil()
  {
    return this.utensil;
  }
  
  /**
   * @param utensil*/
  public void setUtensil(final DetailedUtensil utensil)
  {
    this.utensil=utensil;
  }
  
  /**
   * @return step on*/
  public Object getOn()
  {
    return this.on;
  }
  
  /**
   * @param on*/
  public void setOn(final Object on)
  {
    this.on=on;
  }
  
  /**
   * @return step detail*/
  public String getDetail()
  {
    return this.detail;
  }
  
  /**
   * @param detail*/
  public void setDetail(final String detail)
  {
    this.detail=detail;
  }
  
  
  /**
   * @return step action*/
  public String getAction()
  {
    return this.action;
  }
  
  /**
   * @param action*/
  public void setAction(final String action)
  {
    this.action=action;
  }
  
  
  /**
   * @return step duration*/
  public Time getDuration()
  {
    return this.duration;
  }
  
  
  /**
   * @param duration*/
  public void setDuration(final Time duration)
  {
    this.duration=duration;
  }
  
  /**
   * @param stime*/
  public void setStartTime(final Time stime)
  {
    this.startTime=stime;
  }
  
  /**
   * @return step startTime*/
  public Time getStartTime()
  {
    return this.startTime;
  }
  

  /** Format the step differently according to information presentation specs.
   * @return string of the step*/
  public String toString()
  {
    //checks if duration is null, if not, check amount of duration
    String s = "";
    if(duration!=null)
    {
      //if duration=0 it need NOT be included in the string
      int d = Time.toMinutes(duration);
      if (d!=0) s = "for "+ duration.toStringDuration();
    }
    
    
    //Step involving an ingredient
    if(on.getClass()==MeasuredIngredient.class)
    {
      return String.format("%s the %s in the %s %s", 
          action, ((MeasuredIngredient)on).toString(true), utensil.toString(), detail)+ s;
    }
    //step involving utensils
    //Step involving contents when the source and destination utensils are the same
    else if (utensil.toString().equalsIgnoreCase(((DetailedUtensil)on).toString()))
    {
      return String.format("%s the contents of the %s %s", action, utensil.toString(), detail) + s;
    }
    else
    //Step involving contents when the source and destination utensils are different
    {
      return String.format("%s the contents of the %s in the %s %s",
          action, ((DetailedUtensil)on).toString(), utensil.toString(), detail) + s;
     
    }
  }
  
  
}
