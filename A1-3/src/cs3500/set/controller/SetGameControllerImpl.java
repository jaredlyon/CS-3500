package cs3500.set.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.view.SetGameView;

/**
 * Represents an implementation of a SetGameController.
 */
public class SetGameControllerImpl implements SetGameController {
  private final SetGameModel model;
  private final SetGameView view;
  private final Readable in;
  private boolean quit = false;

  /**
   * Constructs a new controller using arguments.
   *
   * @param model - a model of a SetGame
   * @param view  - a view of a SetGame
   * @param in    - inputs in the form of a readable
   * @throws IllegalArgumentException if any arguments are null
   */
  public SetGameControllerImpl(SetGameModel model, SetGameView view, Readable in)
          throws IllegalArgumentException {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Model, view, or input is null within controller "
              + "implementation!");
    }

    this.model = model;
    this.view = view;
    this.in = in;
  }

  /**
   * Plays a game of set.
   *
   * @throws IllegalStateException if any behavior is unexpected
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner sc = new Scanner(this.in);
    int inputHeight;
    int inputWidth;
    int firstCardRow;
    int firstCardCol;
    int secondCardRow;
    int secondCardCol;
    int thirdCardRow;
    int thirdCardCol;

    // overall instruction
    try {
      this.view.renderMessage("Welcome to SET!\n"
              + "Your goal is to match cards of three based on attributes.\n"
              + "Use standard coordinate integers to select your cards.\n");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    // begin start loop
    boolean goodStart = false;

    while (!goodStart) {
      // height instruction
      try {
        this.view.renderMessage("Input game height:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        inputHeight = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }

      // if msg processor signals bad input or quit
      if (inputHeight == -2) {
        while (inputHeight == -2) {
          try {
            this.view.renderMessage("Invalid height input! Please re-enter:\n");
            try {
              inputHeight = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      } else if (inputHeight == -1) {
        try {
          this.view.renderMessage("Game quit!\n"
                  + "Score: 0");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
        quit = true;
        return;
      }

      // width instruction
      try {
        this.view.renderMessage("Input game width:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        inputWidth = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }

      // if msg processor signals bad input or quit
      if (inputWidth == -2) {
        while (inputWidth == -2) {
          try {
            this.view.renderMessage("Invalid width input! Please re-enter:\n");
            try {
              inputWidth = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      } else if (inputWidth == -1) {
        try {
          this.view.renderMessage("Game quit!\n"
                  + "Score: 0");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
        quit = true;
        return;
      }

      // start game
      try {
        this.model.startGameWithDeck(this.model.getCompleteDeck(), inputHeight, inputWidth);
        goodStart = true;

        // confirm game start to user
        try {
          this.view.renderMessage("Game starting...\n"
                  + "Loading deck...\n"
                  + "Size parameters confirmed...\n"
                  + "---------------SET---------------\n");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("Invalid height/width. Try again.\n");
        } catch (IOException e1) {
          throw new IllegalStateException(e1);
        }
      }
    }

    // play the game (using loop)
    while (!quit) {
      // render the game state
      try {
        this.view.renderGrid();
      } catch (IOException e) {
        throw new IllegalStateException("Controller failed to render grid.");
      }

      // render the score
      try {
        this.view.renderMessage("\nScore: " + this.model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Controller failed to render score.");
      }

      // instruct user to claim a set
      try {
        this.view.renderMessage("Time to claim a set!\n"); // instruction prompt
      } catch (IOException e) {
        throw new IllegalStateException("Controller failed to render claimSet instruction.");
      }

      // instruct for first card column
      try {
        this.view.renderMessage("Input first card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        firstCardRow = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard1ColValid = false;

      while (!isCard1ColValid) {
        // check for quit
        if (firstCardRow == -1) {
          this.quitGame();
          return;
        } else if (firstCardRow == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid firstCardRow input! Please re-enter:\n");
            try {
              firstCardRow = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard1ColValid = true;
          firstCardRow--;
        }
      }

      // instruct for first card row
      try {
        this.view.renderMessage("Input first card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        firstCardCol = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard1RowValid = false;

      while (!isCard1RowValid) {
        // check for quit
        if (firstCardCol == -1) {
          this.quitGame();
          return;
        } else if (firstCardCol == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid firstCardCol input! Please re-enter:\n");
            try {
              firstCardCol = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard1RowValid = true;
          firstCardCol--;
        }
      }

      // instruct for second card col
      try {
        this.view.renderMessage("Input second card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        secondCardRow = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard2ColValid = false;

      while (!isCard2ColValid) {
        // check for quit
        if (secondCardRow == -1) {
          this.quitGame();
          return;
        } else if (secondCardRow == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid secondCardRow input! "
                    + "Please re-enter:\n");
            try {
              secondCardRow = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard2ColValid = true;
          secondCardRow--;
        }
      }

      // instruct for second card row
      try {
        this.view.renderMessage("Input second card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        secondCardCol = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard2RowValid = false;

      while (!isCard2RowValid) {
        // check for quit
        if (secondCardCol == -1) {
          this.quitGame();
          return;
        } else if (secondCardCol == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid secondCardCol input! Please re-enter:\n");
            try {
              secondCardCol = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard2RowValid = true;
          secondCardCol--;
        }
      }

      // instruct for third card col
      try {
        this.view.renderMessage("Input third card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        thirdCardRow = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard3ColValid = false;

      while (!isCard3ColValid) {
        // check for quit
        if (thirdCardRow == -1) {
          this.quitGame();
          return;
        } else if (thirdCardRow == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid thirdCardRow input! Please re-enter:\n");
            try {
              thirdCardRow = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard3ColValid = true;
          thirdCardRow--;
        }
      }

      // instruct for third card row
      try {
        this.view.renderMessage("Input third card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        thirdCardCol = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard3RowValid = false;

      while (!isCard3RowValid) {
        // check for quit
        if (thirdCardCol == -1) {
          this.quitGame();
          return;
        } else if (thirdCardCol == -2) {
          // check for invalid input
          try {
            this.view.renderMessage("Invalid thirdCardCol input! "
                    + "Please re-enter:\n");
            try {
              thirdCardCol = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard3RowValid = true;
          thirdCardCol--;
        }
      }

      // convert inputs to coords
      Coord card1Coord = new Coord(firstCardRow, firstCardCol);
      Coord card2Coord = new Coord(secondCardRow, secondCardCol);
      Coord card3Coord = new Coord(thirdCardRow, thirdCardCol);

      // checks for a valid claim data
      try {
        boolean isValidSet = this.model.isValidSet(card1Coord, card2Coord, card3Coord);

        // execute claimSet
        if (isValidSet) {
          this.model.claimSet(card1Coord, card2Coord, card3Coord);
          try {
            this.view.renderMessage("Nice job! Go again.\n");
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          try {
            this.view.renderMessage("Invalid claim. Try again.\n");
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("Invalid claim. Try again.\n");
        } catch (IOException e1) {
          throw new IllegalStateException(e1);
        }
      }

      // game over sequence
      if (this.model.isGameOver()) {
        try {
          this.view.renderMessage("Game over!\n");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

        try {
          this.view.renderMessage("Score: " + this.model.getScore() + "\n");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

        quit = true;
        return;
      }
    }
  }

  /**
   * Processes Strings generated by the .next() Scanner method.
   * @param in the String returned by the scanner
   * @return an integer representing a processed game output
   */
  private int processInput(String in) {
    try {
      int parsedInput = Integer.parseInt(in);

      if (parsedInput <= 0) {
        return -2; // flags bad input
      } else {
        return parsedInput;
      }
    } catch (NumberFormatException e) {
      if (in.equals("q") || in.equals("Q")) {
        return -1; // flags quit
      } else {
        return -2; // flags bad input
      }
    }
  }

  /**
   * Quits the game.
   */
  private void quitGame() {
    try {
      this.view.renderMessage("Game quit!\n"
              + "State of game when quit:\n"
              + this.view.toString()
              + "\nScore: " + this.model.getScore());
    } catch (IOException e) {
      throw new IllegalStateException("Controller failed to quit game.");
    }

    quit = true;
  }
}