package edu.cs3500.spreadsheets.formula;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class LT implements Formula {

  @Override
  public Sexp apply(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
    if (args.size() != 2) throw new IllegalArgumentException("The < formula needs exactly 2 arguments");
    if (!SNumber.class.isInstance(args.get(0)) || !SNumber.class.isInstance(args.get(1))) {
      throw new IllegalArgumentException("Cannot multiply non-numbers.");
    }
    double arg1 = (double) args.get(0).accept(visitor).getResult();
    double arg2 = (double) args.get(1).accept(visitor).getResult();
    return new SBoolean(arg1 < arg2);
  }

}
