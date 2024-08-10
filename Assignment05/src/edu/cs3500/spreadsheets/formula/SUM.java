package edu.cs3500.spreadsheets.formula;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class SUM implements Formula {

  @Override
  public Sexp apply(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
    return new SNumber(sum(args, visitor));
  }
  
  private double sum(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
    double sum = 0;
    for (Sexp arg: args) {
      if (SList.class.isInstance(arg)) {
        @SuppressWarnings("unchecked") List<Sexp> exps = (List<Sexp>) arg.accept(visitor).getResult();
        sum += sum(exps, visitor);
      }
      if (SNumber.class.isInstance(arg)) {
        sum += (double) arg.accept(visitor).getResult();
      }
    }
    return sum;
  }
}
