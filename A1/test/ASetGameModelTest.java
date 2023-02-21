import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;

import static org.junit.Assert.assertEquals;

/**
 * Represents the test suite for the SetGameModel.
 */
public class ASetGameModelTest {
  protected SetGameModel illegalStateTestObjectThree;
  protected SetGameModel startGameTestThree;
  protected List<Card> startDeckThree = new ArrayList<Card>();
  protected SetGameModel illegalStateTestObjectGeneral;
  protected SetGameModel startGameTestGeneral;
  protected List<Card> startDeckGeneral = new ArrayList<Card>();

  @Before
  public void init() {
    // creates an object for the IllegalStateExceptions that are thrown before game start
    this.illegalStateTestObjectThree = this.generateModel("three");
    this.illegalStateTestObjectGeneral = this.generateModel("general");

    // creates an object for startGameWithDeck
    this.startGameTestThree = this.generateModel("three");
    this.startGameTestGeneral = this.generateModel("general");

    // creates a decks for the above objects
    this.startDeckThree.add(new Card(1, "full", "diamond"));
    this.startDeckThree.add(new Card(1, "full", "squiggle"));
    this.startDeckThree.add(new Card(1, "full", "oval"));
    this.startDeckThree.add(new Card(1, "full", "squiggle"));
    this.startDeckThree.add(new Card(2, "full", "squiggle"));
    this.startDeckThree.add(new Card(3, "full", "oval"));
    this.startDeckThree.add(new Card(1, "full", "diamond"));
    this.startDeckThree.add(new Card(1, "full", "squiggle"));
    this.startDeckThree.add(new Card(1, "full", "oval"));
    this.startDeckThree.add(new Card(3, "striped", "diamond"));
    this.startDeckThree.add(new Card(3, "striped", "squiggle"));
    this.startDeckThree.add(new Card(3, "striped", "oval"));
    this.startDeckThree.add(new Card(2, "empty", "oval"));

    // creates a deck for the above object
    this.startDeckGeneral.add(new Card(1, "full", "diamond"));
    this.startDeckGeneral.add(new Card(1, "full", "squiggle"));
    this.startDeckGeneral.add(new Card(1, "full", "oval"));
    this.startDeckGeneral.add(new Card(1, "full", "squiggle"));
    this.startDeckGeneral.add(new Card(2, "full", "squiggle"));
    this.startDeckGeneral.add(new Card(3, "full", "oval"));
    this.startDeckGeneral.add(new Card(2, "full", "diamond"));
    this.startDeckGeneral.add(new Card(2, "full", "squiggle"));
    this.startDeckGeneral.add(new Card(2, "full", "oval"));
    this.startDeckGeneral.add(new Card(3, "striped", "diamond"));
    this.startDeckGeneral.add(new Card(3, "striped", "squiggle"));
    this.startDeckGeneral.add(new Card(3, "striped", "oval"));
    this.startDeckGeneral.add(new Card(2, "empty", "oval"));
  }

  /**
   * Generates a model for testing.
   * @param str - the type of model needed
   * @return - a SetGameModel
   */
  protected SetGameModel generateModel(String str) throws IllegalArgumentException {
    if (str.equals("three")) {
      return new SetThreeGameModel();
    } else if (str.equals("general")) {
      return new GeneralSetGameModel();
    } else {
      throw new IllegalArgumentException("Must make a game of General or Three.");
    }
  }

  @Test
  public void testConstructor() {
    SetGameModel testConstructorThree = this.generateModel("three");
    SetGameModel testConstructorGeneral = this.generateModel("general");

    // must start the game to check params
    testConstructorThree.startGameWithDeck(this.startDeckThree, 3, 3);
    testConstructorGeneral.startGameWithDeck(this.startDeckGeneral, 2, 4);

    assertEquals(0, testConstructorThree.getScore());
    assertEquals(3, testConstructorThree.getWidth());
    assertEquals(3, testConstructorThree.getHeight());
    assertEquals(0, testConstructorGeneral.getScore());
    assertEquals(4, testConstructorGeneral.getWidth());
    assertEquals(2, testConstructorGeneral.getHeight());
  }

  @Test
  public void testClaimSet() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 2, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    assertEquals("1FD", this.startGameTestThree.getCardAtCoord(coord1).toString());
    assertEquals("1FQ", this.startGameTestThree.getCardAtCoord(coord2).toString());
    assertEquals("1FO", this.startGameTestThree.getCardAtCoord(coord3).toString());
    assertEquals(0, this.startGameTestThree.getScore());

    assertEquals("1FD", this.startGameTestGeneral.getCardAtCoord(coord1).toString());
    assertEquals("1FQ", this.startGameTestGeneral.getCardAtCoord(coord2).toString());
    assertEquals("1FO", this.startGameTestGeneral.getCardAtCoord(coord3).toString());
    assertEquals(0, this.startGameTestGeneral.getScore());

    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);

    assertEquals("3SD", this.startGameTestThree.getCardAtCoord(coord1).toString());
    assertEquals("3SQ", this.startGameTestThree.getCardAtCoord(coord2).toString());
    assertEquals("3SO", this.startGameTestThree.getCardAtCoord(coord3).toString());
    assertEquals(1, this.startGameTestThree.getScore());

    assertEquals("2FD", this.startGameTestGeneral.getCardAtCoord(coord1).toString());
    assertEquals("2FQ", this.startGameTestGeneral.getCardAtCoord(coord2).toString());
    assertEquals("2FO", this.startGameTestGeneral.getCardAtCoord(coord3).toString());
    assertEquals(1, this.startGameTestGeneral.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidClaimSetArgument() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    Coord coord1 = new Coord(4, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    this.startGameTestThree.claimSet(coord4, coord5, coord6);
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    this.startGameTestGeneral.claimSet(coord4, coord5, coord6);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidClaimSetState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);
    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
  }

  @Test
  public void testStartGameWithDeck() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 1, 3);

    assertEquals(3, this.startGameTestThree.getWidth());
    assertEquals(3, this.startGameTestThree.getHeight());
    assertEquals(0, this.startGameTestThree.getScore());

    assertEquals(3, this.startGameTestGeneral.getWidth());
    assertEquals(1, this.startGameTestGeneral.getHeight());
    assertEquals(0, this.startGameTestGeneral.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartGameWithDeck() {
    List<Card> badDeck = new ArrayList<Card>();

    // SetThree rejects non-3x3 grids here
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 4, 3);
    this.startGameTestThree.startGameWithDeck(badDeck, 3, 3);

    this.startGameTestGeneral.startGameWithDeck(badDeck, 3, 3);
  }

  @Test
  public void testGetWidth() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    assertEquals(3, this.startGameTestThree.getWidth());

    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 4);
    assertEquals(4, this.startGameTestGeneral.getWidth());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetWidth() {
    this.illegalStateTestObjectThree.getWidth();
    this.illegalStateTestObjectGeneral.getWidth();
  }

  @Test
  public void testGetHeight() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    assertEquals(3, this.startGameTestThree.getHeight());

    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 4, 3);
    assertEquals(4, this.startGameTestGeneral.getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetHeight() {
    this.illegalStateTestObjectThree.getHeight();
    this.illegalStateTestObjectGeneral.getHeight();
  }

  @Test
  public void testGetScore() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    assertEquals(0, this.startGameTestThree.getScore());
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 2, 3);
    assertEquals(0, this.startGameTestGeneral.getScore());

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(1, this.startGameTestThree.getScore());
    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(2, this.startGameTestThree.getScore());

    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(1, this.startGameTestGeneral.getScore());
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(2, this.startGameTestGeneral.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetScore() {
    this.illegalStateTestObjectThree.getScore();
    this.illegalStateTestObjectGeneral.getScore();
  }

  @Test
  public void testAnySetsPresent() {
    // always true here since a 3x3 grid will always contain a SET
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    assertEquals(true, this.startGameTestThree.anySetsPresent());
    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestThree.anySetsPresent());
    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestThree.anySetsPresent());
    assertEquals(true, this.startGameTestGeneral.anySetsPresent());
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestGeneral.anySetsPresent());
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestGeneral.anySetsPresent());

    // should be false here with a bad deck
    ArrayList<Card> badDeck = new ArrayList<Card>();
    badDeck.add(new Card(1, "striped", "diamond"));
    badDeck.add(new Card(1, "striped", "oval"));
    badDeck.add(new Card(1, "full", "diamond"));

    this.startGameTestGeneral.startGameWithDeck(badDeck, 1, 3);
    assertEquals(false, this.startGameTestGeneral.anySetsPresent());
  }

  @Test
  public void testIsValidSet() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    assertEquals(true, this.startGameTestThree.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestThree.isValidSet(coord4, coord5, coord6));
    assertEquals(true, this.startGameTestGeneral.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestGeneral.isValidSet(coord4, coord5, coord6));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidIsValidSetState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 0);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    assertEquals(false, this.startGameTestThree.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestThree.isValidSet(coord4, coord5, coord6));
    assertEquals(false, this.startGameTestGeneral.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestGeneral.isValidSet(coord4, coord5, coord6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIsValidSetArgument() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(4, 1);
    Coord coord3 = new Coord(0, 2);

    Coord coord4 = new Coord(1, 4);
    Coord coord5 = new Coord(1, 1);
    Coord coord6 = new Coord(1, 2);

    Coord coord7 = null;
    Coord coord8 = new Coord(1, 1);
    Coord coord9 = new Coord(1, 2);

    assertEquals(true, this.startGameTestThree.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestThree.isValidSet(coord4, coord5, coord6));
    assertEquals(false, this.startGameTestThree.isValidSet(coord7, coord8, coord9));
    assertEquals(true, this.startGameTestGeneral.isValidSet(coord1, coord2, coord3));
    assertEquals(false, this.startGameTestGeneral.isValidSet(coord4, coord5, coord6));
    assertEquals(false, this.startGameTestGeneral.isValidSet(coord7, coord8, coord9));
  }

  @Test
  public void getCardAtCoord() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    Coord coord1 = new Coord(0, 0);

    assertEquals("1FD", this.startGameTestThree.getCardAtCoord(coord1).toString());
    assertEquals("1FD", this.startGameTestThree.getCardAtCoord(0, 0).toString());
    assertEquals("1FD", this.startGameTestGeneral.getCardAtCoord(coord1).toString());
    assertEquals("1FD", this.startGameTestGeneral.getCardAtCoord(0, 0).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetCardAtCoordState() {
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);

    assertEquals("1FD",
            this.startGameTestThree.getCardAtCoord(coord1).toString());
    assertEquals("2FQ",
            this.startGameTestGeneral.getCardAtCoord(coord2).toString());
    assertEquals("1FD",
            this.startGameTestGeneral.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ",
            this.startGameTestThree.getCardAtCoord(0, 1).toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGetCardAtCoordArgument() {
    Coord coord1 = new Coord(0, 4);
    Coord coord2 = new Coord(4, 1);
    Coord coord3 = null;

    assertEquals("1FD",
            this.startGameTestThree.getCardAtCoord(coord1).toString());
    assertEquals("2FQ",
            this.startGameTestThree.getCardAtCoord(coord2).toString());
    assertEquals("1FD",
            this.startGameTestThree.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ",
            this.startGameTestThree.getCardAtCoord(0, 1).toString());
    assertEquals("2FQ",
            this.startGameTestThree.getCardAtCoord(coord3).toString());
    assertEquals("1FD",
            this.startGameTestGeneral.getCardAtCoord(coord1).toString());
    assertEquals("2FQ",
            this.startGameTestGeneral.getCardAtCoord(coord2).toString());
    assertEquals("1FD",
            this.startGameTestGeneral.getCardAtCoord(0, 0).toString());
    assertEquals("2FQ",
            this.startGameTestGeneral.getCardAtCoord(0, 1).toString());
    assertEquals("2FQ",
            this.startGameTestGeneral.getCardAtCoord(coord3).toString());
  }

  @Test
  public void testIsGameOver() {
    this.startGameTestThree.startGameWithDeck(this.startDeckThree, 3, 3);
    this.startGameTestGeneral.startGameWithDeck(this.startDeckGeneral, 3, 3);

    assertEquals(this.startGameTestThree.isGameOver(), false);
    assertEquals(this.startGameTestGeneral.isGameOver(), false);

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(false, this.startGameTestThree.isGameOver()); // lastTurn = true
    this.startGameTestThree.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestThree.isGameOver()); // end game

    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(false, this.startGameTestGeneral.isGameOver()); // lastTurn = true
    this.startGameTestGeneral.claimSet(coord1, coord2, coord3);
    assertEquals(true, this.startGameTestGeneral.isGameOver()); // end game
  }

  @Test
  public void testGetCompleteDeck() {
    List<Card> completeDeck1 = this.startGameTestThree.getCompleteDeck();
    assertEquals(27, completeDeck1.size());
    List<Card> completeDeck2 = this.startGameTestGeneral.getCompleteDeck();
    assertEquals(27, completeDeck2.size());
    // should always be 27 because there are only 27 possible unique cards
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

    SetGameModel generalModel = this.generateModel("general");
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

    SetGameModel threeModel = this.generateModel("three");
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

    SetGameModel model = this.generateModel("general");
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

    SetGameModel model = this.generateModel("general");
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

    SetGameModel model = this.generateModel("general");
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
}
