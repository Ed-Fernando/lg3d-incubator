/**
 * BrowseNodeLookupResponse.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class BrowseNodeLookupResponse  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes[] browseNodes;

    public BrowseNodeLookupResponse() {
    }

    public BrowseNodeLookupResponse(
           org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes[] browseNodes) {
           this.operationRequest = operationRequest;
           this.browseNodes = browseNodes;
    }


    /**
     * Gets the operationRequest value for this BrowseNodeLookupResponse.
     * 
     * @return operationRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest getOperationRequest() {
        return operationRequest;
    }


    /**
     * Sets the operationRequest value for this BrowseNodeLookupResponse.
     * 
     * @param operationRequest
     */
    public void setOperationRequest(org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest) {
        this.operationRequest = operationRequest;
    }


    /**
     * Gets the browseNodes value for this BrowseNodeLookupResponse.
     * 
     * @return browseNodes
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes[] getBrowseNodes() {
        return browseNodes;
    }


    /**
     * Sets the browseNodes value for this BrowseNodeLookupResponse.
     * 
     * @param browseNodes
     */
    public void setBrowseNodes(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes[] browseNodes) {
        this.browseNodes = browseNodes;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes getBrowseNodes(int i) {
        return this.browseNodes[i];
    }

    public void setBrowseNodes(int i, org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes _value) {
        this.browseNodes[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BrowseNodeLookupResponse)) return false;
        BrowseNodeLookupResponse other = (BrowseNodeLookupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operationRequest==null && other.getOperationRequest()==null) || 
             (this.operationRequest!=null &&
              this.operationRequest.equals(other.getOperationRequest()))) &&
            ((this.browseNodes==null && other.getBrowseNodes()==null) || 
             (this.browseNodes!=null &&
              java.util.Arrays.equals(this.browseNodes, other.getBrowseNodes())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOperationRequest() != null) {
            _hashCode += getOperationRequest().hashCode();
        }
        if (getBrowseNodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrowseNodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrowseNodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BrowseNodeLookupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodeLookupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browseNodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
