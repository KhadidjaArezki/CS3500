package edu.cs3500.spreadsheets.formula;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class UPPER implements Formula {

  @Override
  public Sexp apply(List<Sexp> args, SexpVisitor<Result<?>> visitor) {
    if (args.size() != 1) throw new IllegalArgumentException("The UPPER formula needs exactly 1 argument");
    if (!SString.class.isInstance(args.get(0))) {
      throw new IllegalArgumentException("Cannot Capitalize Non-string.");
    }
    return new SString(((String) args.get(0).accept(visitor).getResult()).toUpperCase());
  }

}
