/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************
 *
 * This file was originally auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 29, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package org.jdesktop.lg3d.apps.googler.engine.internal;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;
import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;

import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
import org.jdesktop.lg3d.apps.googler.engine.*;

public class GoogleSearchBindingStub extends Stub implements GoogleSearchPort {
    private Vector cachedSerClasses = new Vector();
    private Vector cachedSerQNames = new Vector();
    private Vector cachedSerFactories = new Vector();
    private Vector cachedDeserFactories = new Vector();
    
    static OperationDesc [] _operations;
    
    static {
        _operations = new OperationDesc[3];
        _initOperationDesc1();
    }
    
    private static void _initOperationDesc1(){
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("doGetCachedPage");
        param = new ParameterDesc(new QName("", "key"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "url"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        oper.setReturnClass(byte[].class);
        oper.setReturnQName(new QName("", "return"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[0] = oper;
        
        oper = new OperationDesc();
        oper.setName("doSpellingSuggestion");
        param = new ParameterDesc(new QName("", "key"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "phrase"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName("", "return"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[1] = oper;
        
        oper = new OperationDesc();
        oper.setName("doGoogleSearch");
        param = new ParameterDesc(new QName("", "key"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "q"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "start"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "maxResults"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "filter"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "restrict"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "safeSearch"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "lr"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "ie"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "oe"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new QName("urn:GoogleSearch", "GoogleSearchResult"));
        oper.setReturnClass(GoogleSearchResult.class);
        oper.setReturnQName(new QName("", "return"));
        oper.setStyle(Style.RPC);
        oper.setUse(Use.ENCODED);
        _operations[2] = oper;
        
    }
    
    public GoogleSearchBindingStub() throws AxisFault {
        this(null);
    }
    
    public GoogleSearchBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }
    
    public GoogleSearchBindingStub(javax.xml.rpc.Service service) throws AxisFault {
        if (service == null) {
            super.service = new Service();
        } else {
            super.service = service;
        }
        ((Service)super.service).setTypeMappingVersion("1.2");
        Class cls;
        QName qName;
        QName qName2;
        Class beansf = BeanSerializerFactory.class;
        Class beandf = BeanDeserializerFactory.class;
        Class enumsf = EnumSerializerFactory.class;
        Class enumdf = EnumDeserializerFactory.class;
        Class arraysf = ArraySerializerFactory.class;
        Class arraydf = ArrayDeserializerFactory.class;
        Class simplesf = SimpleSerializerFactory.class;
        Class simpledf = SimpleDeserializerFactory.class;
        Class simplelistsf = SimpleListSerializerFactory.class;
        Class simplelistdf = SimpleListDeserializerFactory.class;
        qName = new QName("urn:GoogleSearch", "DirectoryCategory");
        cachedSerQNames.add(qName);
        cls = DirectoryCategory.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);
        
        qName = new QName("urn:GoogleSearch", "DirectoryCategoryArray");
        cachedSerQNames.add(qName);
        cls = DirectoryCategory[].class;
        cachedSerClasses.add(cls);
        qName = new QName("urn:GoogleSearch", "DirectoryCategory");
        qName2 = null;
        cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new ArrayDeserializerFactory());
        
        qName = new QName("urn:GoogleSearch", "GoogleSearchResult");
        cachedSerQNames.add(qName);
        cls = GoogleSearchResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);
        
        qName = new QName("urn:GoogleSearch", "ResultElement");
        cachedSerQNames.add(qName);
        cls = ResultElement.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);
        
        qName = new QName("urn:GoogleSearch", "ResultElementArray");
        cachedSerQNames.add(qName);
        cls = ResultElement[].class;
        cachedSerClasses.add(cls);
        qName = new QName("urn:GoogleSearch", "ResultElement");
        qName2 = null;
        cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new ArrayDeserializerFactory());
        
    }
    
    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        QName qName =
                                (QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class)
                            cachedSerFactories.get(i);
                            Class df = (Class)
                            cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof SerializerFactory) {
                            SerializerFactory sf = (SerializerFactory)
                            cachedSerFactories.get(i);
                            DeserializerFactory df = (DeserializerFactory)
                            cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        } catch (Throwable _t) {
            throw new AxisFault("Failure trying to get the Call object", _t);
        }
    }
    
    public byte[] doGetCachedPage(String key, String url) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:GoogleSearchAction");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("urn:GoogleSearch", "doGetCachedPage"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {key, url});
        
        if (_resp instanceof RemoteException) {
            throw (RemoteException)_resp;
        } else {
            extractAttachments(_call);
            try {
                return (byte[]) _resp;
            } catch (Exception _exception) {
                return (byte[]) JavaUtils.convert(_resp, byte[].class);
            }
        }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }
    
    public String doSpellingSuggestion(String key, String phrase) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:GoogleSearchAction");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("urn:GoogleSearch", "doSpellingSuggestion"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {key, phrase});
        
        if (_resp instanceof RemoteException) {
            throw (RemoteException)_resp;
        } else {
            extractAttachments(_call);
            try {
                return (String) _resp;
            } catch (Exception _exception) {
                return (String) JavaUtils.convert(_resp, String.class);
            }
        }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }
    
    public GoogleSearchResult doGoogleSearch(String key, String q, int start, int maxResults, boolean filter, String restrict, boolean safeSearch, String lr, String ie, String oe) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:GoogleSearchAction");
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("urn:GoogleSearch", "doGoogleSearch"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {key, q, new Integer(start), new Integer(maxResults), new Boolean(filter), restrict, new Boolean(safeSearch), lr, ie, oe});
        
        if (_resp instanceof RemoteException) {
            throw (RemoteException)_resp;
        } else {
            extractAttachments(_call);
            try {
                return (GoogleSearchResult) _resp;
            } catch (Exception _exception) {
                return (GoogleSearchResult) JavaUtils.convert(_resp, GoogleSearchResult.class);
            }
        }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }
    
}
