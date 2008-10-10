package org.fest.swing.driver;

import javax.swing.text.JTextComponent;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the selected text of a
 * <code>{@link JTextComponent}</code>.
 * 
 * @author Alex Ruiz
 */
final class JTextComponentSelectedTextQuery {
  
  static String selectedTextOf(final JTextComponent textBox) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return textBox.getSelectedText();
      }
    });
  }
  
  private JTextComponentSelectedTextQuery() {}
}