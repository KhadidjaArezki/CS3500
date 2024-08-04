package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * The main class for our program.
 * TODO:
 * Your main method should expect a command-line of the following form:
   -in <some-filename> -eval <some-cellname>
 * For example, one valid command line would be -in posn-distances.gOOD -eval A3.
 * The first two arguments specify a filename to read in. Your program must read in the file
 * and construct a model from it. The second two arguments specify a cell whose contents you want
 * to evaluate.
 *
 *   - If a cell has a problem, then print out a one-line message "Error in cell Z42: ...",
 *      where Z42 is whatever cell is broken, and where the additional message can give whatever details
 *      you want. If multiple cells have problems, then print a one-line message for each error. 
 *      
 *   - If no cells are in error, then you must fully evaluate the requested cell, and print its final value.
 *      (Numbers should simply be printed using String.format("%f", ...). Strings should be printed in
 *      double-quotes, and any quotes within the string should be escaped with a backslash...and any
 *      backslashes should be escaped with another backslash. For instance, "Jack says \"hi\".
 *      Jill has one backslash \\ here.")
 * 
 * If the command-line is malformed, print an error and exit.
 * 
 * Provide three input files that your model can process. Two of them should work properly, while the third
 * should trigger an evaluation error. The two working files should be substantively different from each other,
 * and should demonstrate all the features of your model that you support.
 */

public class BeyondGood_ConsoleView {
  
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      DONE: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    
    Objects.requireNonNull(args);
    if (args.length < 4) {
      System.err.println("Invalid Command.");
      System.exit(0); 
    }
    for (String arg: args) {
      if (Objects.isNull(arg) || arg.length() == 0) {
        System.err.println(String.format("Invalid Argument: %s", arg));
        System.exit(0);
      } 
    }
    
    if (!args[0].equals("-in") || !args[2].equals("-eval")) {
      System.err.println("Unknown Command-Line Argument.");
      System.exit(0);
    }
    
    Readable rd;

    try {
      String cellName = args[3];
      Path path = new File(args[1]).toPath();
      rd = new StringReader(Files.readString(path));
      Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
      System.out.println(String.format("Result: %s", model.evalCell(cellName).toString()));
    }
    catch(Exception e) {  
      e.printStackTrace();
      System.out.println(String.format("Error: %s", e.getMessage()));
    }
   }
}
