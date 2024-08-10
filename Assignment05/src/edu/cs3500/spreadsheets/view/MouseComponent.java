package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import edu.cs3500.spreadsheets.controller.Features;

@SuppressWarnings("serial")
public class MouseComponent extends JPanel {
  List<Features> featureListeners = new ArrayList<>();

  // Includes this new feature listener in responding to keys
  public void addFeatures(Features f) {
    this.featureListeners.add(f);
  }
  
  MouseComponent() {
 // Install action command -> Feature callback associations
    this.getActionMap().put("selectCell", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
//        for (Features f : featureListeners)
//          f.selectCell();
      }
    });
  }
}
