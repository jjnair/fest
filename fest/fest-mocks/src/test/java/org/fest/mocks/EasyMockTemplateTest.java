/*
 * Created on Apr 22, 2007
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
package org.fest.mocks;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.createMock;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for <code>{@link EasyMockTemplate}</code>.
 *
 * @author Alex Ruiz
 */
public class EasyMockTemplateTest {

  private static interface Server {
    void process(String arg);
  }
  
  private static class Client {
    final Server server;
    
    Client(Server server) {
      this.server = server;
    }
    
    void delegateProcessToServer(String arg) {
      server.process(arg);
    }
  }
  
  private Client client;
  private Server mockServer;
  
  @BeforeMethod public void setUp() {
    mockServer = createMock(Server.class);
    client = new Client(mockServer);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsNull() {
    new EasyMockTemplate(new Object[0]) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksContainsNull() {
    Object[] mocks = new Object[] { mockServer, null };
    new EasyMockTemplate(mocks) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfArrayOfMocksIsEmpty() {
    new EasyMockTemplate((Object[])null) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };    
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "notMocksProvider")
  public void shouldThrowExceptionIfGivenObjectIsNotMock(Object[] mocks) {
    new EasyMockTemplate(mocks) {
      @Override protected void codeToTest() {}
      @Override protected void expectations() {}
    };    
  }

  @DataProvider(name = "notMocksProvider")
  public Object[][] notMocksProvider() {
   return new Object[][] { 
       { new Object[] { mockServer, "Not a mock" } }, 
       { new Object[] { "Not a mock" } }, 
       { new Object[] { "Not a mock", "Not a mock either" } }, 
       { new Object[] { "Not a mock", mockServer } } 
   };
  }

  @Test public void shouldCompleteMockUsageCycle() {
    final String arg = "name";
    EasyMockTemplateStub template = new EasyMockTemplateStub(mockServer) {
      
      @Override protected void expectations() {
        super.expectations();
        mockServer.process(arg);  
      }
      
      @Override protected void codeToTest() {
        super.codeToTest();
        client.delegateProcessToServer(arg);
      }
    };
    template.run();
    assertMethodCallOrder(template.setUp, template.expectations, template.codeToTest);
  }

  private void assertMethodCallOrder(int... callOrders) {
    int expected = 0;
    for (int callOrder : callOrders) assertEquals(callOrder, ++expected);
  }
  
  /** <code>{@link EasyMockTemplate}</code> that tracks the order in which its methods are called. */
  private static class EasyMockTemplateStub extends EasyMockTemplate {
    int setUp;
    int expectations;
    int codeToTest;

    int methodCallOrder;
    
    public EasyMockTemplateStub(Object... mocks) {
      super(mocks);
    }

    @Override protected void setUp() {
      setUp = ++methodCallOrder;
    }

    @Override protected void expectations() {
      expectations = ++methodCallOrder;
    }
    
    @Override protected void codeToTest() {
      codeToTest = ++methodCallOrder;     
    }
  }
}