/*
 * Created on Jun 12, 2007
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

/**
 * Understands simulation of user events on a <code>{@link Component}</code> that contains or displays a group of items,
 * and verification of the state of such <code>{@link Component}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface ItemGroupFixture {

  /**
   * Returns the elements in this fixture's <code>{@link Component}</code> as <code>String</code>s.
   * @return the elements in the managed <code>Component</code>.
   */
  String[] contents();

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link Component}</code>. 
   * @param index the index of the item to select.
   * @return this fixture.
   */
  ItemGroupFixture selectItem(int index);

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link Component}</code>. 
   * @param text the text of the item to select.
   * @return this fixture.
   */
  ItemGroupFixture selectItem(String text);

  /**
   * Returns the <code>String</code> representation of an item in the <code>{@link Component}</code> managed by this 
   * fixture. If such <code>String</code> representation is not meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the String reprentation of the item under the given index, or <code>null</code> if nothing meaningful.
   */
  String valueAt(int index);
  
  /**
   * Verifies that the <code>String</code> representation of the selected item in this fixture's 
   * <code>{@link Component}</code> matches the given text.
   * @param text the text to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   */
  ItemGroupFixture requireSelection(String text);
}