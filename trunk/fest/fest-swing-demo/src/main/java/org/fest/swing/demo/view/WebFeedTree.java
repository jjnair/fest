/*
 * Created on Mar 9, 2008
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
package org.fest.swing.demo.view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;

import org.fest.swing.demo.model.Folder;

import static org.fest.swing.demo.view.Icons.FOLDER_SMALL_ICON;
import static org.fest.util.Arrays.array;

/**
 * Understands the tree containing all web feeds.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class WebFeedTree extends JXTree {

  private static final long serialVersionUID = 1L;

  private static final String TREE_ROOT_KEY = "tree.root";

  private final DefaultMutableTreeNode root;
  private final DefaultTreeModel model;

  private final NodeModelComparator comparator = new NodeModelComparator();
  
  private final I18n i18n;
  
  private final Map<String, FolderNode> folderNodes = new HashMap<String, FolderNode>();
  
  WebFeedTree() {
    i18n = new I18n(this);
    root = new DefaultMutableTreeNode(i18n.message(TREE_ROOT_KEY));
    model = new DefaultTreeModel(root);
    setModel(model);
    setRootVisible(true);
    setOpenIcon(FOLDER_SMALL_ICON);
    setClosedIcon(FOLDER_SMALL_ICON);
  }
  
  void addContent(Object content) {
    if (content instanceof Folder) addFolder((Folder)content);
  }
  
  private FolderNode addFolder(Folder folder) {
    String folderName = folder.name();
    if (folderNodes.containsKey(folderName)) return folderNodes.get(folderName);
    FolderNode folderNode = new FolderNode(folder);
    addFolderNode(folderNode);
    return folderNode;
  }

  private void addFolderNode(FolderNode folderNode) {
    int insertIndex = indexForNewNodeInsertion(folderNode);
    model.insertNodeInto(folderNode, root, insertIndex);
    setSelectionPath(new TreePath(array(root, folderNode)));
    folderNodes.put(folderNode.folder.name(), folderNode);
  }
  
  private int indexForNewNodeInsertion(TreeNode nodeToInsert) {
    int childCount = root.getChildCount();
    if (childCount == 0) return 0;
    if (childCount == 1) {
      int compareToFirst = comparator.compare(nodeToInsert, root.getChildAt(0));
      return compareToFirst < 0 ? 0 : 1;
    }
    for (int i = 0; i < childCount; i++) {
      TreeNode currentNode = root.getChildAt(i);
      int compareToCurrentNode = comparator.compare(nodeToInsert, currentNode);
      if (compareToCurrentNode < 0) return i;
    }
    return childCount++;
  }

  private static class FolderNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;
    
    final Folder folder;

    FolderNode(Folder folder) {
      super(folder.name());
      this.folder = folder;
      setAllowsChildren(true);
    }

    /** @see javax.swing.tree.DefaultMutableTreeNode#isLeaf() */
    @Override public boolean isLeaf() {
      return false;
    }
  }
  
  private static class NodeModelComparator implements Comparator<TreeNode> {
    public int compare(TreeNode node1, TreeNode node2) {
      String text1 = textFrom(node1);
      String text2 = textFrom(node2);
      if (text1 == null) {
        if (text2 == null) return 0;
        return -1;
      }
      if (text2 == null) return 1;
      return text1.compareToIgnoreCase(text2);
    }
    
    private String textFrom(TreeNode node) {
      if (!(node instanceof DefaultMutableTreeNode)) return null;
      Object userObject = ((DefaultMutableTreeNode)node).getUserObject();
      if (userObject != null) return userObject.toString();
      return null;
    }
  }
}
