package edu.cs3500.spreadsheets.formula;

public class FormulaCreator {

  public static Formula create(String name) {
    switch(name.toUpperCase()) {
    case "SUM":
      return new SUM();
    case "PRODUCT":
      return new PRODUCT();
    case "<":
      return new LT();
    default:
      throw new IllegalArgumentException("Formula Not Supported");
    }
  }
}
