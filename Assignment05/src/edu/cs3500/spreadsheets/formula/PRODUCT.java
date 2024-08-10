package edu.cs3500.spreadsheets.formula;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class PRODUCT implements Formula {

  @Override
  public Sexp apply(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
    return new SNumber(multiply(args, visitor));
  }
  
  private double multiply(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
//    if (args.isEmpty()) return 0;
    double product = 1;
    for (Sexp arg: args) {
      if (SList.class.isInstance(arg)) {
        @SuppressWarnings("unchecked") List<Sexp> exps = (List<Sexp>) arg.accept(visitor).getResult();
        product *= multiply(exps, visitor);
      }
      if (SNumber.class.isInstance(arg)) {
//        if (product == 0) product = 1;
        product *= (double) arg.accept(visitor).getResult();
      }
    }
    return product;
  }

}
