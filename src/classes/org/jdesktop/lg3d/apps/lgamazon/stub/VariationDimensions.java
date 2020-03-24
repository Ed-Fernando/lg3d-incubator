/**
 * VariationDimensions.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class VariationDimensions  implements java.io.Serializable {
    private java.lang.String[] variationDimension;

    public VariationDimensions() {
    }

    public VariationDimensions(
           java.lang.String[] variationDimension) {
           this.variationDimension = variationDimension;
    }


    /**
     * Gets the variationDimension value for this VariationDimensions.
     * 
     * @return variationDimension
     */
    public java.lang.String[] getVariationDimension() {
        return variationDimension;
    }


    /**
     * Sets the variationDimension value for this VariationDimensions.
     * 
     * @param variationDimension
     */
    public void setVariationDimension(java.lang.String[] variationDimension) {
        this.variationDimension = variationDimension;
    }

    public java.lang.String getVariationDimension(int i) {
        return this.variationDimension[i];
    }

    public void setVariationDimension(int i, java.lang.String _value) {
        this.variationDimension[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariationDimensions)) return false;
        VariationDimensions other = (VariationDimensions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.variationDimension==null && other.getVariationDimension()==null) || 
             (this.variationDimension!=null &&
              java.util.Arrays.equals(this.variationDimension, other.getVariationDimension())));
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
        if (getVariationDimension() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVariationDimension());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVariationDimension(), i);
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
        new org.apache.axis.description.TypeDesc(VariationDimensions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">VariationDimensions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variationDimension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "VariationDimension"));
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
