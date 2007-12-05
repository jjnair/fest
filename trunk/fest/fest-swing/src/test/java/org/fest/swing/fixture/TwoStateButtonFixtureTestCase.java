/*
 * Created on Sep 18, 2007
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

import javax.swing.AbstractButton;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.testng.annotations.Test;

/**
 * Understands test methods for subclasses of <code>{@link org.fest.swing.fixture.TwoStateButtonFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class TwoStateButtonFixtureTestCase<T extends AbstractButton> extends ComponentFixtureTestCase<T> {

  private static final String SELECTED = "selected";

  @Test public void shouldPassIfButtonHasMatchingText() {
    twoStateButtonFixture().requireText("Target");
  }
  
  @Test(dependsOnMethods = "shouldPassIfButtonHasMatchingText") 
  public void shouldFailIfButtonHasNotMatchingText() {
    try {
      twoStateButtonFixture().requireText("A Button");
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      assertThat(errorMessage).contains(property("text"), expected("'A Button'"), actual("'Target'"));
    }
  }
  
  @Test public final void shouldPassIfButtonIsSelectedAndExpectingSelected() {
    twoStateButtonFixture().target.setSelected(true);
    twoStateButtonFixture().requireSelected();
  }
  
  @Test public final void shouldFailIfButtonIsNotSelectedAndExpectingSelected() {
    twoStateButtonFixture().target.setSelected(false);
    try {
      twoStateButtonFixture().requireSelected();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      assertThat(errorMessage).contains(property(SELECTED), expected("true"), actual("false"));
    }
  }
  
  @Test public final void shouldPassIfButtonIsNotSelectedAndExpectingNotSelected() {
    twoStateButtonFixture().target.setSelected(false);
    twoStateButtonFixture().requireNotSelected();
  }
  
  @Test public final void shouldFailIfButtonIsSelectedAndExpectingNotSelected() {
    twoStateButtonFixture().target.setSelected(true);
    try {
      twoStateButtonFixture().requireNotSelected();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      assertThat(errorMessage).contains(property(SELECTED), expected("false"), actual("true"));
    }
  }
  
  protected final TwoStateButtonFixture<T> twoStateButtonFixture() {
    return (TwoStateButtonFixture<T>)fixture();
  }
}
