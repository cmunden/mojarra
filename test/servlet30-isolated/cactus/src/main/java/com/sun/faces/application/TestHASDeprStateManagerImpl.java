/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.application;

import com.sun.faces.cactus.ServletFacesTestCase;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Locale;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import org.apache.cactus.WebRequest;


/**
 * This class tests the <code>StateManagerImpl</code> class with deprecated
 * methods only - does not contain any of the replacement methods (such
 * as saveView).
 */
public class TestHASDeprStateManagerImpl extends ServletFacesTestCase {

    public static final String TEST_URI = "/greeting.jsp";
                                                                                                                      
    public String getExpectedOutputFilename() {
        return "TestViewHandlerImpl_correct";
    }
                                                                                                                      
                                                                                                                      
    public static final String ignore[] = {
    };
                                                                                                                      
    public String[] getLinesToIgnore() {
        return ignore;
    }

    public boolean sendResponseToFile() {
        return true;
    }

    //
    // Constructors/Initializers
    //
    public TestHASDeprStateManagerImpl() {
        super("TestHASDeprStateManagerImpl");
    }


    public TestHASDeprStateManagerImpl(String name) {
        super(name);
    }
    
    private Application application = null;
    
    public void setUp() {
        super.setUp();
        ApplicationFactory aFactory =
            (ApplicationFactory) FactoryFinder.getFactory(
                FactoryFinder.APPLICATION_FACTORY);
        application = (Application) aFactory.getApplication();
        application.setViewHandler(new ViewHandlerImpl());
        application.setStateManager(new DeprStateManagerImpl());
    }
    
    //
    // Test Methods
    //
    
    public void beginRender(WebRequest theRequest) {
	String containerPort = System.getProperty("container.port");
	if (null == containerPort || 0 == containerPort.length()) {
	    containerPort = "8080";
	}

        theRequest.setURL("localhost:" + containerPort, "/test", "/faces", TEST_URI, null);
    }

    public void testRender() {
        UIViewRoot newView = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        newView.setViewId(TEST_URI);
        newView.setLocale(Locale.US);
        getFacesContext().setViewRoot(newView);
                                                                                                                      
        try {
            ViewHandler viewHandler =
                Util.getViewHandler(getFacesContext());
            viewHandler.renderView(getFacesContext(),
                                   getFacesContext().getViewRoot());
        } catch (IOException e) {
            System.out.println("ViewHandler IOException:" + e);
        } catch (FacesException fe) {
            System.out.println("ViewHandler FacesException: " + fe);
        }
                                                                                                                      
        assertTrue(!(getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));
                                                                                                                      
        assertTrue(verifyExpectedOutput());
    }
}
