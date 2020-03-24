/**
 * TransactionTransactionItems.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TransactionTransactionItems  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem[] transactionItem;

    public TransactionTransactionItems() {
    }

    public TransactionTransactionItems(
           org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem[] transactionItem) {
           this.transactionItem = transactionItem;
    }


    /**
     * Gets the transactionItem value for this TransactionTransactionItems.
     * 
     * @return transactionItem
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem[] getTransactionItem() {
        return transactionItem;
    }


    /**
     * Sets the transactionItem value for this TransactionTransactionItems.
     * 
     * @param transactionItem
     */
    public void setTransactionItem(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem[] transactionItem) {
        this.transactionItem = transactionItem;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem getTransactionItem(int i) {
        return this.transactionItem[i];
    }

    public void setTransactionItem(int i, org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem _value) {
        this.transactionItem[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionTransactionItems)) return false;
        TransactionTransactionItems other = (TransactionTransactionItems) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transactionItem==null && other.getTransactionItem()==null) || 
             (this.transactionItem!=null &&
              java.util.Arrays.equals(this.transactionItem, other.getTransactionItem())));
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
        if (getTransactionItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransactionItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransactionItem(), i);
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
        new org.apache.axis.description.TypeDesc(TransactionTransactionItems.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Transaction>TransactionItems"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionItem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionItem"));
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
