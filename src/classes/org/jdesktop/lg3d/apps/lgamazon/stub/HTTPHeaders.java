/**
 * HTTPHeaders.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class HTTPHeaders  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader[] header;

    public HTTPHeaders() {
    }

    public HTTPHeaders(
           org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader[] header) {
           this.header = header;
    }


    /**
     * Gets the header value for this HTTPHeaders.
     * 
     * @return header
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader[] getHeader() {
        return header;
    }


    /**
     * Sets the header value for this HTTPHeaders.
     * 
     * @param header
     */
    public void setHeader(org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader[] header) {
        this.header = header;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader getHeader(int i) {
        return this.header[i];
    }

    public void setHeader(int i, org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader _value) {
        this.header[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HTTPHeaders)) return false;
        HTTPHeaders other = (HTTPHeaders) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.header==null && other.getHeader()==null) || 
             (this.header!=null &&
              java.util.Arrays.equals(this.header, other.getHeader())));
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
        if (getHeader() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHeader());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHeader(), i);
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
        new org.apache.axis.description.TypeDesc(HTTPHeaders.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">HTTPHeaders"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>HTTPHeaders>Header"));
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
