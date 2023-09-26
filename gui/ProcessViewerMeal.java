
package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import math.Time;
import recipe.Meal;

/**
 * Class that represents the GUI oft he Process Viewer.
 * 
 * @author Hiba Akbi
 */
public class ProcessViewerMeal implements ActionListener
{
  private static String PRINT = "Print";
  private static String CALCULATE = "calculate";
  private JFrame frame;
  private Container contentPane;
  private JPanel genPanel;
  private JPanel utensilPanel;
  private JPanel stepPanel;
  private JPanel printPanel;
  private JPanel platingPanel;
  private JTextArea steps;
  private JTextArea utensils;
  private JTextField jfieldHours;
  private JTextField jfieldMin;
  private DocumentListener d;
  private JComboBox<String> timeUnits;
  private JButton calculateButton;
  private JScrollPane utensilPane;
  private JScrollPane stepPane;
  private Meal meal;
  private String[] units = {"a.m", "p.m"};
  // private char lastValidKey; REMOVED TO CONFIRM TO CHECKSTYLE

  /**
   * Constructor. Initializes the window depending on the type of file opened.
   * @throws ClassNotFoundException 
   */
  public ProcessViewerMeal() throws ClassNotFoundException
  {
    fileDialog();
    if(meal!=null) init();
  }

  /**
   * open fileDialog window.
   * @throws ClassNotFoundException 
   */
  public void fileDialog() throws ClassNotFoundException
  {
    int retval;
    JFileChooser fileChooser;
    File chosenDirectory, lastDirectory;
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // use the directory user chose or home for default - Nicole
    lastDirectory = DirectoryPreference.getLastDirectory();
    if (lastDirectory != null && lastDirectory.isDirectory()) 
      chosenDirectory = lastDirectory;
    else chosenDirectory = new File(System.getProperty("user.home"));
    
   
    // set-up approved options
    fileChooser = new JFileChooser(chosenDirectory);
    FileNameExtensionFilter mealfilter = new FileNameExtensionFilter("Meal Files", "mel");
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(mealfilter);

    retval = fileChooser.showOpenDialog(frame);

    if (retval == JFileChooser.APPROVE_OPTION)
    {
      try
      {
        meal = Meal.deserialize(fileChooser.getSelectedFile());
      }
      catch (IOException e)
      {
        System.out.println("File" + fileChooser.getSelectedFile().getName()+ " not found" );
        e.printStackTrace();
        
      }

    }
  }

  private void init()
  {
    frame = new JFrame();
    frame.setTitle("KilowBites Process Viewer \t" + meal.getName());
    frame.setSize(690, 600);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setBackground(Color.white);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
   
    // components
    contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    // button Panel

    printPanel = new JPanel();
    printPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    printPanel.setBackground(Color.white);

    // new button stuff
    ImageIcon printButtonIcon = new ImageIcon(
        getClass().getClassLoader().getResource("icons/print (smaller).png"));
    JButton printButton = new JButton(printButtonIcon);
    printButton.setActionCommand(PRINT);
    printButton.addActionListener(this);
    printPanel.add(printButton);

    // plating time Panel

    platingPanel = new JPanel();
    platingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    platingPanel.setBackground(Color.white);

    // plating textFields
    JLabel jname = new JLabel("Plating time: ");
    jfieldHours = new JTextField(10);
    jfieldMin = new JTextField(10);
    JLabel j1 = new JLabel(":");
    timeUnits = new JComboBox<>(units);
    timeUnits.addActionListener(this);
    platingPanel.add(jname);
    platingPanel.add(jfieldHours);
    platingPanel.add(j1);
    platingPanel.add(jfieldMin);
    platingPanel.add(timeUnits);
    
    //plating button
    calculateButton= new JButton("Calculate start time");
    calculateButton.setActionCommand(CALCULATE);
    calculateButton.setEnabled(false);
    calculateButton.addActionListener(this);
    platingPanel.add(calculateButton);

    //gen Panel
    genPanel = new JPanel();
    genPanel.setLayout(new BoxLayout(genPanel, BoxLayout.Y_AXIS));
    
    // utensil Panel
    utensilPanel = new JPanel();
    utensilPanel.setBackground(Color.white);
    utensilPanel.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Utensils"));
    
    utensils= new JTextArea(meal.printAllUtensils());
    utensils.setEditable(false);
    JPanel p= new JPanel();
    p.setBackground(Color.white);
    p.add(utensils);
    p.setAlignmentX(JPanel.LEFT_ALIGNMENT);
    
    utensilPane = new JScrollPane(utensils);
    utensilPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    utensilPane.setBackground(Color.white);
    utensilPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
    utensilPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
    utensilPane.setPreferredSize(new Dimension(500, 100));
    utensilPane.setMinimumSize(new Dimension(500, 100));
    
    utensilPanel.add(utensilPane);
    genPanel.add(utensilPanel);

    // step Panel
    stepPanel = new JPanel();
    stepPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    stepPanel.setBackground(Color.white);
    stepPanel
        .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Steps"));
    steps= new JTextArea(meal.printSteps()); // PROBLEM 
    steps.setEditable(false);
    stepPane = new JScrollPane(steps);
    stepPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
    stepPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
    stepPane.setPreferredSize(new Dimension(500, 150));
    stepPane.setMinimumSize(new Dimension(500, 150));
    stepPanel.add(stepPane);
    
    genPanel.add(stepPanel);

    // add components to frame
    contentPane.add(printPanel);
    contentPane.add(platingPanel);
    contentPane.add(genPanel);
    frame.setVisible(true);
    
    //set up document listener to check for empty field
    d=new DocumentListener()
    {
      public void changedUpdate(final DocumentEvent e)
      {
        changed();
      }
      public void removeUpdate(final DocumentEvent e)
      {
        changed();
      }
      public void insertUpdate(final DocumentEvent e)
      {
        changed();
      }

      public void changed() 
      {
        //ensure both tiem fields are 
        if (jfieldHours.getText().equals("") || jfieldMin.getText().equals(""))
        {
          calculateButton.setEnabled(false);
        }
        else
        {
          calculateButton.setEnabled(true);
          
        }

      }
    };
    
    //set up key Listener to check for invalid values
    KeyAdapter k= new KeyAdapter() 
    {
      public void keyTyped(final KeyEvent e)
      {
        //prevents user from typing invalid character
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) 
            || (c == KeyEvent.VK_DELETE)))
        {
          e.consume();
        }
//        //prevents suer from typing invalid numbers of hours and minutes
        if(calculateButton.isEnabled())
        {
          int h= Integer.parseInt(jfieldHours.getText());
          int m=  Integer.parseInt(jfieldMin.getText());
          
          if(h>12 || h==0) jfieldHours.setText(jfieldHours.getText().substring(0,0));
          if(m>59) jfieldMin.setText(jfieldMin.getText().substring(0,0));
        }
        
        
      }
    };
    
    jfieldHours.getDocument().addDocumentListener(d);
    jfieldMin.getDocument().addDocumentListener(d);
    jfieldHours.addKeyListener(k);
    jfieldMin.addKeyListener(k);

    
  }

   
  /**Responds to click on calculate and print.
   * @param e is the click event */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    
    if(e.getActionCommand().equals(PRINT)) 
    {
      //System.out.println("PRINT button as clicked");
      printPage(genPanel);
      
    }
    else if(e.getActionCommand().equals(CALCULATE))
    {
      //ensure there is a value entered for plating time
      
      int h= Integer.parseInt(jfieldHours.getText());
      int m= Integer.parseInt(jfieldMin.getText());
      String mer= (String) timeUnits.getSelectedItem();
      
      //get Start Time from fields
      Time startTime= new Time(h,m,mer);
      startTime= Time.toMilitary(startTime);
      System.out.println("took time and converted it ");
      //performs calculations
      meal.setStartTime(startTime);
      System.out.println("made calculations");
      steps.setText(meal.printStepsStartTime(startTime));
      
    }
    
  }
  
  /**
   * Prints the ShoppingListViewer window.
   * @param viewer the jpanel that needs to be printed
   */
  public void printPage(final JPanel viewer)
  {
    PrinterJob printComp = PrinterJob.getPrinterJob();
    printComp.setJobName(PRINT);
    printComp.setPrintable(new Printable()
    {
      @Override
      public int print(final Graphics graphics, final PageFormat pageFormat, final int pageNum)
          throws PrinterException
      {
        if (pageNum > 0)
        {
          return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        viewer.paint(g2);
        return Printable.PAGE_EXISTS;
      }
    });

    if (!printComp.printDialog())
    {
      return;
    }

    try
    {
      printComp.print();
    }
    catch (PrinterException ex)
    {
      // handle exception
      ex.printStackTrace();
    }

  }
 
}