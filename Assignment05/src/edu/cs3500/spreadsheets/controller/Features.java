package edu.cs3500.spreadsheets.controller;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.WorksheetEditableView;

public interface Features {
  public void setView(WorksheetEditableView view) throws IOException;
  public void selectCell(int x, int y);
  public void acceptCellEdit();
  void rejectCellEdit();
  void exitProgram();
  public void updateCell();
  public void saveChanges() throws IOException;
  public void close();
}
