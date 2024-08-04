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

/** Tests for {@link RelaxedPyramidSolitaireModel}s. */

public class RelaxedPyramidSolitaireTest extends PyramidSolitaireModelTest.BasicPyramidSolitaireTest {
  private PyramidSolitaireModel<Card> relaxed() {
    return new RelaxedPyramidSolitaire();
  }
  
  PyramidSolitaireModel<Card> relaxedSolitaire = relaxed();
  PyramidSolitaireTextualView pyramidView = new PyramidSolitaireTextualView(relaxedSolitaire);
  int numRows = 7;
  int numDraw = 3;
//  List<K> regularDeck = super.getRegularDeck();
  
  @Before
  public void startGame() {
    relaxedSolitaire.startGame(deck, true, numRows, numDraw);
//    System.out.println(pyramidView);
  }
  
  @Test
  public void testIsGameOver() {
    relaxedSolitaire.startGame(deck, true, numRows, numDraw);
//    System.out.println(pyramidView);
    assertFalse(relaxedSolitaire.isGameOver());
  }
}

