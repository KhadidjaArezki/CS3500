package edu.cs3500.spreadsheets;

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
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetTextualView;
import edu.cs3500.spreadsheets.view.WorksheetView;

public class BeyondGood_TextualView {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    
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
    
    if (!args[0].equals("-in") || !args[2].equals("-save")) {
      System.err.println("Unknown Command-Line Argument.");
      System.exit(0);
    }
    
    Readable rd;
    PrintWriter printWriter;    
    
    try {
      // "/home/hadhad/eclipse-workspace/cs3500/Assignment05/sum-viewout1.txt"
      File file = new File (args[3]);
      printWriter = new PrintWriter(file);
      Path path = new File(args[1]).toPath();
      rd = new StringReader(Files.readString(path));
      
      Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
      ReadOnlyWorksheetModel roModel = new ReadOnlyWorksheetModel(model);
      WorksheetView textView = new WorksheetTextualView(roModel, printWriter);
      textView.render();
      printWriter.close();
    } catch(IllegalStateException e) {  
//      e.printStackTrace();
      System.out.println(String.format("Error: %s", e.getMessage())); 
    } catch (FileNotFoundException e) {
      System.out.println(String.format("Error: %s", e.getMessage())); 
    } catch (IOException e) {
      System.out.println("Error: Cannot Render Worksheet.");
    }
  }
}
