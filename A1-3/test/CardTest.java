import org.junit.Test;

import cs3500.set.model.hw02.Card;

import static org.junit.Assert.assertEquals;

/**
 * Represents the Card test class.
 */
public class CardTest {

  private Card c1 = new Card(1, "empty", "oval");
  private Card c2 = new Card(2, "full", "squiggle");
  private Card c3 = new Card(3, "striped", "diamond");

  @Test
  public void testValidStringConstructor() {
    Card stringTest = new Card(1, "full", "squiggle");
    assertEquals(1, stringTest.count());
    assertEquals("F", stringTest.filling());
    assertEquals("Q", stringTest.shape());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStringConstructor() {
    new Card(4, "empty", "oval");
    new Card(1, "lkjdanrlgak", "oval");
    new Card(1, "empty", "a.kjrnglak.j");
  }

  @Test
  public void testValidIntConstructor() {
    Card intTest = new Card(1, 0, 2);
    assertEquals(2, intTest.count());
    assertEquals("E", intTest.filling());
    assertEquals("D", intTest.shape());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIntConstructor() {
    new Card(4, 1,1);
    new Card(1, 4, 1);
    new Card(1, 1,4);
  }

  @Test
  public void testCount() {
    assertEquals(1, this.c1.count());
    assertEquals(2, this.c2.count());
    assertEquals(3, this.c3.count());
  }

  @Test
  public void testFilling() {
    assertEquals("E", this.c1.filling());
    assertEquals("F", this.c2.filling());
    assertEquals("S", this.c3.filling());
  }

  @Test
  public void testShape() {
    assertEquals("O", this.c1.shape());
    assertEquals("Q", this.c2.shape());
    assertEquals("D", this.c3.shape());
  }

  @Test
  public void testToString() {
    assertEquals("1EO", this.c1.toString());
    assertEquals("2FQ", this.c2.toString());
    assertEquals("3SD", this.c3.toString());
  }
}
