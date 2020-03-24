/**
 * CartModifyRequestItemsItem.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class CartModifyRequestItemsItem  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItemAction action;
    private java.lang.String cartItemId;
    private org.apache.axis.types.NonNegativeInteger quantity;

    public CartModifyRequestItemsItem() {
    }

    public CartModifyRequestItemsItem(
           org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItemAction action,
           java.lang.String cartItemId,
           org.apache.axis.types.NonNegativeInteger quantity) {
           this.action = action;
           this.cartItemId = cartItemId;
           this.quantity = quantity;
    }


    /**
     * Gets the action value for this CartModifyRequestItemsItem.
     * 
     * @return action
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItemAction getAction() {
        return action;
    }


    /**
     * Sets the action value for this CartModifyRequestItemsItem.
     * 
     * @param action
     */
    public void setAction(org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItemAction action) {
        this.action = action;
    }


    /**
     * Gets the cartItemId value for this CartModifyRequestItemsItem.
     * 
     * @return cartItemId
     */
    public java.lang.String getCartItemId() {
        return cartItemId;
    }


    /**
     * Sets the cartItemId value for this CartModifyRequestItemsItem.
     * 
     * @param cartItemId
     */
    public void setCartItemId(java.lang.String cartItemId) {
        this.cartItemId = cartItemId;
    }


    /**
     * Gets the quantity value for this CartModifyRequestItemsItem.
     * 
     * @return quantity
     */
    public org.apache.axis.types.NonNegativeInteger getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this CartModifyRequestItemsItem.
     * 
     * @param quantity
     */
    public void setQuantity(org.apache.axis.types.NonNegativeInteger quantity) {
        this.quantity = quantity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CartModifyRequestItemsItem)) return false;
        CartModifyRequestItemsItem other = (CartModifyRequestItemsItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.cartItemId==null && other.getCartItemId()==null) || 
             (this.cartItemId!=null &&
              this.cartItemId.equals(other.getCartItemId()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity())));
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
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getCartItemId() != null) {
            _hashCode += getCartItemId().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CartModifyRequestItemsItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>CartModifyRequest>Items>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>CartModifyRequest>Items>Item>Action"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartItemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartItemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
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
