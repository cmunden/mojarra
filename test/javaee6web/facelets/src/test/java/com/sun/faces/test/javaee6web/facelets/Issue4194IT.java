/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2016 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDLGPL_1_1.html
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
package com.sun.faces.test.javaee6web.facelets;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Issue4194IT {

    private String webUrl;
    private WebClient webClient;
    private List<String> receivedAlerts;
    private List<String> expectedAlerts;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        receivedAlerts = new ArrayList<>();
        webClient.setAlertHandler(new CollectingAlertHandler(receivedAlerts));
        expectedAlerts = new ArrayList<>();
        expectedAlerts.add("static onSubmit triggered");
        expectedAlerts.add("static onSubmit triggered");
        expectedAlerts.add("dynamic onSubmit triggered");
        expectedAlerts.add("dynamic onSubmit triggered");
    }

    @Test
    public void testIssue4194() throws Exception {

        HtmlPage page = webClient.getPage(webUrl + "faces/issue4194.xhtml");
        HtmlElement he = page.getHtmlElementById("staticOnSubmitForm:AAA");
        he.click();
        
        page = webClient.getPage(webUrl + "faces/issue4194.xhtml");
        he = page.getHtmlElementById("staticOnSubmitForm:BBB");
        he.click();

        page = webClient.getPage(webUrl + "faces/issue4194.xhtml");
        he = page.getHtmlElementById("dynamicOnSubmitForm:AAA");
        he.click();

        page = webClient.getPage(webUrl + "faces/issue4194.xhtml");
        he = page.getHtmlElementById("dynamicOnSubmitForm:BBB");
        he.click();

        assertEquals(expectedAlerts, receivedAlerts);

    }

    @After
    public void tearDown() {
        webClient.close();
    }

}
