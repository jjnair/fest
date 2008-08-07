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

import javax.swing.JComboBox;

import org.testng.annotations.Test;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.task.IsComponentShowingTask.isShowing;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTableComboBoxEditorCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableComboBoxEditorCellWriterTest extends JTableCellWriterTestCase {

  protected JTableCellWriter createWriter(Robot robot) {
    return new JTableComboBoxEditorCellWriter(robot);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemInComboBoxEditor(EventMode eventMode) {
    robot().settings().eventMode(eventMode);
    writer().enterValue(table(), 0, 2, "Pool");
    assertThat(valueAt(0, 2)).isEqualTo("Pool");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldCancelEditing(EventMode eventMode) {
    robot().settings().eventMode(eventMode);
    int row = 0;
    int column = 2;
    JComboBox editor = (JComboBox)writer().editorForCell(table(), row, column);
    writer().startCellEditing(table(), row, column);
    assertThat(isShowing(editor)).isTrue();
    writer().cancelCellEditing(table(), row, column);
    assertThat(valueAt(row, column)).isEqualTo("Snowboarding");
    assertThat(isShowing(editor)).isFalse();
  }
}