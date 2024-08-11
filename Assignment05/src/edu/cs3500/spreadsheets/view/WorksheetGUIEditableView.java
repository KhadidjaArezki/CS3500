package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;

/** DONE:
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
 * 4- As items are edited, the computed values of any affected cells must be recomputed and
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
 *    
 ** TODO:
 *    5- Enhance your editor view with the ability for the user to scroll indefinitely to the right of
 *    the current rightmost cell, or down beneath the bottom-most cell, and be able to edit any cell
 *    they choose. Ideally, this could be done simply by scrolling.
 */

public class WorksheetGUIEditableView extends JFrame implements WorksheetEditableView {

  private WorksheetPanel worksheetPanel;
  private CellEditingPanel cellEditingPanel;
  private JScrollPane scrollPane;
  private Coord worksheetPanelSize;
  private int worksheetMaxRowWidth;
  
  public WorksheetGUIEditableView(Coord worksheetPanelSizeCoords, int minNumRows,
      List<List<String>> cells, List<String> headers) {
    super();
    
    this.setTitle("Worksheet");
    //TODO: ASK USER TO SAVE
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    
    cellEditingPanel = new CellEditingPanel();
    cellEditingPanel.setLayout(new BoxLayout(cellEditingPanel, BoxLayout.X_AXIS));
    cellEditingPanel.setPreferredSize(new Dimension(500, 30));
    cellEditingPanel.setLocation(new Point(0, 0));
    cellEditingPanel.setComponents();
    container.add(cellEditingPanel);

    worksheetPanel = new WorksheetPanel();
    Coord worksheetPanelSize = new Coord(Math.max(worksheetPanelSizeCoords.col, worksheetPanel.getMinNumRows()),
        Math.max(worksheetPanelSizeCoords.row, worksheetPanel.getMinNumRows()));
    
    worksheetPanel.setCells(cells);
    worksheetPanel.setMinNumRows(minNumRows);
    worksheetPanel.setHeaders(headers);
    worksheetPanel.setCellSize(100, 40);
    scrollPane = new JScrollPane(worksheetPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    int maxHeight = Math.max((worksheetPanelSize.row+1)*worksheetPanel.getCellHeight(), 500);
    int maxWidth = Math.max(worksheetPanelSize.col*worksheetPanel.getCellWidth(), 500);
    worksheetPanel.setPreferredSize(new Dimension(maxWidth, maxHeight));
    worksheetPanel.setBackground(Color.WHITE);
    scrollPane.setPreferredSize(new Dimension((int) worksheetPanel.getPreferredSize().getWidth(),
        (int)(worksheetPanel.getPreferredSize().getHeight())));
    
    container.add(scrollPane);
    this.add(container);
    
    this.pack();
  }
  
  public void selectCell(int x, int y) {
    worksheetPanel.selectCell(x, y);
  }
  
  public void acceptCellEdit() {
    setEditingPanelTextField("");
    refresh();
  }
  
  public void rejectCellEdit(String contents) {
    setEditingPanelTextField(contents);
  }
  
  public void setInput(String contents) {
    cellEditingPanel.setInputFieldContents("");
//    String selectedCellRawContents = roModel.selectCell(getSelectedCellName()).getContents().toString();
    if (contents.startsWith("(")) {
      cellEditingPanel.setInputFieldContents("="+contents);
    } else {
      cellEditingPanel.setInputFieldContents(contents);
    }
  }
  
  private void setEditingPanelTextField(String contents) {
    if (contents.startsWith("(")) {
      cellEditingPanel.setInputFieldContents("="+contents);
    } else {
      cellEditingPanel.setInputFieldContents("");
    }
  }
  
  public String getEditedCellContents() {
    return this.cellEditingPanel.getInputText();
  }
  
  public String getSelectedCellName() {
    return this.worksheetPanel.getSelectedCellName();
  }
  
  @Override
  public void addFeatures(Features features) {
    JFrame frame = this;
    this.addWindowListener(new WindowAdapter() {
      //I skipped unused callbacks for readability
      
      @Override
      public void windowClosing(WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to save?");
          if( choice == JOptionPane.OK_OPTION){
            try {
              features.saveChanges();
              features.close();
              frame.setVisible(false);
              frame.dispose();
            }
            catch (IOException e1) {
              showErrorMessage("Cannot save changes.");
            }
          }
          else if( choice == JOptionPane.NO_OPTION){
            features.close();
            frame.setVisible(false);
            frame.dispose();
          }
      }
    });

    this.worksheetPanel.addFeatures(features);
    this.cellEditingPanel.addFeatures(features);
  }
  
  @Override
  public void render() throws IOException {
    this.setVisible(true);
  }
  
  public void refresh() {
    this.repaint();
  }
  
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  public int getWorksheetMaxRowWidth() {
    return this.worksheetMaxRowWidth;
  }
  
  public void setWorksheetMaxRowWidth(int maxWidth) {
    this.worksheetMaxRowWidth = maxWidth;
  }
  
  public int getMinNumRows() {
    return this.worksheetPanel.getMinNumRows();
  }
  
  public void setWorksheetSize(Coord size) {
    this.worksheetPanelSize = size;
  }
  
  public Coord getWorksheetSize() {
    return this.worksheetPanelSize;
  }
  
  public void setWorksheetCells(List<List<String>> cells) {
    worksheetPanel.setCells(cells);
  }
  
  public void setCell(Coord coord, String value) {
    worksheetPanel.setCells(coord.row-1, coord.col-1, value);
    
  }
  
  public void setWorksheetHeaders(List<String> headers) {
    worksheetPanel.setHeaders(headers);
  }
}
