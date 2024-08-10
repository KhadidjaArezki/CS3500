package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

/*
 * This main() method will be the entry point for your program. Your program needs to
 * take inputs as command-line arguments 
 * Allows to choose different board shapes from the command line, when running the program.
 * 
 *  The first command-line argument must be a string, specifically one of basic, relaxed, or tripeaks.
 *  This argument will decide which game variant (and hence which model) you should use.
 *  You may optionally pass two more arguments R D, both of which should be parsed as integers
 *    * where R specifies the number of rows, and 
 *    * D specifies the number of draw cards.
 *  If unspecified, you should use 7 rows and 3 draw cards as the defaults.
 *  The following are some examples of valid command lines, and their meanings:
 *    basic produces a basic game of solitaire with default number of rows and draw cards.
 *    basic 7 3 achieves the same thing, but explicitly sets the size of the game.
 *    relaxed 6 2 produces a game of relaxed-rules solitaire, with six rows and 2 visible draw cards.
 *    tripeaks 7 8 produces a TriPeaks game with 7 rows and 8 visible draw cards.
 *  
 */

public final class PyramidSolitaire {  
  
  public static void main(String[] args) {
    String modelArg = args[0];
    PyramidSolitaireCreator.GameType gameType;
    int r, d;
    List<Card> deck;
    
    switch (modelArg) {
    case "basic":
      gameType = PyramidSolitaireCreator.GameType.BASIC;
      break;
    case "relaxed":
      gameType = PyramidSolitaireCreator.GameType.RELAXED;
      break;
    case "tripeaks":
      gameType = PyramidSolitaireCreator.GameType.TRIPEAKS;
    default:
      throw new IllegalArgumentException("Unknown Game Variant");
    }
    
    try {
      r = Integer.valueOf(args[1]);
      d = Integer.valueOf(args[2]);
    } catch (Exception e) {
      r = 7; d = 3;
    }
    
    switch (gameType) {
    case BASIC:
    case RELAXED:
      deck = Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
          new Card("2", 2, "♣"), new Card("2", 2, "♦"), new Card("2", 2, "♥"), new Card("2", 2, "♠"),
          new Card("3", 3, "♣"), new Card("3", 3, "♦"), new Card("3", 3, "♥"), new Card("3", 3, "♠"),
          new Card("4", 4, "♣"), new Card("4", 4, "♦"), new Card("4", 4, "♥"), new Card("4", 4, "♠"),
          new Card("5", 5, "♣"), new Card("5", 5, "♦"), new Card("5", 5, "♥"), new Card("5", 5, "♠"),
          new Card("6", 6, "♣"), new Card("6", 6, "♦"), new Card("6", 6, "♥"), new Card("6", 6, "♠"),
          new Card("7", 7, "♣"), new Card("7", 7, "♦"), new Card("7", 7, "♥"), new Card("7", 7, "♠"),
          new Card("8", 8, "♣"), new Card("8", 8, "♦"), new Card("8", 8, "♥"), new Card("8", 8, "♠"),
          new Card("9", 9, "♣"), new Card("9", 9, "♦"), new Card("9", 9, "♥"), new Card("9", 9, "♠"),
          new Card("10", 10, "♣"), new Card("10", 10, "♦"), new Card("10", 10, "♥"), new Card("10", 10, "♠"),
          new Card("J", 11, "♣"), new Card("J", 11, "♦"), new Card("J", 11, "♥"), new Card("J", 11, "♠"),
          new Card("Q", 12, "♣"), new Card("Q", 12, "♦"), new Card("Q", 12, "♥"), new Card("Q", 12, "♠"),
          new Card("K", 13, "♣"), new Card("K", 13, "♦"), new Card("K", 13, "♥"), new Card("K", 13, "♠"));
      break;
    case TRIPEAKS:
      deck = Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
          new Card("2", 2, "♣"), new Card("2", 2, "♦"), new Card("2", 2, "♥"), new Card("2", 2, "♠"),
          new Card("3", 3, "♣"), new Card("3", 3, "♦"), new Card("3", 3, "♥"), new Card("3", 3, "♠"),
          new Card("4", 4, "♣"), new Card("4", 4, "♦"), new Card("4", 4, "♥"), new Card("4", 4, "♠"),
          new Card("5", 5, "♣"), new Card("5", 5, "♦"), new Card("5", 5, "♥"), new Card("5", 5, "♠"),
          new Card("6", 6, "♣"), new Card("6", 6, "♦"), new Card("6", 6, "♥"), new Card("6", 6, "♠"),
          new Card("7", 7, "♣"), new Card("7", 7, "♦"), new Card("7", 7, "♥"), new Card("7", 7, "♠"),
          new Card("8", 8, "♣"), new Card("8", 8, "♦"), new Card("8", 8, "♥"), new Card("8", 8, "♠"),
          new Card("9", 9, "♣"), new Card("9", 9, "♦"), new Card("9", 9, "♥"), new Card("9", 9, "♠"),
          new Card("10", 10, "♣"), new Card("10", 10, "♦"), new Card("10", 10, "♥"), new Card("10", 10, "♠"),
          new Card("J", 11, "♣"), new Card("J", 11, "♦"), new Card("J", 11, "♥"), new Card("J", 11, "♠"),
          new Card("Q", 12, "♣"), new Card("Q", 12, "♦"), new Card("Q", 12, "♥"), new Card("Q", 12, "♠"),
          new Card("K", 13, "♣"), new Card("K", 13, "♦"), new Card("K", 13, "♥"), new Card("K", 13, "♠"),
          new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
          new Card("2", 2, "♣"), new Card("2", 2, "♦"), new Card("2", 2, "♥"), new Card("2", 2, "♠"),
          new Card("3", 3, "♣"), new Card("3", 3, "♦"), new Card("3", 3, "♥"), new Card("3", 3, "♠"),
          new Card("4", 4, "♣"), new Card("4", 4, "♦"), new Card("4", 4, "♥"), new Card("4", 4, "♠"),
          new Card("5", 5, "♣"), new Card("5", 5, "♦"), new Card("5", 5, "♥"), new Card("5", 5, "♠"),
          new Card("6", 6, "♣"), new Card("6", 6, "♦"), new Card("6", 6, "♥"), new Card("6", 6, "♠"),
          new Card("7", 7, "♣"), new Card("7", 7, "♦"), new Card("7", 7, "♥"), new Card("7", 7, "♠"),
          new Card("8", 8, "♣"), new Card("8", 8, "♦"), new Card("8", 8, "♥"), new Card("8", 8, "♠"),
          new Card("9", 9, "♣"), new Card("9", 9, "♦"), new Card("9", 9, "♥"), new Card("9", 9, "♠"),
          new Card("10", 10, "♣"), new Card("10", 10, "♦"), new Card("10", 10, "♥"), new Card("10", 10, "♠"),
          new Card("J", 11, "♣"), new Card("J", 11, "♦"), new Card("J", 11, "♥"), new Card("J", 11, "♠"),
          new Card("Q", 12, "♣"), new Card("Q", 12, "♦"), new Card("Q", 12, "♥"), new Card("Q", 12, "♠"),
          new Card("K", 13, "♣"), new Card("K", 13, "♦"), new Card("K", 13, "♥"), new Card("K", 13, "♠"));
      
      default:
        throw new IllegalArgumentException("Unknown Game Variant.");
    }
    
    Readable rd = new BufferedReader(new InputStreamReader(System.in));
    Appendable ap = System.out;
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(rd, ap);
    
    @SuppressWarnings("unchecked")
    PyramidSolitaireModel<Card> model = (PyramidSolitaireModel<Card>) PyramidSolitaireCreator.create(gameType);
    controller.playGame(model, deck, false, r, d);
  }

}
