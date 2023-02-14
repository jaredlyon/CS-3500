package cs3500.set;

import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

/**
 * Represents a Main class that runs the program.
 * NOTE - still a WIP - this class does not do anything at the moment.
 */
public class Main {
  /**
   * Runs the program.
   * @param args - any main arguments
   */
  public static void main(String[] args) {
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model);
    //SetGameController controller = new SetGameControllerImpl(model, view, ???);
  }
}
