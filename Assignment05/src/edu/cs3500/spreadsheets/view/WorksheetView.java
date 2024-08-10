package edu.cs3500.spreadsheets.view;

import java.io.IOException;

/**
 * Renders a {@link Worksheet} in some manner.
 */
public interface WorksheetView {
  
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  public void render() throws IOException;
}
