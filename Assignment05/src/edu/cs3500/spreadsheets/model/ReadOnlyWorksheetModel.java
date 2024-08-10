package edu.cs3500.spreadsheets.model;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;

public class ReadOnlyWorksheetModel implements Worksheet {

  private Worksheet delegate;
  
  public ReadOnlyWorksheetModel(Worksheet model) {
    this.delegate = model;
  }
  @Override
  public Sexp readCell(String cellName) {
    return delegate.readCell(cellName);
  }

  @Override
  public void writeCell(String cellName, String contents) {
    throw new UnsupportedOperationException("Cannot write into a Read-only Model.");
  }

  @Override
  public Sexp evalCell(String cellName) {
    return delegate.evalCell(cellName);
  }

  @Override
  public Cell selectCell(String cellName) {
    return delegate.selectCell(cellName);
  }

  @Override
  public List<Cell> selectRange(String rangeName) {
    return delegate.selectRange(rangeName);
  }

  @Override
  public Appendable save(Appendable ap) throws IOException {
    return delegate.save(ap);
  }
  @Override
  public Cell selectCell(int row, int col) {
    if (row < getWorksheetSize() && col < getRowWidth(row)) {
      return delegate.selectCell(row, col);
    }
    else throw new IndexOutOfBoundsException("Cell Not In Worksheet.");
  }
  @Override
  public int getWorksheetSize() {
    return delegate.getWorksheetSize();
  }
  @Override
  public int getRowWidth(int row) {
    return delegate.getRowWidth(row);
  }
  @Override
  public Sexp evalCell(Sexp exp) {
    return delegate.evalCell(exp);
  }
  @Override
  public List<String> getRefs(String cellName) {
    return delegate.getRefs(cellName);
  }
  @Override
  public Sexp getNewRefVal(String targetCellName, Sexp newValue, Sexp refCellContents) {
    return delegate.getNewRefVal(targetCellName, newValue, refCellContents);
  }

}
