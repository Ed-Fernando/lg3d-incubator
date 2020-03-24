/**
 * PromotionalTag.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class PromotionalTag  implements java.io.Serializable {
    private java.lang.String promotionalTag;

    public PromotionalTag() {
    }

    public PromotionalTag(
           java.lang.String promotionalTag) {
           this.promotionalTag = promotionalTag;
    }


    /**
     * Gets the promotionalTag value for this PromotionalTag.
     * 
     * @return promotionalTag
     */
    public java.lang.String getPromotionalTag() {
        return promotionalTag;
    }


    /**
     * Sets the promotionalTag value for this PromotionalTag.
     * 
     * @param promotionalTag
     */
    public void setPromotionalTag(java.lang.String promotionalTag) {
        this.promotionalTag = promotionalTag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PromotionalTag)) return false;
        PromotionalTag other = (PromotionalTag) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.promotionalTag==null && other.getPromotionalTag()==null) || 
             (this.promotionalTag!=null &&
              this.promotionalTag.equals(other.getPromotionalTag())));
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
        if (getPromotionalTag() != null) {
            _hashCode += getPromotionalTag().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PromotionalTag.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">PromotionalTag"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("promotionalTag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PromotionalTag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
