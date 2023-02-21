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
   * Initializes the viewer such that System.out is the default.
   * @param state - The game's ModelState, aka current behavior
   * @throws IllegalArgumentException when the ModelState is null
   */
  public SetGameTextView(SetGameModelState state) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("State is null.");
    }

    this.view = System.out;
    this.state = state;
  }

  /**
   * Initializes the viewer.
   * @param state - The game's ModelState, aka the current behavior
   * @param view - A 'view', which is an Appendable that rendered messages are sent to
   * @throws IllegalArgumentException if any arguments are null
   */
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
    String output = "";

    for (int i = 0; i < this.state.getHeight(); i++) {
      for (int j = 0; j < this.state.getWidth(); j++) {
        output += state.getCardAtCoord(i, j).toString();

        if (j != (this.state.getWidth() - 1)) {
          output += " ";
        }
      }

      if (i != (this.state.getHeight() - 1)) {
        output += "\n";
      }
    }

    return output;
  }

  @Override
  public void renderGrid() throws IOException {
    this.view.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.view.append(message);
  }
}
