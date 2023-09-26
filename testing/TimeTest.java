package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import math.Time;

class TimeTest
{
  @Test
  void toMinutesTest()
  {
    assertEquals(Time.toMinutes(new Time(0,0)),0);
    assertEquals(Time.toMinutes(new Time(1, 0)),60);
    assertEquals(Time.toMinutes(new Time(0, 60)),60);
    assertEquals(Time.toMinutes(new Time(1, 10)),70);
    assertEquals(Time.toMinutes(new Time(2, 0)),120);
    assertEquals(Time.toMinutes(new Time(1, 60)),120);
    assertEquals(Time.toMinutes(new Time(-2, 0)),0);
    assertEquals(Time.toMinutes(new Time(0, -3)),0);
    assertEquals(Time.toMinutes(new Time(-21, -3)),0);
    
  }
  
  @Test
  void toTimeTest()
  {
    Time t= new Time();
    
    t= Time.toTime(0);
    assertEquals(t.getHours(),0);
    assertEquals(t.getMinutes(),0);
    
    t= Time.toTime(-50);
    assertEquals(t.getHours(),0);
    assertEquals(t.getMinutes(),0);
    
    t= Time.toTime(60);
    assertEquals(t.getHours(),1);
    assertEquals(t.getMinutes(),0);
    
    t= Time.toTime(20);
    assertEquals(t.getHours(),0);
    assertEquals(t.getMinutes(),20);
    
    t= Time.toTime(120);
    assertEquals(t.getHours(),2);
    assertEquals(t.getMinutes(),0);
    
    t= Time.toTime(150);
    assertEquals(t.getHours(),2);
    assertEquals(t.getMinutes(),30);
    
  }
  
  @Test
  void subDurationTest()
  {
    Time t= new Time();
    
    Time tf= new Time(5,20);
    Time duration= new Time(1,20);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),4);
    assertEquals(t.getMinutes(),0);
    
    tf= new Time(5,20);
    duration= new Time(0,0);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),5);
    assertEquals(t.getMinutes(),20);
    
    tf= new Time(18,30);
    duration= new Time(6,0);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),12);
    assertEquals(t.getMinutes(),30);
    
    tf= new Time(1,20);
    duration= new Time(2,20);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),23);
    assertEquals(t.getMinutes(),0);
    
    tf= new Time(1,20);
    duration= new Time(2,40);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),22);
    assertEquals(t.getMinutes(),40);
    
    tf= new Time(1,20);
    duration= new Time(0,50);
    t=Time.subDuration(tf, duration);
    assertEquals(t.getHours(),00);
    assertEquals(t.getMinutes(),30);
   
  }
  
  @Test
  void sumDurationTest()
  {
    Time t= new Time();
    
    Time ti= new Time(5,20);
    Time duration= new Time(1,20);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),6);
    assertEquals(t.getMinutes(),40);
    
    ti= new Time(5,20);
    duration= new Time(0,0);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),5);
    assertEquals(t.getMinutes(),20);
    
    ti= new Time(18,30);
    duration= new Time(6,0);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),00);
    assertEquals(t.getMinutes(),30);
    
    ti= new Time(1,20);
    duration= new Time(2,20);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),3);
    assertEquals(t.getMinutes(),40);
    
    ti= new Time(1,20);
    duration= new Time(2,40);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),4);
    assertEquals(t.getMinutes(),00);
    
    ti= new Time(1,20);
    duration= new Time(0,50);
    t=Time.sumDuration(ti, duration);
    assertEquals(t.getHours(),2);
    assertEquals(t.getMinutes(),10);
   
  }
  
  @Test
  void toMeridiemTest()
  {
    Time ti= new Time(23,0);
    Time t= Time.toMeridiem(ti);
    assertEquals(t.getHours(),11);
    assertEquals(t.getMinutes(),0);
    assertEquals(t.getMeridiem(),Time.PM);
    
    ti= new Time(12,24);
    t= Time.toMeridiem(ti);
    assertEquals(t.getHours(),12);
    assertEquals(t.getMinutes(),24);
    assertEquals(t.getMeridiem(),Time.PM);
    
    ti= new Time(00,24);
    t= Time.toMeridiem(ti);
    assertEquals(t.getHours(),12);
    assertEquals(t.getMinutes(),24);
    assertEquals(t.getMeridiem(),Time.AM);
    
    ti= new Time(18,30);
    t= Time.toMeridiem(ti);
    assertEquals(t.getHours(),6);
    assertEquals(t.getMinutes(),30);
    assertEquals(t.getMeridiem(),Time.PM);
    
    ti= new Time(6,30);
    t= Time.toMeridiem(ti);
    assertEquals(t.getHours(),6);
    assertEquals(t.getMinutes(),30);
    assertEquals(t.getMeridiem(),Time.AM);
    
   
  }
  
  @Test
  void toStringTest()
  {
    Time t= new Time(5,20);
    assertEquals(t.toString(),"05:20 (a.m)");
    
    t= new Time(15,40);
    assertEquals(t.toString(),"03:40 (p.m)");
    
    t= new Time(20,0);
    assertEquals(t.toString(),"08:00 (p.m)");
    
    t= new Time(0,0);
    assertEquals(t.toString(),"12:00 (a.m)");
    
    t= new Time(12,0);
    assertEquals(t.toString(),"12:00 (p.m)");
    
    
  }
  
  @Test
  void toMilitaryTest()
  {
    Time t= new Time(5,20,Time.PM);
    Time r= Time.toMilitary(t);
    assertEquals(r.getHours(),17);
    assertEquals(r.getMinutes(),20);
    
    t= new Time(7,30,Time.PM);
    r= Time.toMilitary(t);
    assertEquals(r.getHours(),19);
    assertEquals(r.getMinutes(),30);
    
    t= new Time(12,30,Time.PM);
    r= Time.toMilitary(t);
    assertEquals(r.getHours(),12);
    assertEquals(r.getMinutes(),30);
    
    t= new Time(5,20,Time.AM);
    r= Time.toMilitary(t);
    assertEquals(r.getHours(),5);
    assertEquals(r.getMinutes(),20);
    
    t= new Time(11,00,Time.AM);
    r= Time.toMilitary(t);
    assertEquals(r.getHours(),11);
    assertEquals(r.getMinutes(),00);
    
    t= new Time(12,55,Time.AM);
    r= Time.toMilitary(t);
    assertEquals(r.getHours(),0);
    assertEquals(r.getMinutes(),55);
    
    
    
  }
  

}