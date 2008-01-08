/*
 * Created on Jan 7, 2008
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

/**
 * Understands scopes of GUI component lookup.
 *
 * @author Alex Ruiz
 */
public enum ComponentLookupScope {

  /**
   * For most of the cases, only GUI components that are being shown on the screen participate in a component lookup.
   * Exceptions include:
   * <ul>
   * <li><code>{@link javax.swing.JMenuItem}</code></li>
   * </ul>
   */
  DEFAULT, 
  
  /** All GUI components participate in a component lookup, regardless if they are being shown on the screen or not. */
  ALL, 
  
  /** Only components that are being shown on the screen can participate in a component lookup. */
  SHOWING_ONLY 

}
