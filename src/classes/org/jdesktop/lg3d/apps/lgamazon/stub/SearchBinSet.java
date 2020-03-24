/**
 * SearchBinSet.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SearchBinSet  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Bin[] bin;
    private java.lang.String narrowBy;  // attribute

    public SearchBinSet() {
    }

    public SearchBinSet(
           org.jdesktop.lg3d.apps.lgamazon.stub.Bin[] bin,
           java.lang.String narrowBy) {
           this.bin = bin;
           this.narrowBy = narrowBy;
    }


    /**
     * Gets the bin value for this SearchBinSet.
     * 
     * @return bin
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Bin[] getBin() {
        return bin;
    }


    /**
     * Sets the bin value for this SearchBinSet.
     * 
     * @param bin
     */
    public void setBin(org.jdesktop.lg3d.apps.lgamazon.stub.Bin[] bin) {
        this.bin = bin;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.Bin getBin(int i) {
        return this.bin[i];
    }

    public void setBin(int i, org.jdesktop.lg3d.apps.lgamazon.stub.Bin _value) {
        this.bin[i] = _value;
    }


    /**
     * Gets the narrowBy value for this SearchBinSet.
     * 
     * @return narrowBy
     */
    public java.lang.String getNarrowBy() {
        return narrowBy;
    }


    /**
     * Sets the narrowBy value for this SearchBinSet.
     * 
     * @param narrowBy
     */
    public void setNarrowBy(java.lang.String narrowBy) {
        this.narrowBy = narrowBy;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchBinSet)) return false;
        SearchBinSet other = (SearchBinSet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bin==null && other.getBin()==null) || 
             (this.bin!=null &&
              java.util.Arrays.equals(this.bin, other.getBin()))) &&
            ((this.narrowBy==null && other.getNarrowBy()==null) || 
             (this.narrowBy!=null &&
              this.narrowBy.equals(other.getNarrowBy())));
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
        if (getBin() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBin());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBin(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNarrowBy() != null) {
            _hashCode += getNarrowBy().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchBinSet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchBinSet"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("narrowBy");
        attrField.setXmlName(new javax.xml.namespace.QName("", "NarrowBy"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Bin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Bin"));
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
