/**
 * Customer.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Customer  implements java.io.Serializable {
    private java.lang.String customerId;
    private java.lang.String nickname;
    private java.lang.String birthday;
    private java.lang.String wishListId;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerLocation location;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews[] customerReviews;

    public Customer() {
    }

    public Customer(
           java.lang.String customerId,
           java.lang.String nickname,
           java.lang.String birthday,
           java.lang.String wishListId,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerLocation location,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews[] customerReviews) {
           this.customerId = customerId;
           this.nickname = nickname;
           this.birthday = birthday;
           this.wishListId = wishListId;
           this.location = location;
           this.customerReviews = customerReviews;
    }


    /**
     * Gets the customerId value for this Customer.
     * 
     * @return customerId
     */
    public java.lang.String getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this Customer.
     * 
     * @param customerId
     */
    public void setCustomerId(java.lang.String customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the nickname value for this Customer.
     * 
     * @return nickname
     */
    public java.lang.String getNickname() {
        return nickname;
    }


    /**
     * Sets the nickname value for this Customer.
     * 
     * @param nickname
     */
    public void setNickname(java.lang.String nickname) {
        this.nickname = nickname;
    }


    /**
     * Gets the birthday value for this Customer.
     * 
     * @return birthday
     */
    public java.lang.String getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this Customer.
     * 
     * @param birthday
     */
    public void setBirthday(java.lang.String birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the wishListId value for this Customer.
     * 
     * @return wishListId
     */
    public java.lang.String getWishListId() {
        return wishListId;
    }


    /**
     * Sets the wishListId value for this Customer.
     * 
     * @param wishListId
     */
    public void setWishListId(java.lang.String wishListId) {
        this.wishListId = wishListId;
    }


    /**
     * Gets the location value for this Customer.
     * 
     * @return location
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerLocation getLocation() {
        return location;
    }


    /**
     * Sets the location value for this Customer.
     * 
     * @param location
     */
    public void setLocation(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerLocation location) {
        this.location = location;
    }


    /**
     * Gets the customerReviews value for this Customer.
     * 
     * @return customerReviews
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews[] getCustomerReviews() {
        return customerReviews;
    }


    /**
     * Sets the customerReviews value for this Customer.
     * 
     * @param customerReviews
     */
    public void setCustomerReviews(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews[] customerReviews) {
        this.customerReviews = customerReviews;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews getCustomerReviews(int i) {
        return this.customerReviews[i];
    }

    public void setCustomerReviews(int i, org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews _value) {
        this.customerReviews[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customerId==null && other.getCustomerId()==null) || 
             (this.customerId!=null &&
              this.customerId.equals(other.getCustomerId()))) &&
            ((this.nickname==null && other.getNickname()==null) || 
             (this.nickname!=null &&
              this.nickname.equals(other.getNickname()))) &&
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.wishListId==null && other.getWishListId()==null) || 
             (this.wishListId!=null &&
              this.wishListId.equals(other.getWishListId()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.customerReviews==null && other.getCustomerReviews()==null) || 
             (this.customerReviews!=null &&
              java.util.Arrays.equals(this.customerReviews, other.getCustomerReviews())));
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
        if (getCustomerId() != null) {
            _hashCode += getCustomerId().hashCode();
        }
        if (getNickname() != null) {
            _hashCode += getNickname().hashCode();
        }
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getWishListId() != null) {
            _hashCode += getWishListId().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getCustomerReviews() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerReviews());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerReviews(), i);
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
        new org.apache.axis.description.TypeDesc(Customer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Customer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nickname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Nickname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wishListId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "WishListId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Customer>Location"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReviews");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerReviews"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerReviews"));
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
