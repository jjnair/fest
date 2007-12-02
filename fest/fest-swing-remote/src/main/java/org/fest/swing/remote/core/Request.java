/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.core;

import static org.fest.swing.remote.util.System.LINE_SEPARATOR;
import static org.fest.util.Strings.concat;


/**
 * Understands a request for the GUI test server.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Request extends Message {

  private static final long serialVersionUID = 1L;
  
  /**
   * Understands different types of request.
   *
   * @author Alex Ruiz
   * @author Yvonne Wang
   */
  public static enum Type {
    PING;
  }
  
  private final Type type;

  public static Request pingRequest() {
    return new Request(Type.PING);
  }
  
  private Request(Type type) {
    this.type = type;
  }

  /**
   * Returns the type of this request.
   * @return the type of this request.
   */
  public Type type() { return type; }

  /**
   * Returns a <code>String</code> representation of this class.
   * @return a <code>String</code> representation of this class.
   */
  @Override public String toString() {
    StringBuilder b = new StringBuilder();
    b.append(concat(getClass().getName(), "[", LINE_SEPARATOR));
    b.append(concat("  ", "type:", type, LINE_SEPARATOR));
    b.append(concat(super.toString(), LINE_SEPARATOR));
    b.append("]");
    return b.toString();
  }
}
