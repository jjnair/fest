/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.Platform;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentDriver.propertyName;
import static org.fest.swing.format.Formatting.format;

/**
 * Understands simulation of user events on a <code>{@link Component}</code> and verification of the state of such
 * <code>{@link Component}</code>.
 * @param <T> the type of <code>Component</code> that this fixture can manage.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ComponentFixture<T extends Component> {

  /** Name of the property "font". */
  protected static final String FONT_PROPERTY = "font";

  /** Name of the property "background". */
  protected static final String BACKGROUND_PROPERTY = "background";
  
  /** Name of the property "foreground". */
  protected static final String FOREGROUND_PROPERTY = "foreground";

  /** Performs simulation of user events on <code>{@link #target}</code> */
  public final Robot robot;

  /** This fixture's <code>{@link Component}</code>. */
  public final T target;

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, Class<? extends T> type) {
    this(robot, findTarget(robot, type));
  }

  private static <C extends Component> C findTarget(Robot robot, Class<? extends C> type) {
    validate(robot, type);
    return robot.finder().findByType(type, requireShowing(robot));
  }

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, String name, Class<? extends T> type) {
    this(robot, findTarget(robot, name, type));
  }

  private static <C extends Component> C findTarget(Robot robot, String name, Class<? extends C> type) {
    validate(robot, type);
    return robot.finder().findByName(name, type, requireShowing(robot));
  }

  private static void validate(Robot robot, Class<?> type) {
    validate(robot);
    if (type == null) throw new IllegalArgumentException("The type of component to look for should not be null");
  }

  /**
   * Returns whether showing components are the only ones participating in a component lookup. The returned value is
   * obtained from the <code>{@link Settings#componentLookupScope() component lookup scope}</code> stored in this
   * fixture's <code>{@link Robot}</code>. 
   * @return <code>true</code> if only showing components can participate in a component lookup, <code>false</code>
   * otherwise.
   */
  protected boolean requireShowing() {
    return requireShowing(robot);
  }
  
  private static boolean requireShowing(Robot robot) {
    return robot.settings().componentLookupScope().requireShowing();
  }

  /**
   * Returns the <code>{@link ComponentDriver}</code> used internally to simulate user input and verify the state of
   * this fixture's <code>{@link Component}</code>.
   * @return the internal <code>ComponentDriver</code>.
   */
  protected abstract ComponentDriver driver();
  
  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Component</code>.
   * @param target the <code>Component</code> to be managed by this fixture.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  public ComponentFixture(Robot robot, T target) {
    validate(robot);
    validateTarget(target);
    this.robot = robot;
    this.target = target;
  }

  /**
   * Verifies that the given <code>{@link Robot}</code> is not <code>null</code>.
   * @param robot the <code>Robot</code> to verify.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   */
  protected static void validate(Robot robot) {
    if (robot == null) throw new IllegalArgumentException("Robot should not be null");
  }
  
  /**
   * Verifies that the given <code>{@link Component}</code> is not <code>null</code>.
   * @param target the <code>Component</code> to verify.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  protected static void validateTarget(Component target) {
    if (target == null) throw new IllegalArgumentException("Target component should not be null");
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click();

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> click(MouseButton button);

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws IllegalArgumentException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  protected abstract ComponentFixture<T> click(MouseClickInfo mouseClickInfo);

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws IllegalArgumentException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  protected final void doClick(MouseClickInfo mouseClickInfo) {
    if (mouseClickInfo == null) throw new IllegalArgumentException("The given MouseClickInfo should not be null");
    driver().click(target, mouseClickInfo.button(), mouseClickInfo.times());
  }
  
  /**
   * Simulates a user double-clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> doubleClick();

  /**
   * Simulates a user right-clicking this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> rightClick();

  /**
   * Gives input focus to this fixture's <code>{@link Component}</code>.
   * @return this fixture.
   */
  protected abstract ComponentFixture<T> focus();

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link Component}</code> .
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> pressAndReleaseKeys(int...keyCodes);

  /**
   * Simulates a user pressing given key on this fixture's <code>{@link Component}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> pressKey(int keyCode);

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link Component}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * <p>
   * The following code listing shows how to press 'CTRL' + 'C' in a platform-safe way:
   * <pre>
   * JTextComponentFixture textBox = dialog.textBox(&quot;username&quot;);
   * textBox.selectAll()
   *        .pressAndReleaseKey(key(<code>{@link KeyEvent#VK_C VK_C}</code>).modifiers({@link Platform#controlOrCommandMask() controlOrCommandMask}())); 
   * </pre>
   * </p>
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws IllegalArgumentException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  protected abstract ComponentFixture<T> pressAndReleaseKey(KeyPressInfo keyPressInfo);
  
  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link Component}</code>.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws IllegalArgumentException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  protected final void doPressAndReleaseKey(KeyPressInfo keyPressInfo) {
    if (keyPressInfo == null) throw new IllegalArgumentException("The given KeyPressInfo should not be null");
    driver().pressAndReleaseKey(target, keyPressInfo.keyCode(), keyPressInfo.modifiers());
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link Component}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  protected abstract ComponentFixture<T> releaseKey(int keyCode);

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is enabled.
   */
  protected abstract ComponentFixture<T> requireDisabled();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is disabled.
   */
  protected abstract ComponentFixture<T> requireEnabled();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>Component</code> is never enabled.
   */
  protected abstract ComponentFixture<T> requireEnabled(Timeout timeout);

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  protected abstract ComponentFixture<T> requireNotVisible();

  /**
   * Asserts that this fixture's <code>{@link Component}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  protected abstract ComponentFixture<T> requireVisible();

  /**
   * Returns a fixture that verifies the font of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the font of this fixture's <code>Component</code>.
   */
  public final FontFixture font() {
    return new FontFixture(target.getFont(), propertyName(target, FONT_PROPERTY));
  }

  /**
   * Returns a fixture that verifies the background color of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the background color of this fixture's <code>Component</code>.
   */
  public final ColorFixture background() {
    return new ColorFixture(target.getBackground(), propertyName(target, BACKGROUND_PROPERTY));
  }
  
  /**
   * Returns a fixture that verifies the foreground color of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the foreground color of this fixture's <code>Component</code>.
   */
  public final ColorFixture foreground() {
    return new ColorFixture(target.getForeground(), propertyName(target, FOREGROUND_PROPERTY));
  }

  /**
   * Returns this fixture's <code>{@link Component}</code> casted to the given subtype.
   * @param <C> enforces that the given type is a subtype of the managed <code>Component</code>.
   * @param type the type that the managed <code>Component</code> will be casted to.
   * @return this fixture's <code>Component</code> casted to the given subtype.
   * @throws AssertionError if this fixture's <code>Component</code> is not an instance of the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).as(format(target)).isInstanceOf(type);
    return type.cast(target);
  }
  
  /**
   * Returns the GUI component in this fixture (same as <code>{@link #target}</code>.)
   * @return the GUI component in this fixture.
   */
  public final T component() {
    return target;
  }
}