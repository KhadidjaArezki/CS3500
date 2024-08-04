package edu.cs3500.spreadsheets.sexp;

import java.util.List;

public class WorksheetSexpVisitor implements SexpVisitor<Result<?>> {
 
  public WorksheetSexpVisitor() {}
  
  @Override
  public Result<Boolean> visitBoolean(boolean b) {
    return new BoolResult(b);
  }

  @Override
  public Result<Double> visitNumber(double d) {
    return new NumResult(d);
  }

  @Override
  public Result<List<Sexp>> visitSList(List<Sexp> l) {
    return new ListResult(l);
  }

  @Override
  public Result<String> visitSymbol(String s) {
    return new SymResult(s);
  }

  @Override
  public Result<String> visitString(String s) {
    return new StrResult(s);
  }

}
