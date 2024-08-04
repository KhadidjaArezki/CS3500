package edu.cs3500.spreadsheets.controller;

/** TODO:
 * - controllers are best described as an interface whose purpose is to mediate the
 *   interactions between the view and the model. Multiple implementations of controllers
 *   are possible — potentially a specialized one for every model/view pairing.
 *   However it is also possible to implement a single controller that works for all your
 *   views: it depends on your design. For this assignment, you should only need a
 *   single controller.
 * 
 * - Implement whatever controller, keyboard and mouse handlers you need to support
 *   the editable view’s functionality.
 *   
 * Keyboard Handling: 
 * - Look at the code for the gui basics lecture. Since you may be using several keys
 *   in this assignment, using the map design is recommended.
 *   Recall how the controller gets control when a key is pressed: make sure your
 *   implementation of this assignment retains this essential ability of a controller.
 * 
 * Mouse Handling:
 * - Mouse handling involves a MouseListener interface and possibly the MouseMotionListener
 *   interface, just as keyboard handling involved a KeyListener.
 *   
 *   1. Design a class that implements MouseListener. It probably does not need to be as
 *      sophisticated and indirect as the KeyboardHandler above, since there are only three
 *      possible mouse events (left, middle and right clicks), rather than an entire keyboard.
 *   
 *   2. Enhance your controller to create one of these mouse listener objects, and configure
 *      it however you need to.
 *   
 *   3. If you need to define only 1 or 2 of these listener methods, starting from a
 *      MouseAdapter may be simpler.
 *      
 * Testing:
 * - Testing the controller should also be straightforward — all of its behavior is
 *   either in its methods, or in the wiring-up of those methods to key, mouse or other
 *   event handlers/listeners. If you’ve already tested that the wiring works properly,
 *   now all that remains is to test the methods themselves.
 *   
 * - Write tests for various components of your application (controller, listeners).
 *   Note that this is distinct from writing tests for your visual and editor views —
 *   we are asking you to test the controller functionality, not the views’ appearance.
 *   
 * - When you are testing each component above, be clear about what you are testing.
 *   The objective of testing the keyboard handler is to ensure that the appropriate action
 *   is taken on the appropriate key, not whether that action is successfully completed
 *   (that is part of testing the controller).
 *   
 */

public interface WorksheetController {
  
}
