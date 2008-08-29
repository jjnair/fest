/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JComboBox;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JComboBoxes.comboBox;
import static org.fest.swing.factory.JTextFields.textField;

/**
 * Tests for <code>{@link JComboBoxFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JComboBoxFormatterTest {

  private JComboBox comboBox;
  private JComboBoxFormatter formatter;
  
  @BeforeClass public void setUp() {
    comboBox = newComboBox();
    formatter = new JComboBoxFormatter();
  }
  
  private static JComboBox newComboBox() {
    return comboBox().editable(true)
                     .withItems("One", 2, "Three", 4)
                     .withName("comboBox")
                     .withSelectedIndex(1)
                     .createInEDT();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJComboBox() {
    formatter.format(textField().createInEDT());
  }
  
  @Test public void shouldFormatJComboBox() {
    String formatted = formatter.format(comboBox);
    assertThat(formatted).contains(comboBox.getClass().getName())
                         .contains("name='comboBox'")
                         .contains("selectedItem=2")
                         .contains("contents=['One', 2, 'Three', 4]")
                         .contains("editable=true")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
