import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw03.GeneralSetGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Represents the test module for GeneralSetGameModel.
 */
public class GeneralSetGameModelTest {

  private GeneralSetGameModel illegalStateTestObject;
  private GeneralSetGameModel startGameTest;
  private List<Card> startDeck = new ArrayList<Card>();

  @Before
  public void init() {
    // creates an object for the IllegalStateExceptions that are thrown before game start
    this.illegalStateTestObject = new GeneralSetGameModel();

    // creates an object for startGameWithDeck
    this.startGameTest = new GeneralSetGameModel();
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

  @Test
  public void testConstructor() {
    GeneralSetGameModel testConstructor = new GeneralSetGameModel();

    // must start the game to check params
    testConstructor.startGameWithDeck(this.startDeck, 3, 3);

    assertEquals(0, testConstructor.getScore());
    assertEquals(3, testConstructor.getWidth());
    assertEquals(3, testConstructor.getHeight());
  }

  @Test
  public void testClaimSet() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    assertEquals("1FD", this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("1FQ", this.startGameTest.getCardAtCoord(coord2).toString());
    assertEquals("1FO", this.startGameTest.getCardAtCoord(coord3).toString());
    assertEquals(0, this.startGameTest.getScore());

    this.startGameTest.claimSet(coord1, coord2, coord3);

    assertEquals("3SD", this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("3SQ", this.startGameTest.getCardAtCoord(coord2).toString());
    assertEquals("3SO", this.startGameTest.getCardAtCoord(coord3).toString());
    assertEquals(1, this.startGameTest.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidClaimSetArgument() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(4, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    this.startGameTest.claimSet(coord1, coord2, coord3);
    this.startGameTest.claimSet(coord4, coord5, coord6);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidClaimSetState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);
    this.startGameTest.claimSet(coord1, coord2, coord3);
  }

  @Test
  public void testStartGameWithDeck() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    assertEquals(3, this.startGameTest.getWidth());
    assertEquals(3, this.startGameTest.getHeight());
    assertEquals(0, this.startGameTest.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameWithDeck() {
    List<Card> badDeck = new ArrayList<Card>();
    this.startGameTest.startGameWithDeck(this.startDeck, 4, 3);
    this.startGameTest.startGameWithDeck(badDeck, 3, 3);
  }

  @Test
  public void testGetWidth() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);
    assertEquals(3, this.startGameTest.getWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetWidth() {
    this.illegalStateTestObject.getWidth();
  }

  @Test
  public void testGetHeight() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);
    assertEquals(3, this.startGameTest.getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetHeight() {
    this.illegalStateTestObject.getHeight();
  }

  @Test
  public void testGetScore() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);
    assertEquals(0, this.startGameTest.getScore());

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(1, this.startGameTest.getScore());
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(2, this.startGameTest.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetScore() {
    this.illegalStateTestObject.getScore();
  }

  @Test
  public void testAnySetsPresent() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    assertEquals(true, this.startGameTest.anySetsPresent());
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTest.anySetsPresent());
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTest.anySetsPresent());
    // always true since a 3x3 grid will always contain a SET
  }

  @Test
  public void testIsValidSet() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    assertEquals(true, this.startGameTest.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTest.isValidSet(coord4, coord5, coord6));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidIsValidSetState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    assertEquals(false, this.startGameTest.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTest.isValidSet(coord4, coord5, coord6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIsValidSetArgument() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(4, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 4);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    Coord coord7 = null;
    Coord coord8 = new Coord(1, 1);
    Coord coord9 = new Coord(1, 2);

    assertEquals(true, this.startGameTest.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTest.isValidSet(coord4, coord5, coord6));
    assertEquals(false, this.startGameTest.isValidSet(coord7, coord8, coord9));
  }

  @Test
  public void getCardAtCoord() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);

    assertEquals("1FD", this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("1FD", this.startGameTest.getCardAtCoord(0, 0).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetCardAtCoordState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);

    assertEquals("1FD", this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("2FQ", this.startGameTest.getCardAtCoord(coord2).toString());
    assertEquals("1FD", this.startGameTest.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ", this.startGameTest.getCardAtCoord(0, 1).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetCardAtCoordArgument() {
    Coord coord1 = new Coord(0, 4);
    Coord coord2 = new Coord(4, 1);
    Coord coord3 = null;

    assertEquals("1FD",this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("2FQ", this.startGameTest.getCardAtCoord(coord2).toString());
    assertEquals("1FD", this.startGameTest.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ", this.startGameTest.getCardAtCoord(0, 1).toString());
    assertEquals("2FQ", this.startGameTest.getCardAtCoord(coord3).toString());
  }

  @Test
  public void testIsGameOver() {
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    assertEquals(this.startGameTest.isGameOver(), false);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(false, this.startGameTest.isGameOver()); // lastTurn = true
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTest.isGameOver()); // end game
  }

  @Test
  public void testGetCompleteDeck() {
    List<Card> completeDeck = this.startGameTest.getCompleteDeck();
    assertEquals(27, completeDeck.size());
    // should always be 27 because there are only 27 possible unique cards
  }

  @Test
  public void testBoardGrowsOnStartWithNoSet() {
    List<Card> deck = new ArrayList<>();

    // 1ED 2EQ 3ED 2ED 1FO 3SQ
    Card card1 = new Card(1, "empty", "diamond");
    Card card2 = new Card(2, "empty", "squiggle");
    Card card3 = new Card(3, "empty", "diamond");
    Card card4 = new Card(2, "empty", "diamond");
    Card card5 = new Card(1, "full", "oval");
    Card card6 = new Card(3, "striped", "squiggle");
    deck.add(card1);
    deck.add(card2);
    deck.add(card3);
    deck.add(card4);
    deck.add(card5);
    deck.add(card6);

    GeneralSetGameModel model = new GeneralSetGameModel();
    model.startGameWithDeck(deck, 1, 3);

    assertEquals(2, model.getHeight());
    assertEquals(3, model.getWidth());
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

    GeneralSetGameModel model = new GeneralSetGameModel();
    model.startGameWithDeck(deck, 1, 4);

    assertEquals(1, model.getHeight());
    assertEquals(4, model.getWidth());
  }
}
