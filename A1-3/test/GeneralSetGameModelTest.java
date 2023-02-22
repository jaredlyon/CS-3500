import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Generates the test suite for GeneralSet.
 */
public class GeneralSetGameModelTest extends ASetGameModelTest {
  @Override
  protected SetGameModel generateModel() {
    return new GeneralSetGameModel();
  }

  @Test
  public void testBoardGrowsOnStartWithNoSet() {
    // tests on GeneralSet (should grow)
    List<Card> deck1 = new ArrayList<>();

    // 1ED 2EQ 3ED 2ED 1FO 3SQ
    Card card1 = new Card(1, "empty", "diamond");
    Card card2 = new Card(2, "empty", "squiggle");
    Card card3 = new Card(3, "empty", "diamond");
    Card card4 = new Card(2, "empty", "diamond");
    Card card5 = new Card(1, "full", "oval");
    Card card6 = new Card(3, "striped", "squiggle");
    deck1.add(card1);
    deck1.add(card2);
    deck1.add(card3);
    deck1.add(card4);
    deck1.add(card5);
    deck1.add(card6);

    SetGameModel generalModel = this.generateModel();
    generalModel.startGameWithDeck(deck1, 1, 3);

    assertEquals(2, generalModel.getHeight());
    assertEquals(3, generalModel.getWidth());

    // tests on SetThree (should not grow)
    List<Card> deck2 = new ArrayList<>();

    // 1ED 2EQ 3ED 2ED 1FO 3SQ
    Card card7 = new Card(1, "empty", "diamond");
    Card card8 = new Card(2, "empty", "squiggle");
    Card card9 = new Card(3, "empty", "diamond");
    Card card10 = new Card(2, "empty", "diamond");
    Card card11 = new Card(1, "full", "oval");
    Card card12 = new Card(3, "striped", "squiggle");
    Card card13 = new Card(2, "empty", "diamond");
    Card card14 = new Card(1, "full", "oval");
    Card card15 = new Card(3, "striped", "squiggle");
    deck2.add(card7);
    deck2.add(card8);
    deck2.add(card9);
    deck2.add(card10);
    deck2.add(card11);
    deck2.add(card12);
    deck2.add(card13);
    deck2.add(card14);
    deck2.add(card15);

    SetGameModel threeModel = this.generateModel();
    threeModel.startGameWithDeck(deck2, 3, 3);

    assertEquals(3, threeModel.getHeight());
    assertEquals(3, threeModel.getWidth());
  }

  @Test
  public void testValidStartingBoardWithValidDimensions() {
    List<Card> deck = new ArrayList<>();

    // 1ED 2ED 3ED
    // 2SD 3SO 2FO
    // 1FQ 2EQ 1EQ
    // 2FQ 3SQ 2FD
    // 3EQ 1SD 3FD
    // 3EO 1EO 2SO
    Card card1 = new Card(1, "empty", "diamond");
    Card card2 = new Card(2, "empty", "diamond");
    Card card3 = new Card(3, "empty", "diamond");
    Card card4 = new Card(2, "striped", "diamond");
    Card card5 = new Card(3, "striped", "oval");
    Card card6 = new Card(2, "full", "oval");
    Card card7 = new Card(1, "full", "squiggle");
    Card card8 = new Card(2, "empty", "squiggle");
    Card card9 = new Card(1, "empty", "squiggle");
    Card card10 = new Card(2, "full", "squiggle");
    Card card11 = new Card(3, "striped", "squiggle");
    Card card12 = new Card(2, "full", "diamond");
    Card card13 = new Card(3, "empty", "squiggle");
    Card card14 = new Card(1, "striped", "diamond");
    Card card15 = new Card(3, "full", "diamond");
    Card card16 = new Card(3, "empty", "oval");
    Card card17 = new Card(1, "empty", "oval");
    Card card18 = new Card(2, "striped", "oval");
    deck.add(card1);
    deck.add(card2);
    deck.add(card3);
    deck.add(card4);
    deck.add(card5);
    deck.add(card6);
    deck.add(card7);
    deck.add(card8);
    deck.add(card9);
    deck.add(card10);
    deck.add(card11);
    deck.add(card12);
    deck.add(card13);
    deck.add(card14);
    deck.add(card15);
    deck.add(card16);
    deck.add(card17);
    deck.add(card18);

    SetGameModel model = this.generateModel();
    model.startGameWithDeck(deck, 1, 4);

    assertEquals(1, model.getHeight());
    assertEquals(4, model.getWidth());
  }

  @Test
  public void testGeneralSetModelAddsRowOnClaimSetWhenNoValidSetsLeft() {
    List<Card> deck = new ArrayList<>();
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);
    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    // 1ED 2ED 3ED - set to be claimed
    // 2SD 3SO 2FO - not a set
    // 1FQ 2EQ 1EQ - should be added by claimSet
    Card card1 = new Card(1, "empty", "diamond");
    Card card2 = new Card(2, "empty", "diamond");
    Card card3 = new Card(3, "empty", "diamond");
    Card card4 = new Card(2, "striped", "diamond");
    Card card5 = new Card(3, "striped", "oval");
    Card card6 = new Card(2, "full", "oval");
    Card card7 = new Card(1, "full", "squiggle");
    Card card8 = new Card(2, "empty", "squiggle");
    Card card9 = new Card(1, "empty", "squiggle");
    deck.add(card1);
    deck.add(card2);
    deck.add(card3);
    deck.add(card4);
    deck.add(card5);
    deck.add(card6);
    deck.add(card7);
    deck.add(card8);
    deck.add(card9);

    SetGameModel model = this.generateModel();
    model.startGameWithDeck(deck, 1, 3);

    assertEquals(1, model.getHeight());
    assertEquals(3, model.getWidth());
    assertEquals("1ED", model.getCardAtCoord(coord1).toString());
    assertEquals("2ED", model.getCardAtCoord(coord2).toString());
    assertEquals("3ED", model.getCardAtCoord(coord3).toString());
    assertEquals(0, model.getScore());

    model.claimSet(coord1, coord2, coord3);

    assertEquals(1, model.getScore());
    assertEquals(2, model.getHeight());
    assertEquals(3, model.getWidth());
    assertEquals("2SD", model.getCardAtCoord(coord1).toString());
    assertEquals("3SO", model.getCardAtCoord(coord2).toString());
    assertEquals("2FO", model.getCardAtCoord(coord3).toString());
    assertEquals("1FQ", model.getCardAtCoord(coord4).toString());
    assertEquals("2EQ", model.getCardAtCoord(coord5).toString());
    assertEquals("1EQ", model.getCardAtCoord(coord6).toString());
  }

  @Test
  public void testGeneralSetModelDoesNotAddRowOnClaimSetWhenNoValidSetsLeft() {
    List<Card> deck = new ArrayList<>();
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    // 1ED 2ED 3ED - set to be claimed
    // 2SD 3SO 1SQ - a set
    // 1FQ 2EQ 1EQ - should be added by claimSet
    Card card1 = new Card(1, "empty", "diamond");
    Card card2 = new Card(2, "empty", "diamond");
    Card card3 = new Card(3, "empty", "diamond");
    Card card4 = new Card(2, "striped", "diamond");
    Card card5 = new Card(3, "striped", "oval");
    Card card6 = new Card(1, "striped", "squiggle");
    Card card7 = new Card(1, "full", "squiggle");
    Card card8 = new Card(2, "empty", "squiggle");
    Card card9 = new Card(1, "empty", "squiggle");
    deck.add(card1);
    deck.add(card2);
    deck.add(card3);
    deck.add(card4);
    deck.add(card5);
    deck.add(card6);
    deck.add(card7);
    deck.add(card8);
    deck.add(card9);

    SetGameModel model = this.generateModel();
    model.startGameWithDeck(deck, 1, 3);

    assertEquals(1, model.getHeight());
    assertEquals(3, model.getWidth());
    assertEquals("1ED", model.getCardAtCoord(coord1).toString());
    assertEquals("2ED", model.getCardAtCoord(coord2).toString());
    assertEquals("3ED", model.getCardAtCoord(coord3).toString());
    assertEquals(0, model.getScore());

    model.claimSet(coord1, coord2, coord3);

    assertEquals(1, model.getScore());
    assertEquals(1, model.getHeight());
    assertEquals(3, model.getWidth());
    assertEquals("2SD", model.getCardAtCoord(coord1).toString());
    assertEquals("3SO", model.getCardAtCoord(coord2).toString());
    assertEquals("1SQ", model.getCardAtCoord(coord3).toString());
  }

  @Test
  public void testNonThreeInstantiation() {
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
  public void testGeneralModelRejectsBadArgs() {
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

    model.startGameWithDeck(deck, -1, 4);
    model.startGameWithDeck(deck, 1, -4);
    model.startGameWithDeck(deck, 4, 4);
    model.startGameWithDeck(null, 1, 4);
  }
}
