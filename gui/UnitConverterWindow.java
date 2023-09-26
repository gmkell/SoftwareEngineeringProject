package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.swing.*;

import math.MeasurementUnits;
import math.UnitConversions;
import recipe.Ingredients;

/**
 * GUI aspect for Unit Converter Window.
 * 
 * @author Nicole Kim
 */
public class UnitConverterWindow extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private static final String FROM_UNITS = "From Units: ";
  private static final String TO_UNITS = "To Units: ";
  private static final String INGREDIENT = "Ingredient: ";
  private static final String FROM_AMOUNT = "From Amount: ";
  private static final String TO_AMOUNT = "To Amount: ";
  
  private String[] sortedIngredients;
  private Boolean enabled, indivFromUnit, indivToUnit;
  private Container contentPane;
  private JButton calculateButton, refreshButton;
  private JComboBox<String> fromUnit, toUnit, ingredientList;
  private JFrame frame;
  private JPanel buttons, dropBoxes, amount;
  private JTextField fromAmount, toAmount;
  private MeasurementUnits foundFromUnit, foundToUnit;
  private Set<String> allIngredients;

  /**
   * Constructor to call the initialization.
   */
  public UnitConverterWindow()
  {
    initialize();
  }

  /**
   * Set up window.
   * 
   * @throws IOException
   */
  private void initialize()
  {
    frame = new JFrame();
    frame.setTitle("KiLowBites Unit Converter");
    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    frame.setSize(850, 300);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.pack();
    
    contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    
    // Setup panel for the two buttons.
    buttons = new JPanel();
    buttons.setLayout(new FlowLayout(FlowLayout.LEFT));

    calculateButton = addButton("icons/calculate_button.png", buttons);
    refreshButton = addButton("icons/refresh_button.png", buttons);
    buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
    buttons.setMaximumSize(buttons.getPreferredSize());
    
    // Setup the panel for the drop down menus.
    dropBoxes = new JPanel();
    dropBoxes.setLayout(new FlowLayout(FlowLayout.LEFT));

    dropBoxes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    // Add drop down menus to panel.
    addLabel(FROM_UNITS, dropBoxes);
    dropBoxes.add(Box.createHorizontalStrut(10));
    fromUnit = addComboBox(MeasurementUnits.getAllUnits(), dropBoxes);
    dropBoxes.add(Box.createHorizontalStrut(30));
    addLabel(TO_UNITS, dropBoxes);
    dropBoxes.add(Box.createHorizontalStrut(10));
    toUnit = addComboBox(MeasurementUnits.getAllUnits(), dropBoxes);
    dropBoxes.add(Box.createHorizontalStrut(30));
    addLabel(INGREDIENT, dropBoxes);
    allIngredients = Ingredients.getAvailableIngredients().keySet();
    sortedIngredients = allIngredients.toArray(new String[allIngredients.size()]);
    Arrays.sort(sortedIngredients);
    ingredientList = addComboBox(sortedIngredients, dropBoxes);
    ingredientList.setEnabled(false);
    dropBoxes.setAlignmentX(Component.LEFT_ALIGNMENT);
    dropBoxes.setMaximumSize(dropBoxes.getPreferredSize());
    
    // Setup the panel for the starting and resulting amount.
    amount = new JPanel();
    amount.setLayout(new FlowLayout(FlowLayout.LEFT));

    amount.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    addLabel(FROM_AMOUNT, amount);
    fromAmount = new JTextField(12);
    fromAmount.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(final KeyEvent e)
      {
        char c = e.getKeyChar();
        if (!((c >= '0') && (c <= '9') || ((c == '.') && (!fromAmount.getText().contains(".")))
            || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
        {
          e.consume(); // consume non-numbers
        }
      }
    });
    amount.add(fromAmount);
    amount.add(Box.createHorizontalStrut(32));
    addLabel(TO_AMOUNT, amount);
    toAmount = new JTextField(13);
    toAmount.setEditable(false);
    amount.add(toAmount);
    amount.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Add panels to content pane.
    contentPane.add(buttons);
    contentPane.add(dropBoxes);
    contentPane.add(amount);
    frame.pack();
  }

  /**
   * Determine what happens when a certain button or drop box is used.
   * 
   * @param e The action event.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    Double totalAmount;
    Object action;
    String firstUnit, secondUnit, unit;
    totalAmount = null;
    action = e.getSource();
    unit = "individual";
    calculateButton.setEnabled(true);
    
    // Determine whether ingredient list should be enabled or disabled.
    if(action.equals(fromUnit) || action.equals(toUnit))
    {
      if(fromUnit.getSelectedIndex() != -1 && toUnit.getSelectedIndex() != -1)
      {
        firstUnit = fromUnit.getSelectedItem().toString();
        secondUnit = toUnit.getSelectedItem().toString();
        foundFromUnit = MeasurementUnits.fromUnit(firstUnit);
        foundToUnit = MeasurementUnits.fromUnit(secondUnit);
        indivFromUnit = foundFromUnit.getUnit().equals(unit);
        indivToUnit = foundToUnit.getType().equals(unit);
        
        if (indivFromUnit || indivToUnit)
        {
          if (indivFromUnit && (!indivToUnit && (MeasurementUnits.isVolumeUnit(foundToUnit) 
              || MeasurementUnits.isWeightUnit(foundToUnit))))
          {
            ingredientList.setEnabled(true);
            enabled = true;
          }
          else if (indivToUnit && (!indivFromUnit && (MeasurementUnits.isVolumeUnit(foundFromUnit)
              || MeasurementUnits.isWeightUnit(foundFromUnit))))
          {
            ingredientList.setEnabled(true);
            enabled = true;
          }
        }
        else if (MeasurementUnits.isWeightUnit(foundFromUnit)
            && MeasurementUnits.isVolumeUnit(foundToUnit))
        {
          ingredientList.setEnabled(true);
          enabled = true;
        }
        else if (MeasurementUnits.isWeightUnit(foundToUnit)
            && MeasurementUnits.isVolumeUnit(foundFromUnit))
        {
          ingredientList.setEnabled(true);
          enabled = true;
        }
        else
        {
          ingredientList.setEnabled(false);
          enabled = false;
        }
      }
    }
    
    // Implementation for the refresh button.
    if(action.equals(refreshButton))
    {
      fromUnit.setSelectedIndex(-1);
      toUnit.setSelectedIndex(-1);
      ingredientList.setSelectedIndex(-1);
      ingredientList.setEnabled(false);
      fromAmount.setText("");
      toAmount.setText("");
    }
    // Implementation for the calculate button.
    else if (action.equals(calculateButton))
    {
      // If not all available drop boxes and text fields are not used.
      if (fromUnit.getSelectedIndex() == -1 || toUnit.getSelectedIndex() == -1 
          || (enabled && ingredientList.getSelectedIndex() == -1)
          || fromAmount.getText().trim().isEmpty())
        calculateButton.setEnabled(false);
      else
      {
        Double initialAmount;
        initialAmount = Double.valueOf(fromAmount.getText());
        
        if (enabled)
        {
          Double weight;
          Ingredients matchingIngredient;
          String selectedIngredient;
          matchingIngredient = null;
          selectedIngredient = ingredientList.getSelectedItem().toString();
          
          for (Ingredients ingredient : Ingredients.getAvailableIngredients().values())
          {
            if (ingredient.getItem().equalsIgnoreCase(selectedIngredient))
            {
              matchingIngredient = ingredient;
              break;
            }
          }
          
          weight = matchingIngredient.getAvgWeight();
          if ((indivFromUnit || indivToUnit) && weight == null)
            //Error if the average weight of an ingredient can not be found (null).
            calculateButton.setEnabled(false);
          else
          {
            if (indivFromUnit) foundFromUnit = MeasurementUnits.GRAMS;
            if (indivToUnit) foundToUnit = MeasurementUnits.GRAMS;
            if (weight == null) weight = 1.0;
            totalAmount = UnitConversions.convert(initialAmount * weight,
                foundFromUnit, foundToUnit, matchingIngredient);
            toAmount.setText(String.valueOf(totalAmount));
          }
        }
        // If ingredient drop down is disabled.
        else 
        {
          totalAmount = UnitConversions.convert(initialAmount, foundFromUnit, foundToUnit, null);
          toAmount.setText(String.valueOf(totalAmount));
        }
      }
    }
  }

  /**
   * Create and add a JButton to the given panel.
   * 
   * @param text The imageicon to add.
   * @param panel The panel to add the JButton to.
   * @return The Jbutton created.
   */
  private JButton addButton(final String text, final JPanel panel)
  {
    ImageIcon icon;
    JButton button;
    
    icon = new ImageIcon(getClass().getClassLoader().getResource(text));
    button = new JButton(icon);
    button.setPreferredSize(new Dimension(40, 40));
    button.setContentAreaFilled(false);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.addActionListener(this);
    panel.add(button);
    
    return button;
  }

  /**
   * Create and add a JComboBox to the given panel.
   * 
   * @param list The given list for the JComboBox.
   * @param panel The panel to add the JComboBox.
   * @return The JComboBox created.
   */
  private JComboBox<String> addComboBox(final String[] list, final JPanel panel)
  {
    JComboBox<String> dropDown;
    
    dropDown = new JComboBox<String>(list);
    dropDown.setSelectedIndex(-1);
    dropDown.setEnabled(true);
    dropDown.addActionListener(this);
    panel.add(dropDown);
    
    return dropDown;
  }

  /**
   * Create and add a JLabel to the given panel.
   * 
   * @param text The given text for the JLabel.
   * @param panel The given panel to add the JLabel to.
   */
  private void addLabel(final String text, final JPanel panel)
  {
    JLabel label;
    
    label = new JLabel(text);
    label.setFont(new Font(Font.DIALOG, Font.PLAIN, 17));
    panel.add(label);
  }
}
