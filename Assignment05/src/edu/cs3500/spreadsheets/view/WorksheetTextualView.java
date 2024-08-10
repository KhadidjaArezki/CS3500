package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;

/**
 * DONE:
 * Design a view implementation that takes a model and an Appendable, and renders
 * the model into the Appendable in the same format as you read files in Assignment 5.
 * You might find the PrintWriter class to be a convenient Appendable for actually
 * writing files to disk. Your output does not have to reproduce any comments or
 * specific formatting that was in the original file, and does not have to print the
 * cells back in the same order that they were in the original file.
 * 
 * One useful test case might be to read in a file, render it through this view,
 * and then read this view’s output back in as a second model, and check that the
 * two models are equivalent. (This is sort of a “round-trip test”, and is a very
 * common pattern for testing file reading and writing.)
 */

public class WorksheetTextualView implements WorksheetView {
  private final ReadOnlyWorksheetModel roModel;
  private final Appendable ap;
  
  public WorksheetTextualView(ReadOnlyWorksheetModel roModel, Appendable ap) {
    this.roModel = roModel;
    this.ap = ap;
  }
  
  @Override
  public void render() throws IOException{
    try {
      this.ap.append(toString());
    } catch(Exception e) {
        throw new IOException();
    }
  }
  
  public String toString() {
    String str = "";
    for (int i=0; i<this.roModel.getWorksheetSize(); i++) {
      for (int j=0; j<this.roModel.getRowWidth(i); j++) {
        Cell cell = this.roModel.selectCell(i, j);
        if (Objects.nonNull(cell.getContents())) {
          str += String.format("%s\n", cell.toString());
        }
      }
    }
    return str;
  }
}
