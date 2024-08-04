package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetGUIView;
import edu.cs3500.spreadsheets.view.WorksheetView;

public class BeyondGood_GUI_View {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    Objects.requireNonNull(args);
    if (args.length < 3) {
      System.err.println("Invalid Command.");
      System.exit(0);  
    }
    for (String arg: args) {
      if (Objects.isNull(arg) || arg.length() == 0) {
        System.err.println(String.format("Invalid Argument: %s", arg));
        System.exit(0);
      } 
    }
    
    if (!args[0].equals("-in") || !args[2].equals("-gui")) {
      System.err.println("Unknown Command-Line Argument.");
      System.exit(0);
    }
    
    Readable rd;
    
    try {
      Path path = new File(args[1]).toPath();
      rd = new StringReader(Files.readString(path));
      
      Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
      ReadOnlyWorksheetModel roModel = new ReadOnlyWorksheetModel(model);
      
      WorksheetView guiView = new WorksheetGUIView(roModel);
      guiView.render();
      
    } catch(IllegalStateException e) {  
//      e.printStackTrace();
      System.out.println(String.format("Error: %s", e.getMessage())); 
    } catch (IOException e) {
//      e.printStackTrace();
      System.out.println("Error: Cannot Render Worksheet.");
    } catch (Exception e) {
//      e.printStackTrace();
      System.out.println(String.format("Error: %s", e.getMessage())); 
    }
  }
}
