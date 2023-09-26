package gui;

import java.awt.Color;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import math.MeasurementUnits;
import math.Time;
import recipe.Step;
import recipe.DetailedUtensil;
import recipe.MeasuredIngredient;
import recipe.Recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hiba Akbi, Emil
 * Recipe Editor GUI 
 */
public class RecipeEditor extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final String ING_NOTIFICATION = "ingredientAdded";
  private static final String UTNSL_NOTIFICATION = "utensilAdded";
  private JFrame jframe;
  private Container contentPane;
  private JTextField nameField;
  private JTextField servesField;
  private JTextField ingrName;
  private JTextField ingrDetails;
  private JTextField ingrAmount;
  private JButton ingrAddBtn;
  private JButton ingrDeleteBtn;
  private JTextField utslName;
  private JTextField utslDetails;
  private JButton utslAddBtn;
  private JButton utslDeleteBtn;
  private JComboBox<String> unitsBox;
  private JComboBox<RC<?>> onBox;
  private JComboBox<RC<?>> utensilBox;
  private JTextField stepDetails;
  private JTextField stepAction;
  private JTextField stepHours;
  private JTextField stepMinutes;
  private JButton stepAddBtn;
  private JButton stepDeleteBtn;
  private JButton saveButton;
  private JButton saveAsButton;
  private JList<WithDependencies<Step>> stepList;
  private DefaultListModel<WithDependencies<Step>> stepListModel;
  private SortedJList<MeasuredIngredient> sortedIngredients;
  private SortedJList<DetailedUtensil> sortedUtensils;
  
  private Recipe recipe = null;
  
  private String[] unitsArray;
  private final String name = "Name";
  private final String details = "Details";
  private final String add = "Add";
  private final String delete = "Delete";
  private final String addUtsl = "addUtsl";
  private final String deleteUtsl = "deleteUtsl";
  private final String addIngrd = "addIngrd";
  private final String deleteIngrd = "deleteIngrd";
  private final String addStep = "addStep";
  private final String deleteStep = "deleteStep";
  private final String addRecipe = "ADD_RECIPE";
  private final String openRecipe = "OPEN_RECIPE";
  private final String saveRecipe = "SAVE_RECIPE";
  private final String saveAsRecipe = "SAVE_AS_RECIPE";
  private final String closeRecipe = "CLOSE";
  private final String error = "Error";
  private final String failedToOpenFile = "Failed to open file!";
  
  /**
   * Instantiate the RecipeEditor.
   * */
  public RecipeEditor()
  {
    sortedIngredients = new SortedJList<>(ING_NOTIFICATION, deleteIngrd);
    sortedUtensils = new SortedJList<>(UTNSL_NOTIFICATION, deleteUtsl);
    unitsArray = MeasurementUnits.getAllUnits();
    initialize();
  }
  
  private void initializeButtons()
  {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setBackground(Color.white);

    addIconButton("icons/add_sm.png", buttonPanel, addRecipe);
    addIconButton("icons/open_sm.png", buttonPanel, openRecipe);
    saveButton = addIconButton("icons/save_sm.png", buttonPanel, saveRecipe);
    saveButton.setEnabled(false);
    saveAsButton = addIconButton("icons/save_as_sm.png", buttonPanel, saveAsRecipe);
    saveAsButton.setEnabled(false);
    addIconButton("icons/close_sm.png", buttonPanel, closeRecipe);
    
    contentPane.add(buttonPanel);
  }

  private void disableAll() 
  {
    nameField.setEnabled(false);
    servesField.setEnabled(false);
    utslName.setEnabled(false);
    utslDetails.setEnabled(false);
    utslAddBtn.setEnabled(false);
    utslDeleteBtn.setEnabled(false);
    ingrName.setEnabled(false);
    ingrDetails.setEnabled(false);
    ingrAmount.setEnabled(false);
    ingrAddBtn.setEnabled(false);
    unitsBox.setEnabled(false);
    ingrDeleteBtn.setEnabled(false);
    onBox.setEnabled(false);
    utensilBox.setEnabled(false);
    stepAction.setEnabled(false);
    stepDetails.setEnabled(false);
    stepHours.setEnabled(false);
    stepMinutes.setEnabled(false);
    stepDeleteBtn.setEnabled(false);
    stepAddBtn.setEnabled(false);
  }
  
  private void enableAll() 
  {
    nameField.setEnabled(true);
    servesField.setEnabled(true);
    utslName.setEnabled(true);
    utslDetails.setEnabled(true);
    utslDeleteBtn.setEnabled(true);
    ingrName.setEnabled(true);
    ingrDetails.setEnabled(true);
    ingrAmount.setEnabled(true);
    unitsBox.setEnabled(true);
    ingrDeleteBtn.setEnabled(true);
    onBox.setEnabled(true);
    utensilBox.setEnabled(true);
    stepAction.setEnabled(true);
    stepDetails.setEnabled(true);
    stepHours.setEnabled(true);
    stepMinutes.setEnabled(true);
    stepDeleteBtn.setEnabled(true);
  }
  
  private void initializeInfo() 
  {
    //info Panel
    JPanel info= new JPanel();
    info.setLayout(new FlowLayout(FlowLayout.LEFT));
    info.setBackground(Color.white);
    
    nameField = addTextField(name, 45, info);
    servesField = addTextField("Serves", 15, info);
    contentPane.add(info);

    nameField.getDocument().addDocumentListener(new DocumentListener() 
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }
    });

    servesField.getDocument().addDocumentListener(new DocumentListener() 
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(servesField.getText());
          if (nameField.getText().equals("")) 
          {
            saveButton.setEnabled(false);
            saveAsButton.setEnabled(false);
          }
          else
          {
            saveButton.setEnabled(true);
            saveAsButton.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          saveButton.setEnabled(false);
          saveAsButton.setEnabled(false);
        }
      }
    });
  }
  
  private void initializeUtensils() 
  {
    JPanel utensil= new JPanel();
    utensil.setLayout(new BoxLayout(utensil, BoxLayout.Y_AXIS));

    utensil.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Utensils"));

    JPanel boxPanel = new JPanel();
    boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    utslName = addTextField(name, 30, boxPanel);
    utslDetails = addTextField(details, 23, boxPanel);
    utslAddBtn = addButton(add, boxPanel, addUtsl);
    utslDeleteBtn = addButton(delete, boxPanel, deleteUtsl);
    utensil.add(boxPanel);
    
    utslName.getDocument().addDocumentListener(new DocumentListener() 
    {

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        if (utslName.getText().equals(""))
        {
          utslAddBtn.setEnabled(false);
        }
        else 
        {
          utslAddBtn.setEnabled(true);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (utslName.getText().equals(""))
        {
          utslAddBtn.setEnabled(false);
        }
        else 
        {
          utslAddBtn.setEnabled(true);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        if (utslName.getText().equals(""))
        {
          utslAddBtn.setEnabled(false);
        }
        else 
        {
          utslAddBtn.setEnabled(true);
        }
      }
    });
    

    addScrollList(utensil, sortedUtensils);
    contentPane.add(utensil);
    sortedUtensils.addActionListener(this);

    ListSelectionListener listSelectionListener = new ListSelectionListener() 
    {
      public void valueChanged(final ListSelectionEvent listSelectionEvent) 
      {
        RC<DetailedUtensil> ing = sortedUtensils.getSelectedItem();

        utslDeleteBtn.setEnabled(ing == null || ing.refCount < 2);
      }
    };
    sortedUtensils.getList().addListSelectionListener(listSelectionListener);
  }
  
  private void initializeIngredients() 
  {
    JPanel ingredient= new JPanel();
    ingredient.setLayout(new BoxLayout(ingredient, BoxLayout.Y_AXIS));

    ingredient.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Ingredients"));

    JPanel boxPanel = new JPanel();
    boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    
    ingrName = addTextField(name, 13, boxPanel);
    ingrDetails = addTextField(details,10, boxPanel);
    ingrAmount = addTextField("Amount",10, boxPanel);
    boxPanel.add(new JLabel("Units"));
    unitsBox = addComboBox(unitsArray, boxPanel);
    ingrAddBtn = addButton(add, boxPanel,addIngrd);
    ingrDeleteBtn = addButton(delete, boxPanel, deleteIngrd);
    ingredient.add(boxPanel);

    ingrAmount.getDocument().addDocumentListener(new DocumentListener() 
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }
    });

    ingrName.getDocument().addDocumentListener(new DocumentListener() 
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }
    });
    
    unitsBox.addItemListener(new ItemListener() 
    {
      @Override
      public void itemStateChanged(final ItemEvent e)
      {
        try 
        {
          Double.parseDouble(ingrAmount.getText());
          if (ingrName.getText().equals("") || unitsBox.getSelectedIndex() == -1) 
          {
            ingrAddBtn.setEnabled(false);
          }
          else
          {
            ingrAddBtn.setEnabled(true);
          }
        } 
        catch (NumberFormatException nfe)
        {
          ingrAddBtn.setEnabled(false);
        }
      }
    });

    addScrollList(ingredient, this.sortedIngredients);
    contentPane.add(ingredient);
    sortedIngredients.addActionListener(this);
    
    ListSelectionListener listSelectionListener = new ListSelectionListener() 
    {
      public void valueChanged(final ListSelectionEvent listSelectionEvent) 
      {
        RC<MeasuredIngredient> ing = sortedIngredients.getSelectedItem();
        
        ingrDeleteBtn.setEnabled(ing == null || ing.refCount < 2);
      }
    };
    sortedIngredients.getList().addListSelectionListener(listSelectionListener);
  }
  
  private void initializeSteps()
  {
    JPanel stepPanel = new JPanel();
    stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.Y_AXIS));

    stepPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Steps"));
    
    JPanel boxPanel = new JPanel();
    boxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


    stepAction = addTextField("Action", 10, boxPanel);
    
    onBox = new JComboBox<>(new DefaultComboBoxModel<>());
    onBox.setSelectedIndex(-1);

    onBox.setPreferredSize(new Dimension(250, 24));
    boxPanel.add(new JLabel("On:"));
    boxPanel.add(onBox);

    utensilBox = new JComboBox<>(new DefaultComboBoxModel<>());
    utensilBox.setSelectedIndex(-1);
    utensilBox.setBackground(Color.white);
    utensilBox.setPreferredSize(new Dimension(250, 24));
    boxPanel.add(new JLabel("Utensil:"));
    boxPanel.add(utensilBox);
    
    stepDetails = addTextField(details, 10, boxPanel);

    stepHours = addTextField("(hrs)", 10, boxPanel);
    stepHours.setPreferredSize(new Dimension(48, 20));
    stepMinutes = addTextField("(mins)", 10, boxPanel);

    stepAddBtn = addButton(add, boxPanel, addStep);
    this.stepListModel = new DefaultListModel<>();
    this.stepList = new JList<>(this.stepListModel);
    
    stepDeleteBtn = addButton(delete, boxPanel, deleteStep);
    stepPanel.add(boxPanel);
    
    onBox.addItemListener(new ItemListener() 
    {
      @Override
      public void itemStateChanged(final ItemEvent e)
      {
        if (stepAction.getText().equals("") 
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1
        ) 
        {
          stepAddBtn.setEnabled(false);
        }
        else
        {
          stepAddBtn.setEnabled(true);
        }
      }
    });

    utensilBox.addItemListener(new ItemListener() 
    {
      @Override
      public void itemStateChanged(final ItemEvent e)
      {
        if (stepAction.getText().equals("") 
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1
        ) 
        {
          stepAddBtn.setEnabled(false);
        }
        else
        {
          stepAddBtn.setEnabled(true);
        }
      }
    });

    stepAction.getDocument().addDocumentListener(new DocumentListener() 
    {
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        if (stepAction.getText().equals("") 
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1
        ) 
        {
          stepAddBtn.setEnabled(false);
        }
        else
        {
          stepAddBtn.setEnabled(true);
        }
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (stepAction.getText().equals("") 
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1
        ) 
        {
          stepAddBtn.setEnabled(false);
        }
        else
        {
          stepAddBtn.setEnabled(true);
        }
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        if (stepAction.getText().equals("") 
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1
        ) 
        {
          stepAddBtn.setEnabled(false);
        }
        else
        {
          stepAddBtn.setEnabled(true);
        }
      }
    });

    JScrollPane scrollable = new JScrollPane(this.stepList);  
    scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    scrollable.setPreferredSize(new Dimension(350,150));
    stepPanel.add(scrollable);
    contentPane.add(stepPanel);
  }
  
  /**
   * Initializes the Recipe editor window and its components.
   * */
  public void initialize() 
  {
    //basic initialization of the jframe
    jframe = new JFrame();
    jframe.setTitle("KilowBites Recipe Editor");
    jframe.setSize(1420, 900);
    jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    jframe.setBackground(Color.white);
    jframe.setLocationRelativeTo(null); 
    jframe.setResizable(true);
    
    //components
    contentPane = jframe.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
    
    initializeButtons();
    initializeInfo();
    initializeUtensils();
    initializeIngredients();
    initializeSteps();
    disableAll();
    
    jframe.setVisible(true);
  }

  /**
   * creates a text label, with a text field and adds it to he JPanel.
   * @param fieldName will be displayed as "fieldName: " next to the text field
   * @param size is the size of the textfield
   * @param panel is the panel to which the field is added.
   * @return JtextField the created textfield */
  public JTextField addTextField(final String fieldName, final int size, final JPanel panel) 
  {
    JLabel jname = new JLabel(fieldName+ ": ");
    JTextField jfield= new JTextField(size);
    panel.add(jname);
    panel.add(jfield);
    
    return jfield;
  }
  
  /**
   * Resets a textField to be empty.
   * @param fieldName will be displayed as "fieldName: " next to the text field
   * */
  public void resetTextField (final JTextField fieldName) 
  {
    fieldName.setText("");
  }
  
  
  /**
   * creates a text label, with a text field and adds it to he JPanel.
   * @param buttonText will be displayed inside the button
   * @param panel is the panel to which the field is added.
   * @param actionCmd is the action command string for the button events 
   * 
   * @return The button that was created.
   */
  public JButton addButton(final String buttonText, final JPanel panel, final String actionCmd) 
  {
    JButton button= new JButton(buttonText);
    button.setBackground(Color.white);
    button.setActionCommand(actionCmd);
    button.addActionListener(this);
    button.addActionListener(sortedUtensils);
    button.addActionListener(sortedIngredients);

    panel.add(button);
    return button;
  }

  /**
   * Create and add a JButton to the given panel.
   * 
   * @param filePath The image icon to add.
   * @param actionCmd The action command.
   * @param panel The panel to add the JButton to.
   * @return The Jbutton created.
   */
  private JButton addIconButton(
      final String filePath, 
      final JPanel panel, 
      final String actionCmd
  )
  {
    JButton button;
    ImageIcon icon = new ImageIcon(
        getClass().getClassLoader().getResource(filePath)
    );
    
    button = new JButton(icon);
    button.setPreferredSize(new Dimension(40, 40));
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setActionCommand(actionCmd);
    button.addActionListener(this);
    panel.add(button);
    
    return button;
  }
  
  /**
   * creates a comboBox with elements from the array.
   * @param array contains all elements added to the drop down 
   * @param panel is the panel to which the field is added.
   * @return the JComboBox created. */
  public JComboBox<String> addComboBox(final String[] array, final JPanel panel) 
  {
    JComboBox<String> cb = new JComboBox<>(array);
    cb.setSelectedIndex(-1);
    cb.setBackground(Color.white);
    panel.add(cb);
    return cb;
    
  }
  
  /**
   * creates a scrollPane with a list of items.
   * @param panel is the panel to which the field is added.
   * @param list is the JPanel list
    */
  public void addScrollList( final JPanel panel, final JPanel list) 
  {
    JScrollPane scrollable = new JScrollPane(list);  
    scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    scrollable.setPreferredSize(new Dimension(350,150));
    panel.add(scrollable);
  }
  
  private static File getDirectory() 
  {
    File chosenDirectory, lastDirectory;
 
    // use the directory user chose or home for default - Nicole
    lastDirectory = DirectoryPreference.getLastDirectory();
    if (lastDirectory != null && lastDirectory.isDirectory()) 
      chosenDirectory = lastDirectory;
    else chosenDirectory = new File(System.getProperty("user.home"));
    
    return chosenDirectory;
  }
  
  /**
   * Responds to add and delete buttons.
   * @param e the event
   */
  public void actionPerformed(final ActionEvent e)
  {
    switch(e.getActionCommand())
    {
      case addIngrd:
        String iname= ingrName.getText();
        String idetail= ingrDetails.getText();
        String iamount= ingrAmount.getText();
        String iunit= (String) unitsBox.getSelectedItem();
        
        //if name or amount fields is empty, display error message.
        if(iname.equals("") || iamount.equals("") ) 
        {
          JOptionPane.showMessageDialog(jframe, "Empty fields. "
              + "Please ensure that all fields have an entry.",
              error, JOptionPane.ERROR_MESSAGE);
        }
        else 
        {
          MeasuredIngredient mg = 
              MeasuredIngredient.fromStringParams(iname, iamount, iunit, idetail);

          if(mg == null) 
          {
            JOptionPane.showMessageDialog(
                jframe, 
                "Incorrect Fields. "
                  + "Please ensure that fields include valid information.",
                error, 
                JOptionPane.ERROR_MESSAGE
            );
          }
          else 
          {
            sortedIngredients.addItem(mg);
            resetTextField(ingrName);
            resetTextField(ingrDetails);
            resetTextField(ingrAmount);
            unitsBox.setSelectedIndex(-1);
          }
        }
        onBox.validate();
        break;
      
      case deleteIngrd:
        break;
        
      case addUtsl:
        String uname = utslName.getText();
        String udetail = utslDetails.getText();
        
        //if name field is empty, display error message.
        if(uname.equals("")) 
        {
          JOptionPane.showMessageDialog(
              jframe,
              " Empty fields. "
              + "Please ensure that all fields have an entry. ",
              error, 
              JOptionPane.ERROR_MESSAGE
          );
        }
        else 
        {
          //add the utensil
          sortedUtensils.addItem(new DetailedUtensil(uname, udetail));
          //reset textfields to empty
          resetTextField(utslName);
          resetTextField(utslDetails);
        }
          
        break;
        
      case deleteUtsl:
        break;
      
      case addStep:
        if (
            stepAction.getText().equals("")
            || onBox.getSelectedIndex() == -1
            || utensilBox.getSelectedIndex() == -1 
        ) 
        {
          JOptionPane.showMessageDialog(
              jframe,
              "Please make sure you have provided an action and "
              + "selected an ingredient and utensil for this step!",
              "Step Error", 
              JOptionPane.ERROR_MESSAGE
          );
          return;
        }
        
        Integer hours = null, minutes = null;
        if (!this.stepHours.getText().equals("")) 
        {
          try 
          {
            hours = Integer.parseInt(this.stepHours.getText());
            
            if (hours < 0 || hours > 23) 
            {
              JOptionPane.showMessageDialog(
                  jframe,
                  "Only hours, where 0 < hours <= 23 are valid!",
                  error, 
                  JOptionPane.ERROR_MESSAGE
              );
              break;
            }
          } 
          catch ( NumberFormatException nfe ) 
          {
            JOptionPane.showMessageDialog(
                jframe,
                "Please ensure step hours are a valid value!",
                error,
                JOptionPane.ERROR_MESSAGE
            );
            break;
          }
        }

        if (!this.stepMinutes.getText().equals("")) 
        {
          try 
          {
            minutes = Integer.parseInt(this.stepMinutes.getText());
            
            if (minutes < 0 || minutes > 59) 
            {
              JOptionPane.showMessageDialog(
                  jframe,
                  "Only minutes, where 0 < minutes <= 59 are valid!",
                  error,
                  JOptionPane.ERROR_MESSAGE
              );
              break;
            }
          } 
          catch ( NumberFormatException nfe ) 
          {
            JOptionPane.showMessageDialog(
                jframe,
                "Please ensure step minutes are a valid value!",
                "step Error", 
                JOptionPane.ERROR_MESSAGE
            );
            break;
          }
        }

        /** Should be working. I'd (Hiba) appreciate if someone took a look at 
         * the excessive type casting though caus frankly i just tried stuff till
         *  I stopped a compilation error or exception */
        
        Object on = (Object)((RC<?>)(onBox.getSelectedItem())).inner();
        DetailedUtensil utensil = (DetailedUtensil)((RC<?>)(utensilBox.getSelectedItem())).inner();
        String detail = this.stepDetails.getText();
        String action = this.stepAction.getText();
        
        // Set duration. Will be null of both hours and minutes were left blank!
        Time duration = null;
        // TODO (Hiba) Does this look right in terms of leaving duration as null of both are blank?
        if (hours != null || minutes != null) 
        {
          duration = new Time(hours == null ? 0 : hours, minutes == null ? 0 : minutes);
        }
        //here must modify !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.stepListModel.addElement(new WithDependencies<>(new Step(
            action, 
            on,
            utensil,
            detail,
            duration
        ), (RC<?>) onBox.getSelectedItem(), (RC<?>) utensilBox.getSelectedItem()));
        
        this.onBox.setSelectedIndex(-1);
        this.utensilBox.setSelectedIndex(-1);
        this.stepDetails.setText("");
        this.stepAction.setText("");
        this.stepHours.setText("");
        this.stepMinutes.setText("");
        this.sortedIngredients.getList().clearSelection();
        this.sortedUtensils.getList().clearSelection();
        this.validate();

        break;
      case deleteStep:
        int selected = this.stepList.getSelectedIndex();
        if (selected != -1) 
        {
          this.stepListModel.getElementAt(selected).drop();
          this.stepListModel.remove(selected);
          this.sortedIngredients.getList().clearSelection();
          this.sortedUtensils.getList().clearSelection();
          this.validate();
        }
        break;
       
      case addRecipe:
        // Clear out the recipe editor.
        this.recipe = new Recipe();
        this.sortedIngredients.getModel().removeAllElements();
        this.sortedUtensils.getModel().removeAllElements();
        this.nameField.setText("");
        this.servesField.setText("");
        this.stepListModel.removeAllElements();
        this.actionPerformed(new ActionEvent(
            this, 
            ActionEvent.ACTION_PERFORMED, 
            ING_NOTIFICATION
            )
        );

        enableAll();
        break;

      case closeRecipe:
        // Clear out the recipe editor.
        this.recipe = null;
        this.sortedIngredients.getModel().removeAllElements();
        this.sortedUtensils.getModel().removeAllElements();
        this.nameField.setText("");
        this.servesField.setText("");
        this.stepListModel.removeAllElements();
        this.actionPerformed(new ActionEvent(
            this, 
            ActionEvent.ACTION_PERFORMED, 
            ING_NOTIFICATION
            )
        );

        disableAll();
        break;

      case saveRecipe:
        saveRecipe(new File(this.nameField
            .getText().
            toLowerCase().
            replace(' ', '_') + Recipe.FILE_EXTENSION
        ));
        break;
       
      case saveAsRecipe:
        JFileChooser saveAsFD = new JFileChooser(getDirectory());
        
        if (
            saveAsFD.showDialog(new JFrame(), "Choose a name for your file.") 
            != JFileChooser.APPROVE_OPTION
        ) 
        {
          return;
        }

        saveRecipe(saveAsFD.getSelectedFile());
        break;
        
      case openRecipe:
        JFileChooser openFD = new JFileChooser(getDirectory());
        
        if (
            openFD.showDialog(new JFrame(), "Select a file to open.") 
            != JFileChooser.APPROVE_OPTION
        ) 
        {
          return;
        }
        
        
        File file = openFD.getSelectedFile();

        FileInputStream fis;
        try
        {
          fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e1)
        {
          e1.printStackTrace();

          JOptionPane.showMessageDialog(
              jframe,
              failedToOpenFile,
              e1.toString(), 
              JOptionPane.ERROR_MESSAGE
          );
          break;
        }
        
        ObjectInputStream ois;
        try
        {
          ois = new ObjectInputStream(fis);
        }
        catch (IOException e1)
        {
          e1.printStackTrace();

          JOptionPane.showMessageDialog(
              jframe,
              failedToOpenFile,
              e1.toString(), 
              JOptionPane.ERROR_MESSAGE
          );
          
          break;
        }
        
        Recipe newRecipe;
        try
        {
          newRecipe = (Recipe) ois.readObject();
        }
        catch (ClassNotFoundException e1)
        {
          e1.printStackTrace();

          JOptionPane.showMessageDialog(
              jframe,
              "Failed to find class.",
              e1.toString(), 
              JOptionPane.ERROR_MESSAGE
          );
          
          break;
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(
              jframe,
              "Failed to read recipe.",
              e1.toString(), 
              JOptionPane.ERROR_MESSAGE
          );
          break;
        }

        try 
        {
          fis.close();
        }
        catch (IOException e1)
        {
          JOptionPane.showMessageDialog(
              jframe,
              failedToOpenFile,
              e1.toString(), 
              JOptionPane.ERROR_MESSAGE
          );
          
          break;
        }
        this.recipe = newRecipe;
        this.sortedIngredients.getModel().removeAllElements();
        this.sortedIngredients.getModel().addAll(
            Arrays.asList(this.recipe.getRCIngredients())
        );

        this.sortedUtensils.getModel().removeAllElements();
        this.sortedUtensils.getModel().addAll(
            Arrays.asList(this.recipe.getRCUtensils())
        );

        this.stepListModel.removeAllElements();
        this.stepListModel.addAll(Arrays
            .asList(this.recipe.getStepsWithDeps())
        );
        
        this.nameField.setText(this.recipe.getName());
        this.servesField.setText(String.format("%d",this.recipe.getServings()));
        this.actionPerformed(new ActionEvent(
            this, 
            ActionEvent.ACTION_PERFORMED, 
            ING_NOTIFICATION
            )
        );
        enableAll();
        break;

      // Handle utensil added cases for the "On" ComboBox"
      case ING_NOTIFICATION: 
      case UTNSL_NOTIFICATION:
        DefaultComboBoxModel<RC<?>> onBoxModel =
            (DefaultComboBoxModel<RC<?>>) this.onBox.getModel();
        onBoxModel.removeAllElements();
        onBoxModel.addAll(
            Arrays.asList(this.sortedIngredients.getRCItems())
        );

        onBoxModel.addAll(
            Arrays.asList(this.sortedUtensils.getRCItems())
        );

        DefaultComboBoxModel<RC<?>> utensilBoxModel =
            (DefaultComboBoxModel<RC<?>>) this.utensilBox.getModel();
        utensilBoxModel.removeAllElements();
        utensilBoxModel.addAll(
            Arrays.asList(this.sortedUtensils.getRCItems())
        );
        
        this.onBox.validate();
        this.utensilBox.validate();
        
        break;
        
      default:
        break;
    }
    
  }
  
  /**
   * Class to count references to Ingredients and Utensils.
   * 
   * @author Emil Hofstetter
   *
   * @param <T>
   */
  public static class RC<T> implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private int refCount = 1;
    private T inner;
    
    /**
     * Constructor.
     * 
     * @param inner
     */
    public RC(final T inner) 
    {
      this.inner = inner;
    }
    
    /**
     * Increments the ref count.
     */
    public void inc() 
    {
      this.refCount += 1;
    }

    /**
     * Decrements the ref count.
     */
    public void dec() 
    {
      this.refCount -= 1;
    }
    
    /**
     * Get the current reference count.
     * 
     * @return The current reference count.
     */
    public int count()
    {
      return this.refCount;
    }

    /**
     * Get the inner value.
     * 
     * @return The inner value.
     */
    public T inner()
    {
      return this.inner;
    }
    
    @Override
    public String toString() 
    {
      if (inner != null) 
      {
        return this.inner.toString();
      } 
      else 
      {
        return null;
      }
    }
  }
  
  /**
   * 
   * @author Emil Hofstetter
   *
   * @param <T>
   */
  public static class WithDependencies<T> implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private List<RC<?>> deps;
    private T inner;
    
    /**
     * Constructor.
     * 
     * @param inner
     * @param dependencies
     */
    public WithDependencies(final T inner, final RC<?>...dependencies) 
    {
      this.inner = inner;
      this.deps = Arrays.asList(dependencies);

      for (RC<?> d : deps)
      {
        d.inc();
      }
    }
    
    /**
     * Decrements the reference count of all dependencies.
     */
    public void drop()
    {
      for (RC<?> d : deps)
      {
        d.dec();
      }
      
    }
    
    /**
     * Returns the inner value.
     * 
     * @return The inner value.
     */
    public T inner()
    {
      return this.inner;
    }

    @Override
    public String toString() 
    {
      if (inner != null) 
      {
        return this.inner.toString();
      } 
      else 
      {
        return null;
      }
    }
  }
  
  @SuppressWarnings("resource")
  private void saveRecipe(final File filePath)
  {
    // Get Name
    this.recipe.setName(this.nameField.getText());
    if (this.recipe.getName().equals("")) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please specify a recipe name before saving!",
          error, 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    
    // Get Servings
    try 
    {
      this.recipe.setServings(Integer.parseInt(this.servesField.getText()));
    } 
    catch (NumberFormatException nfe) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please make sure servings are a valid whole number.",
          nfe.toString(), 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    if (this.recipe.getServings() <= 0) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please ensure that the amount of servings are greater than 0.",
          "Field Error", 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }


    // Get Ingredients
    this.recipe.setIngredienst(this.sortedIngredients.getRCItems());
    if (this.recipe.getIngredients().length == 0) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please add ingredients to your recipe before saving!",
          error, 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    // Get Utensils
    this.recipe.setUtensils(this.sortedUtensils.getRCItems());
    if (this.recipe.getUtensils().length == 0) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please add utensils to your recipe before saving!",
          error, 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    WithDependencies<Step>[] steps = Recipe.newArray(this.stepList.getModel().getSize());
    for (int i = 0; i < steps.length; i++) 
    {
      steps[i] = this.stepList.getModel().getElementAt(i);
    }

    // Get Steps
    this.recipe.setSteps(steps);
    if (this.recipe.getSteps().length == 0) 
    {
      JOptionPane.showMessageDialog(
          jframe,
          "Please add steps to your recipe before saving!",
          "Recipe Error", 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }

    // Open file in default location.
    FileOutputStream fos;
    try
    {
      fos = new FileOutputStream(filePath);
    }
    catch (FileNotFoundException e1)
    {
      e1.printStackTrace();

      JOptionPane.showMessageDialog(
          jframe,
          failedToOpenFile,
          e1.toString(), 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    
    ObjectOutputStream oos;
    try
    {
      oos = new ObjectOutputStream(fos);
    }
    catch (IOException e1)
    {
      e1.printStackTrace();

      JOptionPane.showMessageDialog(
          jframe,
          failedToOpenFile,
          e1.toString(), 
          JOptionPane.ERROR_MESSAGE
      );
      
      return;
    }
    
    try
    {
      oos.writeObject(this.recipe);
    }
    catch (IOException e1)
    {
      e1.printStackTrace();

      JOptionPane.showMessageDialog(
          jframe,
          "Failed to write to file.",
          e1.toString(), 
          JOptionPane.ERROR_MESSAGE
      );
      return;
    }
    
    try
    {
      fos.close();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();

      JOptionPane.showMessageDialog(
          jframe,
          "Failed to close file.",
          e1.toString(), 
          JOptionPane.ERROR_MESSAGE
      );
      
      return;
    }
    
    JOptionPane.showConfirmDialog(
        null, 
        "Recipe saved!", 
        "Success", 
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.PLAIN_MESSAGE
    );
  }
}
