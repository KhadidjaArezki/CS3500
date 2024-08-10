package cs3500.pyramidsolitaire.model.hw04;
import org.junit.Before;
import org.junit.Test;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

/** Tests for {@link TriPeaksPyramidSolitaireModel}s. 
 * @param <K>*/    
public class TriPeaksPyramidSolitaireTest extends PyramidSolitaireModelTest.BasicPyramidSolitaireTest {
  private TriPeaksPyramidSolitaire tripeaks() {
    return new TriPeaksPyramidSolitaire();
  }
  
  PyramidSolitaireModel<Card> tripeaksSolitaire = tripeaks();
  PyramidSolitaireTextualView pyramidView = new PyramidSolitaireTextualView(tripeaksSolitaire);
  int numRows = 7;
  int numDraw = 3;
  List<Card> deck = tripeaksSolitaire.getDeck();
  
  @Before
  public void startGame() {
    tripeaksSolitaire.startGame(deck, true, numRows, numDraw);
//    System.out.println(pyramidView);
  }
  
  @Test
  public void testIsGameOver() {
//    System.out.println(pyramidView);
    assertFalse(tripeaksSolitaire.isGameOver());
  }
}