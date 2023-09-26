package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import recipe.DetailedUtensil;

class TestDetailedUtensil
{

  @Test
  void testToString()
  {
    DetailedUtensil du = new DetailedUtensil("Whisk", "large");
    assertEquals("large Whisk", du.toString());
    assertEquals("large Whisk", du.toString(true));
    assertEquals("large Whisk", du.toString(false));
    du = new DetailedUtensil("Whisk", "");
    assertEquals("Whisk", du.toString());
    assertEquals("Whisk", du.toString(true));
    assertEquals("Whisk", du.toString(false));
  }

  @Test
  void testCompareTo()
  {
    DetailedUtensil du1 = new DetailedUtensil("Whisk", "large");
    DetailedUtensil du2 = new DetailedUtensil("Whisk", "large");
    assertEquals(
        0, du1.compareTo(du2));
  }

}
