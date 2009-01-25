/*
 * Created on Jan 24, 2009
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.field;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands a template for the type of a field to access using Java Reflection. This implementation supports
 * Java generics.
 *
 * @author Alex Ruiz
 */
abstract class TypeReferenceTemplate<T> {

  private final TypeRef<T> type;
  private final String name;

  TypeReferenceTemplate(TypeRef<T> type, NameTemplate fieldName) {
    name = fieldName.name;
    if (type == null) throw new IllegalArgumentException("The type reference of the field to access should not be null");
    this.type = type;
  }

  final Invoker<T> fieldInvoker(Object target, Class<?> declaringType) {
    Invoker<T> invoker = new Invoker<T>(name, target);
    invoker.verifyCorrectType(type);
    return invoker;
  }

  final Invoker<T> fieldInvoker(Class<?> target, Class<?> declaringType) {
    Invoker<T> invoker = new Invoker<T>(name, target);
    invoker.verifyCorrectType(type);
    return invoker;
  }
}
