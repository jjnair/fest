/*
 * Created on Aug 28, 2008
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
package org.fest.swing.factory;

import java.awt.Frame;

import javax.swing.JDialog;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands creation of <code>{@link JDialog}</code>s.
 *
 * @author Alex Ruiz
 */
public final class JDialogs {

  private JDialogs() {}

  public static JDialogFactory dialog() {
    return new JDialogFactory();
  }

  public static class JDialogFactory {
    String name;
    String title;
    Frame owner;

    public JDialogFactory withOwner(Frame newOwner) {
      owner = newOwner;
      return this;
    }

    public JDialogFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JDialogFactory withTitle(String newTitle) {
      title = newTitle;
      return this;
    }

    @RunsInEDT
    public JDialog createNew() {
      return execute(new GuiQuery<JDialog>() {
        protected JDialog executeInEDT() {
          return create();
        }
      });
    }

    @RunsInEDT
    public JDialog createAndShow() {
      return execute(new GuiQuery<JDialog>() {
        protected JDialog executeInEDT() {
          JDialog dialog = create();
          dialog.pack();
          dialog.setVisible(true);
          return dialog;
        }
      });
    }

    @RunsInCurrentThread
    JDialog create() {
      JDialog dialog = owner != null ? new JDialog(owner) : new JDialog();
      dialog.setName(name);
      dialog.setTitle(title);
      return dialog;
    }
  }
}