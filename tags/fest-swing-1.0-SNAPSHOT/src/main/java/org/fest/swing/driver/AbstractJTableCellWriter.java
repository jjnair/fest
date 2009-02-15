/*
 * Created on Jun 10, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JTableCancelCellEditingTask.cancelEditing;
import static org.fest.swing.driver.JTableCellEditorQuery.cellEditorIn;
import static org.fest.swing.driver.JTableCellValidator.*;
import static org.fest.swing.driver.JTableStopCellEditingTask.validateAndStopEditing;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

/**
 * Understands the base class for implementations of <code>{@link JTableCellWriter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractJTableCellWriter implements JTableCellWriter {

  protected final Robot robot;
  protected final JTableLocation location = new JTableLocation();

  public AbstractJTableCellWriter(Robot robot) {
    this.robot = robot;
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void cancelCellEditing(JTable table, int row, int column) {
    cancelEditing(table, row, column);
    robot.waitForIdle();
  }
  
  /** {@inheritDoc} */
  @RunsInEDT
  public void stopCellEditing(JTable table, int row, int column) {
    validateAndStopEditing(table, row, column);
    robot.waitForIdle();
  }

  /**
   * Scrolls the given <code>{@link JTable}</code> to the given cell.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param table the target <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @param location understands how to get the bounds of the given cell.
   */
  @RunsInCurrentThread
  protected static void scrollToCell(JTable table, int row, int column, JTableLocation location) {
    table.scrollRectToVisible(location.cellBounds(table, row, column));
  }

  /**
   * Throws a <code>{@link ActionFailedException}</code> if the given editor cannot be handled by this
   * <code>{@link JTableCellWriter}</code>.
   * @param editor the given editor.
   * @return the thrown exception.
   */
  protected static final ActionFailedException cannotHandleEditor(Component editor) {
    throw actionFailure(concat("Unable to handle editor component of type ", editorTypeName(editor)));
  }

  private static String editorTypeName(Component editor) {
    if (editor == null) return "<null>";
    return editor.getClass().getName();
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public Component editorForCell(JTable table, int row, int column) {
    return cellEditor(table, row, column);
  }
  
  @RunsInEDT
  private static Component cellEditor(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        validateIndices(table, row, column);
        return cellEditorIn(table, row, column);
      }
    });
  }

  /**
   * Finds the component used as editor for the given <code>{@link JTable}</code>.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param <T> the generic type of the supported editor type.
   * @param table the target <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @param supportedType the type of component we expect as editor.
   * @return the component used as editor for the given table cell.
   * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the <code>JTable</code> does not
   * have any rows.
   * @throws IllegalStateException if the <code>JTable</code> is disabled.
   * @throws IllegalStateException if the <code>JTable</code> is not showing on the screen.
   * @throws IllegalStateException if the table cell in the given coordinates is not editable.
   * @throws ActionFailedException if an editor for the given cell cannot be found.
   */
  @RunsInCurrentThread
  protected static <T extends Component> T editor(JTable table, int row, int column, Class<T> supportedType) {
    validateIndices(table, row, column);
    validateIsEnabledAndShowing(table);
    validateCellIsEditable(table, row, column);
    Component editor = cellEditorIn(table, row, column);
    if (supportedType.isInstance(editor)) return supportedType.cast(editor);
    throw cannotHandleEditor(editor);
  }
}