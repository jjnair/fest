/*
 * Created on Dec 16, 2006
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
 * Copyright @2006 the original author or authors.
 */
package com.jtuzi.fest.fixture;

import javax.swing.JButton;

import com.jtuzi.fest.RobotFixture;

import static com.jtuzi.fest.assertions.Assertions.assertThat;


/**
 * Understands simulation of user events and state verification of a <code>{@link JButton}</code>.
 *
 * @author Yvonne Wang
 */
public class JButtonFixture extends AbstractComponentFixture<JButton> implements TextDisplayFixture<JButton> {

  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JButton</code>.
   * @param buttonName the name of the button to find using the given <code>AbbotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public JButtonFixture(RobotFixture robot, String buttonName) {
    super(robot, buttonName, JButton.class);
  }
  
  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on the given button.
   * @param target the target button.
   */
  public JButtonFixture(RobotFixture robot, JButton target) {
    super(robot, target);
  }

  /** {@inheritDoc} */
  public final JButtonFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JButtonFixture focus() {
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final JButtonFixture shouldHaveThisText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }

  /** {@inheritDoc} */
  public final String text() {
    return target.getText();
  }

  /** {@inheritDoc} */
  public JButtonFixture shouldBeVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public JButtonFixture shouldNotBeVisible() {
    assertIsNotVisible();
    return this;
  }
}
