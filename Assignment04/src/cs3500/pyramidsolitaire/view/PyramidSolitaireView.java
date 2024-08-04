package cs3500.pyramidsolitaire.view;

import java.io.IOException;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;

/**
 * Renders a {@link PyramidSolitaireModel} in some manner.
 */
public interface PyramidSolitaireView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;
}
