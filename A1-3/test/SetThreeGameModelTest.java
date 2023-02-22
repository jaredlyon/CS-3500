import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Generates the test suite for SetThree.
 */
public class SetThreeGameModelTest extends ASetGameModelTest {
  @Override
  protected SetGameModel generateModel() {
    return new SetThreeGameModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetThreeRejectsNonThreeGrid() {
    SetGameModel model = this.generateModel();
    List<Card> deck = new ArrayList<Card>();
    deck.add(new Card(1, "full", "diamond"));
    deck.add(new Card(1, "full", "squiggle"));
    deck.add(new Card(1, "full", "oval"));
    deck.add(new Card(1, "full", "squiggle"));
    deck.add(new Card(2, "full", "squiggle"));
    deck.add(new Card(3, "full", "oval"));
    deck.add(new Card(2, "full", "diamond"));
    deck.add(new Card(2, "full", "squiggle"));
    deck.add(new Card(2, "full", "oval"));
    deck.add(new Card(3, "striped", "diamond"));
    deck.add(new Card(3, "striped", "squiggle"));
    deck.add(new Card(3, "striped", "oval"));
    deck.add(new Card(2, "empty", "oval"));

    model.startGameWithDeck(deck, 1, 4);

    assertEquals(1, model.getHeight());
    assertEquals(4, model.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetThreeRejectsSmallDeck() {
    SetGameModel model = this.generateModel();
    List<Card> deck = new ArrayList<Card>();
    deck.add(new Card(1, "full", "diamond"));
    deck.add(new Card(1, "full", "squiggle"));
    deck.add(new Card(1, "full", "oval"));
    deck.add(new Card(1, "full", "squiggle"));
    deck.add(new Card(2, "full", "squiggle"));

    model.startGameWithDeck(deck, 3, 3);
  }
}
