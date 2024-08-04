package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

/*
 * Factory class to create pyramid solitaire models.
 */

public class PyramidSolitaireCreator {
  public enum GameType {
    BASIC, RELAXED, TRIPEAKS
  }
  public static PyramidSolitaireModel<?> create(GameType type) {
    switch(type) {
    case BASIC:
      return new BasicPyramidSolitaire();
    case RELAXED:
      return new RelaxedPyramidSolitaire();
    case TRIPEAKS:
      return new TriPeaksPyramidSolitaire();
    default:
      throw new IllegalArgumentException("Unknown Game Variant.");
    }
  }
}
