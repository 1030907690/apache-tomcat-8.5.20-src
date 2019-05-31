/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.util.digester;

import org.xml.sax.Attributes;

/**
 * Concrete implementations of this class implement actions to be taken when
 * a corresponding nested pattern of XML elements has been matched.
 */
/*
 
 Tomcat将server.xml文件中的所有元素上的属性都抽象为Rule，以Server元素为例，在内存中对应Server实例，
 Server实例的属性值就来自于Server元素的属性值。通过对规则（Rule）的应用，最终改变Server实例的属性值。Rule是一个抽象类
 * */
public abstract class Rule {

    // ----------------------------------------------------------- Constructors

    /**
     * <p>Base constructor.
     * Now the digester will be set when the rule is added.</p>
     */
    public Rule() {}


    // ----------------------------------------------------- Instance Variables


    /**
     * The Digester with which this Rule is associated.
     */
    protected Digester digester = null;


    /**
     * The namespace URI for which this Rule is relevant, if any.
     */
    protected String namespaceURI = null;


    // ------------------------------------------------------------- Properties

    /**
     * Identify the Digester with which this Rule is associated.
     *
     * @return the Digester with which this Rule is associated.
     */
    public Digester getDigester() {  //获取Digester实例； 
        return digester;
    }


    /**
     * Set the <code>Digester</code> with which this <code>Rule</code> is
     * associated.
     *
     * @param digester The digester with which to associate this rule
     */
    public void setDigester(Digester digester) { // 设置Digester实例；
        this.digester = digester;
    }


    /**
     * Return the namespace URI for which this Rule is relevant, if any.
     *
     * @return The namespace URI for which this rule is relevant or
     *         <code>null</code> if none.
     */
    public String getNamespaceURI() { //获取Rule所在的相对命名空间URI；
        return namespaceURI;
    }


    /**
     * Set the namespace URI for which this Rule is relevant, if any.
     *
     * @param namespaceURI Namespace URI for which this Rule is relevant,
     *  or <code>null</code> to match independent of namespace.
     */
    public void setNamespaceURI(String namespaceURI) {  //设置Rule所在的相对命名空间URI；
        this.namespaceURI = namespaceURI;
    }


    // --------------------------------------------------------- Public Methods

    /**
     * This method is called when the beginning of a matching XML element
     * is encountered. The default implementation is a NO-OP.
     *
     * @param namespace the namespace URI of the matching element, or an
     *                  empty string if the parser is not namespace aware or the
     *                  element has no namespace
     * @param name the local name if the parser is namespace aware, or just
     *             the element name otherwise
     * @param attributes The attribute list of this element
     *
     * @throws Exception if an error occurs while processing the event
     */
    public void begin(String namespace, String name, Attributes attributes) throws Exception {  //此方法在遇到一个匹配的XML元素的开头时被调用，如<Server
        // NO-OP by default.
    }


    /**
     * This method is called when the body of a matching XML element is
     * encountered.  If the element has no body, this method is not called at
     * all. The default implementation is a NO-OP.
     *
     * @param namespace the namespace URI of the matching element, or an empty
     *                  string if the parser is not namespace aware or the
     *                  element has no namespace
     * @param name the local name if the parser is namespace aware, or just the
     *             element name otherwise
     * @param text The text of the body of this element
     *
     * @throws Exception if an error occurs while processing the event
     */
    public void body(String namespace, String name, String text) throws Exception { //在遇到匹配XML元素的body时，此方法被调用，如进入标签内部时；
        // NO-OP by default.
    }


    /**
     * This method is called when the end of a matching XML element
     * is encountered. The default implementation is a NO-OP.
     *
     * @param namespace the namespace URI of the matching element, or an empty
     *                  string if the parser is not namespace aware or the
     *                  element has no namespace
     * @param name the local name if the parser is namespace aware, or just the
     *             element name otherwise
     *
     * @throws Exception if an error occurs while processing the event
     */
    public void end(String namespace, String name) throws Exception { //此方法在遇到一个匹配的XML元素的末尾时被调用。如：</Server>
        // NO-OP by default.
    }


    /**
     * This method is called after all parsing methods have been
     * called, to allow Rules to remove temporary data.
     *
     * @throws Exception if an error occurs while processing the event
     */
    public void finish() throws Exception {
        // NO-OP by default.
    }
}
