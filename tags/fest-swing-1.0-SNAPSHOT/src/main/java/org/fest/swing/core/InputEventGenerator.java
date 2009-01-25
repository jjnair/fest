/*
 * Created on Apr 2, 2008
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Point;

import org.fest.swing.exception.ActionFailedException;

/**
 * Understands generation of input events.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
interface InputEventGenerator {

  /**
   * Simulates a user pressing mouse buttons.
   * @param buttons the buttons to press.
   */
  void pressMouse(int buttons);

  /**
   * Simulates a user pressing the given mouse buttons on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param where the position where to press the given mouse button.
   * @param buttons the mouse buttons to press.
   * @throws ActionFailedException if the component to click is out of the boundaries of the screen.
   */
  void pressMouse(Component c, Point where, int buttons);

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given
   * <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @param x horizontal coordinate relative to the given <code>Component</code>.
   * @param y vertical coordinate relative to the given <code>Component</code>.
   */
  void moveMouse(Component c, int x, int y);
  
  /**
   * Releases the given mouse buttons.
   * @param buttons the mouse buttons to release.
   */
  void releaseMouse(int buttons);

  /**
   * Simulates a user pressing given key.
   * @param keyCode the code of the key to press.
   * @param keyChar the given character.
   * @see java.awt.event.KeyEvent
   */
  void pressKey(int keyCode, char keyChar);

  /**
   * Simulates a user releasing the given key.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   */
  void releaseKey(int keyCode);

}