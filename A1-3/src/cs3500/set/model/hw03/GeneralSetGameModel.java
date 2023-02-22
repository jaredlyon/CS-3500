package cs3500.set.model.hw03;

import java.util.ArrayList;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;

/**
 * Represents a game of GeneralSet.
 */
public class GeneralSetGameModel extends ASetGameModel {
  /**
   * Constructs a new, blank Game Model.
   */
  public GeneralSetGameModel() {
    super();
  }

  /**
   * Attempts to claim a set of three cards as a SET.
   *
   * @param coord1 - the coordinates of the first card
   * @param coord2 - the coordinates of the second card
   * @param coord3 - the coordinates of the third card
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException    if the game has not started or parameters do not make a SET
   */
  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3)
          throws IllegalArgumentException, IllegalStateException {
    super.claimSet(coord1, coord2, coord3);

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

      while (!anySetsPresent()) {
        this.addSetOnClaim();
      }
    } else if (!this.isValidSet(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Not a set!");
    }
  }

  /**
   * Adds a new row to the grid.
   */
  private void addSetOnClaim() {
    try {
      ArrayList<Card> row = new ArrayList<Card>();
      for (int j = 0; j < this.getWidth(); j++) {
        row.add(this.deck.remove(0));
      }
      this.cards.add(row);
    } catch (Exception e) {
      this.gameOver = true;
    }
  }

  /**
   * Starts a game of SET with given parameters.
   *
   * @param deck   - the list of cards in the order they will be played
   * @param height - the height of the board for this game
   * @param width  - the width of the board for this game
   * @throws IllegalArgumentException if the grid parameters cannot be used to play SET
   */
  @Override
  public void startGameWithDeck(List deck, int height, int width) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null.");
    } else if (deck.size() < (height * width)) {
      throw new IllegalArgumentException("Not enough cards in deck!");
    } else if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Height/width cannot be negative.");
    } else if ((height * width) < 3) {
      throw new IllegalArgumentException("Game must initialize with at least three cards.");
    }

    super.startGameWithDeck(deck, height, width);

    while (!this.anySetsPresent()) {
      try {
        ArrayList<Card> row = new ArrayList<Card>();
        for (int i = 0; i < this.getWidth(); i++) {
          row.add(this.deck.remove(0));
        }
        this.cards.add(row);
      } catch (Exception e) {
        this.gameOver = true;
        break;
      }
    }
  }
}
