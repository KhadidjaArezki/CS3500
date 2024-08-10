package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

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

  public String getEditedCellContents();

  public String getSelectedCellName();
  
  public void rejectCellEdit(String contents);

  public void acceptCellEdit();

  public void refresh();

  public void setInput(String contents);

  void showErrorMessage(String error);

  public void setWorksheetMaxRowWidth(int maxWidth);

  public int getWorksheetMaxRowWidth();

  public int getMinNumRows();

  public void setWorksheetSize(Coord coord);

  public Coord getWorksheetSize();

  public void setWorksheetCells(List<List<String>> cells);

  public void setWorksheetHeaders(List<String> headers);

}
