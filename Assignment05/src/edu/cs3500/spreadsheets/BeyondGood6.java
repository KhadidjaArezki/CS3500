package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.WorksheetReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.view.*;

public class BeyondGood6 {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /* DONE: 
     * You must enhance your your command-line handling to support four command-line styles:
     *   -in some-filename -eval some-cell — as in the prior assignment, reads in the file,
     *    evaluates it, and prints the result of the requested cell
     *   -in some-filename -save some-new-filename — opens the first file, and saves it as
     *    the second file, using the textual view you created
     *   -in some-filename -gui — opens your graphical view and loads the requested file and
     *    evaluates it
     *   -gui — opens your graphical view with a blank new spreadsheet
     */
    Objects.requireNonNull(args);
    if (args.length == 4) {
      if (Objects.nonNull(args[2]) && args[2].equals("-eval")) {
        BeyondGood_ConsoleView.main(args);
      }
      else if (Objects.nonNull(args[2]) && args[2].equals("-save")) {
        BeyondGood_TextualView.main(args);
      }
      else {
        System.err.println("Invalid Command.");
        System.exit(0);
      }
    }
    else if (args.length == 3) {
      if (Objects.nonNull(args[2]) && args[2].equals("-gui")) {
        BeyondGood_GUI_View.main(args);
      }
      else {
        System.err.println("Invalid Command.");
        System.exit(0);
      }
    }
    else if (args.length == 1 && (Objects.nonNull(args[0]) && args[0].equals("-gui"))) {
      Readable rd;
      
      try {
        rd = new StringReader("");
        
        Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
        ReadOnlyWorksheetModel roModel = new ReadOnlyWorksheetModel(model);
        
        WorksheetView guiView = new WorksheetGUIView(roModel);
        guiView.render();
        
      } catch(IllegalStateException e) {
//        e.printStackTrace();
        System.out.println(String.format("Error: %s", e.getMessage())); 
      } catch (IOException e) {
//        e.printStackTrace();
        System.out.println("Error: Cannot Render Worksheet.");
      } catch (Exception e) {
//        e.printStackTrace();
        System.out.println(String.format("Error: %s", e.getMessage())); 
      }
    }
    else {
      System.err.println("Invalid Command.");
      System.exit(0);
    }
  }
}
