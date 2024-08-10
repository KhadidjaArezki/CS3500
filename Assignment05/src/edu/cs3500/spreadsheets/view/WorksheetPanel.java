package edu.cs3500.spreadsheets.view;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

@SuppressWarnings("serial")
public class WorksheetPanel extends javax.swing.JPanel {
  private List<List<String>> cells;
  private List<String> headers;
  private List<Rectangle> rectangles;
  private Rectangle selectedRect;
  private String selectedCellName;
  private String selectedCellContents;
  private int cellWidth;
  private int cellHeight;
  private int rowHeaderWidth;
  private int minNumRows;
  int margin = 10;
  int maxTextLength = 12;
  
  public int getMinNumRows() {
    return this.minNumRows;
  }
  
  public int getCellWidth() {
    return this.cellWidth;
  }
  
  public int getCellHeight() {
    return this.cellHeight; 
  }
  
  public void setCellSize(int width, int height) {
    this.cellWidth = width;
    this.rowHeaderWidth = cellWidth/3;
    this.cellHeight = height;
  }
  
  public void setMinNumRows(int minNumRows) {
    this.minNumRows = minNumRows;
  }
  
  public void setCells(List<List<String>> cells) {
    this.cells = cells;
  }
  
  public void setHeaders(List<String> headers) {
    this.headers = headers;
  }
  
  public void selectCell(int x, int y) {
    for (Rectangle rect: rectangles) {
      if (x>= rect.x && x < (rect.x + rect.width) &&
          y>= rect.y && y < (rect.y + rect.height)) {
        this.selectedRect = new Rectangle(rect.x, rect.y, cellWidth, cellHeight);
        this.selectedCellName = Coord.colIndexToName(((int) rect.x/cellWidth) + 1) +
            Integer.toString((int) rect.y/cellHeight);
        this.selectedCellContents = cells.get((int) rect.y/cellHeight - 1).get((int) rect.x/cellWidth);
      }
    }
  }
  
  public String getSelectedCellName() {
    return selectedCellName;
  }
  
  public String getSelectedCellContents() {
    return selectedCellContents;
  }
  
  public void addFeatures(Features features) {
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        features.selectCell(e.getX(), e.getY());
      }
    });
  }
    
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    
    this.rectangles = new ArrayList<Rectangle>(cells.size());
    int initRowPos = 0;
    int initColPos = 0;
    
    // Draw row headers
    int currRowPos = initRowPos;
    int currColPos = initColPos;
    g2d.setColor(Color.BLUE);
    for (int i=1; i<this.cells.size()+2; i++) {
      g2d.drawRect(currRowPos, currColPos, rowHeaderWidth, cellHeight);
      currColPos = currColPos + cellHeight;
      g2d.drawString(Integer.toString(i),
          currRowPos + margin,
          currColPos+cellHeight/2);
//      g2d.setColor(Color.GRAY);
//      g2d.fillRect(currRowPos, currColPos, rowHeaderWidth, cellHeight);
    }
    
    // Draw column headers
    currRowPos = rowHeaderWidth;
    currColPos = initColPos;
//    int headerCellHeight = cellHeight * 2/3;
    g2d.setColor(Color.BLUE);
    for (int i=0; i<this.headers.size(); i++) {
      g2d.drawRect(currRowPos, currColPos, cellWidth, cellHeight);
      currRowPos = currRowPos + cellWidth;
      g2d.drawString(headers.get(i),
          (currRowPos-cellWidth/2),
          currColPos+cellHeight/2);
    }
    
    // Draw Cells
    currRowPos = rowHeaderWidth;
    currColPos = currColPos + cellHeight;
    for(int i=0; i<this.cells.size(); i++) {
      for (int j=0; j<this.cells.get(i).size(); j++) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(currRowPos, currColPos, cellWidth, cellHeight);
        rectangles.add(new Rectangle(currRowPos, currColPos, cellWidth, cellHeight));
        
        currRowPos = currRowPos + cellWidth;
        g2d.drawString(cells.get(i).get(j).substring(0, Math.min(maxTextLength, cells.get(i).get(j).length())),
            (currRowPos - cellWidth + margin),
            currColPos+cellHeight/2);
      }
      currRowPos = rowHeaderWidth;
      currColPos = currColPos + cellHeight;
    }
    if (Objects.nonNull(this.selectedRect)) {
      g2d.setColor(Color.RED);
      g2d.drawRect(selectedRect.x, selectedRect.y, cellWidth, cellHeight);
    }
  }

  public void setCells(int row, int col, String value) {
    cells.get(row).set(col, value);
  }
}
