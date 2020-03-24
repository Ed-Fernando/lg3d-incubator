/**
 * CartAddRequestItems.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class CartAddRequestItems  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem[] item;

    public CartAddRequestItems() {
    }

    public CartAddRequestItems(
           org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem[] item) {
           this.item = item;
    }


    /**
     * Gets the item value for this CartAddRequestItems.
     * 
     * @return item
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this CartAddRequestItems.
     * 
     * @param item
     */
    public void setItem(org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem[] item) {
        this.item = item;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem _value) {
        this.item[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CartAddRequestItems)) return false;
        CartAddRequestItems other = (CartAddRequestItems) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.item==null && other.getItem()==null) || 
             (this.item!=null &&
              java.util.Arrays.equals(this.item, other.getItem())));
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
        if (getItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItem(), i);
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
        new org.apache.axis.description.TypeDesc(CartAddRequestItems.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAddRequest>Items"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("item");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Item"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>CartAddRequest>Items>Item"));
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
