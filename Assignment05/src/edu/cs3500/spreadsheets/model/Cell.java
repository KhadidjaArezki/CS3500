package edu.cs3500.spreadsheets.model;

import java.util.Objects;

import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;

public class Cell {
  private final String cellname;
  private final Coord coord;
  private Sexp contents;
  
  public Cell(Coord coord, Sexp contents) {
    this.coord = coord;
    this.cellname = coord.toString();
    this.contents = contents;
  }
  
  public String getCellName() {
    return this.cellname;
  }
  
  public Coord getCoord() {
    return new Coord(this.coord.col, this.coord.row);
  }
  
  public Sexp getContents() {
    return this.contents;
  }
  
  public void setContents(Sexp newContents) {
    this.contents = newContents;
  }
  
  @Override
  public String toString() {
    String sexpStr = "";
    if (SList.class.isInstance(this.contents)) sexpStr += "=";
    if (Objects.nonNull(this.contents)) sexpStr += this.contents.toString();
    return String.format("%s %s", this.cellname, sexpStr);
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return this.cellname.equals(cell.getCellName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.cellname);
  }
}
