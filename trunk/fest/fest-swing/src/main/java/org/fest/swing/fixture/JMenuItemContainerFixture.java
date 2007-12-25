/*
 * Created on Sep 5, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JMenuItem;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands lookup of <code>{@link JMenuItem}</code>s contained in a <code>{@link Container}</code>.
 * @param <T> the type of container handled by this fixture.
 * 
 * @author Yvonne Wang
 */
public abstract class JMenuItemContainerFixture<T extends Container> extends ComponentFixture<T> {

  /**
   * Creates a new <code>{@link JMenuItemContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>Container</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>Container</code> is found.
   * @see org.fest.swing.core.ComponentFinder#findByType(Class)
   */
  public JMenuItemContainerFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, robot.finder().findByType(type));
  }

  /**
   * Creates a new <code>{@link JMenuItemContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param name the name of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>Container</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>Container</code> is found.
   * @see org.fest.swing.core.ComponentFinder#findByName(String, Class)
   */
  public JMenuItemContainerFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, robot.finder().findByName(name, type));
  }

  /**
   * Creates a new <code>{@link JMenuItemContainerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Container</code>.
   * @param target the <code>Container</code> to be managed by this fixture.
   */
  public JMenuItemContainerFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in this fixture's <code>{@link Container}</code>, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> having a matching name could not be found.
   */
  public final JMenuItemFixture menuItem(String name) {
    return new JMenuItemFixture(robot, findByName(name, JMenuItem.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in this fixture's <code>{@link Container}</code>, 
   * that matches the specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JMenuItem</code>.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> that matches the given search criteria could not be
   * found.
   */
  public final JMenuItemFixture menuItem(GenericTypeMatcher<? extends JMenuItem> matcher) {
    return new JMenuItemFixture(robot, find(matcher));
  }

  protected final <C extends Component> C findByName(String name, Class<C> type) {
    return finder().findByName(target, name, type);
  }
  
  protected final <C extends Component> C find(GenericTypeMatcher<? extends C> matcher) {
    return finder().find(target, matcher);
  }

  protected final ComponentFinder finder() { return robot.finder(); }
}