/*
 * Created on Jun 22, 2007
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
package org.fest.swing.groovy;

import java.awt.Component;
import java.util.Map;

import org.fest.swing.fixture.ComponentFixture;

/**
 * Understands creation of FEST fixtures.
 * @param <C> the type of component that the fixture to create can manage. 
 * @param <F> the type of the fixture to create.
 *
 * @author Alex Ruiz
 */
public interface FixtureFactory<C extends Component, F extends ComponentFixture<C>> {

  /**
   * Creates a new FEST fixture.
   * @param builder the <code>GUITestBuilder</code> being used.
   * @param name the name of the fixture to create.
   * @param value the value given to the builder.
   * @param properties the properties for the fixture to create.
   * @return the created FEST fixture.
   */
  F newInstance(GUITestBuilder builder, Object name, Object value, Map<Object, Object> properties);
  
  boolean subnodeHandled(ComponentFixture<?> fixture, GUITestBuilder builder, Object name, Object value, Map properties);
}
