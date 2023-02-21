package cs3500.set.model.hw03;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;

/**
 * Represents the abstracted methods of the SetGameModel.
 */
public abstract class ASetGameModel implements SetGameModel {
  protected ArrayList<ArrayList<Card>> cards = new ArrayList<ArrayList<Card>>();
  protected List<Card> deck = new ArrayList<Card>();
  protected int score = -1;
  protected boolean gameOver = false;
  protected boolean lastTurn = false;

  /**
   * Checks if a given coord is 'bad' or 'out of bounds'.
   * @param coord - the coord to be tested
   * @return a boolean representing if the coord is out of expected bounds
   */
  protected boolean isBadCoord(Coord coord) {
    return (coord == null || (coord.row > (this.getHeight() - 1) || coord.row < 0
            || coord.col > (this.getWidth() - 1) || coord.col < 0));
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
      throw new IllegalStateException("Game has not been initialized (score check).");
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
    ArrayList<Coord> coords = new ArrayList<>();

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        coords.add(new Coord(i, j));
      }
    }

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
  protected boolean areSameCoord(Coord coord1, Coord coord2) {
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
    } else if (this.areSameCoord(coord1, coord2)
            || this.areSameCoord(coord1, coord3)
            || this.areSameCoord(coord2, coord3)) {
      return false;
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
              && card2.shape() != card3.shape()
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
    } else if (row > (this.getHeight() - 1) || row < 0 || col > (this.getWidth() - 1) || col < 0) {
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
