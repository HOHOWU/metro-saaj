/*
 * 
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
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

/**
*
* @author JAX-RPC RI Development Team
*/
package util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class JAXPHelper {
    private static DocumentBuilder builder;
    private static Transformer transformer;

    static {
        DocumentBuilderFactory factory =
            new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();
        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Create an instance of our own transformer factory impl
            TransformerFactory transFactory =
                new com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl();

            // create Transformer
            transformer = transFactory.newTransformer();

        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser     
            System.out.println("* Transformer Factory error");
            System.out.println("  " + tce.getMessage());

            // Use the contained exception, if any      
            Throwable x = tce;
            if (tce.getException() != null)
                x = tce.getException();
            x.printStackTrace();

        }
    }

    public static Source readXMLFile(String xmlFileName) {

        Source domSource = null;

        try {
            Document domDoc = builder.parse(new File(xmlFileName));
            domSource = new javax.xml.transform.dom.DOMSource(domDoc);

        } catch (SAXParseException spe) {
            // Error generated by the parser    
            System.out.println(
                "\n** Parsing error"
                    + ", line "
                    + spe.getLineNumber()
                    + ", uri "
                    + spe.getSystemId());
            System.out.println("  " + spe.getMessage());

            // Use the contained exception, if any  
            Exception x = spe;
            if (spe.getException() != null)
                x = spe.getException();
            x.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return domSource;
    }
}
