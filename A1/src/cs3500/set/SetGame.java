package cs3500.set;

import java.io.InputStreamReader;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

/**
 * Allows for a user to play SetGame via the CLI.
 */
public final class SetGame {
  /**
   * Plays the game.
   * @param args - a String[] determining which version to play
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      for (String argument : args) {
        if (argument.equals("three")) {
          SetGameModel model = new SetThreeGameModel();
          SetGameView view = new SetGameTextView(model);
          Readable in = new InputStreamReader(System.in);
          SetGameController controller = new SetGameControllerImpl(model, view, in);
          controller.playGame();
        } else if (argument.equals("general")) {
          SetGameModel model = new GeneralSetGameModel();
          SetGameView view = new SetGameTextView(model);
          Readable in = new InputStreamReader(System.in);
          SetGameController controller = new SetGameControllerImpl(model, view, in);
          controller.playGame();
        }
      }
    } else {
      System.out.println("No arguments found.");
    }
  }
}