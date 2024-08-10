package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

/**
 * While your builder creates a worksheet whose contents have been filled in from somewhere, the resulting
 * model should still be editable. Make sure you do not inadvertently build in assumptions about the size
 * or contents of your model based on what the builder produces.
 
 *   - {@link createCell}: Your implementation of this method should determine what kind of data this cell should
 *     contain, and record this information in your model somehow.
 *   - {@link createWorkSheet} returns the worksheet you have filled in.
 *     If you need to do any additional processing to fully construct your model before returning it, do so here.
 */

public class WorksheetBuilderImp implements WorksheetBuilder<Worksheet> {
  
  private List<List<Cell>> worksheet;
  
  public WorksheetBuilderImp() {
    this.worksheet = getNewWorksheet();
  }
  
  private static List<List<Cell>> getNewWorksheet() {
    int newWorksheetSize = 1000;
    int newRowSize = 1000;
    List<List<Cell>> newWorksheet = new ArrayList<List<Cell>>(newWorksheetSize);
    
    for (int i=0; i<newWorksheetSize; i++) {
      List<Cell> newRow = new ArrayList<Cell>(newRowSize);
      for (int j=0; j<newRowSize; j++) {
        newRow.add(new Cell(new Coord(j+1, i+1), null));
      }
      newWorksheet.add(newRow);
    }
    return newWorksheet;
  }
  
  /**
   * 
   * @param worksheet a matrix of {@link Cell}s - may be empty or null.
   * @param row the minimum size of the new worksheet.
   * @param col the mi,imum size of rows in the new worksheet.
   * @return a new bigger worksheet with the contents of the old worksheet.
   */

  // TODO: DON'T CREATE EMPTY CELLS. INSTEAD ADD CELLS WHEN NECESSARY
  public static List<List<Cell>> expandWorksheet(List<List<Cell>> worksheet, int row, int col) {
    if (Objects.isNull(worksheet) || worksheet.size() == 0) return getNewWorksheet();

    int newRowSize = Math.max(col*2, worksheet.get(0).size()*2);
    int newWorksheetSize = Math.max(row*2, worksheet.size()*2);

    List<List<Cell>> newWorksheet = new ArrayList<List<Cell>>(newWorksheetSize);
    
    for (int i=0; i<worksheet.size(); i++) {
      List<Cell> oldRow = worksheet.get(i);
      List<Cell> newRow = new ArrayList<Cell>(newRowSize);
      Collections.copy(newRow, oldRow);
      for (int j=oldRow.size(); j<newRowSize; j++) {
        newRow.set(j, new Cell(new Coord(j+1, i+1), null));
      }
      newWorksheet.set(i, newRow);
    }
    
    for (int k=worksheet.size(); k<newWorksheetSize; k++) {
      List<Cell> newEmptyRow = new ArrayList<Cell>(newRowSize);
      for (int m=0; m<newRowSize; m++) {
        newEmptyRow.set(m, new Cell(new Coord(m+1, k+1), null));
      }
      newWorksheet.set(k, newEmptyRow);
    }
    return newWorksheet;
  }
  
  @Override
  public WorksheetBuilder<Worksheet> createCell(int col, int row, String contents) {
    if (row < 1 || col < 1) throw new IllegalArgumentException("Invalid Cell Coordinates.");
    if (contents.startsWith("=")) contents = contents.substring(1);
    
    row = row - 1;
    col = col - 1;
    if (row >= worksheet.size() || col >= worksheet.get(row).size()) {
      this.worksheet = expandWorksheet(this.worksheet, row, col);
    }
    
    if (Objects.isNull(contents)) this.worksheet.get(row).get(col).setContents(null);
    else {
      try {
        Sexp exp = Parser.parse(contents);
        this.worksheet.get(row).get(col).setContents(exp);
      } catch (IllegalArgumentException e) {
          String cellName = String.format("%s%f", Coord.colIndexToName(col+1), row+1);
          throw new IllegalArgumentException(String.format("Error in cell %s: Cannot Parse Cell Contents.", cellName));
      }
    }
    return this;
  }

  @Override
  public Worksheet createWorksheet() {
    return new WorksheetModel(this.worksheet);
  }

}
