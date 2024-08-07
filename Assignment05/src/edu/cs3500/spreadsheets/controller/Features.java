package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;

import edu.cs3500.spreadsheets.view.WorksheetEditableView;

public interface Features {
  public void selectCell(int x, int y);
  void exitProgram();
  public void setView(WorksheetEditableView view) throws IOException;
}
