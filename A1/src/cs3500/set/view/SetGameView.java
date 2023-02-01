package cs3500.set.view;

/**
 * The interface for a client's perspective of the model of
 * a game of Set.
 */

public interface SetGameView {

  /**
   * Produces a textual view of the grid of cards of the current game.
   * Each card is displayed as initials of all of its attributes.
   * For instance, if a card has a single red oval, the card is displayed as 1RO.
   * If a card has three squiggly purple shapes, the card is displayed as 3PS.
   * @return representation of the current state of the game
   */
  String toString();
}
