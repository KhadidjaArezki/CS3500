package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;

import edu.cs3500.spreadsheets.view.WorksheetEditableView;

public interface Features {
  public void setView(WorksheetEditableView view) throws IOException;
  public void selectCell(int x, int y);
  public void acceptCellEdit();
  void rejectCellEdit();
  void exitProgram();
  public void updateCell();
}
