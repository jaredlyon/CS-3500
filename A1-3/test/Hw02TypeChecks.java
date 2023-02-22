import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;

/**
 * Do not modify this file. This file should compile correctly with your code.
 */
public class Hw02TypeChecks {
  public static void main(String[] args) {
    SetGameModel game = new SetThreeGameModel();
    helper(game);
  }

  private static void helper(SetGameModel model) {
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    model.claimSet(new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2));
  }
}
