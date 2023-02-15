import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetGameModelState;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents the test suite for SetGameControllerImpl.
 */
public class SetGameControllerImplTest {

  /**
   * Represents a mock of the SetGameModel to confirm ClaimSet inputs from the controller.
   */
  static class ControllerConfirmClaimSetInputsMock extends SetThreeGameModel {
    private final StringBuilder log;
    final int width;
    final int height;
    final int score;

    ControllerConfirmClaimSetInputsMock(StringBuilder log) {
      this.log = Objects.requireNonNull(log);
      this.width = 3;
      this.height = 3;
      this.score = 0;
    }

    @Override
    public void claimSet(Coord coord1, Coord coord2, Coord coord3) {
      log.append(String.format("coord1 = %s, coord2 = %s, coord3 = %s\n", coord1, coord2, coord3));
    }
  }

  @Test
  public void testClaimSet() {
    Readable in = new StringReader("3 3 1 1 1 2 1 3 q");
    Appendable out = new StringBuilder();

    StringBuilder log = new StringBuilder();
    SetGameModel model = new ControllerConfirmClaimSetInputsMock(log);
    SetGameView view = new SetGameTextView(model, out);

    SetGameController controller = new SetGameControllerImpl(model, view, in);
    controller.playGame();

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("coord1 = (r0,c0), coord2 = (r0,c1), coord3 = (r0,c2)", lines[0]);
  }

  /**
   * Represents a mock of the SetGameModel to confirm isValidSet inputs from the controller.
   */
  static class ControllerConfirmIsValidSetInputsMock extends SetThreeGameModel {
    private final StringBuilder log;
    final int width;
    final int height;
    final int score;

    ControllerConfirmIsValidSetInputsMock(StringBuilder log) {
      this.log = Objects.requireNonNull(log);
      this.width = 3;
      this.height = 3;
      this.score = 0;
    }

    @Override
    public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3) {
      log.append(String.format("coord1 = %s, coord2 = %s, coord3 = %s\n", coord1, coord2, coord3));
      return true;
    }
  }

  @Test
  public void testIsValidSet() {
    Readable in = new StringReader("3 3 1 1 1 2 1 3 q");
    Appendable out = new StringBuilder();

    StringBuilder log = new StringBuilder();
    SetGameModel model = new ControllerConfirmIsValidSetInputsMock(log);
    SetGameView view = new SetGameTextView(model, out);

    SetGameController controller = new SetGameControllerImpl(model, view, in);
    controller.playGame();

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("coord1 = (r0,c0), coord2 = (r0,c1), coord3 = (r0,c2)", lines[0]);
  }

  /**
   * Represents a mock of the SetGameModel to confirm startGameWithDeck inputs from the controller.
   */
  static class ControllerConfirmStartGameInputsMock implements SetGameModel {

    private final StringBuilder log;

    ControllerConfirmStartGameInputsMock(StringBuilder log) {
      this.log = Objects.requireNonNull(log);
    }
    @Override
    public void claimSet(Coord coord1, Coord coord2, Coord coord3) {
      // do nothing
    }

    @Override
    public void startGameWithDeck(List deck, int height, int width) throws IllegalArgumentException {
      log.append(String.format("deck = %s, height = %s, width = %s\n", deck, height, width));
    }

    @Override
    public int getWidth() throws IllegalStateException {
      return 0;
    }

    @Override
    public int getHeight() throws IllegalStateException {
      return 0;
    }

    @Override
    public int getScore() throws IllegalStateException {
      return 0;
    }

    @Override
    public boolean anySetsPresent() {
      return false;
    }

    @Override
    public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3) throws IllegalArgumentException {
      return false;
    }

    @Override
    public Object getCardAtCoord(int row, int col) {
      return null;
    }

    @Override
    public Object getCardAtCoord(Coord coord) {
      return null;
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public List getCompleteDeck() {
      return null;
    }
  }

  /**
   * Represents a mock of the view for the startGameWithDeck input test.
   */
  static class ViewConfirmStartGameInputsMock implements SetGameView {
    final SetGameModelState state;
    final Appendable view;

    public ViewConfirmStartGameInputsMock(SetGameModelState state, Appendable view)
            throws IllegalArgumentException {
      if (state == null || view == null) {
        throw new IllegalArgumentException("State or view is null.");
      }

      this.state = state;
      this.view = view;
    }

    @Override
    public void renderGrid() throws IOException {
      // do nothing
    }

    @Override
    public void renderMessage(String message) throws IOException {
      // do nothing
    }
  }

  @Test
  public void testStartGameWithDeck() {
    Readable in = new StringReader("3 3 q");
    Appendable out = new StringBuilder();

    StringBuilder log = new StringBuilder();
    SetGameModel model = new ControllerConfirmStartGameInputsMock(log);
    SetGameView view = new ViewConfirmStartGameInputsMock(model, out);

    SetGameController controller = new SetGameControllerImpl(model, view, in);
    controller.playGame();

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("deck = null, height = 3, width = 3", lines[0]);
  }

  static class BadViewRenderGridExceptionMock implements SetGameView {
    final SetGameModelState state;
    final Appendable view;

    public BadViewRenderGridExceptionMock(SetGameModelState state, Appendable view)
            throws IllegalArgumentException {
      if (state == null || view == null) {
        throw new IllegalArgumentException("State or view is null.");
      }

      this.state = state;
      this.view = view;
    }

    @Override
    public void renderGrid() throws IOException {
      throw new IOException();
    }

    @Override
    public void renderMessage(String message) throws IOException {
      this.view.append(message);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderGridException() {
    Readable in = new StringReader("3 3 1 2 1 1 1 3 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new BadViewRenderGridExceptionMock(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    controller.playGame();
  }

  static class BadViewRenderMessageExceptionMock implements SetGameView {
    final SetGameModelState state;
    final Appendable view;

    public BadViewRenderMessageExceptionMock(SetGameModelState state, Appendable view)
            throws IllegalArgumentException {
      if (state == null || view == null) {
        throw new IllegalArgumentException("State or view is null.");
      }

      this.state = state;
      this.view = view;
    }

    @Override
    public void renderGrid() throws IOException {
      this.view.append(this.toString());
    }

    @Override
    public void renderMessage(String message) throws IOException {
      throw new IOException();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderMessageException() {
    Readable in = new StringReader("3 3 1 2 1 1 1 3 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new BadViewRenderMessageExceptionMock(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    controller.playGame();
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGameExceptionOutOfInput() {
    Readable in = new StringReader("1 3 3");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    controller.playGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorException() {
    Readable in = new StringReader("1 3 3");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(null, view, in);

    controller.playGame();
  }

  @Test
  public void testWelcomeMessage() {
    Readable in = new StringReader("q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Game quit!", lines[4]);
    assertEquals("Score: 0", lines[5]);
  }

  @Test
  public void testStartGameGoodInput() {
    Readable in = new StringReader("3 3 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Input game width:", lines[4]);
    assertEquals("Game starting...", lines[5]);
    assertEquals("Loading deck...", lines[6]);
    assertEquals("Size parameters confirmed...", lines[7]);
    assertEquals("---------------SET---------------", lines[8]);
    assertEquals("1EO 1EQ 1ED", lines[9]);
    assertEquals("1FO 1FQ 1FD", lines[10]);
    assertEquals("1SO 1SQ 1SD", lines[11]);
    assertEquals("Score: 0", lines[12]);
    assertEquals("Time to claim a set!", lines[13]);
    assertEquals("Input first card column:", lines[14]);
    assertEquals("Game quit!", lines[15]);
    assertEquals("State of game when quit:", lines[16]);
    assertEquals("1EO 1EQ 1ED", lines[17]);
    assertEquals("1FO 1FQ 1FD", lines[18]);
    assertEquals("1SO 1SQ 1SD", lines[19]);
    assertEquals("Score: 0", lines[20]);
  }

  @Test
  public void testStartGameBadHeightInput() {
    Readable in = new StringReader("4 3 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Input game width:", lines[4]);
    assertEquals("Invalid height/width. Try again.", lines[5]);
    assertEquals("Input game height:", lines[6]);
    assertEquals("Game quit!", lines[7]);
    assertEquals("Score: 0", lines[8]);
  }

  @Test
  public void testStartGameBadWidthInput() {
    Readable in = new StringReader("3 4 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Input game width:", lines[4]);
    assertEquals("Invalid height/width. Try again.", lines[5]);
    assertEquals("Input game height:", lines[6]);
    assertEquals("Game quit!", lines[7]);
    assertEquals("Score: 0", lines[8]);
  }

  @Test
  public void testStartGameFixedInput() {
    Readable in = new StringReader("3 4 3 3 q");
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Input game width:", lines[4]);
    assertEquals("Invalid height/width. Try again.", lines[5]);
    assertEquals("Input game height:", lines[6]);
    assertEquals("Input game width:", lines[7]);
    assertEquals("Game starting...", lines[8]);
    assertEquals("Loading deck...", lines[9]);
    assertEquals("Size parameters confirmed...", lines[10]);
    assertEquals("---------------SET---------------", lines[11]);
    assertEquals("1EO 1EQ 1ED", lines[12]);
    assertEquals("1FO 1FQ 1FD", lines[13]);
    assertEquals("1SO 1SQ 1SD", lines[14]);
    assertEquals("Score: 0", lines[15]);
    assertEquals("Time to claim a set!", lines[16]);
    assertEquals("Input first card column:", lines[17]);
    assertEquals("Game quit!", lines[18]);
    assertEquals("State of game when quit:", lines[19]);
    assertEquals("1EO 1EQ 1ED", lines[20]);
    assertEquals("1FO 1FQ 1FD", lines[21]);
    assertEquals("1SO 1SQ 1SD", lines[22]);
    assertEquals("Score: 0", lines[23]);
  }

  @Test
  public void testQuitAfterInvalidSet() {
    Readable in = new StringReader("3 4 " // bad grid params
            + "3 3 " // good grid params
            + "1 1 3 1 2 2 " // bad claim (cards dont match)
            + "1 1 1 1 1 1 " // bad claim (cards are the same)
            + "1 1 1 2 1 3 " // good claim
            + "1 1 1 2 1 3 " // good claim
            + "q"); // quit
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("Your goal is to match cards of three based on attributes.", lines[1]);
    assertEquals("Use standard coordinate integers to select your cards.", lines[2]);
    assertEquals("Input game height:", lines[3]);
    assertEquals("Input game width:", lines[4]);
    assertEquals("Invalid height/width. Try again.", lines[5]);
    assertEquals("Input game height:", lines[6]);
    assertEquals("Input game width:", lines[7]);
    assertEquals("Game starting...", lines[8]);
    assertEquals("Loading deck...", lines[9]);
    assertEquals("Size parameters confirmed...", lines[10]);
    assertEquals("---------------SET---------------", lines[11]);
    assertEquals("1EO 1EQ 1ED", lines[12]);
    assertEquals("1FO 1FQ 1FD", lines[13]);
    assertEquals("1SO 1SQ 1SD", lines[14]);
    assertEquals("Score: 0", lines[15]);
    assertEquals("Time to claim a set!", lines[16]);
    assertEquals("Input first card column:", lines[17]);
    assertEquals("Input first card row:", lines[18]);
    assertEquals("Input second card column:", lines[19]);
    assertEquals("Input second card row:", lines[20]);
    assertEquals("Input third card column:", lines[21]);
    assertEquals("Input third card row:", lines[22]);
    assertEquals("Invalid claim. Try again.", lines[23]);
    assertEquals("1EO 1EQ 1ED", lines[24]);
    assertEquals("1FO 1FQ 1FD", lines[25]);
    assertEquals("1SO 1SQ 1SD", lines[26]);
    assertEquals("Score: 0", lines[27]);
    assertEquals("Time to claim a set!", lines[28]);
    assertEquals("Input first card column:", lines[29]);
    assertEquals("Input first card row:", lines[30]);
    assertEquals("Input second card column:", lines[31]);
    assertEquals("Input second card row:", lines[32]);
    assertEquals("Input third card column:", lines[33]);
    assertEquals("Input third card row:", lines[34]);
    assertEquals("Invalid claim. Try again.", lines[35]);
    assertEquals("1EO 1EQ 1ED", lines[36]);
    assertEquals("1FO 1FQ 1FD", lines[37]);
    assertEquals("1SO 1SQ 1SD", lines[38]);
    assertEquals("Score: 0", lines[39]);
    assertEquals("Time to claim a set!", lines[40]);
    assertEquals("Input first card column:", lines[41]);
    assertEquals("Input first card row:", lines[42]);
    assertEquals("Input second card column:", lines[43]);
    assertEquals("Input second card row:", lines[44]);
    assertEquals("Input third card column:", lines[45]);
    assertEquals("Input third card row:", lines[46]);
    assertEquals("Nice job! Go again.", lines[47]);
    assertEquals("2EO 2EQ 2ED", lines[48]);
    assertEquals("1FO 1FQ 1FD", lines[49]);
    assertEquals("1SO 1SQ 1SD", lines[50]);
    assertEquals("Score: 1", lines[51]);
    assertEquals("Time to claim a set!", lines[52]);
    assertEquals("Input first card column:", lines[53]);
    assertEquals("Input first card row:", lines[54]);
    assertEquals("Input second card column:", lines[55]);
    assertEquals("Input second card row:", lines[56]);
    assertEquals("Input third card column:", lines[57]);
    assertEquals("Input third card row:", lines[58]);
    assertEquals("Nice job! Go again.", lines[59]);
    assertEquals("2FO 2FQ 2FD", lines[60]);
    assertEquals("1FO 1FQ 1FD", lines[61]);
    assertEquals("1SO 1SQ 1SD", lines[62]);
    assertEquals("Score: 2", lines[63]);
    assertEquals("Time to claim a set!", lines[64]);
    assertEquals("Input first card column:", lines[65]);
    assertEquals("Game quit!", lines[66]);
    assertEquals("State of game when quit:", lines[67]);
    assertEquals("2FO 2FQ 2FD", lines[68]);
    assertEquals("1FO 1FQ 1FD", lines[69]);
    assertEquals("1SO 1SQ 1SD", lines[70]);
    assertEquals("Score: 2", lines[71]);
  }

  @Test
  public void testQuitAfterFullGameWithInvalidSets() {
    Readable in = new StringReader("3 3 " // good grid params
            + "1 1 3 1 2 2 " // bad claim (cards dont match)
            + "1 1 1 1 1 1 " // bad claim (cards are the same)
            + "1 1 1 2 1 3 " // good claim
            + "1 1 1 2 1 3 " // good claim
            + "1 1 1 2 1 3 " // good claim
            + "4 1 3 2 2 3 " // bad claim (coordinates are out of bound)
            + "1 1 3 1 2 2 " // bad claim (cards dont match and are not the same)
            + "1 1 1 2 1 3 " // good claim
            + "1 1 1 2 1 3 " // good claim
            + "1 1 1 2 1 3 " // good claim
            + "-4 " // illegal card1Col claim
            + "1 " // legal card1Col claim
            + "-1 " // illegal card1Row claim
            + "1 " // legal card1Row claim
            + "-6 " // illegal card2Col claim
            + "1 " // legal card2Col claim
            + "-2 " // illegal card2Row claim
            + "2 " // legal card2Row claim
            + "1 3 " // remaining legal claim of card3
            + "q"); // quit
    Appendable log = new StringBuilder();
    SetGameModel model = new SetThreeGameModel();
    SetGameView view = new SetGameTextView(model, log);
    SetGameController controller = new SetGameControllerImpl(model, view, in);

    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      fail("Unexpected IllegalStateException");
    }

    String[] lines = log.toString().split("\\r?\\n");

    assertEquals("Welcome to SET!", lines[0]);
    assertEquals("---------------SET---------------", lines[8]);
    assertEquals("Score: 0", lines[12]);
    assertEquals("Time to claim a set!", lines[13]);
    assertEquals("Invalid claim. Try again.", lines[20]);
    assertEquals("Invalid claim. Try again.", lines[32]);
    assertEquals("Nice job! Go again.", lines[44]);
    assertEquals("Nice job! Go again.", lines[56]);
    assertEquals("Nice job! Go again.", lines[68]);
    assertEquals("Invalid claim. Try again.", lines[80]);
    assertEquals("Invalid claim. Try again.", lines[92]);
    assertEquals("Nice job! Go again.", lines[104]);
    assertEquals("Nice job! Go again.", lines[116]);
    assertEquals("Nice job! Go again.", lines[128]);
    assertEquals("Time to claim a set!", lines[133]);
    assertEquals("Input first card column:", lines[134]);
    assertEquals("Invalid firstCardRow input! Please re-enter:", lines[135]);
    assertEquals("Input first card row:", lines[136]);
    assertEquals("Invalid firstCardCol input! Please re-enter:", lines[137]);
    assertEquals("Input second card column:", lines[138]);
    assertEquals("Invalid secondCardRow input! Please re-enter:", lines[139]);
    assertEquals("Input second card row:", lines[140]);
    assertEquals("Invalid secondCardCol input! Please re-enter:", lines[141]);
    assertEquals("Input third card column:", lines[142]);
    assertEquals("Input third card row:", lines[143]);
    assertEquals("Nice job! Go again.", lines[144]);
    assertEquals("Game over!", lines[145]);
    assertEquals("Score: 7", lines[146]);
  }
}
