package math;

import java.io.Serializable;

/**
 * This class represents a Time as the format hh:mm.
 * It has hours and minutes
 * @author Hiba Akbi
 *
 */
public class Time implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int h;
  private int min;
  public static final String AM= "a.m";
  public static final String PM= "p.m";
  private String meridiem; // pm or am
  
  /**
   * Constructor for military time.
   * @param h is the hours
   * @param min is the minutes*/
  public Time(final int h, final int min)
  {
    this.h=h;
    this.min=min;
    this.meridiem=null;
  }
  
  /**
   * Constructor for meridiem time.
   * @param h is the hours
   * @param min is the minutes
   * @param m either p.m or a.m*/
  public Time(final int h, final int min, final String m)
  {
    this.h=h;
    this.min=min;
    this.meridiem=m;
  }
  
  /**
   * Default Constructor.
   * */
  public Time()
  {
    this.h=0;
    this.min=0;
    this.meridiem= null;
  }
  
  /**
   * @return hours.*/
  public int getHours()
  {
    return this.h;
  }
  
  /**
   * @return minutes.*/
  public int getMinutes()
  {
    return this.min;
  }
  
  /**
   * @return meridiem.*/
  public String getMeridiem()
  {
    return this.meridiem;
  }
  
  /** @param m Setter for minutes*/
  public void setMinutes(final int m)
  {
    this.min=m;
  }
  
  /**
   * @param hours Setter for hours*/
  public void setHours(final int hours)
  {
    
    this.h=hours;
  }
  
  /** @param m Setter for meridiem*/
  public void setMeridiem(final String m)
  {
    this.meridiem=m;
  }
  
  /**
   * Converts a time given in hours and minute to a duration in minutes.
   * @param t is the time
   * @return calculated duration in minutes or 0 if passed values are negative*/
  public static int toMinutes(final Time t) 
  {
    int h= t.getHours();
    int m=t.getMinutes();
    //ensure positive values for h and m
    if (h >= 0 && m >= 0) return (60*h + m);
    else return 0;
        
  }
  
  /**
   * Converts a given duration in minutes into the equivalent in hours and minutes.
   * @param d is the duration in minutes
   * @return the equivalent in hours and minutes*/
  public static Time toTime(final int d) 
  {
    Time t= new Time();
    if(d<0) return t;
    
    if(d<60) 
    {
      t.setHours(0);
      t.setMinutes(d);
    }
    else
    {
      t.setHours(d/60);
      t.setMinutes(d%60);
    }
    return t;
        
  }
  
  /**
   * Performs a subtraction on time.
   * @param tf the final military time we are dedcuting from
   * @param d duration in hours and minutes
   * @return ti the calculated initial military time */
  public static Time subDuration(final Time tf, final Time d) 
  {
    int m;
    int h;
   
    if((d.getHours() + d.getMinutes())==0) 
    //if duration = 0, skip calculation and return same final time
    {
      h=tf.getHours();
      m=tf.getMinutes();
    }
    else
    { //calculate the differences
      m=tf.getMinutes()-d.getMinutes();
      h=tf.getHours()-d.getHours();
      
      if (m<0) //adjust minutes
      {
        m= m+60;
        h-=1;
      }
      if(h<0) //adjust hours
      {
        h+=24;
      }
     
    }
    
    Time ti= new Time();
    ti.setHours(h);
    ti.setMinutes(m);
    
    return ti;
  }
  
  /**
   * Performs a sum on time.
   * @param ti the initial military time we are addint to
   * @param d duration in hours and minutes
   * @return tf the calculated final military time */
  public static Time sumDuration(final Time ti, final Time d) 
  {
    int m;
    int h;
   
    if((d.getHours() + d.getMinutes())==0) 
    //if duration = 0, skip calculation and return same final time
    {
      h=ti.getHours();
      m=ti.getMinutes();
    }
    else
    { //calculate the differences
      m=ti.getMinutes()+d.getMinutes();
      h=ti.getHours()+d.getHours();
      
      if (m>=60) //adjust minutes
      {
        m= m-60;
        h+=1;
      }
      if(h>23) //adjust hours
      {
        h-=24;
      }
     
    }
    
    Time tf= new Time();
    tf.setHours(h);
    tf.setMinutes(m);
    
    return tf;
  }
  /**
   * Converts from meridiem to military.
   * @param t is the given time in meridiem
   * @return the equivalent time in military*/
  public static Time toMilitary(final Time t) 
  {
    Time r= null;
    //check if meridiem value is given before proceeding
    if(t.getMeridiem()==null || t.getMeridiem().equals(""))
    {
      return null;
    }
    if(t.getMeridiem().equalsIgnoreCase(PM))
    {
      int h= t.getHours();
      int m= t.getMinutes();
      if(h!=12) h=h+12;
      r= new Time(h,m);
    }else if(t.getMeridiem().equalsIgnoreCase(AM)) 
    {
      int h= t.getHours();
      int m= t.getMinutes();
      if(h==12) h=0;
      r= new Time(h,m);
    }
    
    return r;
  }
  
  /**
   * Converts a valid clock time to meridiem timing format.
   * @param t is the time in military timing
   * @return is the result in meridiem format*/
  
  public static Time toMeridiem(final Time t)
  {
    Time result = new Time();
    
    if(t.getHours()==0) 
    {
      result.setHours(12);
      result.setMinutes(t.getMinutes());
      result.setMeridiem(AM);
    }
    else if(t.getHours()>12)
    {
      result.setHours(t.getHours()-12);
      result.setMinutes(t.getMinutes());
      result.setMeridiem(PM);
    }
    else if(t.getHours()==12)
    {
      result.setHours(12);
      result.setMinutes(t.getMinutes());
      result.setMeridiem(PM);
    }
    else
    {
      result.setHours(t.getHours());
      result.setMinutes(t.getMinutes());
      result.setMeridiem(AM);
    }
    
    return result;
      
  }
  
  /**
   * @return the time in the format hh:mm (AM/PM)*/
  public String toString()
  {
    Time t= Time.toMeridiem(this);
    return String.format("%02d:%02d (%s)", t.getHours(), t.getMinutes(), t.getMeridiem());
  }
  
  /**
   * @return the time in the format "h hours and m minutes" */
  public String toStringDuration()
  {
    if(h==0 && min==0) 
    {
      return "";
    }
    else if (h==0)
    {
      return min+" minute(s)";
    }else if (min ==0)
    {
      return h+" hour(s)";
    }else
    {
      return h+" hour(s) and "+ min+" minute(s)";
    }
  }

}