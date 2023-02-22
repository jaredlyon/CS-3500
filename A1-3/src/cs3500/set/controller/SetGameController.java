package cs3500.set.controller;

/**
 * Represents an interface for the controller of a Set game.
 */
public interface SetGameController {

  /**
   * Plays a game of Set.
   * @throws IllegalStateException if any behavior is unexpected.
   */
  void playGame() throws IllegalStateException;
}
