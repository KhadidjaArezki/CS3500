package edu.cs3500.spreadsheets.view;

import java.io.IOException;

/** TODO:
 * Design a new view that contains the existing visual view as a component within it,
 * and adds the following abilities:
 * 
 * 1- Select an individual cell by clicking on it. This should provide some obvious visual
 *    feedback to the user — in the screenshot above, I highlight the border of the cell,
 *    but you may choose another UI if you wish.
 *  
 * 2- Displaying the full formula for that cell somewhere editable — in the screenshot above,
 *    I place that formula in a textbox in the toolbar, but you may choose another UI if you wish.
 *  
 * 3- Allowing a user to edit that formula and confirm or reject the edits —in the screenshot above
 *    I used the two toolbar buttons on the left, but you may choose another UI if you wish.
 *    If the edits are confirmed, the cell must be edited; if the edits are rejected, then the
 *    displayed formula must revert to the current contents of the cell.
 *  
 * 4- Enhance your editor view with the ability for the user to scroll indefinitely to the right of
 *    the current rightmost cell, or down beneath the bottom-most cell, and be able to edit any cell
 *    they choose. Ideally, this could be done simply by scrolling.
 *    
 * 5- As items are edited, the computed values of any affected cells must be recomputed and
 *    redisplayed immediately. (One expensive way to do this is simply to recompute all cells on
 *    every edit; a more efficiently designed solution is up to you.) Note that this may cause
 *    cycles in your model: your program must not crash if this happens, even if you do not
 *    currently explicitly detect cycles.
 * 
 * NOTES:
 *  - You should not add this functionality directly to the existing visual view; you should
 *    build a new, composite view, and leave your existing visual view alone.
 *       
 *  - For this assignment you need to reuse the JPanel as a component in your new editor view;
 *    you do not have to reuse the JFrame class you created, and can develop a new one if needed. 
 */

public class WorksheetGUIEditableView implements WorksheetView {

  @Override
  public void render() throws IOException {
    // TODO Auto-generated method stub

  }

}
