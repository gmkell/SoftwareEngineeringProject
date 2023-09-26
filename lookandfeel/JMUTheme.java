package lookandfeel;
 
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
 
public class JMUTheme extends DefaultMetalTheme
{
    private ColorUIResource  gold       = new ColorUIResource(194,161, 77);
    private ColorUIResource  darkGold   = new ColorUIResource(143,118, 50);  
    private ColorUIResource  lightGold  = new ColorUIResource(214,192,135);  
    private ColorUIResource  paleGold   = new ColorUIResource(252,246,228);
    private ColorUIResource  purple     = new ColorUIResource( 69,  0,132);
    private ColorUIResource  palePurple = new ColorUIResource(214,168,255);
 
 
 
 
    public ColorUIResource getWhite()
    {
  return paleGold;
    }
 
 
    protected ColorUIResource getPrimary1() 
    { 
  return purple; 
    }
 
    protected ColorUIResource getPrimary2() 
    { 
  return purple; 
    }
 
    protected ColorUIResource getPrimary3() 
    { 
  return palePurple; 
    }
    
    protected ColorUIResource getSecondary1() 
    { 
  return lightGold; 
    }
 
    protected ColorUIResource getSecondary2() 
    { 
  return darkGold; 
    }
 
    protected ColorUIResource getSecondary3() 
    { 
  return gold; 
    }
 
 
    public FontUIResource getControlTextFont()
    {
  return new FontUIResource("Serif", Font.BOLD, 14);
    }
 
    public FontUIResource getMenuTextFont()
    {
  return new FontUIResource("Serif", Font.BOLD, 14);
    }
 
    public FontUIResource getSubTextFont()
    {
  return new FontUIResource("Serif", Font.PLAIN, 12);
    }
 
    public FontUIResource getSystemTextFont()
    {
  return new FontUIResource("Sans Serif", Font.PLAIN, 10);
    }
 
    public FontUIResource getUserTextFont()
    {
  return new FontUIResource("Serif", Font.PLAIN, 14);
    }
 
    public FontUIResource getWindowTitleTextFont()
    {
  return new FontUIResource("Serif", Font.PLAIN, 16);
    }
}
