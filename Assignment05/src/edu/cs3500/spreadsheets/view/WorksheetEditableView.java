package edu.cs3500.spreadsheets.view;

import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;

public interface WorksheetEditableView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  public void render() throws IOException;

  /**
   * Add ability to respond to certain user events
   * @param features a function object that defines application features
   */
  public void addFeatures(Features features);

  public void selectCell(int x, int y);
}
