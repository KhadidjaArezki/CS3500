package edu.cs3500.spreadsheets;

import java.io.IOException;
import java.io.StringReader;

import edu.cs3500.spreadsheets.model.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImp;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetEditableView;
import edu.cs3500.spreadsheets.view.WorksheetGUIEditableView;
import edu.cs3500.spreadsheets.model.ReadOnlyWorksheetModel;
import edu.cs3500.spreadsheets.controller.*;

public class BeyonGood7 {

  public static void main(String[] args) {
    try {
      Readable rd = new StringReader("");
      Worksheet model = WorksheetReader.read(new WorksheetBuilderImp(), rd);
      WorksheetEditableView view = new WorksheetGUIEditableView(new ReadOnlyWorksheetModel(model));
      Features controller = new WorksheetController(model);
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

}
