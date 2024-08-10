package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

public final class Card {
  String name;
  int value;
  String suit;
  
  public Card (String name, int value, String suit) {
    this.name = name;
    this.value = value;
    this.suit = suit;
  }
   @Override
  public String toString() {
     return this.name + this.suit;
  }
  
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Card)) {
      return false;
    }
    return this.name == ((Card) that).name && this.getValue() == ((Card) that).getValue() && this.suit == ((Card) that).suit;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.suit);
  }
  public int getValue() {
    return value;
  }
} 
