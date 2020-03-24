/**
 * TransactionItem.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TransactionItem  implements java.io.Serializable {
    private java.lang.String transactionItemId;
    private java.lang.String quantity;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price unitPrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price totalPrice;
    private java.lang.String ASIN;
    private org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItemChildTransactionItems childTransactionItems;

    public TransactionItem() {
    }

    public TransactionItem(
           java.lang.String transactionItemId,
           java.lang.String quantity,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price unitPrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price totalPrice,
           java.lang.String ASIN,
           org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItemChildTransactionItems childTransactionItems) {
           this.transactionItemId = transactionItemId;
           this.quantity = quantity;
           this.unitPrice = unitPrice;
           this.totalPrice = totalPrice;
           this.ASIN = ASIN;
           this.childTransactionItems = childTransactionItems;
    }


    /**
     * Gets the transactionItemId value for this TransactionItem.
     * 
     * @return transactionItemId
     */
    public java.lang.String getTransactionItemId() {
        return transactionItemId;
    }


    /**
     * Sets the transactionItemId value for this TransactionItem.
     * 
     * @param transactionItemId
     */
    public void setTransactionItemId(java.lang.String transactionItemId) {
        this.transactionItemId = transactionItemId;
    }


    /**
     * Gets the quantity value for this TransactionItem.
     * 
     * @return quantity
     */
    public java.lang.String getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this TransactionItem.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.String quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the unitPrice value for this TransactionItem.
     * 
     * @return unitPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getUnitPrice() {
        return unitPrice;
    }


    /**
     * Sets the unitPrice value for this TransactionItem.
     * 
     * @param unitPrice
     */
    public void setUnitPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price unitPrice) {
        this.unitPrice = unitPrice;
    }


    /**
     * Gets the totalPrice value for this TransactionItem.
     * 
     * @return totalPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getTotalPrice() {
        return totalPrice;
    }


    /**
     * Sets the totalPrice value for this TransactionItem.
     * 
     * @param totalPrice
     */
    public void setTotalPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price totalPrice) {
        this.totalPrice = totalPrice;
    }


    /**
     * Gets the ASIN value for this TransactionItem.
     * 
     * @return ASIN
     */
    public java.lang.String getASIN() {
        return ASIN;
    }


    /**
     * Sets the ASIN value for this TransactionItem.
     * 
     * @param ASIN
     */
    public void setASIN(java.lang.String ASIN) {
        this.ASIN = ASIN;
    }


    /**
     * Gets the childTransactionItems value for this TransactionItem.
     * 
     * @return childTransactionItems
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItemChildTransactionItems getChildTransactionItems() {
        return childTransactionItems;
    }


    /**
     * Sets the childTransactionItems value for this TransactionItem.
     * 
     * @param childTransactionItems
     */
    public void setChildTransactionItems(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItemChildTransactionItems childTransactionItems) {
        this.childTransactionItems = childTransactionItems;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionItem)) return false;
        TransactionItem other = (TransactionItem) obj;
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
              this.transactionItemId.equals(other.getTransactionItemId()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.unitPrice==null && other.getUnitPrice()==null) || 
             (this.unitPrice!=null &&
              this.unitPrice.equals(other.getUnitPrice()))) &&
            ((this.totalPrice==null && other.getTotalPrice()==null) || 
             (this.totalPrice!=null &&
              this.totalPrice.equals(other.getTotalPrice()))) &&
            ((this.ASIN==null && other.getASIN()==null) || 
             (this.ASIN!=null &&
              this.ASIN.equals(other.getASIN()))) &&
            ((this.childTransactionItems==null && other.getChildTransactionItems()==null) || 
             (this.childTransactionItems!=null &&
              this.childTransactionItems.equals(other.getChildTransactionItems())));
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
            _hashCode += getTransactionItemId().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getUnitPrice() != null) {
            _hashCode += getUnitPrice().hashCode();
        }
        if (getTotalPrice() != null) {
            _hashCode += getTotalPrice().hashCode();
        }
        if (getASIN() != null) {
            _hashCode += getASIN().hashCode();
        }
        if (getChildTransactionItems() != null) {
            _hashCode += getChildTransactionItems().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransactionItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionItemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionItemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "UnitPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ASIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ASIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childTransactionItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ChildTransactionItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>TransactionItem>ChildTransactionItems"));
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
