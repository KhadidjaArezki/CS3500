package cs3500.pyramidsolitaire.controller;

import org.junit.Before;
import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.TriPeaksPyramidSolitaire;
import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.StringBuilder;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

public abstract class PyramidSolitaireControllerTest {
  protected abstract PyramidSolitaireModel<Card> basic();
  protected abstract PyramidSolitaireModel<Card> relaxed();
  protected abstract PyramidSolitaireModel<Card> tripeaks();
  
  public static final class PyramidSolitaireTextualControllerTest extends PyramidSolitaireControllerTest {
    @Override
    protected PyramidSolitaireModel<Card> basic() {
      return new BasicPyramidSolitaire();
    }
    
    @Override
    protected PyramidSolitaireModel<Card> relaxed() {
      return new RelaxedPyramidSolitaire();
    }
    
    @Override
    protected PyramidSolitaireModel<Card> tripeaks() {
      return new TriPeaksPyramidSolitaire();
    }
    
    List<Card> deck = Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
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

    List<Card> doubleDeck = Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
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
    
    PyramidSolitaireModel<Card> testBasicSolitaire = basic();
    PyramidSolitaireModel<Card> testRelaxedSolitaire = relaxed();
    PyramidSolitaireModel<Card> testTriPeaksSolitaire = tripeaks();
    
    Appendable testAp = new StringBuilder();
    PyramidSolitaireView testBasicView = new PyramidSolitaireTextualView(testBasicSolitaire);
    PyramidSolitaireView testRelaxedView = new PyramidSolitaireTextualView(testRelaxedSolitaire);
    PyramidSolitaireView testTriPeaksView = new PyramidSolitaireTextualView(testTriPeaksSolitaire);
    
    PyramidSolitaireModel<Card> basicSolitaire = basic();
    PyramidSolitaireModel<Card> relaxedSolitaire = relaxed();
    PyramidSolitaireModel<Card> triPeaksSolitaire = tripeaks();
    
    @Before
    public void startBasicGame() {
      int numRows = 7;
      int numDraw = 3;
      testBasicSolitaire.startGame(deck, false, numRows, numDraw);
//      System.out.print(testView.toString());
    }
    
    /* Tests for TextualController with BasicSolitaireModel */
    
    @Test
    public void testController1BasicwCallbacks() {
      int numRows = 7;
      int numDraw = 3;
      String startStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n",
          testBasicView.toString(), testBasicSolitaire.getScore());
      String gameStateString = startStateString + quitMessage;
      String inputString = "q";
      
      Reader fakeInput = new StringReader(""); // A character stream whose source is a string.
      Appendable expectedOutput = new StringBuilder();
      Interaction[] interactions = { Interaction.inputs(CharBuffer.wrap(inputString)), Interaction.prints(gameStateString)}; 
          
      for (Interaction interaction : interactions) {
        interaction.apply(fakeInput, expectedOutput);
      }
      
      Reader rd1 = new StringReader(inputString);
      Appendable ap1 = new StringBuilder();
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(basicSolitaire, deck, false, numRows, numDraw);
      
//      System.out.print(expectedOutput.toString());
//      System.out.print(ap1.toString());
      assertEquals(expectedOutput.toString(), ap1.toString());
    }
    
    @Test
    public void testControllerBasic1() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap1 = new StringBuilder();
      Reader rd1 = new StringReader("rm2 7 1 7 7 rm2 7 2 7 6 q"); // A character stream whose source is a string.
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(basicSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      testBasicSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.remove(6, 1, 6, 5);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
//      System.out.println("Test Output");
//      System.out.println(String.format("%s%s", gameStateString, quitMessage));
//      System.out.println("Controller Output");
//      System.out.print(ap.toString());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap1.toString());
    }
    
    @Test
    public void testControllerBasic2() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap2 = new StringBuilder();
      Reader rd2 = new StringReader("rm2 7 1 7 7 rm2 7 2 7 q");
      PyramidSolitaireController controller2 = new PyramidSolitaireTextualController(rd2, ap2);
      controller2.playGame(basicSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      testBasicSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap2.toString());
    }
    
    @Test
    public void testControllerBasic3() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap3 = new StringBuilder();
      Reader rd3 = new StringReader("rn2 rm2 7 1 7 7 q");
      PyramidSolitaireController controller3 = new PyramidSolitaireTextualController(rd3, ap3);
      controller3.playGame(basicSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      gameStateString = gameStateString.concat("Invalid move. Play again.\n");
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap3.toString());
    }
    
    @Test
    public void testControllerBasic4() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap4 = new StringBuilder();
      Reader rd4 = new StringReader("rm2 7 ! 1 7 7 q");
      PyramidSolitaireController controller4 = new PyramidSolitaireTextualController(rd4, ap4);
      controller4.playGame(basicSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      gameStateString = gameStateString.concat("Please Enter a Valid Number.\n");
      testBasicSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap4.toString());
    }
    
    @Test
    public void testControllerBasic5() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap5 = new StringBuilder();
      Reader rd5 = new StringReader("rm2 7 1 7 7 rm2 7 2 7 6 dd 1 dd 1 rmwd 1 6 1 q");
      PyramidSolitaireController controller5 = new PyramidSolitaireTextualController(rd5, ap5);
      controller5.playGame(basicSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      testBasicSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.remove(6, 1, 6, 5);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.discardDraw(0);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.discardDraw(0);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      testBasicSolitaire.removeUsingDraw(0, 5, 0);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testBasicView.toString(), testBasicSolitaire.getScore());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap5.toString());
    }

    
    /* Tests for TextualController with RelaxedSolitaireModel */
    
    @Before
    public void startRelaxedGame() {
      int numRows = 7;
      int numDraw = 3;
      testRelaxedSolitaire.startGame(deck, false, numRows, numDraw);
//      System.out.print(testRelaxedView.toString());
    }
    
    @Test
    public void testController1RelaxedwCallbacks() {
      int numRows = 7;
      int numDraw = 3;
      String startStateString = String.format("%sScore: %d\n", testRelaxedView.toString(), testRelaxedSolitaire.getScore());
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n",
          testRelaxedView.toString(), testRelaxedSolitaire.getScore());
      String gameStateString = startStateString + quitMessage;
      String inputString = "q";
      
      Reader fakeInput = new StringReader(""); // A character stream whose source is a string.
      Appendable expectedOutput = new StringBuilder();
      Interaction[] interactions = { Interaction.inputs(CharBuffer.wrap(inputString)), Interaction.prints(gameStateString)}; 
          
      for (Interaction interaction : interactions) {
        interaction.apply(fakeInput, expectedOutput);
      }
      
      Reader rd1 = new StringReader(inputString);
      Appendable ap1 = new StringBuilder();
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(relaxedSolitaire, deck, false, numRows, numDraw);
      
//      System.out.print(expectedOutput.toString());
//      System.out.print(ap1.toString());
      assertEquals(expectedOutput.toString(), ap1.toString());
    }
    
    @Test
    public void testControllerRelaxed1() {
      int numRows = 7;
      int numDraw = 3;
      Appendable ap1 = new StringBuilder();
      Reader rd1 = new StringReader("rm2 7 1 7 7 rm2 7 6 6 6 q"); // A character stream whose source is a string.
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(relaxedSolitaire, deck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testRelaxedView.toString(), testRelaxedSolitaire.getScore());
      testRelaxedSolitaire.remove(6, 0, 6, 6);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testRelaxedView.toString(), testRelaxedSolitaire.getScore()));
      testRelaxedSolitaire.remove(6, 5, 5, 5);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testRelaxedView.toString(), testRelaxedSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testRelaxedView.toString(), testRelaxedSolitaire.getScore());
//      System.out.println("Test Output");
//      System.out.println(String.format("%s%s", gameStateString, quitMessage));
//      System.out.println("Controller Output");
//      System.out.print(ap1.toString());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap1.toString());
    }
    
    
/* Tests for TextualController with TriPeaksSolitaireModel */
    
    @Before
    public void startTriPeaksGame() {
      int numRows = 8;
      int numDraw = 3;
      testTriPeaksSolitaire.startGame(doubleDeck, false, numRows, numDraw);
//      System.out.print(testTriPeaksView);
    }
    
    @Test
    public void testController1TriPeakswCallbacks() {
      int numRows = 8;
      int numDraw = 3;
      String startStateString = String.format("%sScore: %d\n", testTriPeaksView.toString(), testTriPeaksSolitaire.getScore());
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n",
          testTriPeaksView.toString(), testTriPeaksSolitaire.getScore());
      String gameStateString = startStateString + quitMessage;
      String inputString = "q";
      
      Reader fakeInput = new StringReader(""); // A character stream whose source is a string.
      Appendable expectedOutput = new StringBuilder();
      Interaction[] interactions = { Interaction.inputs(CharBuffer.wrap(inputString)), Interaction.prints(gameStateString)}; 
          
      for (Interaction interaction : interactions) {
        interaction.apply(fakeInput, expectedOutput);
      }
      
      Reader rd1 = new StringReader(inputString);
      Appendable ap1 = new StringBuilder();
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(triPeaksSolitaire, doubleDeck, false, numRows, numDraw);
      
//      System.out.print(expectedOutput.toString());
//      System.out.print(ap1.toString());
      assertEquals(expectedOutput.toString(), ap1.toString());
    }
    
    @Test
    public void testControllerTriPeaks1() {
      int numRows = 8;
      int numDraw = 3;
      Appendable ap1 = new StringBuilder();
      Reader rd1 = new StringReader("rm2 8 1 8 5 rm2 8 2 8 6 q"); // A character stream whose source is a string.
      PyramidSolitaireController controller1 = new PyramidSolitaireTextualController(rd1, ap1);
      controller1.playGame(triPeaksSolitaire, doubleDeck, false, numRows, numDraw);
      String gameStateString = String.format("%sScore: %d\n", testTriPeaksView.toString(), testTriPeaksSolitaire.getScore());
      testTriPeaksSolitaire.remove(7, 0, 7, 4);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testTriPeaksView.toString(), testTriPeaksSolitaire.getScore()));
      testTriPeaksSolitaire.remove(7, 1, 7, 5);
      gameStateString = gameStateString.concat(String.format("%sScore: %d\n", testTriPeaksView.toString(), testTriPeaksSolitaire.getScore()));
      String quitMessage = String.format("Game quit!\nState of game when quit:\n%sScore: %d\n", testTriPeaksView.toString(), testTriPeaksSolitaire.getScore());
//      System.out.println("Test Output");
//      System.out.println(String.format("%s%s", gameStateString, quitMessage));
//      System.out.println("Controller Output");
//      System.out.print(ap1.toString());
      assertEquals(String.format("%s%s", gameStateString, quitMessage), ap1.toString());
    }
  }
}
