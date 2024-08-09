package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import edu.cs3500.spreadsheets.controller.Features;

@SuppressWarnings("serial")
public class CellEditingPanel extends javax.swing.JPanel {
  private JTextField selectedCellContents;
  private JButton acceptEditButton;
  private JButton rejectEditButton;
  
  public String getInputText() {
    return selectedCellContents.getText();
  }
  
  public void setComponents() {
    acceptEditButton = new JButton("accept");
    acceptEditButton.setActionCommand("Accept Edit");
    rejectEditButton = new JButton("cancel");
    rejectEditButton.setActionCommand("Cancel Edit");
    selectedCellContents = new JTextField(120);
    this.add(acceptEditButton);
    this.add(rejectEditButton);
    this.add(selectedCellContents);
  }
  
  public void addFeatures(Features features) {
    acceptEditButton.addActionListener(evt -> features.acceptCellEdit());
    rejectEditButton.addActionListener(evt -> features.rejectCellEdit());
//    selectedCellContents.addActionListener(evt -> features.);
    selectedCellContents.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        features.updateCell();
      }
      public void removeUpdate(DocumentEvent e) {
        features.updateCell();
      }
      public void insertUpdate(DocumentEvent e) {
        features.updateCell();
      }
    });
  }

  public void setInputFieldContents(String contents) {
    selectedCellContents.setText(contents);
  }
  
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
