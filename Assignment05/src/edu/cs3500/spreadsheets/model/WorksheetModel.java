package edu.cs3500.spreadsheets.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;
import edu.cs3500.spreadsheets.sexp.WorksheetSexpVisitor;
import edu.cs3500.spreadsheets.formula.Formula;
import edu.cs3500.spreadsheets.formula.FormulaCreator;

/** DONE:
 * 1. Implement your model. If some method of your model cannot be implemented because it
 *  requires details you think may be revealed in future assignments, you may leave the method
 *  body blank for now — but leave a comment inside the method body explaining why it’s empty and
 *  what details you’re waiting for. If your implementation makes additional assumptions about
 *  invariants (beyond those asserted by the interface), document them.
 *  
 *  Support values, refs, and formulas
 *  
 *  2. Implement at least four functions:
      - A SUM function, that adds up all the values of all of its arguments. Its arguments could
         be references of arbitrary size, and the sum should cover all the values in the region
         being referenced. Any arguments whose contents are blank or not numeric should be ignored.
         If there are no numeric arguments at all, the default value is zero.
      - A PRODUCT function, that multiplies all the values of all of its arguments.
         Its arguments could be references of arbitrary size. Any arguments whose contents are
         blank or not numeric should be ignored. If there are no numeric arguments at all, the
         default value is zero. (Yes, zero: I don’t know why professional spreadsheet programs
         chose this value instead of one, but they did.)
      - A < function, that takes exactly two values and returns whether the first is less than
         the second. If either value is missing or is not a number, the function should error.
      - Another function of your choosing, whose return type is a string.
 * The names given above can be used in formulas, like =(< (SUM A1:A4) (PRODUCT B2:C5 D8)).
 * 
 * 3. Ensure that (direct or indirect) cyclic references between formulas are detected and
 * prohibited in your model implementation. Hint: Doing this well is trickier than it may
 * seem at first.
 * 
 * 4. Ensure that you can evaluate cells. Hint: Doing this well is trickier than it may seem at
 * first. We’re not extremely concerned with efficiency, but your code should not be egregiously slow.
 * 
 * 5. Test your model thoroughly. 
 * 
 */

public class WorksheetModel implements Worksheet {
  private List<List<Cell>> worksheet;
  private SexpVisitor<Result<?>> visitor = new WorksheetSexpVisitor();;
  
  public WorksheetModel(List<List<Cell>> worksheet) throws IllegalStateException {
    this.worksheet = Objects.requireNonNull(worksheet);
    String errors = "";
    for (List<Cell> row: worksheet) {
      for (Cell cell: row) {
        if (Objects.nonNull(cell.getContents())) {
          try {
            checkRep(cell.getCellName(), cell.getContents());
          } catch(IllegalStateException e) {
            errors += String.format("%s\n", e.getMessage());          
          }
        }
      }
    }
    if (!errors.isEmpty()) throw new IllegalStateException(errors);
  }
  
  private void validateColName(String colName) {
    for (int i = 0; i != colName.length(); ++i) {
      if (!Character.isLetter(colName.charAt(i))) {
        throw new IllegalArgumentException();
      }
    }
  }
  
  private void checkRep(String cellName, Sexp contents) throws IllegalStateException {
    // Enforce variant: Cyclic references - direct or indirect - are disallowed
    if (Objects.isNull(contents)) return;
    
    Result<?> result = contents.accept(this.visitor);
    String errMsg = String.format("Error in cell %s: Cyclic References are Not Allowed.", cellName);
    
    switch(result.getType()) {
    case REF:
      if (contents.toString().equals(cellName)) {
        throw new IllegalStateException(errMsg);
      }
      // Recursively check contents
      checkRep(cellName, selectCell(contents.toString()).getContents());
      break;
    case REFRANGE:
      if (isCellInRange(cellName, contents.toString())){
        throw new IllegalStateException(errMsg);
      }
      List<Cell> cells = selectRange(contents.toString());
      for (Cell cell : cells) {
        checkRep(cellName, cell.getContents());
      }
      break;
    case CALL:
      @SuppressWarnings("unchecked") List<Sexp> list = ((Result<List<Sexp>>) result).getResult();
      for (Sexp exp: list.subList(1, list.size())) {
        checkRep(cellName, exp);
      }
      break;
    // For primitive values, no check is required
    default:
      break;
    }
  }
  
  private boolean isCellInRange(String cellName, String range) {
    String[] startEnd = range.split(":", 2);
    int[] cellCoords = getCellCoords(cellName);
    int cellRow = cellCoords[0];
    int cellCol = cellCoords[1];

    int[] rangeCoords = getRangeCoords(startEnd[0], startEnd[1]);
    int startRow = rangeCoords[0];
    int startCol = rangeCoords[1];
    int endRow = rangeCoords[2];
    int endCol = rangeCoords[3];
    
    return cellRow >= startRow && cellRow <= endRow &&
        cellCol >= startCol && cellCol <= endCol;
  }
  
  private boolean isCellRef(String cellName, Sexp contents) {
    Result<?> result = contents.accept(this.visitor);
    
    switch(result.getType()) {
    case REF:
      if (contents.toString().equals(cellName)) return true;
      else return isCellRef(cellName, selectCell(contents.toString()).getContents());
    case REFRANGE:
      if (isCellInRange(cellName, contents.toString())) return true;
      else {
        List<Cell> rangeCells = selectRange(contents.toString());
        List<Sexp> expressions = new ArrayList<Sexp>();
        for(Cell cell : rangeCells) {
          if (cell.getCellName().equals(cellName)) return true; 
          if (isCellRef(cellName, cell.getContents())) return true;
        }
        return false;
      }
    case CALL:
      @SuppressWarnings("unchecked") List<Sexp> list = ((Result<List<Sexp>>) result).getResult();
      for (Sexp exp: list.subList(1, list.size())) {
        if (isCellRef(cellName, exp)) return true;
      }
      return false;
    default:
      return false;
    }
  }

  /* TODO: CREATE MAP<CELLNAME, LIST<CELLNAME>> TO STORE CELLS THAT REFRENCE CELLNAME 
   * Get list of cell names referencing cellName
   */
  public List<String> getRefs(String cellName) {
    List<String> refs = new ArrayList<String>();
    for (List<Cell> row : this.worksheet) {
      for (Cell cell : row) {
        if (Objects.nonNull(cell.getContents()) &&
          (isCellRef(cellName, cell.getContents()))) {
          refs.add(cell.getCellName());
        }
      }
    }
    return refs;
  }
  
  public Sexp getNewRefVal(String targetCellName, Sexp newValue, Sexp refCellContents) {
    if (Objects.isNull(refCellContents)) return null;
    
    Result<?> result = refCellContents.accept(this.visitor);
    switch(result.getType()) {
    case REF:
      if (refCellContents.toString().equals(targetCellName)) return newValue;
      else return getNewRefVal(targetCellName, newValue, 
          selectCell(refCellContents.toString()).getContents());
    case CALL:
      @SuppressWarnings("unchecked") List<Sexp> exps = ((Result<List<Sexp>>) result).getResult();
      Formula fun = FormulaCreator.create(exps.get(0).toString());
      List<Sexp> args = new ArrayList<Sexp>();
      for (Sexp arg: exps.subList(1, exps.size())) {
        // check if exp is ref, range, or another call, else keep value
        switch(arg.accept(this.visitor).getType()) {
        case REF:
          args.add(evalCell(getNewRefVal(targetCellName, newValue, arg)));
          break;
        case REFRANGE:
          if (isCellInRange(targetCellName, arg.toString())) {
            List<Cell> rangeCells = selectRange(arg.toString());
            List<Sexp> expressions = new ArrayList<Sexp>();
            for(Cell cell : rangeCells) {
              if (cell.getCellName().equals(targetCellName)) {
                cell.setContents(newValue);
              }
              expressions.add(getNewRefVal(targetCellName, newValue,cell.getContents()));
            }
            args.add(new SList(expressions));
          } else {
            args.add(getNewRefVal(targetCellName, newValue, arg));
          }
          
          break;
        case CALL:
          args.add(getNewRefVal(targetCellName, newValue, arg));
          break;
        default:
          args.add(evalCell(arg));
          break;
        }
      }
      return fun.apply(args, visitor);
    default:
      return evalCell(refCellContents);
    }
  }
  
  @Override
  public Sexp readCell(String cellName) {
    return selectCell(cellName).getContents();
  }

  @Override
  public void writeCell(String cellName, String contents) throws IllegalStateException {
    try {
      Cell cell = select(cellName);
      if (Objects.isNull(contents)) cell.setContents(null);
      else {
        if (contents.startsWith("=")) contents = contents.substring(1);
        Sexp exp = Parser.parse(contents);
        // Check that no cell contains cyclic refs
        checkRep(cellName, exp);
        cell.setContents(exp);
      }
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(String.format("Error in cell %s: Cannot Parse Cell Contents.", cellName));
    } catch(IllegalStateException e) {
        throw e;
    }
  }

  @Override
  public Sexp evalCell(String cellName) {
    Sexp contents = selectCell(cellName).getContents();
    return evalCell(contents);
  }
  
  public Sexp evalCell(Sexp contents) {
    if (Objects.isNull(contents)) return null;
    Result<?> result = contents.accept(this.visitor);
    
    switch(result.getType()) {
    case VAL:  
      return contents;
    
    case REF:
      return evalCell(selectCell(contents.toString()).getContents());
    
    case REFRANGE:
      List<Cell> cells = selectRange(contents.toString());
      
//      System.out.println(cells.get(0).toString());
      List<Sexp> expressions = new ArrayList<Sexp>();
      for (Cell cell : cells) {
        expressions.add(evalCell(cell.getContents()));
      }
      return new SList(expressions);
    
    case CALL:
      @SuppressWarnings("unchecked") List<Sexp> exps = ((Result<List<Sexp>>) result).getResult();
      Formula fun = FormulaCreator.create(exps.get(0).toString());
      List<Sexp> args = new ArrayList<Sexp>();
      
      for (Sexp arg: exps.subList(1, exps.size())) {
        args.add(evalCell(arg));
      }
      return fun.apply(args, this.visitor);
    
    default:
      return contents;
    }
  }
  
  private Cell copyCell(Cell cell) {
    Sexp cellContents;
    try {
      cellContents = Parser.parse(cell.getContents().toString());
      return new Cell(cell.getCoord(), cellContents);
    } catch(Exception e) {
      return new Cell(cell.getCoord(), null);
    }
  }
  
  @Override
  public Cell selectCell(int row, int col) {
    return copyCell(select(row, col));
  } 
  
  @Override
  public Cell selectCell(String cellName) {
    return copyCell(select(cellName));
  }
  
  private Cell select(int row, int col) {
    if (row < 0 || col < 0) throw new IllegalArgumentException("Invalid Cell Coordinates.");
    if (row >= getWorksheetSize() || col >= getRowWidth(row)) {
      this.worksheet = WorksheetBuilderImp.expandWorksheet(this.worksheet, row , col);
    }
    return this.worksheet.get(row).get(col);
  }
  
  private Cell select(String cellName) {
    String[] colRow;
    try {
      colRow = cellName.split("(?=\\d+)", 2);
      validateColName(colRow[0]);
      assert(colRow.length == 2);
      int col = Coord.colNameToIndex(colRow[0].trim()) -1;
      int row = Integer.parseInt(colRow[1].trim()) -1;
      
      return select(row, col);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Arguments.");
    }
  }
  
  private int[] getCellCoords(String cellName) {
    Cell cell = selectCell(cellName);
    return new int[] { cell.getCoord().row-1, cell.getCoord().col-1 };
  }
  
  private int[] getRangeCoords(String startCellName, String endCellName) {
    int[] startCellCoords = getCellCoords(startCellName);
    int[] endCellCoords = getCellCoords(endCellName);
    return new int[] { startCellCoords[0], startCellCoords[1], endCellCoords[0], endCellCoords[1] };
  }

  @Override
  public List<Cell> selectRange(String rangeName) {
    List<Cell> list = new ArrayList<Cell>();
    String[] startEnd;
    try {
      startEnd = rangeName.split(":", 2);
      assert(startEnd.length == 2);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid Range.");
    }
    
    int[] rangeCoords = getRangeCoords(startEnd[0], startEnd[1]);
    int startRow = rangeCoords[0];
    int startCol = rangeCoords[1];
    int endRow = rangeCoords[2];
    int endCol = rangeCoords[3];
    // check that end >= start
    if ( endRow < startRow || endCol < startCol)
      throw new IllegalArgumentException("invalid Range.");
    
    for (int i=startRow; i<endRow+1; i++) {
      for (int j=startCol; j<endCol+1; j++) {
        list.add(selectCell(i, j));
      }
    }
    return list;
  }
  
  public int getWorksheetSize() {
    return this.worksheet.size();
  }
  
  public int getRowWidth(int row) {
    return this.worksheet.get(row).size();
  }

  @Override
  public Appendable save(Appendable ap) throws IOException {
    for (List<Cell> row : this.worksheet) {
      for (Cell cell : row) {
        if (Objects.nonNull(cell.getContents())) {
          ap.append(String.format("%s\n", cell.toString()));
        }
      }
    }
    return null;
  }
}
