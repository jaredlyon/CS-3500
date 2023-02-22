import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Represents the test module for the View module.
 */
public class SetGameTextViewTest {

  private SetThreeGameModel startGameTest;
  private List<Card> startDeck = new ArrayList<Card>();

  @Before
  public void init() {
    // creates an object for startGameWithDeck
    this.startGameTest = new SetThreeGameModel();
    // creates a deck for the above object
    this.startDeck.add(new Card(1, "full", "diamond"));
    this.startDeck.add(new Card(1, "full", "squiggle"));
    this.startDeck.add(new Card(1, "full", "oval"));
    this.startDeck.add(new Card(1, "full", "squiggle"));
    this.startDeck.add(new Card(2, "full", "squiggle"));
    this.startDeck.add(new Card(3, "full", "oval"));
    this.startDeck.add(new Card(1, "full", "diamond"));
    this.startDeck.add(new Card(1, "full", "squiggle"));
    this.startDeck.add(new Card(1, "full", "oval"));
    this.startDeck.add(new Card(3, "striped", "diamond"));
    this.startDeck.add(new Card(3, "striped", "squiggle"));
    this.startDeck.add(new Card(3, "striped", "oval"));
    this.startDeck.add(new Card(2, "empty", "oval"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor() {
    SetGameTextView invalidView = new SetGameTextView(null);
  }

  @Test
  public void testValidConstructor() {
    SetGameTextView validView = new SetGameTextView(startGameTest);
    assertFalse(validView.state == null);
  }

  @Test
  public void testToString() {
    SetGameTextView validView = new SetGameTextView(startGameTest);
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    assertEquals("1FD 1FQ 1FO\n"
                    + "1FQ 2FQ 3FO\n"
                    + "1FD 1FQ 1FO",
            validView.toString());
  }
}
