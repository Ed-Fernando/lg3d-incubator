/**
 * Errors.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Errors  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError[] error;

    public Errors() {
    }

    public Errors(
           org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError[] error) {
           this.error = error;
    }


    /**
     * Gets the error value for this Errors.
     * 
     * @return error
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError[] getError() {
        return error;
    }


    /**
     * Sets the error value for this Errors.
     * 
     * @param error
     */
    public void setError(org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError[] error) {
        this.error = error;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError getError(int i) {
        return this.error[i];
    }

    public void setError(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError _value) {
        this.error[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Errors)) return false;
        Errors other = (Errors) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              java.util.Arrays.equals(this.error, other.getError())));
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
        if (getError() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getError());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getError(), i);
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
        new org.apache.axis.description.TypeDesc(Errors.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Errors"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Errors>Error"));
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
