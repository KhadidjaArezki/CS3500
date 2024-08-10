package edu.cs3500.spreadsheets;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Objects;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.controller.GUIWorksheetController;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;
import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetEditableView;
import edu.cs3500.spreadsheets.view.WorksheetGUIEditableView;

public class BeyondGood_GUI_Edit_Empty {

  public static void main(String[] args) {
    if (args.length == 2 &&
        (Objects.nonNull(args[0]) && args[0].equals("-gui")) &&
        (Objects.nonNull(args[1]) && args[1].equals("-edit"))) {
      try {
        Readable rd = new StringReader("");
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
