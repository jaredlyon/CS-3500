package cs3500.set.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game's Model, or behavior.
 */
public class SetThreeGameModel implements SetGameModel {
  private ArrayList<ArrayList<Card>> cards = new ArrayList<ArrayList<Card>>();
  private List<Card> deck = new ArrayList<Card>();
  private int score = -1;
  private boolean gameOver = false;
  private boolean lastTurn = false;


  /**
   * Constructs a new, blank Game Model.
   */
  public SetThreeGameModel() {
    // Does nothing since startGameWithDeck initializes remaining variables.
  }

  /**
   * Attempts to claim a set of three cards as a SET.
   * @param coord1 - the coordinates of the first card
   * @param coord2 - the coordinates of the second card
   * @param coord3 - the coordinates of the third card
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException if the game has not started or parameters do not make a SET
   */
  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3)
          throws IllegalArgumentException, IllegalStateException {

    // checks if the game has started
    int height = cards.size();
    if (height == 0) {
      throw new IllegalStateException("Game has not been initialized (width check).");
    }

    // checks if the coords are okay
    if (this.isBadCoord(coord1) || this.isBadCoord(coord2) || this.isBadCoord(coord3)) {
      throw new IllegalArgumentException("Invalid coord(s) (claimSet).");
    }

    // checks for a valid set
    if (this.lastTurn && this.isValidSet(coord1, coord2, coord3)) {
      // does not replace cards because there are not enough
      this.score += 1;
      this.gameOver = true;
    } else if (!this.lastTurn && this.isValidSet(coord1, coord2, coord3)) {
      this.cards.get(coord1.row).set(coord1.col, this.deck.remove(0));
      this.cards.get(coord2.row).set(coord2.col, this.deck.remove(0));
      this.cards.get(coord3.row).set(coord3.col, this.deck.remove(0));
      this.score += 1;

      if (this.deck.size() < 3) {
        this.lastTurn = true;
      }
    } else if (!this.isValidSet(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Not a set!");
    }

    this.isGameOver();
  }

  /**
   * Checks if a given coord is 'bad' or 'out of bounds'.
   * @param coord - the coord to be tested
   * @return a boolean representing if the coord is out of expected bounds
   */
  private boolean isBadCoord(Coord coord) {
    return (coord == null || (coord.row > 2 || coord.row < 0
            || coord.col > 2 || coord.col < 0));
  }

  /**
   * Starts a game of SET with given parameters.
   * @param deck - the list of cards in the order they will be played
   * @param height - the height of the board for this game
   * @param width - the width of the board for this game
   * @throws IllegalArgumentException if the grid parameters are not 3x3 or given deck is too small
   */
  @Override
  public void startGameWithDeck(List deck, int height, int width) throws IllegalArgumentException {
    if (height != 3 || width != 3) {
      throw new IllegalArgumentException("Invalid grid arguments.");
    } else if (deck == null || deck.size() < 9) {
      throw new IllegalArgumentException("Not enough cards in deck!");
    }

    this.deck = deck;
    this.score += 1;

    for (int i = 0; i < 3; i++) {
      ArrayList<Card> row = new ArrayList<Card>();
      for (int j = 0; j < 3; j++) {
        row.add(this.deck.remove(0));
      }
      this.cards.add(row);
    }
  }

  /**
   * Gets the width of the current grid.
   * @return an integer representing width
   * @throws IllegalStateException if the game has not started yet
   */
  @Override
  public int getWidth() throws IllegalStateException {
    int height = cards.size();

    if (height == 0) {
      throw new IllegalStateException("Game has not been initialized (width check).");
    } else {
      int width = cards.get(0).size();
      return width;
    }
  }

  /**
   * Gets the height of the current grid.
   * @return an integer representing height
   * @throws IllegalStateException if the game has not started yet
   */
  @Override
  public int getHeight() throws IllegalStateException {
    int height = cards.size();

    if (height == 0) {
      throw new IllegalStateException("Game has not been initialized (height check).");
    } else {
      return height;
    }
  }

  /**
   * Gets the current score of the ongoing game.
   * @return An integer representing score
   * @throws IllegalStateException if the game has not started yet
   */
  @Override
  public int getScore() throws IllegalStateException {
    if (score == -1) {
      throw new IllegalStateException("Game has not been initialized (score check). IM THE BOOGY MAN");
    } else {
      return score;
    }
  }

  /**
   * Checks if there are any possible sets left on the board.
   * @return a boolean representing set presence
   */
  @Override
  public boolean anySetsPresent() {
    boolean anySets = false;
    Coord coord00 = new Coord(0, 0);
    Coord coord01 = new Coord(0, 1);
    Coord coord02 = new Coord(0, 2);
    Coord coord10 = new Coord(1, 0);
    Coord coord11 = new Coord(1, 1);
    Coord coord12 = new Coord(1, 2);
    Coord coord20 = new Coord(2, 0);
    Coord coord21 = new Coord(2, 1);
    Coord coord22 = new Coord(2, 2);

    ArrayList<Coord> coords = new ArrayList<>();
    coords.add(coord00);
    coords.add(coord01);
    coords.add(coord02);
    coords.add(coord10);
    coords.add(coord11);
    coords.add(coord12);
    coords.add(coord20);
    coords.add(coord21);
    coords.add(coord22);

    for (Coord first : coords) {
      for (Coord second : coords) {
        for (Coord third : coords) {
          if (this.isValidSet(first, second, third)
                  && !this.areSameCoord(first, second)
                  && !this.areSameCoord(first, third)
                  && !this.areSameCoord(second, third)) {
            anySets = true;
          }
        }
      }
    }

    return anySets;
  }

  /**
   * Checks if two coordinates are the same for anySetsPresent.
   * @param coord1 - the first coord
   * @param coord2 - the second coord
   * @return a boolean representing coord equality
   */
  private boolean areSameCoord(Coord coord1, Coord coord2) {
    return (coord1.col == coord2.col && coord1.row == coord2.row);
  }

  /**
   * Checks if three given coordinates create a valid SET.
   * @param coord1 - the coordinates of the first card
   * @param coord2 - the coordinates of the second card
   * @param coord3 - the coordinates of the third card
   * @return A boolean value representing a valid SET
   * @throws IllegalStateException if the game has not started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3)
          throws IllegalStateException, IllegalArgumentException {
    if (this.cards.size() == 0) {
      throw new IllegalStateException("Game has not been initialized (isValidSet).");
    } else if (this.isBadCoord(coord1) || this.isBadCoord(coord2) || this.isBadCoord(coord3)) {
      throw new IllegalArgumentException("Bad coords (isValidSet).");
    } else {
      Card card1 = this.getCardAtCoord(coord1);
      Card card2 = this.getCardAtCoord(coord2);
      Card card3 = this.getCardAtCoord(coord3);

      boolean countCheck = (card1.count() == card2.count()
              && card2.count() == card3.count())
              || (card1.count() != card2.count()
              && card2.count() != card3.count()
              && card1.count() != card3.count());

      boolean fillingCheck = (card1.filling() == card2.filling()
              && card2.filling() == card3.filling())
              || (card1.filling() != card2.filling()
              && card2.filling() != card3.filling()
              && card1.filling() != card3.filling());

      boolean shapeCheck = (card1.shape() == card2.shape()
              && card2.shape() == card3.shape())
              || (card1.shape() != card2.shape()
              && card2.filling() != card3.shape()
              && card1.shape() != card3.shape());

      return (countCheck && fillingCheck && shapeCheck);
    }
  }

  /**
   * Retrieves the Card Object from a given Coord.
   * @param row - the row of the desired card
   * @param col - the column of the desired card
   * @return a Card from the board
   * @throws IllegalStateException if the game has not started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public Card getCardAtCoord(int row, int col)
          throws IllegalStateException, IllegalArgumentException {
    if (this.cards.size() == 0) {
      throw new IllegalStateException("Game has not been initialized (getCardAtCoord).");
    } else if (row > 2 || row < 0 || col > 2 || col < 0) {
      throw new IllegalArgumentException("Invalid coordinates (getCardAtCoord).");
    } else {
      return this.cards.get(row).get(col);
    }
  }

  /**
   * Retrieves the Card Object from a given Coord.
   * @param coord the coordinates of the desired card
   * @return a Card from the board
   * @throws IllegalStateException if the game has not started yet
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  @Override
  public Card getCardAtCoord(Coord coord)
          throws IllegalStateException, IllegalArgumentException {
    int row = coord.row;
    int col = coord.col;

    if (this.cards.size() == 0) {
      throw new IllegalStateException("Game has not been initialized (getCardAtCoord).");
    } else if (this.isBadCoord(coord)) {
      throw new IllegalArgumentException("Invalid coordinates (getCardAtCoord).");
    } else {
      return this.cards.get(row).get(col);
    }
  }

  /**
   * Checks if the game is over.
   * @return a boolean value representing finish state
   */
  @Override
  public boolean isGameOver() {
    return (this.gameOver || !this.anySetsPresent());
  }

  /**
   * Generates a new deck from scratch.
   * @return a List of cards
   */
  @Override
  public List<Card> getCompleteDeck() {
    List<Card> newDeck = new ArrayList<Card>();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 3; k++) {
          newDeck.add(new Card(i, j, k));
        }
      }
    }

    return newDeck;
  }
}
