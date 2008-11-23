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

import javax.swing.JSlider;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JSliderSetValueTask}</code>.
 *
 * @author Alex Ruiz
 */
public class JSliderSetValueTaskTest {

  private Robot robot;
  private JSlider slider;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    slider = window.slider;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "values", groups = { ACTION, GUI })
  public void shouldReturnValueOfJSlider(int value) {
    JSliderSetValueTask.setValue(slider, value);
    robot.waitForIdle();
    assertThat(valueOf(slider)).isEqualTo(value);
  }

  @DataProvider(name = "values") public Object[][] values() {
    return new Object[][] { { 8 }, { 10 }, { 28 }, { 68 }, { 80 } };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSlider slider = new JSlider(6, 80);

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSliderSetValueTaskTest.class);
      slider.setValue(6);
      addComponents(slider);
    }
  }
}
