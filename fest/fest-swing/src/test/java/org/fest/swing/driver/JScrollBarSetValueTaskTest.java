/*
 * Created on Aug 11, 2008
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

import javax.swing.JScrollBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static java.awt.Adjustable.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JScrollBarSetValueTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JScrollBarSetValueTaskTest {

  private Robot robot;
  private JScrollBar scrollBar;
  private int value;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    value = 6;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetValueOfJScrollBar() {
    JScrollBarSetValueTask.setValue(scrollBar, value);
    robot.waitForIdle();
    assertThat(valueOf(scrollBar)).isEqualTo(value);
  }

  private static class MyWindow extends TestWindow {

    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JScrollBar scrollBar = new JScrollBar(HORIZONTAL);

    private MyWindow() {
      super(JScrollBarSetValueTaskTest.class);
      scrollBar.setMinimum(2);
      scrollBar.setMaximum(20);
      scrollBar.setValue(8);
      addComponents(scrollBar);
    }
  }
}
