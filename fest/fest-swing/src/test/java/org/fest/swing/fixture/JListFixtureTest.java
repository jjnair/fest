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

import java.awt.Point;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JListDriver;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.util.Range.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JListFixture}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class JListFixtureTest extends ComponentFixtureTestCase<JList> {

  private JListDriver driver;
  private JList target;
  private JListFixture fixture;
  
  void onSetUp(Robot robot) {
    driver = createMock(JListDriver.class);
    target = new JList();
    fixture = new JListFixture(robot, target);
    fixture.updateDriver(driver);
  }
  
  @Test public void shouldReturnContents() {
    final String[] contents = array("Luke", "Leia");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.contentsOf(target)).andReturn(contents);
      }
      
      protected void codeToTest() {
        String[] result = fixture.contents();
        assertThat(result).isSameAs(contents);
      }
    }.run();
  }

  @Test public void shouldSelectItemsInRange() {
    final From from = from(6);
    final To to = to(8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, from, to);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(from, to));
      }
    }.run();
  }

  @Test public void shouldSelectItemsUnderIndices() {
    final int[] indices = new int[] { 6, 8 };
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, indices);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(indices));
      }
    }.run();
  }
  
  @Test public void shouldSelectItemUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, 6);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem(6));
      }
    }.run();
  }

  @Test public void shouldSelectItemsWithValues() {
    final String[] values = array("Frodo", "Sam");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItems(target, values);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItems(values));
      }
    }.run();
  }

  @Test public void shouldSelectItemWithValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, "Frodo");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem("Frodo"));
      }
    }.run();
  }

  @Test public void shouldDoubleClickItemUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickItem(target, 6, LEFT_BUTTON, 2);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickItem(6));
      }
    }.run();
  }

  @Test public void shouldDoubleClickItemWithValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickItem(target, "Frodo", LEFT_BUTTON, 2);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.doubleClickItem("Frodo"));
      }
    }.run();
  }

  @Test public void shouldRequireSelection() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelection(target, "Frodo");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelection("Frodo"));
      }
    }.run();
  }
  
  @Test public void shouldRequireSelectedItems() {
    final String[] items = array("Frodo", "Sam");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelectedItems(target, items);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelectedItems(items));
      }
    }.run();
  }

  @Test public void shouldReturnValueAtIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.text(target, 6)).andReturn("Frodo");
      }
      
      protected void codeToTest() {
        assertThat(fixture.valueAt(6)).isEqualTo("Frodo");
      }
    }.run();
  }
  
  @Test public void shouldReturnJListItemFixture() {
    JListItemFixture item = fixture.item(6);
    assertThat(item.index).isEqualTo(6);
    assertThat(item.list).isSameAs(fixture);
  }

  @Test public void shouldReturnJListItemFixtureUsingValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.indexOf(target, "Frodo")).andReturn(8);
      }
      
      protected void codeToTest() {
        JListItemFixture item = fixture.item("Frodo");
        assertThat(item.index).isEqualTo(8);
        assertThat(item.list).isSameAs(fixture);
      }
    }.run();
  }
  
  @Test public void shouldDragElementWithElement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, "Frodo");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag("Frodo"));
      }
    }.run();
  }

  @Test public void shouldDropElementWithElement() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, "Frodo");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop("Frodo"));
      }
    }.run();
  }

  @Test public void shouldDragElementUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(8));
      }
    }.run();
  }

  @Test public void shouldDrop() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop());
      }
    }.run();
  }

  @Test public void shouldDropElementUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(8));
      }
    }.run();
  }

  @Test public void shouldShowJPopupMenuAtItemUnderIndex() {
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenuAt(target, 6)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(6);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  @Test public void shouldShowJPopupMenuAtItemWithValue() {
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenuAt(target, "Frodo")).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt("Frodo");
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  @Test public void shouldShowJPopupMenu() {
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenu();
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }
  
  @Test public void shouldShowJPopupMenuAtPoint() {
    final Point p = new Point(8, 6);
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, p)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(p);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JList target() { return target; }
  ComponentFixture<JList> fixture() { return fixture; }
}
