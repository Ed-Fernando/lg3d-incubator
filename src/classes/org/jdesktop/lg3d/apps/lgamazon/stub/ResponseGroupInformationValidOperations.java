/**
 * ResponseGroupInformationValidOperations.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ResponseGroupInformationValidOperations  implements java.io.Serializable {
    private java.lang.String[] operation;

    public ResponseGroupInformationValidOperations() {
    }

    public ResponseGroupInformationValidOperations(
           java.lang.String[] operation) {
           this.operation = operation;
    }


    /**
     * Gets the operation value for this ResponseGroupInformationValidOperations.
     * 
     * @return operation
     */
    public java.lang.String[] getOperation() {
        return operation;
    }


    /**
     * Sets the operation value for this ResponseGroupInformationValidOperations.
     * 
     * @param operation
     */
    public void setOperation(java.lang.String[] operation) {
        this.operation = operation;
    }

    public java.lang.String getOperation(int i) {
        return this.operation[i];
    }

    public void setOperation(int i, java.lang.String _value) {
        this.operation[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseGroupInformationValidOperations)) return false;
        ResponseGroupInformationValidOperations other = (ResponseGroupInformationValidOperations) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operation==null && other.getOperation()==null) || 
             (this.operation!=null &&
              java.util.Arrays.equals(this.operation, other.getOperation())));
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
        if (getOperation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperation(), i);
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
        new org.apache.axis.description.TypeDesc(ResponseGroupInformationValidOperations.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ResponseGroupInformation>ValidOperations"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
