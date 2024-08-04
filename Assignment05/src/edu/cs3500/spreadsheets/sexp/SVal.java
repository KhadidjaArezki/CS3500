package edu.cs3500.spreadsheets.sexp;

public class SVal<V> implements Sexp {
  private V val;
  public Sexp exp;
  private String valType;
  
  public SVal(V val) {
    if (String.class.isInstance(val)) {
      this.exp = new SString((String) val);
      this.valType = "string";
    }
    else if (Boolean.class.isInstance(val)) {
      this.exp = new SBoolean((Boolean) val);
      this.valType = "boolean";
    }
    else if (Double.class.isInstance(val)) {
      this.exp = new SNumber((Double) val);
      this.valType = "number";
    }
    else
      throw new IllegalArgumentException("An s-expression must evaluate to either a string, a boolean, or a number");
    this.val = val;
  }
  
  @Override
  public <R> R accept(SexpVisitor<R> visitor) {
    if (this.valType == "string") return visitor.visitString((String) this.val); 
    else if (this.valType == "boolean") return visitor.visitBoolean((Boolean) this.val);
    else return visitor.visitNumber((Double) this.val);
  }
}
