/**
 * CustomerContentSearchResponse.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class CustomerContentSearchResponse  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Customers[] customers;

    public CustomerContentSearchResponse() {
    }

    public CustomerContentSearchResponse(
           org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.Customers[] customers) {
           this.operationRequest = operationRequest;
           this.customers = customers;
    }


    /**
     * Gets the operationRequest value for this CustomerContentSearchResponse.
     * 
     * @return operationRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest getOperationRequest() {
        return operationRequest;
    }


    /**
     * Sets the operationRequest value for this CustomerContentSearchResponse.
     * 
     * @param operationRequest
     */
    public void setOperationRequest(org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest) {
        this.operationRequest = operationRequest;
    }


    /**
     * Gets the customers value for this CustomerContentSearchResponse.
     * 
     * @return customers
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Customers[] getCustomers() {
        return customers;
    }


    /**
     * Sets the customers value for this CustomerContentSearchResponse.
     * 
     * @param customers
     */
    public void setCustomers(org.jdesktop.lg3d.apps.lgamazon.stub.Customers[] customers) {
        this.customers = customers;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.Customers getCustomers(int i) {
        return this.customers[i];
    }

    public void setCustomers(int i, org.jdesktop.lg3d.apps.lgamazon.stub.Customers _value) {
        this.customers[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerContentSearchResponse)) return false;
        CustomerContentSearchResponse other = (CustomerContentSearchResponse) obj;
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
            ((this.customers==null && other.getCustomers()==null) || 
             (this.customers!=null &&
              java.util.Arrays.equals(this.customers, other.getCustomers())));
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
        if (getCustomers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomers(), i);
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
        new org.apache.axis.description.TypeDesc(CustomerContentSearchResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentSearchResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Customers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Customers"));
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
