/**
 * Accessories.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Accessories  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory[] accessory;

    public Accessories() {
    }

    public Accessories(
           org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory[] accessory) {
           this.accessory = accessory;
    }


    /**
     * Gets the accessory value for this Accessories.
     * 
     * @return accessory
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory[] getAccessory() {
        return accessory;
    }


    /**
     * Sets the accessory value for this Accessories.
     * 
     * @param accessory
     */
    public void setAccessory(org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory[] accessory) {
        this.accessory = accessory;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory getAccessory(int i) {
        return this.accessory[i];
    }

    public void setAccessory(int i, org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory _value) {
        this.accessory[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Accessories)) return false;
        Accessories other = (Accessories) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessory==null && other.getAccessory()==null) || 
             (this.accessory!=null &&
              java.util.Arrays.equals(this.accessory, other.getAccessory())));
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
        if (getAccessory() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccessory());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccessory(), i);
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
        new org.apache.axis.description.TypeDesc(Accessories.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Accessories"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Accessory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Accessories>Accessory"));
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
