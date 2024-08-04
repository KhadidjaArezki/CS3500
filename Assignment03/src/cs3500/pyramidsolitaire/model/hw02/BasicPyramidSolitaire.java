package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Game play:
 * A standard play of this game starts by shuffling a standard deck of 52 cards, and dealing them out into a 7-row triangle,
 * where each card is partially covered by the two cards beneath it. The remaining cards are placed face-down in a pile
 * called the stock. Some number of cards (typically three) from the stock are then turned face-up.

 * We say that a card is exposed if it is not covered by any cards in rows beneath it. We give cards values:
 * number cards have value equal to their number, Jacks have value 11, Queens have value 12, and Kings have value 13.
 * The primary rule of the game is: You are allowed to remove either one or two exposed cards, if the sum of their values is 13.
 * (Therefore, you can remove a King by itself, or a Queen and ace, or a Jack and a two, etc.) You may also turn over one
 * (or more depending on the variation) card from the stock, and try to use that to remove a card from the pyramid.
 * The visible card(s) from the stock is called a “draw” card.
 * When a draw card is discarded or used, it should be replaced with the next card from the stock pile. Any other draw
 * cards should not be affected.

 * The score of the game is the sum of the values of all remaining cards in the pyramid. The goal of the game is to obtain
 * the lowest score: a perfect zero means the player has eliminated all of the cards in the pyramid.
 
 * TODO:
 * 1. Design a suitable representation of this game. Think carefully about what fields and types you will need, and how
 * possible values of the fields correspond to game states.
 
 * 2. Positioning: A position is specified using a pair (row, card), assuming that the pyramid is laid out from top to bottom
 * and left to right. The row and card numbers start at 0 at the top of the pyramid and 0 at the left, increasing top to
 * bottom and left to right respectively. For instance, in the example output shown below, the Q♣ is at position (4, 1).
 
 * 3. Instantiating the game: Your class should define at least one constructor with zero arguments, which initializes your
 * game into a state that’s ready for someone to call startGame and begin playing. You may define whatever other
 * constructors you wish; consider carefully all the methods you are expected to implement, and design your code to avoid
 * as much duplication as possible.
 
 * 4. Encapsulation: Your BasicPyramidSolitaire class should not have any public fields, nor any public methods other than
 * constructors and the public methods required by the PyramidSolitaireModel interface.
 
 * Be sure to properly document your code with Javadoc as appropriate.
 
 * In order to see the contents of your model, while still keeping the implementation details separate from the rendering
 * details, you will create and implement the following class in the cs3500.pyramidsolitaire.view package.

 */

public class BasicPyramidSolitaire<K> implements PyramidSolitaireModel<K> {
  private List<K> deck;
  private int numRows = -1;
  private int numDraw = -1;
  private int drawOffset;
  private int score;
  private List<List<K>> pyramid;
  private List<K> drawCards;
  
  public BasicPyramidSolitaire() {}
  
  @SuppressWarnings("unchecked")
  public List<K> getDeck() {
    return (List<K>) Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
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
  }
  
  public void startGame(List<K> deck, boolean shuffle, int numRows, int numDraw) {
    if (deck == null) throw new IllegalArgumentException();
    if (numRows < 0 || numDraw < 0) throw new IllegalArgumentException();
    List<K> validDeck = getDeck();
    if (!(deck.get(0) instanceof Card)) throw new IllegalArgumentException();
    if (deck.size() != validDeck.size()) throw new IllegalArgumentException();
    for(K card: deck) {
      if (!validDeck.contains(card)) throw new IllegalArgumentException();
    }
    // Deck is valid
    if ((numRows * (numRows + 1)/2) > validDeck.size()) throw new IllegalArgumentException(); // number of cards required to fill all rows exceeds deck size
    if (numDraw > validDeck.size() - (numRows * (numRows + 1)/2)) throw new IllegalArgumentException();
    
    if (shuffle) Collections.shuffle(deck);
    this.deck = deck;
    this.numRows = numRows;
    this.numDraw = numDraw;
    this.pyramid = new ArrayList<List<K>>();
    int drawCardsOffset;
    int i = 0;
    int j = 0;
    int m = 0;
    while (i<numRows) {
      List<K> row = new ArrayList<K>();
      j = 0;
      while (! (j>i)) {
        K c = deck.get(m);
        row.add(c);
        this.score += ((Card) c).value;
        j++;
        m++;
      }
      this.pyramid.add(row);
      i++;
    }
    drawCardsOffset = m;
    
//    for (List<K> row: this.pyramid) {
//      for (K card: row) System.out.print(card.toString());
//      System.out.println();
//    }
    this.drawCards = new ArrayList<K>();
    int k=0;
    while (k<numDraw) {
      drawCards.add(deck.get(drawCardsOffset+k));
//      System.out.println(drawCards.get(k));
      k++;
    }
    this.drawOffset = drawCardsOffset+k+1;
//    System.out.println("Draw Offset: "+ (this.drawOffset));
  }
  
  private boolean isValidMove(int row, int card) {
    return row < getNumRows() && row >= 0 && // row number is valid
      card < this.pyramid.get(row).size() && card >= 0 && // column number is valid
      (row == this.pyramid.size() - 1 || // card is either in the last card
      this.pyramid.get(row + 1).get(card) == null &&
      this.pyramid.get(row + 1).get(card + 1) == null) && // or, if not, card is not covered by the two cards beneath it   
      this.pyramid.get(row).get(card) != null; // card is in pyramid
  }
  
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (!isValidMove(row1, card1) || !isValidMove(row2, card2)) throw new IllegalArgumentException("Invalid Row Number or Card.");
    if (((Card) this.pyramid.get(row1).get(card1)).value + ((Card) this.pyramid.get(row2).get(card2)).value != 13) 
      throw new IllegalArgumentException("The Sum of The Values of The Two Cards is not 13.");
    this.score -= ((Card) this.pyramid.get(row1).get(card1)).value;
    this.pyramid.get(row1).set(card1, null);
    this.score -= ((Card) this.pyramid.get(row2).get(card2)).value;
    this.pyramid.get(row2).set(card2, null);
  }
  
  public void remove(int row, int card) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! isValidMove(row, card)) throw new IllegalArgumentException("Invalid Row Number or Card.");
    if (((Card) this.pyramid.get(row).get(card)).value != 13) throw new IllegalArgumentException("Card Vlue is not 13."); 
    this.score -= ((Card) this.pyramid.get(row).get(card)).value;
    this.pyramid.get(row).set(card, null);
  }
  
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException{
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet.");
    if (! (drawIndex < getDrawCards().size())) throw new IllegalArgumentException("Draw Index Invalid.");
    if (! isValidMove(row, card)) throw new IllegalArgumentException("Invalid Row Number or Card.");
    if (((Card) this.pyramid.get(row).get(card)).value + ((Card) this.getDrawCards().get(drawIndex)).value != 13)
      throw new IllegalArgumentException("The Sum of The Values of The Two Cards is not 13.");
    this.score -= ((Card) this.pyramid.get(row).get(card)).value;
    this.pyramid.get(row).set(card, null);
    if (drawOffset < deck.size()) {
      this.drawCards.set(drawIndex, this.deck.get(this.drawOffset));
      this.drawOffset++;
    } else this.drawCards.set(drawIndex, null);
  }
  
  public void discardDraw(int drawIndex) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! (drawIndex < getDrawCards().size())) throw new IllegalArgumentException("Draw Index Invalid.");
    if (drawOffset < deck.size()) {
      this.drawCards.set(drawIndex, this.deck.get(this.drawOffset));
      this.drawOffset++;
    } else this.drawCards.set(drawIndex, null);
  }
  
  public int getNumRows() {
    return this.numRows;
  }
  
  public int getNumDraw(){
    return this.numDraw;
  }
  
  public int getRowWidth(int row) {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! (row < getNumRows()) || row < 0) throw new IllegalArgumentException("Row Number is Invalid");
    return this.pyramid.get(row).size();
  }
  
  private int getPyramidSize() {
    return getNumRows() * (getNumRows() + 1)/2;
  }
  
  private K getPyramidCardAt(int index) {
    int rowIndex;
    int cardIndex;
    for(int i=0; i<getNumRows(); i++) {
      int currRowStart = i*(i+1)/2;
      int nextRowStart = (i+1)*(i+2)/2;
      if (index >= currRowStart && index < nextRowStart) {
        rowIndex = i;
        cardIndex = index - currRowStart;
        return this.pyramid.get(rowIndex).get(cardIndex);
      }
    }
    return null;
  }
  public boolean isGameOver() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    /* check if any valid move can be made. If not then, end game  a valid move can be made if 
     * there is one or two cards in the pyramid (or one in the pyramid and one draw) with a sum of 13
     */
    if (getScore() == 0) return true;
    for (int i=0; i < getPyramidSize()-1; i++) {
      Card card1 = (Card) this.deck.get(i);      
      if (getPyramidCardAt(i) == null) continue;
      if (card1.value == 13) return false;
      for (K drawCard: getDrawCards()) {
        if (card1.value + ((Card) drawCard).value == 13) return false;
      }
      for (int j=i+1; j < getPyramidSize(); j++) {
        Card card2 = (Card) this.deck.get(j);
        if (getPyramidCardAt(j) == null) continue;
        if (card1.value + card2.value == 13) return false;
      }
    }
    return true;
  }
  
  public int getScore() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    return this.score;
  }
  
  public K getCardAt(int row, int card) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! (row < getNumRows()) || row < 0) throw new IllegalArgumentException("Row Number is Invalid");
    if (! (card < this.pyramid.get(row).size()) || card < 0)throw new IllegalArgumentException("Card is Invalid");
    return this.pyramid.get(row).get(card);
  }
  
  public List<K> getDrawCards() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    return this.drawCards;
  }
}
