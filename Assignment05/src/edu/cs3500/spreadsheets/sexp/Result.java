package edu.cs3500.spreadsheets.sexp;

import java.util.List;

public abstract class Result<T> {
  // a result object is one of: boolean, double, string, or list of results
  private T result;
  
  public Result(T val) {
    this.result = val;
  }
  public enum SexpType {
    VAL, REF, REFRANGE, CALL
  }
  public T getResult() {
    return this.result;
  }
  public abstract SexpType getType();
}

class BoolResult extends Result<Boolean> {
  private SexpType type;

  public BoolResult(boolean b) {
    super(b);
    this.type = Result.SexpType.VAL;
  }
  public SexpType getType() {
    return this.type;
  }
}

class NumResult extends Result<Double> {
  private SexpType type;

  public NumResult(double d) {
    super(d);
    this.type = Result.SexpType.VAL;
  }
  public SexpType getType() {
    return this.type;
  }
}

class StrResult extends Result<String> {
  private SexpType type;

  public StrResult(String s) {
    super(s);
    this.type = Result.SexpType.VAL;
  }
  public SexpType getType() {
    return this.type;
  }
}

class ListResult extends Result<List<Sexp>> {
  private SexpType type;

  public ListResult(List<Sexp> l) {
    super(l);
    this.type = Result.SexpType.CALL;
  }
  public SexpType getType() {
    return this.type;
  }
}

class SymResult extends Result<String> {
  private SexpType type;

  public SymResult(String s) {
    super(s);
    if (s.indexOf(":") == -1) this.type = Result.SexpType.REF;
    else this.type = Result.SexpType.REFRANGE;
  }
  public SexpType getType() {
    return this.type;
  }
}
/**
  public Result(String s, boolean isSymbol) {
    this.result = s;
    if (!isSymbol) {
      this.type = expType.VAL;
    }
    else {
      if (s.indexOf(":") == -1) this.type = expType.REF;
      else this.type = expType.REFRANGE;
    }
  }
  
  public Result(Double d) {
    this.result = d;
    this.type = expType.VAL;
  }
  
  public <T> Result(List<T> l) {
    this.result = l;
    this.type = expType.CALL;
  }

  public Object getResult() {
    return this.result;
  }
  
  public expType getType() {
    return this.type;
  }
  */
