package cs3500.set.model.hw02;

/**
 * Represents a card within the game Set.
 */
public class Card {
  private int count;
  private String filling;
  private String shape;

  /**
   * Creates a new card.
   * @param count - the card's count (1, 2, 3)
   * @param filling - the card's filling (empty, full, striped)
   * @param shape - the card's shape (oval, squiggle, diamond)
   * @throws IllegalArgumentException if any params are not within the above constraints
   */
  public Card(int count, String filling, String shape) throws IllegalArgumentException {
    if (count > 3 || count < 1) {
      throw new IllegalArgumentException("Count must be 1-3, inclusive.");
    } else if (!(filling.equals("empty") || filling.equals("full") || filling.equals("striped"))) {
      throw new IllegalArgumentException("Filling must be empty, full, or striped.");
    } else if (!(shape.equals("oval") || shape.equals("squiggle") || shape.equals("diamond"))) {
      throw new IllegalArgumentException("Shape must be oval, squiggle, or diamond.");
    }

    this.count = count;
    this.filling = filling;
    this.shape = shape;
  }

  /**
   * Creates a new card using integer values in order to correspond to deck generation.
   * @param count - the card's count (1, 2, 3)
   * @param filling - the card's filling (0, 1, 2)
   * @param shape - the card's shape (0, 1, 2)
   * @throws IllegalArgumentException if any params are not within the above constraints
   */
  public Card(int count, int filling, int shape) throws IllegalArgumentException {
    if (count > 2 || count < 0) {
      throw new IllegalArgumentException("Count must be 0-2, inclusive.");
    } else if (filling > 2 || filling < 0) {
      throw new IllegalArgumentException("Filling must be 0-2, inclusive.");
    } else if (shape > 2 || shape < 0) {
      throw new IllegalArgumentException("Shape must be 0-2, inclusive.");
    }

    if (count == 0) {
      this.count = 1;
    } else if (count == 1) {
      this.count = 2;
    } else if (count == 2) {
      this.count = 3;
    }

    if (filling == 0) {
      this.filling = "empty";
    } else if (filling == 1) {
      this.filling = "full";
    } else if (filling == 2) {
      this.filling = "striped";
    }

    if (shape == 0) {
      this.shape = "oval";
    } else if (shape == 1) {
      this.shape = "squiggle";
    } else if (shape == 2) {
      this.shape = "diamond";
    }
  }

  /**
   * Gets this card's count.
   * @return an integer representing the card's count
   */
  public int count() {
    return this.count;
  }

  /**
   * Gets the card's filling in the form of one letter.
   * @return a String representing a card's filling
   * @throws IllegalStateException if the card does not have an expected filling
   */
  public String filling() {
    if (this.filling.equals("empty")) {
      return "E";
    } else if (this.filling.equals("full")) {
      return "F";
    } else if (this.filling.equals("striped")) {
      return "S";
    } else {
      throw new IllegalStateException("Card carries incorrect filling.");
    }
  }

  /**
   * Gets the card's shape in the form of one letter.
   * @return a String representing a card's shape
   * @throws IllegalStateException if the card does not have an expected shape
   */
  public String shape() {
    if (this.shape.equals("oval")) {
      return "O";
    } else if (this.shape.equals("squiggle")) {
      return "Q";
    } else if (this.shape.equals("diamond")) {
      return "D";
    } else {
      throw new IllegalStateException("Card carries incorrect shape.");
    }
  }

  /**
   * Gets the card denomination in the form of a three letter code.
   * @return a String representing a card's attributes
   */
  @Override
  public String toString() {
    return this.count() + this.filling() + this.shape();
  }
}
