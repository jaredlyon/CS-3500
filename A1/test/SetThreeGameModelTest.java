import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;

/**
 * Generates the test suite for SetThree.
 */
public class SetThreeGameModelTest extends ASetGameModelTest {
  @Override
  protected SetGameModel generateModel() {
    return new SetThreeGameModel();
  }
}
