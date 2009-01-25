/*
 * Created on Feb 6, 2008
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
package org.fest.reflect.method;


/**
 * Understands a template for the parameter types of the method to invoke.
 *
 * @author Alex Ruiz
 */
abstract class ParameterTypesTemplate<T> {

  final Class<?>[] parameterTypes;
  final String methodName;
  
  public ParameterTypesTemplate(Class<?>[] parameterTypes, ReturnTypeTemplate<T> returnType) {
    if (parameterTypes == null) throw new NullPointerException("The array of parameter types should not be null");
    this.parameterTypes = parameterTypes;
    this.methodName = returnType.methodName;
  }
}
