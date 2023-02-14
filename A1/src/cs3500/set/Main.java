package cs3500.set;

import java.io.InputStreamReader;
import java.io.StringReader;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

public class Main {
  public static void main(String[] args) {
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model);
    //SetGameController controller = new SetGameControllerImpl(model, view, ???);
  }
}
