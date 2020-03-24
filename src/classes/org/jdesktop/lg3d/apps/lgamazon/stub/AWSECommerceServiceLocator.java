/**
 * AWSECommerceServiceLocator.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class AWSECommerceServiceLocator extends org.apache.axis.client.Service implements org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceService {

    public AWSECommerceServiceLocator() {
    }


    public AWSECommerceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AWSECommerceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    private java.lang.String AWSECommerceServicePort_address = "http://soap.amazon.com/onca/soap?Service=AWSECommerceService";

    public java.lang.String getAWSECommerceServicePortAddress() {
        return AWSECommerceServicePort_address;
    }

    private java.lang.String AWSECommerceServicePortWSDDServiceName = "AWSECommerceServicePort";

    public java.lang.String getAWSECommerceServicePortWSDDServiceName() {
        return AWSECommerceServicePortWSDDServiceName;
    }

    public void setAWSECommerceServicePortWSDDServiceName(java.lang.String name) {
        AWSECommerceServicePortWSDDServiceName = name;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServicePortType getAWSECommerceServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AWSECommerceServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAWSECommerceServicePort(endpoint);
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServicePortType getAWSECommerceServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServiceBindingStub _stub = new org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServiceBindingStub(portAddress, this);
            _stub.setPortName(getAWSECommerceServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAWSECommerceServicePortEndpointAddress(java.lang.String address) {
        AWSECommerceServicePort_address = address;
    }

    /**
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServiceBindingStub _stub = new org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServiceBindingStub(new java.net.URL(AWSECommerceServicePort_address), this);
                _stub.setPortName(getAWSECommerceServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("[en]-(There is no stub implementation for the interface:)  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AWSECommerceServicePort".equals(inputPortName)) {
            return getAWSECommerceServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AWSECommerceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AWSECommerceServicePort"));
        }
        return ports.iterator();
    }

    /**
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AWSECommerceServicePort".equals(portName)) {
            setAWSECommerceServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException("[en]-(Cannot set Endpoint Address for Unknown Port)" + portName);
        }
    }

    /**
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
