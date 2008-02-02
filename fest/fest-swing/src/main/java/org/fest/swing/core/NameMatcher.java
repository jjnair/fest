/*
 * Created on Jun 18, 2007
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
package org.fest.swing.core;

import java.awt.Component;

import static java.lang.String.valueOf;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands <code>{@link java.awt.Component}</code> matching by name.
 *
 * @author Alex Ruiz
 */
public final class NameMatcher implements ComponentMatcher {

  private final String name;
  private final boolean requireShowing;

  /**
   * Creates a new <code>{@link NameMatcher}</code>. The component to match does not have to be showing.
   * @param name the name of the component we are looking for.
   */
  public NameMatcher(String name) {
    this(name, false);
  }

  /**
   * Creates a new <code>{@link TypeMatcher}</code>.
   * @param name the name of the component we are looking for.
   * @param requireShowing indicates if the component to match should be showing or not.
   */
  public NameMatcher(String name, boolean requireShowing) {
    this.name = name;
    this.requireShowing = requireShowing;
  }
  
  /** 
   * Indicates whether the name and visibility of the given <code>{@link java.awt.Component}</code> matches the values
   * specified in this matcher.
   * @return <code>true</code> if the name and visibility of the given <code>Component</code> matches the values
   *         specified in this matcher, <code>false</code> otherwise.
   */
  public boolean matches(Component c) {
    return areEqual(name, c.getName()) && (!requireShowing || c.isShowing());
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "name=", quote(name), ", ",
        "requireShowing=", valueOf(requireShowing), 
        "]"
    );
  }
}