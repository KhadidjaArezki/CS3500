package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;

/** TODO:
 * Design a new view that contains the existing visual view as a component within it,
 * and adds the following abilities:
 * 
 * 1- Select an individual cell by clicking on it. This should provide some obvious visual
 *    feedback to the user — in the screenshot above, I highlight the border of the cell,
 *    but you may choose another UI if you wish.
 *  
 * 2- Displaying the full formula for that cell somewhere editable — in the screenshot above,
 *    I place that formula in a textbox in the toolbar, but you may choose another UI if you wish.
 *  
 * 3- Allowing a user to edit that formula and confirm or reject the edits —in the screenshot above
 *    I used the two toolbar buttons on the left, but you may choose another UI if you wish.
 *    If the edits are confirmed, the cell must be edited; if the edits are rejected, then the
 *    displayed formula must revert to the current contents of the cell.
 *  
 * 4- Enhance your editor view with the ability for the user to scroll indefinitely to the right of
 *    the current rightmost cell, or down beneath the bottom-most cell, and be able to edit any cell
 *    they choose. Ideally, this could be done simply by scrolling.
 *    
 * 5- As items are edited, the computed values of any affected cells must be recomputed and
 *    redisplayed immediately. (One expensive way to do this is simply to recompute all cells on
 *    every edit; a more efficiently designed solution is up to you.) Note that this may cause
 *    cycles in your model: your program must not crash if this happens, even if you do not
 *    currently explicitly detect cycles.
 * 
 * NOTES:
 *  - You should not add this functionality directly to the existing visual view; you should
 *    build a new, composite view, and leave your existing visual view alone.
 *       
 *  - For this assignment you need to reuse the JPanel as a component in your new editor view;
 *    you do not have to reuse the JFrame class you created, and can develop a new one if needed. 
 */

public class WorksheetGUIEditableView extends JFrame implements WorksheetEditableView {

  private final ReadOnlyWorksheetModel roModel;
  private WorksheetPanel worksheetPanel;
  private JScrollPane scrollPane;
  private MouseComponent mouseComponent;
//  private JLabel display;
//  private JTextField input;
  
  public WorksheetGUIEditableView(ReadOnlyWorksheetModel roModel) {
    super();
    this.mouseComponent = new MouseComponent();
    this.add(mouseComponent);
    
    this.roModel = roModel;
    this.setTitle("Worksheet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    worksheetPanel = new WorksheetPanel();
    Coord panelSize = getWorksheetPanelSize();
    setWorksheetPanelHeaders();
    setWorksheetPanelCells();
    worksheetPanel.setCellSize(100, 40);
    scrollPane = new JScrollPane(worksheetPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    int maxHeight = Math.max((panelSize.row+1)*worksheetPanel.getCellHeight(), 500);
    int maxWidth = Math.max(panelSize.col*worksheetPanel.getCellWidth(), 500);
    worksheetPanel.setPreferredSize(new Dimension(maxWidth, maxHeight));
    scrollPane.setPreferredSize(new Dimension((int) worksheetPanel.getPreferredSize().getWidth(),
        (int)(worksheetPanel.getPreferredSize().getHeight())));
    this.add(scrollPane);
    this.pack();
  }
  
  public void selectCell(int x, int y) {
    worksheetPanel.colorCell(x, y);
  }
  
  @Override
  public void addFeatures(Features features) {
//    this.mouseComponent.addFeatures(features);
    this.worksheetPanel.addMouseListener(new MouseAdapter() {
      /* TODO
       * So in your mouseClick logic you need to save the point where you clicked
       * in an ArrayList and invoke repaint(). Then the paintComponent(...) method
       * needs to iterate through the ArrayList to paint the rectangle at the given point.
       */
      public void mouseClicked(MouseEvent e) {
        features.selectCell(e.getX(), e.getY());
        refresh();
      }
    });
  }
  
  @Override
  public void render() throws IOException {
    this.setVisible(true);
  }
  
  private int getWorksheetMaxRowWidth() {
    int maxWidth = 0;
    for (int i=0; i<this.roModel.getWorksheetSize(); i++) {
      for (int j=0; j<this.roModel.getRowWidth(i); j++) {
        Cell cell = this.roModel.selectCell(i, j);
        if ((cell.getCoord().col > maxWidth) && Objects.nonNull(cell.getContents())) {
          maxWidth = cell.getCoord().col;
        }
      }
    }
    return maxWidth;
  }
  
  public Coord getWorksheetPanelSize() {
    int col = 1;
    int row = 1;
    for (int i=this.roModel.getWorksheetSize()-1; i>=0; i--) {
      for (int j=this.roModel.getRowWidth(i)-1; j>=0; j--) {
        Cell cell = this.roModel.selectCell(i, j);
        if (Objects.nonNull(cell.getContents())) {
          col = getWorksheetMaxRowWidth();
          row = i + 1;
          break;
        }
      }
      if (col > 1 || row > 1) {
        break;
      }
    }
    return new Coord(Math.max(col, this.worksheetPanel.getMinNumRows()),
        Math.max(row, this.worksheetPanel.getMinNumRows()));
  }
  
  public void setWorksheetPanelCells() {
    Coord panelSize = getWorksheetPanelSize();
    List<List<String>> cells = new ArrayList<List<String>>(panelSize.row);
    for (int i=0; i<panelSize.row; i++) {
      List<String> row = new ArrayList<String>(panelSize.col);
      for (int j=0; j<panelSize.col; j++) {
        Cell cell = this.roModel.selectCell(i, j);
        if (Objects.nonNull(cell.getContents())) {
          row.add(this.roModel.evalCell(cell.getCellName()).toString());
        }
        else {
          row.add("");
        }
      }
      cells.add(row);
    }
    worksheetPanel.setCells(cells);
  }
  
  public void setWorksheetPanelHeaders() {
    Coord panelSize = getWorksheetPanelSize();
    List<String> headers = new ArrayList<String>(panelSize.col);
    for (int j=0; j<panelSize.col; j++) {
      Cell cell = this.roModel.selectCell(0, j);
      headers.add(Coord.colIndexToName(cell.getCoord().col));
    }
    worksheetPanel.setHeaders(headers);
  }
  
  public void refresh() {
    this.repaint();
  }
  
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

}
