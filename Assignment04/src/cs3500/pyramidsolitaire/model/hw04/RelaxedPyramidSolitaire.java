package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

/**
 * Relax the basic solitaire rules for which cards can be removed.
 
            A♣
          Q♣
 Draw:

 * If a card is covered by only one other card, and the player is trying to remove those
 * two cards as a pair, then we treat the pair as uncovered and permit it to be removed it
 * if adds up to 13 as desired.
 * This variant, of Relaxed Pyramid Solitaire, should coexist with the original game:
 * implementing it should not preclude playing the original.
 *
 */

public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {
  
  public RelaxedPyramidSolitaire() {}
  
  private void validateRemoveTwoMoveRelaxed(int row1, int card1, int row2, int card2) {
    if ((row1 != getNumRows() - 1 && row2 != getNumRows() - 1) &&
        ((getCardAt(row1 + 1, card1) != null && getCardAt(row1 + 1, card1 + 1) != null) ||
        (getCardAt(row1 + 1, card1) == null && (row2 != row1 + 1 || card2 != card1 + 1)) ||
        (getCardAt(row1 + 1, card1 + 1) == null && (row2 != row1 + 1 || card2 != card1))))
      throw new IllegalArgumentException("Card is partially or totally covered.");
  }
  
  @Override
  protected void validRemoveTwoMove(int row1, int card1, int row2, int card2) {
//    System.out.println(String.format("%d %d %d %d", row1, card1, row2, card2));
    validateArguments(row1, card1);
    validateArguments(row2, card2);
    validateRemoveTwoMoveRelaxed(row1, card1, row2, card2);
    validateRemoveTwoMoveRelaxed(row2, card2, row1, card1);
  }
}
