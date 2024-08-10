package edu.cs3500.spreadsheets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.view.WorksheetEditableView;
import edu.cs3500.spreadsheets.view.WorksheetGUIEditableView;
/** TODO:
 * - controllers are best described as an interface whose purpose is to mediate the
 *   interactions between the view and the model. Multiple implementations of controllers
 *   are possible — potentially a specialized one for every model/view pairing.
 *   However it is also possible to implement a single controller that works for all your
 *   views: it depends on your design. For this assignment, you should only need a
 *   single controller.
 * 
 * - Implement whatever controller, keyboard and mouse handlers you need to support
 *   the editable view’s functionality.
 *   
 * Keyboard Handling: 
 * - Look at the code for the gui basics lecture. Since you may be using several keys
 *   in this assignment, using the map design is recommended.
 *   Recall how the controller gets control when a key is pressed: make sure your
 *   implementation of this assignment retains this essential ability of a controller.
 * 
 * Mouse Handling:
 * - Mouse handling involves a MouseListener interface and possibly the MouseMotionListener
 *   interface, just as keyboard handling involved a KeyListener.
 *   
 *   1. Design a class that implements MouseListener. It probably does not need to be as
 *      sophisticated and indirect as the KeyboardHandler above, since there are only three
 *      possible mouse events (left, middle and right clicks), rather than an entire keyboard.
 *   
 *   2. Enhance your controller to create one of these mouse listener objects, and configure
 *      it however you need to.
 *   
 *   3. If you need to define only 1 or 2 of these listener methods, starting from a
 *      MouseAdapter may be simpler.
 *      
 * Testing:
 * - Testing the controller should also be straightforward — all of its behavior is
 *   either in its methods, or in the wiring-up of those methods to key, mouse or other
 *   event handlers/listeners. If you’ve already tested that the wiring works properly,
 *   now all that remains is to test the methods themselves.
 *   
 * - Write tests for various components of your application (controller, listeners).
 *   Note that this is distinct from writing tests for your visual and editor views —
 *   we are asking you to test the controller functionality, not the views’ appearance.
 *   
 * - When you are testing each component above, be clear about what you are testing.
 *   The objective of testing the keyboard handler is to ensure that the appropriate action
 *   is taken on the appropriate key, not whether that action is successfully completed
 *   (that is part of testing the controller).
 *   
 */

public class GUIWorksheetController implements Features {
  private Worksheet model;
  private WorksheetEditableView view;
  private int minNumRows;
  
  public GUIWorksheetController(Worksheet m) {
    model = m;
  }

  public void setView(WorksheetEditableView v) throws IOException {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
    view.render();
  }

  @Override
  public void selectCell(int x, int y) {
    configureWorksheetCells();
    view.selectCell(x, y);
    try {
      Sexp contents = model.selectCell(view.getSelectedCellName()).getContents();
      if (Objects.isNull(contents)) view.setInput("");  
      else view.setInput(contents.toString());
      view.refresh();
    } catch(NullPointerException e) {
      view.setInput("");
    }
  }
  
  @Override
  public void exitProgram() {
    System.exit(0);
  }
  
  @Override
  public void updateCell() {
    String newCellContents = view.getEditedCellContents();
    if (newCellContents .length() == 0) newCellContents = null;
    
    else if (newCellContents.startsWith("=")) newCellContents = newCellContents.substring(1);
//    System.out.println(String.format("Updating %s", newCellContents));
    
    String selectedCellName = view.getSelectedCellName();
    try {
      Sexp exp;
      if (Objects.isNull(newCellContents)) {
        exp = null;
        view.setCell(model.selectCell(selectedCellName).getCoord(), "");
      }
      else {
        exp = Parser.parse(newCellContents);
        view.setCell(model.selectCell(selectedCellName).getCoord(), model.evalCell(exp).toString());
      }
      List<String> refs = model.getRefs(selectedCellName);
      for (String cn : refs) {
        Cell c = model.selectCell(cn);
        String newVal;
        try {
          newVal = model.evalCell(model.getNewRefVal(selectedCellName, exp, c.getContents())).toString();
        } catch(NullPointerException e) {
          newVal = "";
        }
        view.setCell(c.getCoord(), newVal);
      }
    
      view.refresh();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void acceptCellEdit() {
    String newCellContents = view.getEditedCellContents();
    if (newCellContents .length() == 0) newCellContents = null;
    String selectedCellName = view.getSelectedCellName();
    try {
      model.writeCell(selectedCellName, newCellContents);
      view.acceptCellEdit();
      configureWorksheetCells();
    } catch(Exception e) {
      rejectCellEdit();
      configureWorksheetCells();
      view.showErrorMessage(e.getMessage());
    }
  }
  
  @Override
  public void rejectCellEdit() {
    String contents = model.selectCell(view.getSelectedCellName()).getContents().toString();
    configureWorksheetCells();
    view.rejectCellEdit(contents);
  }
  
  public void configureView() {
    configureMaxRowWidth();
    configureWorksheetSize();
    configureWorksheetCells();
    configureWorksheetHeaders();
  }
  
  private void configureMaxRowWidth() {
    view.setWorksheetMaxRowWidth(getMaxRowWidth());
  }
  
  private void configureWorksheetSize() {
    view.setWorksheetSize(getworksheetSizeCoords());
  }
  
  private void configureWorksheetCells() {
    view.setWorksheetCells(getWorksheetCells());
  }
  
  private void configureWorksheetHeaders() {
    view.setWorksheetHeaders(getWorksheetHeaders());
  }
  
  public void setMinNumRows(int minNumRows) {
    this.minNumRows = minNumRows;
  }
  
  private int getMaxRowWidth() {
    int maxWidth = 0;
    for (int i=0; i<model.getWorksheetSize(); i++) {
      for (int j=0; j<model.getRowWidth(i); j++) {
        Cell cell = model.selectCell(i, j);
        if ((cell.getCoord().col > maxWidth) && Objects.nonNull(cell.getContents())) {
          maxWidth = cell.getCoord().col;
        }
      }
    }
    return maxWidth;
  }
  
  public Coord getworksheetSizeCoords() {
    int col = 1;
    int row = 1;
    for (int i=model.getWorksheetSize()-1; i>=0; i--) {
      for (int j=model.getRowWidth(i)-1; j>=0; j--) {
        Cell cell = model.selectCell(i, j);
        if (Objects.nonNull(cell.getContents())) {
          col = getMaxRowWidth();
          row = i + 1;
          break;
        }
      }
      if (col > 1 || row > 1) {
        break;
      }
    }
    return new Coord(Math.max(col, this.minNumRows),
        Math.max(row, this.minNumRows));
  }
  
  public List<List<String>> getWorksheetCells() {
    Coord panelSize = getworksheetSizeCoords();
    List<List<String>> cells = new ArrayList<List<String>>(panelSize.row);
    for (int i=0; i<panelSize.row; i++) {
      List<String> row = new ArrayList<String>(panelSize.col);
      for (int j=0; j<panelSize.col; j++) {
        Cell cell = model.selectCell(i, j);
        if (Objects.nonNull(cell.getContents())) {
          row.add(model.evalCell(cell.getCellName()).toString());
        }
        else {
          row.add("");
        }
      }
      cells.add(row);
    }
    return cells;
  }
  
  public List<String> getWorksheetHeaders() {
    Coord panelSize = getworksheetSizeCoords();
    List<String> headers = new ArrayList<String>(panelSize.col);
    for (int j=0; j<panelSize.col; j++) {
      Cell cell = model.selectCell(0, j);
      headers.add(Coord.colIndexToName(cell.getCoord().col));
    }
    return headers;
  }
}