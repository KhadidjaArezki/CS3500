package cs3500.pyramidsolitaire.view;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * There are four possible outputs for your toString method:

 * If the game is not started, your toString should return the empty string "", and nothing else.
 * If the pyramid is emptied, your toString should simply return the string "You win!", and nothing else.
 * If there are no remaining moves available (and therefore the game is over), your view should simply print
 * "Game over. Score: ##", where "##" is the current score of the pyramid.
 * Otherwise, render the following. An individual card should be rendered as its value followed by its suit
 * (which is one of the following four characters: '♣' '♦' '♥' '♠').
 
 * Each card should “visually” be three characters wide: for a one-digit card, you may need to add an extra
 * space for padding.
 * If a position is blank, you should fill it with spaces. There should be one blank space between each
 * (3-character-wide) column of cards.
 * If a row is completely empty, you should still render a blank line.
 * Beneath the pyramid is the word "Draw: ", followed by a comma-space-separated list of the card(s)
 * currently available in the stock. 
 
 * The following shows a possible output, after some cards have been removed (the highlighting is there
 * only to illustrate exactly where any spaces are):
 
              A♣
            2♣  3♣
          4♣  5♣  6♣
        7♣  8♣  10♣ 10♥
      J♣  Q♣  K♣  A♦  2♦
    3♦  4♦      6♦  7♦  8♦
  9♦  10♦         K♦  A♥
  Draw: 5♥
 
 * NOTE: The text you produce should not have a newline at the end of PyramidSolitaireModelthe last line. Also, there should be no
 * spaces after the last card in a row. Clarification: if no draw cards are available, you should eliminate
 * the trailing space after "Draw:" as well.
 
 * TODO:
 * Add a second constructor to your PyramidSolitaireTextualView class, that takes in both a model and an Appendable,
 * and implement render such that it appends the current textual output to that Appendable.
 */

public class PyramidSolitaireTextualView implements PyramidSolitaireView{
  private final PyramidSolitaireModel<?> model;
  private final Appendable ap;
  // ... any other fields you need

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.ap = System.out;
  }
  
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }
  
  public void render() throws IOException {
    try {
      this.ap.append(toString());
    } catch(IllegalStateException e) {
        throw new IOException();
    }
  }
  
  private int distanceToBottom(int i) {
    int indexLastRow = this.model.getNumRows() - 1;
    return indexLastRow - i;
  }
  
  private String addSpaces(int i) {
    String str = "";
    int j = 0;
    int dist2Bottom = distanceToBottom(i);
    while (j < dist2Bottom) {
      str+="  ";
      j++;
    }
    return str;
  }

  @Override
  public String toString() throws IllegalStateException{
    if (this.model.getNumRows() == -1) return ""; // game has not started yet
    else if (this.model.getScore() == 0) return "You win!"; // empty pyramid
    else if (this.model.isGameOver())
      return String.format("Game over. Score: %d", this.model.getScore()); // no more moves possible
    else {
      String str = "";
      for (int i=0; i < this.model.getNumRows(); i++) {
        str += addSpaces(i);
        for (int j=0; j < this.model.getRowWidth(i); j++) {
          String cardStr = "";
          if (Objects.isNull(this.model.getCardAt(i, j))) cardStr = "   ";
          else cardStr = this.model.getCardAt(i, j).toString();
          str += cardStr;
          if (cardStr.length() < 3 && !(j == this.model.getRowWidth(i) - 1)) str += " ";
          if (!(j == this.model.getRowWidth(i) - 1)) str += " ";
        }
        str += "\n";
      }
      
      str += "Draw:";
      List<?> drawCards = this.model.getDrawCards();
      for (int k=0; k < drawCards.size(); k++) {
        if (!Objects.isNull((drawCards).get(k))) {
          if (!(drawCards.size() == 0)) str += " ";
          str += drawCards.get(k).toString();
          if (! ( k == drawCards.size() - 1)) str += ",";
          else str += "\n";
        }
      }
      return str;
    }
  }
}
