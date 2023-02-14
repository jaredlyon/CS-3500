package cs3500.set.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.view.SetGameView;

public class SetGameControllerImpl implements SetGameController {
  private final SetGameModel model;
  private final SetGameView view;
  private final Readable in;
  private boolean quit = false;

  public SetGameControllerImpl(SetGameModel model, SetGameView view, Readable in) {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Model, view, or input is null within controller" +
              "implementation!");
    }

    this.model = model;
    this.view = view;
    this.in = in;
  }

  @Override
  public void playGame() throws IllegalStateException {
    Scanner sc = new Scanner(this.in);
    int inputHeight;
    int inputWidth;
    int card1Col;
    int card1Row;
    int card2Col;
    int card2Row;
    int card3Col;
    int card3Row;

    // overall instruction
    try {
      this.view.renderMessage("Welcome to SET!\n" +
              "Your goal is to match cards of three based on attributes.\n" +
              "Use standard coordinate integers to select your cards.\n");
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
          this.view.renderMessage("Game quit!\n" +
                  "Score: 0");
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
          this.view.renderMessage("Game quit!\n" +
                  "Score: 0");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
        quit = true;
        return;
      }

      // start game
      if (inputHeight == 3 && inputWidth == 3) {
        goodStart = true;

        // confirm game start to user
        try {
          this.view.renderMessage("Game starting...\n" +
                  "Loading deck...\n" +
                  "Size parameters confirmed...\n" +
                  "---------------SET---------------");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

        this.model.startGameWithDeck(this.model.getCompleteDeck(), inputHeight, inputWidth);
      } else {
        try {
          this.view.renderMessage("Invalid height/width. Try again.");
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
      }
    }

    // play the game (using loop)
    while (!quit) {
      // render the game state
      try {
        this.view.renderGrid();
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      // render the score
      try {
        this.view.renderMessage("\nScore: " + this.model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // instruct user to claim a set
      try {
        this.view.renderMessage("Time to claim a set!\n"); // instruction prompt
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // instruct for first card column
      try {
        this.view.renderMessage("Input first card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card1Col = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard1ColValid = false;

      while (!isCard1ColValid) {
        // check for quit
        if (card1Col == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card1Col == -2) {
          try {
            this.view.renderMessage("Invalid card1Col input (non-digit)! Please re-enter:\n");
            try {
              card1Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card1Col < 1 || card1Col > this.model.getHeight()) {
          try {
            this.view.renderMessage("Invalid card1Col input (out of bounds)! Please re-enter:\n");
            try {
              card1Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard1ColValid = true;
          card1Col--;
        }
      }

      // instruct for first card row
      try {
        this.view.renderMessage("Input first card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card1Row = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard1RowValid = false;

      while (!isCard1RowValid) {
        // check for quit
        if (card1Row == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card1Row == -2) {
          try {
            this.view.renderMessage("Invalid card1Row input (non-digit)! Please re-enter:\n");
            try {
              card1Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card1Row < 1 || card1Row > this.model.getWidth()) {
          try {
            this.view.renderMessage("Invalid card1Row input (out of bounds)! Please re-enter:\n");
            try {
              card1Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard1RowValid = true;
          card1Row--;
        }
      }

      // instruct for second card col
      try {
        this.view.renderMessage("Input second card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card2Col = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard2ColValid = false;

      while (!isCard2ColValid) {
        // check for quit
        if (card2Col == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card2Col == -2) {
          try {
            this.view.renderMessage("Invalid card2Col input (non-digit)! Please re-enter:\n");
            try {
              card2Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card2Col < 1 || card2Col > this.model.getHeight()) {
          try {
            this.view.renderMessage("Invalid card2Col input (out of bounds)! Please re-enter:\n");
            try {
              card2Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard2ColValid = true;
          card2Col--;
        }
      }

      // instruct for second card row
      try {
        this.view.renderMessage("Input second card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card2Row = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard2RowValid = false;

      while (!isCard2RowValid) {
        // check for quit
        if (card2Row == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card2Row == -2) {
          try {
            this.view.renderMessage("Invalid card2Row input (non-digit)! Please re-enter:\n");
            try {
              card2Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card2Row < 1 || card2Row > this.model.getWidth()) {
          try {
            this.view.renderMessage("Invalid card2Row input (out of bounds)! Please re-enter:\n");
            try {
              card2Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard2RowValid = true;
          card2Row--;
        }
      }

      // instruct for third card col
      try {
        this.view.renderMessage("Input third card column:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card3Col = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard3ColValid = false;

      while (!isCard3ColValid) {
        // check for quit
        if (card3Col == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card3Col == -2) {
          try {
            this.view.renderMessage("Invalid card3Col input (non-digit)! Please re-enter:\n");
            try {
              card3Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card3Col < 1 || card3Col > this.model.getHeight()) {
          try {
            this.view.renderMessage("Invalid card3Col input (out of bounds)! Please re-enter:\n");
            try {
              card3Col = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard3ColValid = true;
          card3Col--;
        }
      }

      // instruct for third card row
      try {
        this.view.renderMessage("Input third card row:\n");
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

      // grab user input
      try {
        card3Row = this.processInput(sc.next());
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Out of input!");
      }
      boolean isCard3RowValid = false;

      while (!isCard3RowValid) {
        // check for quit
        if (card3Row == -1) {
          this.quitGame();
          return;
        } else
        // check for invalid input
        if (card3Row == -2) {
          try {
            this.view.renderMessage("Invalid card3Row input (non-digit)! Please re-enter:\n");
            try {
              card3Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else
        // check for invalid bounds
        if (card3Row < 1 || card3Row > this.model.getWidth()) {
          try {
            this.view.renderMessage("Invalid card3Row input (out of bounds)! Please re-enter:\n");
            try {
              card3Row = this.processInput(sc.next());
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Out of input!");
            }
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        } else {
          isCard3RowValid = true;
          card3Row--;
        }
      }

      // convert inputs to coords
      Coord card1Coord = new Coord(card1Col, card1Row);
      Coord card2Coord = new Coord(card2Col, card2Row);
      Coord card3Coord = new Coord(card3Col, card3Row);

      // checks for a valid claim data
      try {
        boolean isValidSet = this.model.isValidSet(card1Coord, card2Coord, card3Coord);

        // execute claimSet
        if (isValidSet) {
          this.model.claimSet(card1Coord, card2Coord, card3Coord);
        } else {
          try {
            this.view.renderMessage("Invalid claim. Try again.");
            continue;
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage("Invalid claim. Try again.");
          continue;
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

  private int processInput(String in) {
    try {
      return Integer.parseInt(in);
    } catch (NumberFormatException e) {
      if (in.equals("q") || in.equals("Q")) {
        return -1; // flags quit
      } else {
        return -2; // flags bad input
      }
    }
  }

  private void quitGame() {
    try {
      this.view.renderMessage("Game quit!\n" +
              "State of game when quit:\n" +
              this.view.toString() +
              "\nScore: " + this.model.getScore());
    } catch (IOException e) {
      throw new IllegalStateException("Controller failed to quit game.");
    }

    quit = true;
  }
}
