/*
 * Created on Aug 24, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.TestGroups.*;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JTreeChildOfPathCountQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTreeChildOfPathCountQueryTest {

  private Robot robot;
  private JTree tree;
  private TreeNode treeRoot;
  private int childCount;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    childCount = 8;
    MyWindow window = MyWindow.createNew(childCount);
    tree = window.tree;
    treeRoot = window.treeRoot;
    robot.showWindow(window);
  }

  public void shouldReturnChildCountOfTreePath() {
    TreePath path = new TreePath(treeRoot);
    int childOfPathCount = JTreeChildOfPathCountQuery.childCount(tree, path);
    assertThat(childOfPathCount).isEqualTo(childCount);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew(final int treeRootChildCount) {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow(treeRootChildCount);
        }
      });
    }

    final JTree tree;
    final TreeNode treeRoot;

    private MyWindow(int treeRootChildCount) {
      super(JTreeChildOfPathCountQueryTest.class);
      treeRoot = root(treeRootChildCount);
      tree = new JTree(treeRoot);
      addComponents(tree);
    }

    private static TreeNode root(int rootChildCount) {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      for (int i = 1; i <= rootChildCount; i++)
        root.add(new DefaultMutableTreeNode(concat("node", i)));
      return root;
    }
  }
}
