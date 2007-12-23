/*
 * Created on Dec 22, 2007
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
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link IntArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class IntArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new IntArrayAssert(459, 23).contains(459, 23);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new IntArrayAssert(emptyArray()).contains(459, 23);
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new IntArrayAssert(459, 23).excludes(90, 82);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new IntArrayAssert(459, 23).excludes(459, 23);
  }

  @Test public void shouldPassIfArrayIsNull() {
    new IntArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new IntArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new IntArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new IntArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new IntArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new IntArrayAssert(459, 23).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new IntArrayAssert(459, 23).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new IntArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new IntArrayAssert(459, 23).isEqualTo(array(459, 23));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new IntArrayAssert(459, 23).isEqualTo(array(90, 82));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new IntArrayAssert(459, 23).isNotEqualTo(array(90, 82));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new IntArrayAssert(459, 23).isNotEqualTo(array(459, 23));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new IntArrayAssert(emptyArray()).containsOnly(459, 23);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new IntArrayAssert(459, 23, 88).containsOnly(array(459, 23));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new IntArrayAssert(459, 23).containsOnly(array(90, 82));
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new IntArrayAssert(459, 23).containsOnly(array(459, 23));    
  }
  
  private int[] nullArray() { return null; }

  private int[] emptyArray() { return new int[0]; }
  
  private int[] array(int... args) { return args; }
}
