package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetEditableView;
import edu.cs3500.spreadsheets.view.WorksheetGUIEditableView;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;
import edu.cs3500.spreadsheets.controller.*;

public class BeyonGood7 {

  public static void main(String[] args) {
    /*
     * TODO:
     * Add an extra option to your command-line option:
     *   -edit. Specifying this option should open the editor view (as opposed to -gui
     *   which opened your non-editable graphical view from Assignment 6).
     *   All previous command-line arguments should continue to work as before.
     */
    if (args.length == 2 &&
        (Objects.nonNull(args[0]) && args[0].equals("-gui")) &&
        (Objects.nonNull(args[1]) && args[1].equals("-edit"))) {
          BeyondGood_GUI_Edit_Empty.main(args);
    }
    else if (args.length == 4 &&
        Objects.nonNull(args[2]) && args[2].equals("-gui") &&
        Objects.nonNull(args[3]) && args[3].equals("-edit")) {
      
      try {
        Path path = new File(args[1]).toPath();
        Readable rd = new StringReader(Files.readString(path));
        Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
        int minNumRows = 26;
        GUIWorksheetController controller = new GUIWorksheetController(model);
        controller.setMinNumRows(minNumRows);
        WorksheetEditableView view = new WorksheetGUIEditableView(
            controller.getworksheetSizeCoords(),
            minNumRows,
            controller.getWorksheetCells(),
            controller.getWorksheetHeaders());
        controller.setView(view);
        
      } catch(IllegalStateException e) {
      e.printStackTrace();
        System.out.println(String.format("Error: %s", e.getMessage())); 
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error: Cannot Render Worksheet.");
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println(String.format("Error: %s", e.getMessage())); 
      }
    }
    
      else BeyondGood6.main(args);
  }
}
