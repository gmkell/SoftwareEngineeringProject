package gui;

import java.awt.Color;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import math.Filterable;

import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import recipe.Ingredients;
import recipe.Recipe;
import recipe.Meal;

/**
 * 
 * @author Emil Hofstetter
 *
 * @param <F> The type of the filterable items.
 */
public class SearchWindow<F extends Filterable> implements ActionListener
{
  private  static final String BOX_ACTION = "ingChange";
  private static final String RESET_ACTION = "reset";
  private static final String DELIM = "_";
  private static final String FORMAT = "%s%s%d";
  private static final String USER_DIR = "user.dir";
  private JFrame frame = new JFrame();
  private Container pane;
  private JPanel boxPanel = new JPanel();
  private List<F> items;
  private DefaultListModel<F> model = new DefaultListModel<>();
  private JList<F> jList = new JList<>(model);
  private List<JComboBox<Ingredients>> boxList = new ArrayList<>();
  
  /**
   * Constructor.
   * 
   * @param items The filterable items.
   * @param title The title of the window.
   * @param subtitle The subtitle of the pane.
   * 
   */
  public SearchWindow(
      final List<F> items, 
      final String title, 
      final String subtitle
  )
  {
    this.items = items;
    frame.setTitle("Recipe Search");
    frame.setSize(1420,900);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    frame.setLocationRelativeTo(null); 
    frame.setResizable(true);
    
    //components
    pane = frame.getContentPane();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    
    boxPanel .setLayout(new FlowLayout(FlowLayout.LEFT));

    boxPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title));
    boxPanel.setPreferredSize(new Dimension(200, 100));
    
    JButton button = new JButton("Reset");

    button.setActionCommand(RESET_ACTION);
    button.addActionListener(this);
    boxPanel.add(button);
    
    JComboBox<Ingredients> box = new JComboBox<Ingredients>(Ingredients.getAvailableIngredients()
        .values()
        .toArray(Recipe.newArray(Ingredients.getAvailableIngredients().size()))
    );
    boxList.add(box);
    box.setActionCommand(String.format(FORMAT, BOX_ACTION, DELIM, 0));
    box.addActionListener(this);
    box.setPreferredSize(new Dimension(150, 24));
    box.setSelectedIndex(-1);
    boxPanel.add(box);
    pane.add(boxPanel);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    jList.setCellRenderer(new RecipeRenderer());

    JScrollPane scrollable = new JScrollPane(this.jList);  
    scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    scrollable.setPreferredSize(new Dimension(350,150));
    scrollable.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), subtitle));
    panel.add(scrollable);
    pane.add(panel);

    
    
    
    frame.setVisible(true);
  }

  /**
   * Helper class to render the SortedIngredients correctly formatted.
   * 
   * @author Emil Hofstetter
   * @version 1.0.0
   * 
   */
  private class RecipeRenderer extends JLabel 
      implements ListCellRenderer<F>
  {
    private static final long serialVersionUID = 1L;

    public Component getListCellRendererComponent(
        final JList<? extends F> renderList,           
        final F value,            
        final int index,               
        final boolean isSelected,      
        final boolean cellHasFocus
    )    
    {
      setText(value.toString());
      setOpaque(true);
      if (isSelected) 
      {
        setBackground(renderList.getSelectionBackground());
        setForeground(renderList.getSelectionForeground());
      } else 
      {
        setBackground(renderList.getBackground());
        setForeground(renderList.getForeground());
      }

      return this;
    }
  }
  
  /**
   * 
   * @param args
   */
  public static void main(final String[] args) 
  {
    List<Recipe> recipes = findRecipes();
    new SearchWindow<>(recipes, "Recipe Search", "Recipes");
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    switch (e.getActionCommand().split(DELIM)[0])
    {
      case(BOX_ACTION):
        model.removeAllElements();
        Ingredients[] ings = Recipe.newArray(this.boxList.size());
        ings = this.boxList
            .stream()
            .map((box) -> box.getModel().getElementAt(box.getSelectedIndex()))
            .toList()
            .toArray(ings);
        
        for (F item : items)
        {
          if (item.all(ings))
          {
            model.addElement(item);
          }
        }

        int index = Integer.parseInt(e.getActionCommand().split(DELIM)[1]);
        if (
            index == boxList.size() - 1 
            && boxList.get(boxList.size() - 1).getSelectedIndex() != -1
        ) 
        {
          JComboBox<Ingredients> box = 
              new JComboBox<Ingredients>(Ingredients.getAvailableIngredients()
              .values()
              .toArray(Recipe.newArray(Ingredients.getAvailableIngredients().size()))
          );
          boxList.add(box);
          box.setActionCommand(String.format(FORMAT, BOX_ACTION, DELIM,boxList.size() - 1));
          box.addActionListener(this);
          box.setPreferredSize(new Dimension(150, 24));
          box.setSelectedIndex(-1);
          boxPanel.add(box);
          boxPanel.validate();
        }
            
        break;
        
      case (RESET_ACTION):
        this.model.removeAllElements();
        for (JComboBox<Ingredients> box : boxList) 
        {
          boxPanel.remove(box);
          boxPanel.validate();
        }
        boxList.clear();
        
        JComboBox<Ingredients> box = 
            new JComboBox<Ingredients>(Ingredients.getAvailableIngredients()
            .values()
            .toArray(Recipe.newArray(Ingredients.getAvailableIngredients().size()))
        );
        boxList.add(box);
        box.setActionCommand(String.format(FORMAT, BOX_ACTION, DELIM, boxList.size() - 1));
        box.addActionListener(this);
        box.setPreferredSize(new Dimension(150, 24));
        box.setSelectedIndex(-1);
        boxPanel.add(box);
        boxPanel.validate();
        break;

      default:
        break;
    }
  }
  
  /**
   * Deserializes and gets all Recipes in the working directory.
   * 
   * @return All Recipes in the working directory.
   */
  public static List<Recipe> findRecipes()
  {
    Path workDir = Paths.get(System.getProperty(USER_DIR));
    List<Recipe> recipes = new ArrayList<>();
    System.out.println(workDir);
    
    try 
    {
      recipes = Files.walk(workDir)
          .filter((p) -> !Files.isDirectory(p))
          .filter((p) -> p.toString().toLowerCase().endsWith("rcp"))
          .map((p) -> p.toFile())
          .map((f) -> 
          {
            try
            {
              return Recipe.deserialize(f);
            }
            catch (IOException e)
            {
              e.printStackTrace();
              return null;
            }
          })
          .filter((r) -> r != null)
          .collect(Collectors.toList());
    } catch (IOException ioe) 
    {
      ioe.printStackTrace();
    }
    
    return recipes;
  }

  /**
   * Deserializes and gets all Meals in the working directory.
   * 
   * @return All Meals in the working directory.
   */
  public static List<Meal> findMeals()
  {
    Path workDir = Paths.get(System.getProperty(USER_DIR));
    List<Meal> recipes = new ArrayList<>();
    System.out.println(workDir);
    
    try 
    {
      recipes = Files.walk(workDir)
          .filter((p) -> !Files.isDirectory(p))
          .filter((p) -> p.toString().toLowerCase().endsWith("mel"))
          .map((p) -> p.toFile())
          .map((f) -> 
          {
            try
            {
              return Meal.deserialize(f);
            }
            catch (IOException e)
            {
              e.printStackTrace();
              return null;
            }
          })
          .filter((r) -> r != null)
          .collect(Collectors.toList());
    } catch (IOException ioe) 
    {
      ioe.printStackTrace();
    }
    
    return recipes;
  }
}
