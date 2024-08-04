package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;

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

public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {
  private List<Card> deck;
  private int numRows = -1;
  private int numDraw = -1;
  private int drawOffset;
  private int score;
  private List<List<Card>> pyramid;
  private List<Card> drawCards;
  
  public BasicPyramidSolitaire() {}
  
  protected List<List<Card>> getPyramid() {
    return this.pyramid;
  }
  
  protected void setPyramid(List<List<Card>> pyramid) {
    this.pyramid = pyramid;
  }
  
  protected void setScore(int newScore) {
    this.score = newScore;
  }
  
  protected int getDrawOffset() {
    return this.drawOffset;
  }
  
  protected void setDrawOffset(int newOffset) {
    this.drawOffset = newOffset;
  }
  
  protected void setPyramidCard(int row, int oldCard, Card newCard) {
    getPyramid().get(row).set(oldCard, newCard);
  }
  
  protected void setDrawCards(int oldDrawIndex, Card newDrawCard) {
    getDrawCards().set(oldDrawIndex, newDrawCard);
  }
  
  @Override
  public List<Card> getDeck() {
    return (List<Card>) Arrays.asList(new Card("A", 1, "♣"), new Card("A", 1, "♦"), new Card("A", 1, "♥"), new Card("A", 1, "♠"),
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
  
  protected void stackPyramid(List<Card> deck) {
    int m = 0;
    for (int i=0; i < getNumRows(); i++) {
      List<Card> row = new ArrayList<Card>(i+1);
      for (int j=0; j <= i; j++) {
        Card c = deck.get(m);
        row.add(c);
        setScore(getScore() + ((Card) c).getValue());
        m++;
      }
      getPyramid().add(row);
    }
  }
  
  protected void validateDeck(List<Card> deck) {
    List<Card> validDeck = getDeck();
    if (deck.size() != validDeck.size()) throw new IllegalArgumentException();
    for(Card card: deck) {
      if (!validDeck.contains(card)) throw new IllegalArgumentException();
    }
  }
  
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw) {
    if (deck == null) throw new IllegalArgumentException();
    if (numRows < 0 || numDraw < 0) throw new IllegalArgumentException();
    validateDeck(deck);
    
    int pyramidSize = getPyramidSize(numRows);
    if (pyramidSize > deck.size()) throw new IllegalArgumentException(); // number of cards required to fill all rows exceeds deck size
    if (numDraw > deck.size() - pyramidSize) throw new IllegalArgumentException();
        
    if (shuffle) Collections.shuffle(deck);
    this.deck = deck;
    this.numRows = numRows;
    this.numDraw = numDraw;
    setPyramid(new ArrayList<List<Card>>());
    
    stackPyramid(deck);
    int drawCardsOffset = pyramidSize;
    
//    for (List<K> row: this.pyramid) {
//      for (K card: row) System.out.print(card.toString());
//      System.out.println();
//    }
    this.drawCards = new ArrayList<Card>();
    int k=0;
    while (k<numDraw) {
      getDrawCards().add(this.deck.get(drawCardsOffset+k));
//      System.out.println(drawCards.get(k));
      k++;
    }
    this.drawOffset = drawCardsOffset+k+1;
//    System.out.println("Draw Offset: "+ (this.drawOffset));
  }
  
  protected void validateArguments(int row, int card) {
    if (row >= getNumRows() || row < 0) throw new IllegalArgumentException("Invalid Row Number.");
    else if (card >= getRowWidth(row) || card < 0) throw new IllegalArgumentException("Invalid Card Number.");
    else if (getCardAt(row, card) == null) throw new IllegalArgumentException("Card not in pyramid.");
  }
  
  private void validRemoveOneMove(int row, int card) {
    validateArguments(row, card);
    if (row != getNumRows() - 1 && // card is not in the last row
      (getCardAt(row + 1,card) != null || // and card is covered by one or two cards beneath
      getCardAt(row + 1, card + 1) != null))
      throw new IllegalArgumentException("Card is partially or totally covered.");
  }
  
  protected void validRemoveTwoMove(int row1, int card1, int row2, int card2) {
    validRemoveOneMove(row1, card1);
    validRemoveOneMove(row2, card2);
  }
  
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    validRemoveTwoMove(row1, card1, row2, card2);
    if (getCardAt(row1, card1).getValue() +  getCardAt(row2, card2).getValue() != 13) 
      throw new IllegalArgumentException("The Sum of The Values of The Two Cards is not 13.");
    setScore(getScore() - getCardAt(row1, card1).getValue());
    setPyramidCard(row1, card1, null);
    setScore(getScore() - getCardAt(row2, card2).getValue());
    setPyramidCard(row2, card2, null);
  }

  public void remove(int row, int card) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    validRemoveOneMove(row, card);
    if (getCardAt(row, card).getValue() != 13) throw new IllegalArgumentException("Card Vlue is not 13."); 
    setScore(getScore() - getCardAt(row, card).getValue());
    setPyramidCard(row, card, null);
  }
  
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException{
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet.");
    if (! (drawIndex < getDrawCards().size())) throw new IllegalArgumentException("Draw Index Invalid.");
    validRemoveOneMove(row, card);
    if ( getCardAt(row, card).getValue() + getDrawCards().get(drawIndex).getValue() != 13)
      throw new IllegalArgumentException("The Sum of The Values of The Two Cards is not 13.");
    setScore(getScore() - getCardAt(row, card).getValue());
    setPyramidCard(row, card, null);
    if (drawOffset < deck.size()) {
      setDrawCards(drawIndex, getDeck().get(getDrawOffset()));
      setDrawOffset(getDrawOffset() + 1);
    } else setDrawCards(drawIndex, null);
  }
  
  public void discardDraw(int drawIndex) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! (drawIndex < getDrawCards().size())) throw new IllegalArgumentException("Draw Index Invalid.");
    if (drawOffset < deck.size()) {
      setDrawCards(drawIndex, getDeck().get(getDrawOffset()));
      setDrawOffset(getDrawOffset() + 1);
    } else setDrawCards(drawIndex, null);
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
    return getPyramid().get(row).size();
  }
  
  protected int getPyramidSize(int numRows) {
    return numRows * (numRows + 1)/2;
  }
  
  protected Card getPyramidCardAt(int index) {
    int rowIndex;
    int cardIndex;
    int currRowStart;
    int nextRowStart;
    for(int i=0; i<getNumRows(); i++) {
      currRowStart = i*(i+1)/2;
      nextRowStart = currRowStart + getRowWidth(i);
//      int nextRowStart = (i+1)*(i+2)/2;
      if (index >= currRowStart && index < nextRowStart) {
        rowIndex = i;
        cardIndex = index - currRowStart;
        return getCardAt(rowIndex, cardIndex);
      }
    }
    return null;
  }
  
  public boolean isGameOver() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    /* check if any valid move can be made. If not then, end game. A valid move can be made if 
     * there is one or two cards in the pyramid (or one in the pyramid and one draw) with a sum of 13
     */
    if (getScore() == 0) return true;
    for (int i=0; i < getPyramidSize(getNumRows())-1; i++) {
      Card card1 = this.deck.get(i);      
      if (getPyramidCardAt(i) == null) continue;
      if (card1.getValue() == 13) return false;
      for (Card drawCard: getDrawCards()) {
        if (card1.getValue() + drawCard.getValue() == 13) return false;
      }
      for (int j=i+1; j < getPyramidSize(getNumRows()); j++) {
        Card card2 = this.deck.get(j);
        if (getPyramidCardAt(j) == null) continue;
        if (card1.getValue() + card2.getValue() == 13) return false;
      }
    }
    return true;
  }
  
  public int getScore() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    return this.score;
  }
  
  public Card getCardAt(int row, int card) throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    if (! (row < getNumRows()) || row < 0) throw new IllegalArgumentException("Row Number is Invalid");
    if (! (card < getRowWidth(row)) || card < 0)throw new IllegalArgumentException("Card is Invalid");
    return getPyramid().get(row).get(card);
  }
  
  public List<Card> getDrawCards() throws IllegalStateException {
    if (getNumRows() == -1) throw new IllegalStateException("Game has not started yet");
    return this.drawCards;
  }
}
