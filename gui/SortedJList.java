package gui;

import java.awt.Color;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListModel;

/**
 * 
 * @author Emil Hofstetter
 * 
 * @param <T>
 *
 */
public class SortedJList<T extends Comparable<T> & gui.SortedJList.Verbose> 
    extends javax.swing.JPanel implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private final String actionNotification;
  private final String deleteNotification;
  private DefaultListModel<RecipeEditor.RC<T>> model =
      new DefaultListModel<>();
  private JList<RecipeEditor.RC<T>> list; 
  private List<ActionListener> actionListeners = 
      new ArrayList<ActionListener>();
  
  /**
   * Constructor.
   * 
   * @param actionNotifiaction
   * @param deleteNotification
   */
  public SortedJList(
      final String actionNotifiaction,
      final String deleteNotification) 
  {
    this.actionNotification = actionNotifiaction;
    this.deleteNotification = deleteNotification;

    this.list = new JList<RecipeEditor.RC<T>>(this.model);

    this.list.setCellRenderer(new CustomRenderer());

    this.setBackground(Color.WHITE);
    
    this.add(list);
  }

  /**
   * Adds an action listener to the component.
   * 
   * @param a The action listener.
   */
  public void addActionListener(final ActionListener a)
  {
    actionListeners.add(a);
  }
  
  private void notifyListeners()
  {
    for (ActionListener a: actionListeners)
    {
      a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionNotification));
    }
  }
  
  /**
   * Returns the underlying list model.
   * 
   * @return the defaultListModel.
   */
  public DefaultListModel<RecipeEditor.RC<T>> getModel()
  {
    return model;
  }
  
  /**
   * Get the underlying list.
   * 
   * @return the JList.
   */
  public JList<RecipeEditor.RC<T>> getList() 
  {
    return list;
  }

  /**
   * Get all utensils in the sorted list.
   * 
   * @return A list of DetailedUtensils.
   */
  public T[] getItems()
  {
    T[] items = newArray(this.model.getSize());
    
    for (int i = 0; i < items.length; i++)
    {
      items[i] = this.model.getElementAt(i).inner();
    }

    return items;
  }

  /**Temporary getter for the sake of testing.
   * @return the defaultListModel.*/
  public RecipeEditor.RC<T>[] getRCItems()
  {
    RecipeEditor.RC<T>[] ingredients = newArray(this.model.getSize());
    
    for (int i = 0; i < ingredients.length; i++)
    {
      ingredients[i] = this.model.getElementAt(i);
    }

    return ingredients;
  }

  @SafeVarargs
  private static <E> E[] newArray(final int length, final E... array)
  {
    return Arrays.copyOf(array, length);
  }

  /**
   * Add a utensil to the List while sorting.
   * 
   * @param item to be added.
   */
  public void addItem(final T item)
  {
    for (int i = 0; i < this.model.getSize(); i++)
    {
      //perform sorting alphabetically.
      if (this.model.getElementAt(i).inner().compareTo(item) > 0)
      {
        this.model.insertElementAt(new RecipeEditor.RC<>(item), i);
        notifyListeners();
        return;
      }
    }
    this.model.addElement(new RecipeEditor.RC<>(item));
    notifyListeners();
  }
  
  
  
  /**
   * Defines how items in the list are rendered.
   * 
   * @author Emil Hofstetter
   */
  private class CustomRenderer extends JLabel 
      implements ListCellRenderer<RecipeEditor.RC<T>>
  {
    private static final long serialVersionUID = 1L;

    public Component getListCellRendererComponent(
        final JList<? extends RecipeEditor.RC<T>> renderList,           
        final RecipeEditor.RC<T> value,            
        final int index,               
        final boolean isSelected,      
        final boolean cellHasFocus
    )    
    {
      String name = value.inner().toString(true);
      setText(name);
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
   * Get the currently selected item.
   * 
   * @return The selected item or null if nothing is selected.
   */
  public RecipeEditor.RC<T> getSelectedItem() 
  {
    int selectedIndex =  this.list.getSelectedIndex();

    if (selectedIndex >= 0) 
    {
      return this.model.getElementAt(selectedIndex);
    }

    return null;
  }
  
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    if (e.getActionCommand().equals(this.deleteNotification))
    {
      int index = this.list.getSelectedIndex();
      if (index >= 0) 
      {
        // Only delete if reference count is low enough
        if (model.getElementAt(index).count() < 2) 
        {
          this.model.removeElementAt(index);
        } 
      }
    }
  }
  
  /**
   * An interface that provides a way to get the verbose representation 
   * of an Object.
   * @author Emil Hofstetter
   *
   */
  public static interface Verbose 
  {
    /**
     * Get the verbose representation of an Object.
     * 
     * @param verbose
     * @return The verbose String representation of an Object.
     */
    String toString(boolean verbose);
  }
}
