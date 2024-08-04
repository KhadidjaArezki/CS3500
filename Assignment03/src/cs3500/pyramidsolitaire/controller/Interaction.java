package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * An interaction with the user consists of some input to send the program
 * and some output to expect.  We represent it as an object that takes in a Reader
 * and an Appendable and produces the intended effects on them
 */
interface Interaction {
  void apply(Reader in, Appendable out);
  
  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        try {
          output.append(line);
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
  }
  
  static Interaction inputs(CharBuffer in) {
    return (input, output) -> {
      try {
        input.read(in);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    };
  }
}

/**
 * Represents the printing of a sequence of lines to output
 */
class PrintInteraction implements Interaction {
  String[] lines;
  PrintInteraction(String... lines) {
    this.lines = lines;
  }
  public void apply(Reader in, Appendable out) {
    for (String line : lines) {
      try {
        out.append(line);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

/**
 * Represents a user providing the program with  an input
 */
class InputInteraction implements Interaction {
  CharBuffer input;
  InputInteraction(CharBuffer input) {
    this.input = input;
  }
  public void apply(Reader in, Appendable out) {
    try {
      in.read(input);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
