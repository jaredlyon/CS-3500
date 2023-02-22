package cs3500.set.model.hw02;

import java.util.List;

import cs3500.set.model.hw03.ASetGameModel;

/**
 * Represents a game of SetThree.
 */
public class SetThreeGameModel extends ASetGameModel {
  /**
   * Constructs a new, blank Game Model.
   */
  public SetThreeGameModel() {
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
    } else if (!this.isValidSet(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Not a set!");
    }
  }

  /**
   * Starts a game of SET with given parameters.
   *
   * @param deck   - the list of cards in the order they will be played
   * @param height - the height of the board for this game
   * @param width  - the width of the board for this game
   * @throws IllegalArgumentException if the grid parameters are not 3x3 or given deck is too small
   */
  @Override
  public void startGameWithDeck(List deck, int height, int width) throws IllegalArgumentException {
    if (height != 3 || width != 3) {
      throw new IllegalArgumentException("Invalid grid arguments.");
    } else if (deck == null || deck.size() < 9) {
      throw new IllegalArgumentException("Not enough cards in deck!");
    }

    super.startGameWithDeck(deck, height, width);
  }
}
