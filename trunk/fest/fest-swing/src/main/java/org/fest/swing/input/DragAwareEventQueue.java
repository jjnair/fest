/*
 * Created on Apr 3, 2008
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
package org.fest.swing.input;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.EmptyStackException;

import static java.awt.AWTEvent.*;
import static java.awt.event.MouseEvent.*;
import static javax.swing.SwingUtilities.*;

/**
 * Catches native drop target events, which are normally hidden from AWTEventListeners.
 * 
 * @author Alex Ruiz
 */
class DragAwareEventQueue extends EventQueue {

  private final long mask;
  private final AWTEventListener eventListener;

  DragAwareEventQueue(long mask, AWTEventListener eventListener) {
    this.mask = mask;
    this.eventListener = eventListener;
  }
  
  @Override public void pop() throws EmptyStackException {
    if (Toolkit.getDefaultToolkit().getSystemEventQueue() == this) super.pop();
  }

  /*
   * Dispatch native drag/drop events the same way non-native drags are reported. Enter/Exit are reported with the
   * appropriate source, while drag and release events use the drag source as the source.
   * <p>
   * TODO: implement enter/exit events TODO: change source to drag source, not mouse under
   */
  @Override protected void dispatchEvent(AWTEvent e) {
    if (e.getClass().getName().indexOf("SunDropTargetEvent") != -1) {
      MouseEvent mouseEvent = (MouseEvent) e;
      Component target = getDeepestComponentAt(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
      if (target != mouseEvent.getSource())
        mouseEvent = convertMouseEvent(mouseEvent.getComponent(), mouseEvent, target);
      relayDnDEvent(mouseEvent);
    }
    super.dispatchEvent(e);
  }

  private void relayDnDEvent(MouseEvent event) {
    int eventId = event.getID();
    if (eventId == MOUSE_MOVED || eventId == MOUSE_DRAGGED) {
      if ((mask & MOUSE_MOTION_EVENT_MASK) != 0) eventListener.eventDispatched(event);
      return;
    }
    if (eventId >= MOUSE_FIRST && eventId <= MOUSE_LAST) {
      if ((mask & MOUSE_EVENT_MASK) != 0) eventListener.eventDispatched(event);
    }
  }
}