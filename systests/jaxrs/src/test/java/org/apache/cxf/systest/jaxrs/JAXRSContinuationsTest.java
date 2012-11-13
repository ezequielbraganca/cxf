/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.cxf.systest.jaxrs;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.model.AbstractResourceInfo;

import org.junit.BeforeClass;
import org.junit.Test;


public class JAXRSContinuationsTest extends AbstractJAXRSContinuationsTest {
    public static final String PORT = BookContinuationServer.PORT;
    @BeforeClass
    public static void startServers() throws Exception {
        AbstractResourceInfo.clearAllMaps();
        createStaticBus();
        assertTrue("server did not launch correctly",
                   launchServer(BookContinuationServer.class));
                   
                   
    }
    
    @Test
    public void testDefaultTimeout() throws Exception {
        WebClient wc = WebClient.create("http://localhost:" + PORT + "/bookstore/books/defaulttimeout");
        WebClient.getConfig(wc).getHttpConduit().getClient().setReceiveTimeout(1000000L);
        Response r = wc.get();
        assertEquals(503, r.getStatus());
    }
    
    @Test
    public void testContinuationWithTimeHandler() throws Exception {
        
        doTestContinuation("books/timeouthandler");
    }
    
    protected String getPort() {
        return PORT;
    }
    
}
