package cs3500.set.view;

import java.io.IOException;

import cs3500.set.model.hw02.SetGameModelState;

/**
 * Represents the View components of the package.
 * WIP possibly
 */
public class SetGameTextView implements SetGameView {
  public SetGameModelState state;
  public Appendable view;

  /**
   * Initializes the Viewer.
   * @param state - The game's ModelState, aka current behavior
   * @throws IllegalArgumentException when  the ModelState is null
   */
  public SetGameTextView(SetGameModelState state) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("State is null.");
    }

    this.view = System.out;
    this.state = state;
  }

  public SetGameTextView(SetGameModelState state, Appendable view) throws IllegalArgumentException {
    if (state == null || view == null) {
      throw new IllegalArgumentException("State or view is null.");
    }

    this.state = state;
    this.view = view;
  }

  /**
   * Prints the game's ModelState to the client.
   * @return a String that visualizes the backend behavior
   */
  @Override
  public String toString() {
    return state.getCardAtCoord(0, 0).toString() + " "
            + state.getCardAtCoord(0, 1).toString() + " "
            + state.getCardAtCoord(0, 2).toString() + "\n"
            + state.getCardAtCoord(1, 0).toString() + " "
            + state.getCardAtCoord(1, 1).toString() + " "
            + state.getCardAtCoord(1, 2).toString() + "\n"
            + state.getCardAtCoord(2, 0).toString() + " "
            + state.getCardAtCoord(2, 1).toString() + " "
            + state.getCardAtCoord(2, 2).toString();
  }

  @Override
  public void renderGrid() throws IOException {
    this.view.append(this.state.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.view.append(message);
  }
}
