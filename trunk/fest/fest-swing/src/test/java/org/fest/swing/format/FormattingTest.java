/*
 * Created on Sep 16, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.format;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.*;

import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;

import static java.awt.Adjustable.VERTICAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.factory.JDialogs.dialog;
import static org.fest.swing.factory.JFrames.frame;
import static org.fest.swing.factory.JLabels.label;
import static org.fest.swing.factory.JMenuBars.menuBar;
import static org.fest.swing.factory.JMenuItems.menuItem;
import static org.fest.swing.factory.JPanels.panel;
import static org.fest.swing.factory.JPopupMenus.popupMenu;
import static org.fest.swing.factory.JScrollBars.scrollBar;
import static org.fest.swing.factory.JScrollPanes.scrollPane;
import static org.fest.swing.factory.JSliders.slider;
import static org.fest.swing.factory.JSpinners.spinner;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.factory.JToggleButtons.toggleButton;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FormattingTest {

  private static Logger logger = Logger.getAnonymousLogger();

  public void shouldReplaceExistingFormatter() {
    final Class<JComboBox> type = JComboBox.class;
    ComponentFormatter oldFormatter = Formatting.formatter(type);
    ComponentFormatter newFormatter = new ComponentFormatterTemplate() {
      protected String doFormat(Component c) { return null; }

      public Class<? extends Component> targetType() { 
        return type;
      }
    };
    try {
      Formatting.register(newFormatter);
      assertThat(Formatting.formatter(type)).isSameAs(newFormatter);
    } finally {
      Formatting.register(oldFormatter);
    }
  }
  
  public void shouldFormatDialog() {
    JDialog dialog = dialog().withName("dialog")
                             .withTitle("A dialog")
                             .createInEDT();
    assertThat(formatted(dialog)).contains(classNameOf(dialog))
                                 .contains("name='dialog'")
                                 .contains("title='A dialog'")
                                 .contains("enabled=true")
                                 .contains("modal=false")
                                 .contains("visible=false")
                                 .contains("showing=false");
  }

  public void shouldFormatFrame() {
    JFrame frame = frame().withName("frame")
                          .withTitle("A frame")
                          .createInEDT();
    assertThat(formatted(frame)).contains(classNameOf(frame))
                                .contains("name='frame'")
                                .contains("title='A frame'")
                                .contains("enabled=true")
                                .contains("visible=false")
                                .contains("showing=false");
  }
  
  public void shouldFormatJComboBox() {
    assertThat(Formatting.formatter(JComboBox.class)).isInstanceOf(JComboBoxFormatter.class);
  }

  public void shouldFormatJButton() {
    JButton button = button().enabled(false)
                             .withName("button")
                             .withText("A button")
                             .createInEDT();
    assertThat(formatted(button)).contains(classNameOf(button))
                                 .contains("name='button'")
                                 .contains("text='A button'")
                                 .contains("enabled=false")
                                 .contains("visible=true")
                                 .contains("showing=false");
  }

  public void shouldFormatJFileChooser() {
    assertThat(Formatting.formatter(JFileChooser.class)).isInstanceOf(JFileChooserFormatter.class);
  }

  public void shouldFormatJLabel() {
    JLabel label = label().withName("label")
                          .withText("A label")
                          .createInEDT();
    assertThat(formatted(label)).contains(classNameOf(label))
                                .contains("name='label'")
                                .contains("text='A label'")
                                .contains("enabled=true")
                                .contains("visible=true")
                                .contains("showing=false");
  }

  public void shouldFormatJLayeredPane() {
    JLayeredPane pane = newJLayeredPane();
    assertThat(formatted(pane)).isEqualTo(concat(classNameOf(pane), "[]"));
  }

  private static JLayeredPane newJLayeredPane() {
    return execute(new GuiQuery<JLayeredPane>() {
      protected JLayeredPane executeInEDT() {
        return new JLayeredPane();
      }
    });
  }

  public void shouldFormatJList() {
    assertThat(Formatting.formatter(JList.class)).isInstanceOf(JListFormatter.class);
  }

  public void shouldFormatJMenuBar() {
    JMenuBar menuBar = menuBar().createInEDT();
    assertThat(formatted(menuBar)).isEqualTo(concat(classNameOf(menuBar), "[]"));
  }

  public void shouldFormatJMenuItem() {
    JMenuItem menuItem = menuItem().withName("menuItem")
                                   .selected(true)
                                   .withText("A menu item")
                                   .createInEDT();
    assertThat(formatted(menuItem)).contains(classNameOf(menuItem))
                                   .contains("name='menuItem'")
                                   .contains("text='A menu item'")
                                   .contains("selected=true")
                                   .contains("enabled=true")
                                   .contains("visible=true")
                                   .contains("showing=false");
  }

  public void shouldFormatJOptionPane() {
    assertThat(Formatting.formatter(JOptionPane.class)).isInstanceOf(JOptionPaneFormatter.class);
  }

  public void shouldFormatJPanel() {
    JPanel panel = panel().withName("panel").createInEDT();
    assertThat(formatted(panel)).contains(classNameOf(panel))
                                .contains("name='panel'");
  }

  public void shouldFormatJPopupMenu() {
    JPopupMenu popupMenu = popupMenu().withLabel("Menu")
                                      .withName("popupMenu")
                                      .createInEDT();
    assertThat(formatted(popupMenu)).contains(classNameOf(popupMenu))
                                    .contains("name='popupMenu'")
                                    .contains("label='Menu'")
                                    .contains("enabled=true")
                                    .contains("visible=false")
                                    .contains("showing=false");
  }

  public void shouldFormatJRootPane() {
    JRootPane pane = newJRootPane();
    assertThat(formatted(pane)).isEqualTo(concat(classNameOf(pane), "[]"));
  }

  private static JRootPane newJRootPane() {
    return execute(new GuiQuery<JRootPane>() {
      protected JRootPane executeInEDT()  {
        return new JRootPane();
      }
    });
  }

  public void shouldFormatJScrollBar() {
    JScrollBar scrollBar = scrollBar().withBlockIncrement(10)
                                      .withMinimum(0)
                                      .withMaximum(60)
                                      .withName("scrollBar")
                                      .withOrientation(VERTICAL)
                                      .withValue(20)
                                      .createInEDT();
    assertThat(formatted(scrollBar)).contains(classNameOf(scrollBar))
                                    .contains("name='scrollBar'")
                                    .contains("value=20")
                                    .contains("blockIncrement=10")
                                    .contains("minimum=0")
                                    .contains("maximum=60")
                                    .contains("enabled=true")
                                    .contains("visible=true")
                                    .contains("showing=false");
  }

  public void shouldFormatJScrollPane() {
    JScrollPane scrollPane = scrollPane().withName("scrollPane").createInEDT();
    assertThat(formatted(scrollPane)).contains(classNameOf(scrollPane))
                                     .contains("name='scrollPane'")
                                     .contains("enabled=true")
                                     .contains("visible=true")
                                     .contains("showing=false");
  }

  public void shouldFormatJSlider() {
    JSlider slider = slider().withMaximum(8)
                             .withMinimum(2)
                             .withValue(6)
                             .withName("slider")
                             .createInEDT();
    assertThat(formatted(slider)).contains(classNameOf(slider))
                                 .contains("name='slider'")   
                                 .contains("value=6")   
                                 .contains("minimum=2")   
                                 .contains("maximum=8")   
                                 .contains("enabled=true")   
                                 .contains("visible=true")   
                                 .contains("showing=false"); 
  }

  public void shouldFormatJSpinner() {
    JSpinner spinner = spinner().withName("spinner")
                                .withValues(6, 2, 8, 1)
                                .createInEDT();
    assertThat(formatted(spinner)).contains(classNameOf(spinner))
                                  .contains("name='spinner'")
                                  .contains("value=6")
                                  .contains("enabled=true")
                                  .contains("visible=true")
                                  .contains("showing=false");
  }

  public void shouldFormatJTabbedPane() {
    assertThat(Formatting.formatter(JTabbedPane.class)).isInstanceOf(JTabbedPaneFormatter.class);
  }

  public void shouldFormatJTable() {
    assertThat(Formatting.formatter(JTable.class)).isInstanceOf(JTableFormatter.class);
  }

  public void shouldFormatJPasswordField() {
    JPasswordField passwordField = newJPasswordField();
    assertThat(formatted(passwordField)).contains(classNameOf(passwordField))
                                        .contains("name='passwordField'")
                                        .contains("enabled=true")
                                        .contains("visible=true")
                                        .contains("showing=false");
  }

  private static JPasswordField newJPasswordField() {
    return execute(new GuiQuery<JPasswordField>() {
      protected JPasswordField executeInEDT()  {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setName("passwordField");
        return passwordField;
      }
    });
  }

  public void shouldFormatJTextComponent() {
    JTextField textField = textField().withName("textField")
                                      .withText("Hello")
                                      .createInEDT();
    assertThat(formatted(textField)).contains(classNameOf(textField))
                                    .contains("name='textField'")
                                    .contains("text='Hello'")
                                    .contains("enabled=true")
                                    .contains("visible=true")
                                    .contains("showing=false");
  }

  public void shouldFormatJToggleButton() {
    JToggleButton toggleButton = toggleButton().withName("toggleButton")
                                               .selected(true)
                                               .withText("A toggle button")
                                               .createInEDT();
    assertThat(formatted(toggleButton)).contains(classNameOf(toggleButton))
                                       .contains("name='toggleButton'")
                                       .contains("text='A toggle button'")
                                       .contains("selected=true")
                                       .contains("enabled=true")
                                       .contains("visible=true")
                                       .contains("showing=false");
  }

  public void shouldFormatJTree() {
    assertThat(Formatting.formatter(JTree.class)).isInstanceOf(JTreeFormatter.class);
  }

  private String formatted(Component c) {
    String formatted = Formatting.format(c);
    logger.info(concat("formatted: ", formatted));
    return formatted;
  }

  private static String classNameOf(Object o) {
    return o.getClass().getName();
  }
  
  public void shouldReturnComponentIsNullIfComponentIsNull() {
    assertThat(Formatting.format(null)).isEqualTo("Null Component");
  }
}
