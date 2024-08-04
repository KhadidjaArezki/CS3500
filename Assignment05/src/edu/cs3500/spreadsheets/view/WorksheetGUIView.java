package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;

import java.awt.*;
import java.awt.event.ActionListener;

public class WorksheetGUIView extends JFrame implements WorksheetView {

  private final ReadOnlyWorksheetModel roModel;
  private WorksheetPanel worksheetPanel;
  private JScrollPane scrollPane;
//  private JLabel display;
//  private JTextField input;
  
  public WorksheetGUIView(ReadOnlyWorksheetModel roModel) {
    super();
    this.roModel = roModel;
    this.setTitle("Worksheet");
//    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
//    this.setLayout(new BorderLayout());
    worksheetPanel = new WorksheetPanel();
    Coord panelSize = getPanelSize();
    setHeaders();
    setCells();
    worksheetPanel.setCellSize(100, 40);
    // TODO: Override getPreferedSize?
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
  
  @Override
  public void render() throws IOException {
    this.setVisible(true);
  }
  
  private int getMaxRowWidth() {
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
  public Coord getPanelSize() {
    int col = 1;
    int row = 1;
    for (int i=this.roModel.getWorksheetSize()-1; i>=0; i--) {
      for (int j=this.roModel.getRowWidth(i)-1; j>=0; j--) {
        Cell cell = this.roModel.selectCell(i, j);
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
    return new Coord(Math.max(col, this.worksheetPanel.getMinNumRows()),
        Math.max(row, this.worksheetPanel.getMinNumRows()));
  }
  
  public void setCells() {
    Coord panelSize = getPanelSize();
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
  
  public void setHeaders() {
    Coord panelSize = getPanelSize();
//    System.out.println(String.format("Coordinates of last non-null column: %d", panelSize.col));
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
  
//  public void addActionListener(ActionListener actionListener) {
//    exitButton.addActionListener(actionListener);
//  }

}
