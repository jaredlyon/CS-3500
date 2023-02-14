import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;

/**
 * Do not modify this file. This file should compile correctly with your code.
 */
public class Hw03TypeChecks {
  /**
   * Runs the program, but this method should NEVER be run.
   * It does nothing of import.
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    Readable rd = null;
    Appendable out = null;

    helper(new SetThreeGameModel(), rd, out);
  }

  private static void helper(SetGameModel<?> model, Readable in, Appendable out) {
    new SetGameControllerImpl(model, new SetGameTextView(model, out), in).playGame();
  }
}
