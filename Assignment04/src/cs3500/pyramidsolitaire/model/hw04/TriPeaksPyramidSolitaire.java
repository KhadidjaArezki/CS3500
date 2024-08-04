package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

/*
 * TriPeaks uses the same rules as the original game, but uses a larger board and a larger deck of cards:

            J♣          5♥          7♥
          9♠  10♦     Q♥  10♥     3♣  6♥
        3♦  8♣  A♥  4♥  4♣  K♥  6♠  3♦  10♠
      Q♦  5♥  J♠  9♦  9♠  7♦  3♠  8♣  4♠  5♣
    A♥  K♣  3♠  8♥  A♠  9♥  3♣  Q♣  3♥  4♣  10♥
  8♦  Q♦  K♦  2♠  A♠  6♣  8♠  A♦  3♥  6♣  2♠  7♣
K♦  J♠  J♦  5♠  Q♣  8♠  10♠ 2♥  K♠  5♠  9♦  7♦  Q♠
Draw: K♥, 5♦, 10♣
Score: 452

 * As with the original game, we can parameterize this game by the number of rows (here, 7)
 * and the number of displayed draw cards (here, 3).
 * To play this game we need a double deck, containing 104 cards (two of each unique card).
 * The three pyramids should overlap for half their height (rounding up):
 * here, a 7-row pyramid should overlap for 4 rows.
 * As above, this variant should coexist with the other two.
 */

public class TriPeaksPyramidSolitaire extends BasicPyramidSolitaire {
  private int score;
  private List<List<Card>> pyramid;
  
  public TriPeaksPyramidSolitaire() {}

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    List<Card> regularDeck = super.getDeck();
    for (Card card: regularDeck) {
      deck.add(card);
      deck.add(card);
    }
    return deck;
  }
  
  @Override
  public int getScore() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    return this.score;
  }
  
  @Override
  protected void setScore(int newScore) {
    this.score = newScore;
  }
  
  @Override
  protected List<List<Card>> getPyramid() {
    return this.pyramid;
  }
  
  @Override
  protected void setPyramid(List<List<Card>> pyramid) {
    this.pyramid = pyramid;
  }

  protected void stackPyramid(List<Card> deck) {
    int m = 0;
    int currRowWidth;
    int prevRowWidth = 0;
    // if rownum <= numrows/2: add 3*(i+1) cards per row, else add 1 to the width of previous row
    for (int i=0; i < getNumRows(); i++) {
      if (i < getNumRows()/2) currRowWidth = 3*(i+1);
      else currRowWidth = prevRowWidth + 1;
      List<Card> row = new ArrayList<Card>(currRowWidth);
      for (int j=0; j < currRowWidth; j++) {
        Card c = deck.get(m);
        row.add(c);
        setScore(getScore() + c.getValue());
        m++;
      }
      prevRowWidth = currRowWidth;
      this.pyramid.add(row);
    }
//    for (List<K> row: this.pyramid) {
//      for (K card: row) System.out.print(card.toString());
//      System.out.println();
//    }
//    System.out.println(this.pyramid.get(0).size());
  }
    
  
  protected void validateDeck(List<Card> deck) {
    List<Card> regularDeck = super.getDeck();
    if (deck.size() != 2 * regularDeck.size()) throw new IllegalArgumentException();
    HashMap<Card, Integer> cardCounts = new HashMap<Card, Integer>(regularDeck.size());
    for(Card card: deck) {
      if (regularDeck.contains(card)) {
        if (!cardCounts.containsKey(card)) cardCounts.put(card, 1); 
        else cardCounts.replace(card, cardCounts.get(card) + 1);
      } else throw new IllegalArgumentException();
    }
    for (Entry<Card, Integer> entry: cardCounts.entrySet()) {
      if (entry.getValue() != 2) throw new IllegalArgumentException();
    }
  }
  
  @Override
  protected int getPyramidSize(int numRows) {
    int numOverlapRows = numRows - (numRows/2);
    return 3 * (numRows * (numRows + 1)/2) - (numOverlapRows * (numOverlapRows + 1));
  }
  
  private int getCurrRowStartIdx(int row) {
    if (row <= getNumRows()/2) return 3*(row*(row+1)/2);
    else {
      int sum = 0;
      for (int j=getNumRows()/2; j<row; j++) {
        sum+= getRowWidth(j);
      }
      return getCurrRowStartIdx(getNumRows()/2) + sum;
    }
  }
  
  @Override
  protected Card getPyramidCardAt(int index) {
    int rowIndex;
    int cardIndex;
    int currRowStart;
    int nextRowStart;
    for(int i=0; i<getNumRows(); i++) {
      currRowStart = getCurrRowStartIdx(i);
      nextRowStart = currRowStart + getRowWidth(i);
      if (index >= currRowStart && index < nextRowStart) {
        rowIndex = i;
        cardIndex = index - currRowStart;
        return getCardAt(rowIndex, cardIndex);
      }
    }
    return null;
  }
}
