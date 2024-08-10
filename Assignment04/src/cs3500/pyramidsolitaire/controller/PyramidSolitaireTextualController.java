package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

/** This controller will “run” a game of Pyramid Solitaire, asking for input and outputting the game state.
 * Since the game will still be text-based and the input/output will be limited to the console and keyboard,
 * the notion of a “view” will be minimal in this assignment.
 * 
 * 
 * The {@link playGame} method should play a new game of Pyramid Solitaire using the provided model, using the startGame
 * method on the model. It should throw an IllegalArgumentException if the provided model is null.
 * It should throw an IllegalStateException only if the controller is unable to successfully receive input
 * or transmit output, or if the game cannot be started. The nature of input/output will be an implementation detail.
 * 
 * TODO:
 * 1. Think about which additional fields and types it needs to implement the promised functionality.
 * 
 * 2. Design a constructor PyramidSolitaireTextualController(Readable rd, Appendable ap)
 *    throws IllegalArgumentException. Readable and Appendable are two existing interfaces in Java that
 *    abstract input and output respectively. The constructor should throw the IllegalArgumentException
 *    if and only if either of its arguments are null.Your controller should accept and store these
 *    objects for doing input and output. Any input coming from the user will be received via the Readable
 *    object, and any output sent to the user should be written to the Appendable object by way of a
 *    PyramidSolitaireTextualView.
 *    Hint: Look at the Readable and Appendable interfaces to see how to read from and write to them.
 *    Ultimately you must figure out a way to transmit a String to an Appendable and read suitable data
 *    from a Readable object. The Scanner class will likely be useful, as will the lecture notes.
 * 
 * 3. The {@link playGame} should play a game. It should “run” the game in the following sequence until the game is over.
 *    Note: Each transmission described below should end with a newline.
 *    a. Transmit game state to the Appendable object exactly as the view of the model provides it.
 *   
 *    b. Transmit "Score: N", replacing N with the actual score.
 *   
 *    c. If the game is ongoing (i.e. there is more user input and the user hasn’t quit yet), obtain the
 *       next user input from the Readable object. A user input consists of a “move” specified by a move
 *       type followed by a sequence of values (separated by any type of whitespace):
 *         * rm1 followed by the row and card numbers of the card. Example: an input of rm1 7 7 should
 *           cause the controller to call the 2-argument remove method on your model with appropriate inputs.
 *        
 *         * rm2 followed by the row and card numbers of the cards. Example: an input of rm2 7 1 7 3 should
 *           cause the controller to call the 4-argument remove method on your model with appropriate inputs.
 *        
 *         * rmwd followed by the number of the draw card and the row and card in the pyramid. Example:
 *           an input of rmwd 1 7 4 should cause the controller to call the removeWithDraw method on your model with appropriate inputs.
 *        
 *         * dd followed by the number of the draw card to be discarded. Example: an input of dd 1 should
 *           cause the controller to call the discardDraw method on your model with an appropriate input.
 *          
 *    Note: To make the inputs more user-friendly, all row and card numbers in the input begin from 1.
 *    This will affect the inputs that your controller passes along to your model.
 *    The controller will parse these inputs and pass the information on to the model to make the move. See below for more detail.
 *   
 *    d. If the game is over, the method should transmit the final game state one last time (which will either be the message
 *       "You win!" or the message "Game over. Score: N") The method should then end.
 * 
 * Key points:
 *   * Quitting: If at any point, the next value is either the letter 'q' or the letter 'Q', the controller
 *     should transmit the following in order: the message "Game quit!", the message "State of game when quit:",
 *     the current game state, and the message "Score: N" with N replaced by the final score. The method should then end. For example:
 *     
 *       Game quit!
         State of game when quit:
                     A♣
                   2♣  3♣
                 4♣  5♣  6♣
               7♣  8♣  10♣ 10♥
             J♣  Q♣  K♣  A♦  2♦
           3♦  4♦      6♦  7♦  8♦
         9♦  10♦         K♦  A♥
         Draw: 5♥
         Score: 156
 *
 *  * Bad inputs: If any individual value is unexpected (i.e. something other than 'q', 'Q' or a number) it should
 *    ask the user to re-enter that value again.
 *    For example, if the user is trying to make a two-card move, and
 *    has entered the row and column of the first card correctly, but entered the row of the second card incorrectly,
 *    the controller should continue attempting to read a value for that second card’s row before moving on to read
 *    the value for the second card’s column.
 *    You should behave similarly for the other commands. Once all the numbers are successfully read, if the model
 *    indicates the move is invalid, the controller should transmit a message to the Appendable object saying
 *    "Invalid move. Play again. X" where X is any informative message about why the move was invalid (all on one line),
 *    and resume waiting for valid input.
 *    Hint: You should probably design a helper method to retry reading inputs until you get a number or a 'q'/'Q'.
 *    Using that helper consistently will make it much easier to implement the desired retrying behavior described here.
 *    That helper probably should not be responsible for determining if a number is a valid coordinate —
 *    that’s the model’s job — but that helper does need to return either the user’s number or their desired to quit the game.
 *    Think carefully about the signature of this method before you start implementing it...
 *    
 *  * Error handling: The playGame method should throw an IllegalArgumentException if a null model is passed to it.
 *    If the Appendable object is unable to transmit output or the Readable object is unable to provide inputs
 *    (for whatever reason), the playGame method should throw an IllegalStateException to its caller.
 *    The playGame method must not throw any other exceptions, nor should it propagate any exceptions thrown by the model.
 *   
 *  * Write sufficient tests to be confident that your code is correct.
 *    Note: once the model has been tested thoroughly (which you hopefully did in Assignment 2),
 *    all that remains to be tested is whether the controller works correctly in all cases.
 *    The lecture notes on mock objects will be essential here.
 *    
 */

public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private Readable rd;
  private Appendable ap;
  
  public PyramidSolitaireTextualController(Readable rd, Appendable ap) {
    if (Objects.isNull(rd) || Objects.isNull(ap)) throw new IllegalArgumentException();
    this.rd = rd;
    this.ap = ap;
  }
  
  private int getValidInput(Scanner scan, String quitMsg) throws IllegalStateException{
    String str = scan.next();
    if (str.equals("q") || str.equals("Q")) {
      try {
        this.ap.append(quitMsg);
        return -1;
      }
      catch (IOException e) {
        throw new IllegalStateException();
      }
    }
    else {
      try {
        return Integer.parseInt(str) - 1;
      } catch (NumberFormatException e) {
        try {
          this.ap.append("Please Enter a Valid Number.\n");
          return getValidInput(scan, quitMsg);
        }
        catch (IOException e1) {
          throw new IllegalStateException();
        }
      }
    }
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) throws IllegalStateException{
    if (Objects.isNull(model)) throw new IllegalArgumentException();
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException();
    }
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);
    Scanner scan = new Scanner(this.rd);
    boolean gameQuit = false;
    while(!model.isGameOver() && !gameQuit) {
      try {
        view.render();
        this.ap.append(String.format("Score: %d\n", model.getScore()));
      }
      catch (IOException e) {
        throw new IllegalStateException();
      }

      String output = "";
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", view.toString(), model.getScore());
      String errorOutput = "Invalid move. Play again.%s\n";
      String input = scan.next();
      if (input.equals("q") || input.equals("Q")) {
        output = quitMessage;
        gameQuit = true;
        scan.close();
      }
      else if (input.equals("rm1")) {
        int row = getValidInput(scan, quitMessage);
        int card = getValidInput(scan, quitMessage);
        if (row != -1 && card != -1) {
          try {
            model.remove(row, card);
          } catch (IllegalArgumentException e){            
            output = String.format(errorOutput, e.getMessage());
          }
        } else { gameQuit = true; break; }
      }
      else if (input.equals("rm2")) {
        int row1 = getValidInput(scan, quitMessage);
        int card1 = getValidInput(scan, quitMessage);
        int row2 = getValidInput(scan, quitMessage);
        int card2 = getValidInput(scan, quitMessage);
        if (row1 != -1 && row2 != -1 && card1 != -1 && card2 != -1) {
          try {
            model.remove(row1, card1, row2, card2);
          } catch (IllegalArgumentException e) {
            output = String.format(errorOutput, e.getMessage());
          }
        } else { gameQuit = true; break; }
      }        
      else if (input.equals("rmwd")) {
        int drawIndex = getValidInput(scan, quitMessage);
        int row = getValidInput(scan, quitMessage);
        int card = getValidInput(scan, quitMessage);
        if (row != -1 && card != -1 && drawIndex != -1) {
          try {
            model.removeUsingDraw(drawIndex, row, card);
          } catch (IllegalArgumentException e) {
            output = String.format(errorOutput, e.getMessage());
          }
        } else { gameQuit = true; break; }
      }
      else if (input.equals("dd")) {
        int drawIndex = getValidInput(scan, quitMessage);
        if (drawIndex != -1) {
          try {
            model.discardDraw(drawIndex);
          } catch (IllegalArgumentException e) {
            output = String.format(errorOutput, e.getMessage());
          }
        } else { gameQuit = true; break; }
      }
      else 
        output = String.format(errorOutput, "");
      try {
        this.ap.append(output);
      }
      catch (IOException e1) {
        throw new IllegalStateException();
      }
    }

    String finalMessage = "";
    if (gameQuit) finalMessage = "";
    else if (model.getScore() == 0) finalMessage = "You win!\n";
    else finalMessage = String.format("Game Over. Score: %d\n", model.getScore());
    try {
      this.ap.append(finalMessage);
    }
    catch (IOException e1) {
      throw new IllegalStateException();
    }
  }
}
