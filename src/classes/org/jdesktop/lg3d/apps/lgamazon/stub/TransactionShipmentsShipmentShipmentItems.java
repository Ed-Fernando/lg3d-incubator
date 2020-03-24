/**
 * TransactionShipmentsShipmentShipmentItems.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TransactionShipmentsShipmentShipmentItems  implements java.io.Serializable {
    private java.lang.String[] transactionItemId;

    public TransactionShipmentsShipmentShipmentItems() {
    }

    public TransactionShipmentsShipmentShipmentItems(
           java.lang.String[] transactionItemId) {
           this.transactionItemId = transactionItemId;
    }


    /**
     * Gets the transactionItemId value for this TransactionShipmentsShipmentShipmentItems.
     * 
     * @return transactionItemId
     */
    public java.lang.String[] getTransactionItemId() {
        return transactionItemId;
    }


    /**
     * Sets the transactionItemId value for this TransactionShipmentsShipmentShipmentItems.
     * 
     * @param transactionItemId
     */
    public void setTransactionItemId(java.lang.String[] transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    public java.lang.String getTransactionItemId(int i) {
        return this.transactionItemId[i];
    }

    public void setTransactionItemId(int i, java.lang.String _value) {
        this.transactionItemId[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionShipmentsShipmentShipmentItems)) return false;
        TransactionShipmentsShipmentShipmentItems other = (TransactionShipmentsShipmentShipmentItems) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transactionItemId==null && other.getTransactionItemId()==null) || 
             (this.transactionItemId!=null &&
              java.util.Arrays.equals(this.transactionItemId, other.getTransactionItemId())));
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
        if (getTransactionItemId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransactionItemId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransactionItemId(), i);
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
        new org.apache.axis.description.TypeDesc(TransactionShipmentsShipmentShipmentItems.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Transaction>Shipments>Shipment>ShipmentItems"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionItemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionItemId"));
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
