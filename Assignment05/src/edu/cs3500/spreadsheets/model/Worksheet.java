package edu.cs3500.spreadsheets.model;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.SVal;
import edu.cs3500.spreadsheets.sexp.Sexp;

/** TODO:
 * Here are some aspects to think about:

   What does the model represent?
     - The model should be able to support at least the three types of values: boolean, double, string,
        though you might support additional ones.
     - The model should be able to support references to cells and to finite rectangular regions of
        cells, though you might try to support additional features.
     - The model should be able to support various formulas, although we’ve only seen sums, differences,
        products, square roots, and comparisons. Note that some formulas (like addition or multiplication)
        can take references of any size, while others (like subtraction or comparison) can only take
        references to single cells. You should consider carefully how to support and how to enforce this.
     - The model should be able to support editing cells’ contents, regardless of a cell’s location or
        prior contents.
   Remember to think from not only the implementors’ perspective (the people that are implementing the model)
   but also the client’s perspective (the people or classes that are using the model).
   What operations might they reasonably want to perform? What observations might they reasonably want to make? 

 * 1. Design a model to represent a spreadsheet.
 * This may consist of one or more interfaces, abstract classes, concrete classes, enums, etc.
 * Consider carefully what operations it should support, what invariants it assumes, etc.
 * You may assume that the number of rows or columns in a spreadsheet can each be represented
 * by a positive int, but you may not make any additional assumptions about them.
 * 
 *  2. Document your model well. Be sure to document clearly what each type and method does,
 *  what purpose it serves and why it belongs in the model. If your model assumes any invariants,
 *  explain them clearly and concisely.
 */

/**
 * Invariants:
 * 1. Cell contents are not allowed to contain direct or indirect cyclic references to the cell.
 * 2. There are no assumptions about the size or the maximum size of the worksheet.
 * 3. All Sexp expressions can be evaluated to a value: string/boolean/double or a list of values.
 * 4. An individual spreadsheet cell may:
      - be blank
      - contain a value
      - contain a formula
 * 5. A formula is one of:
      - a value
      - a reference to a rectangular region of cells in the spreadsheet
      - a function applied to one or more formulas as its arguments
 * 6. Evaluating cells:
 *    Cells that contain values just evaluate to themselves.
 *    Formula can take as inputs references to other cells (or groups of cells). 
 */
public interface Worksheet {
  
  /**
   * Read the contents of a cell in the worksheet
   * @param cellName the name of the cell in the worksheet. e.g. A3
   * @return the contents of the cell as an s-expression
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  public Sexp readCell(String cellName);
  
  /**
   * Edit the contents of a cell in the worksheet
   * @param cellName the name of the cell in the worksheet. e.g. A3
   * @param contents the contents of the cell as an s-expression
   * @throws IllegalArgumentException if the coordinates are invalid
   */
  public void writeCell(String cellName, String contents);
    
  /**
   * Get the result of evaluating the contents of a cell
   * @param cellName the name of the cell in the worksheet. e.g. A3
   * @param contents the contents of the cell as an s-expression
   * @throws IllegalArgumentException if the coordinates are invalid
   * @return an s-expression resulting from the evaluation
   */
  public Sexp evalCell(String cellName);
  
  public Sexp evalCell(Sexp exp);
  
  /**
   * Get the cell corresonding to the name in the worksheet
   * @param cellName the name of the cell in the worksheet. e.g. A3
   * @return the column and row numbers of the cell
   */
  public Cell selectCell(String cellName);
  
  /**
   * 
   * @param row the row of the cell (0-indexed).
   * @param col the column of the cell (0-indexed).
   * @return a {@link Cell} with the given coordinates in the worksheet.
   */
  public Cell selectCell(int row, int col);
  
  /**
   * Get the cells corresponding to the given range of cells in the worksheet
   * @param rangeName the names of the start and end cells of the range separated by a colon. e.g. C42:D53 
   * @return the list of coordinates corresponding to the range of cells
   */
  public List<Cell> selectRange(String rangeName);
  
  /**
   * Return the number of rows in the worksheet.
   * @return the number of rows in the worksheet.
   */
  public int getWorksheetSize();
  
  /**
   * Returns the width of the requested row,
   * from the leftmost column to the rightmost column (inclusive).
   * @param row the desired row (0-indexed)
   * @return the number of columns in the row.
   */
  public int getRowWidth(int row);
  
  /**
   * 
   * @param ap an {@code Appendable} object to write the contents of the worksheet into.
   * @return the overwritten {@code Appendable} object
   * @throws IOException 
   */
  public Appendable save(Appendable ap) throws IOException;

  /**
   * Get list of cell names that refrence cellName
   * @param cellName the name of the cell for which to find all references
   * @return list of names of cells that refrence cellName
   */
  public List<String> getRefs(String cellName);

  /**
   * 
   * @param targetCellName the name of the reevaluated cell
   * @param newValue the new computed value of targetCellName
   * @param refCellContents the contents of the cell that contains refrence to targetCellName
   * @return
   */
  public Sexp getNewRefVal(String selectedCellName, Sexp exp, Sexp sexp);

  public boolean isValidNewVal(String cellName, Sexp newVal);
}
