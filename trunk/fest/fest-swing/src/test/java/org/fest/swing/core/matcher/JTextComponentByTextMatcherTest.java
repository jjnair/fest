/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import javax.swing.JTextField;

import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTextComponentByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JTextComponentByTextMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText(text);
    JTextField textField = textField().withText(text).createInEDT();
    assertThat(matcher.matches(textField)).isTrue();
  }
  
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText("Hello");
    JTextField textField = textField().withText("Bye").createInEDT();
    assertThat(matcher.matches(textField)).isFalse();
  }
  
  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    MyWindow window = MyWindow.showNew();
    try {
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(window.textField)).isTrue();
    } finally {
      window.destroy();
    }
  } 
  
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing(text);
    JTextField textField = textField().withText(text).createInEDT();
    assertThat(matcher.matches(textField)).isFalse();    
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    MyWindow window = MyWindow.showNew();
    try {
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Bye");
      assertThat(matcher.matches(window.textField)).isFalse();
    } finally {
      window.destroy();
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
    JTextField textField = textField().withText("Bye").createInEDT();
    assertThat(matcher.matches(textField)).isFalse();    
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow showNew() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
      window.display();
      return window;
    }
    
    final JTextField textField = new JTextField("Hello");
    
    MyWindow() {
      super(JLabelByTextMatcherTest.class);
      addComponents(textField);
    }
  }
}
