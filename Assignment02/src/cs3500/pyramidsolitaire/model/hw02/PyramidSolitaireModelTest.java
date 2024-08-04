package cs3500.pyramidsolitaire.model.hw02;

import org.junit.Test;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Arrays;

/** Tests for {@link PyramidSolitaireModel}s. 
 * @param <K>*/
public abstract class PyramidSolitaireModelTest<K> {
  protected abstract PyramidSolitaireModel<K> basic();
  
  public static final class BasicPyramidSolitaireTest<K> extends PyramidSolitaireModelTest<K> {
    
    @Override
    protected PyramidSolitaireModel<K> basic() {
      return new BasicPyramidSolitaire<K>();
    }
    
    /** Helper methods */
    private String getCardName(int value) {
      if (value == 1) return "A";
      else if (value == 11) return "J";
      else if (value == 12) return "Q";
      else if (value == 13) return "K";
      else return String.valueOf(value);
    }
    @SuppressWarnings("unchecked")
    private List<K> getRegularDeck() {
      Card[] regularDeck = new Card[52];
      String[] suits = {"♣", "♦", "♥", "♠"};
      int i = 0;
      while (i < 13) {
        String name = getCardName(i+1);
        for (int j=0; j<4; j++) {
          regularDeck[i+j] = new Card(name, i+1, suits[j]);
        }
        i++;
      }
      //List<K> l = (List<K>) List.of(regularDeck);
      //System.out.print(l);
      //return l;
      return (List<K>) Arrays.asList(regularDeck);
    }
  
    @SuppressWarnings("unchecked")
    List<K> regularDeck = (List<K>) Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
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

    PyramidSolitaireModel<K> basicSolitaire = basic();
    PyramidSolitaireTextualView pyramidView = new PyramidSolitaireTextualView(basicSolitaire);
    int numRows = 7;
    int numDraw = 3;
    List<K> deck = basicSolitaire.getDeck();
    
//    @Test
//    public void testGetDeck() {
//      
//    }
    
//    @Test
//    public void testStartGame() {
//      basicSolitaire.startGame(deck, true, numRows, numDraw);
//      System.out.println(pyramidView);
//    }
    
    @Test
    public void testIsGameOver() {
      basicSolitaire.startGame(deck, true, numRows, numDraw);
      System.out.println(pyramidView);
      assertFalse(basicSolitaire.isGameOver());
    }
  }
}
