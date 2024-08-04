package edu.cs3500.spreadsheets.formula;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Result;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public interface Formula {
  public Sexp apply(List<Sexp> args, SexpVisitor<Result<?>> visitor);
}
