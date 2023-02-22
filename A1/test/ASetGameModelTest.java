import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Contains all abstracted tests for each submodel.
 */
public abstract class ASetGameModelTest {
  protected SetGameModel illegalStateTestObject;
  protected SetGameModel startGameTest;
  protected List<Card> startDeck = new ArrayList<Card>();

  abstract SetGameModel generateModel();

  @Before
  public void init() {
    // creates an object for the IllegalStateExceptions that are thrown before game start
    this.illegalStateTestObject = this.generateModel();

    // creates an object for startGameWithDeck
    this.startGameTest = this.generateModel();

    // creates a deck for the above object
    this.startDeck.add(new Card(1, "full", "diamond"));
    this.startDeck.add(new Card(1, "full", "squiggle"));
    this.startDeck.add(new Card(1, "full", "oval"));
    this.startDeck.add(new Card(1, "full", "squiggle"));
    this.startDeck.add(new Card(2, "full", "squiggle"));
    this.startDeck.add(new Card(3, "full", "oval"));
    this.startDeck.add(new Card(2, "full", "diamond"));
    this.startDeck.add(new Card(2, "full", "squiggle"));
    this.startDeck.add(new Card(2, "full", "oval"));
    this.startDeck.add(new Card(3, "striped", "diamond"));
    this.startDeck.add(new Card(3, "striped", "squiggle"));
    this.startDeck.add(new Card(3, "striped", "oval"));
    this.startDeck.add(new Card(2, "empty", "oval"));
  }

  @Test
  public void testConstructor() {
    SetGameModel testConstructor= this.generateModel();

    // must start the game to check params
    testConstructor.startGameWithDeck(this.startDeck, 3, 3);

    assertEquals(0, testConstructor.getScore());
    assertEquals(3, testConstructor.getWidth());
    assertEquals(3, testConstructor.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeck() {
    SetGameModel testConstructor= this.generateModel();
    testConstructor.startGameWithDeck(null, 3, 3);
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

    // SetThree rejects non-3x3 grids here
    this.startGameTest.startGameWithDeck(this.startDeck, 4, 3);
    this.startGameTest.startGameWithDeck(badDeck, 3, 3);

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
    // always true here since a 3x3 grid will always contain a SET
    this.startGameTest.startGameWithDeck(this.startDeck, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    assertEquals(true, this.startGameTest.anySetsPresent());
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTest.anySetsPresent());
    this.startGameTest.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTest.anySetsPresent());

    // will always be true because a 3x3 grid will always have a set
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

    assertEquals("1FD",
            this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("2FQ",
            this.startGameTest.getCardAtCoord(0, 1).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetCardAtCoordArgument() {
    Coord coord1 = new Coord(0, 4);
    Coord coord2 = new Coord(4, 1);
    Coord coord3 = null;

    assertEquals("1FD",
            this.startGameTest.getCardAtCoord(coord1).toString());
    assertEquals("2FQ",
            this.startGameTest.getCardAtCoord(coord2).toString());
    assertEquals("1FD",
            this.startGameTest.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ",
            this.startGameTest.getCardAtCoord(0, 1).toString());
    assertEquals("2FQ",
            this.startGameTest.getCardAtCoord(coord3).toString());
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
    List<Card> completeDeck1 = this.startGameTest.getCompleteDeck();
    assertEquals(27, completeDeck1.size());
    // should always be 27 because there are only 27 possible unique cards
  }
}
