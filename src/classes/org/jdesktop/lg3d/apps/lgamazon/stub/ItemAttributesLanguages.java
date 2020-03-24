/**
 * ItemAttributesLanguages.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ItemAttributesLanguages  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage[] language;

    public ItemAttributesLanguages() {
    }

    public ItemAttributesLanguages(
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage[] language) {
           this.language = language;
    }


    /**
     * Gets the language value for this ItemAttributesLanguages.
     * 
     * @return language
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage[] getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this ItemAttributesLanguages.
     * 
     * @param language
     */
    public void setLanguage(org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage[] language) {
        this.language = language;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage getLanguage(int i) {
        return this.language[i];
    }

    public void setLanguage(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage _value) {
        this.language[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemAttributesLanguages)) return false;
        ItemAttributesLanguages other = (ItemAttributesLanguages) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              java.util.Arrays.equals(this.language, other.getLanguage())));
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
        if (getLanguage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLanguage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLanguage(), i);
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
        new org.apache.axis.description.TypeDesc(ItemAttributesLanguages.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ItemAttributes>Languages"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>ItemAttributes>Languages>Language"));
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
