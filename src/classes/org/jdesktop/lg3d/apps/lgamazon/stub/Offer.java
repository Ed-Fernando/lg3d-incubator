/**
 * Offer.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Offer  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Merchant merchant;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller;
    private org.jdesktop.lg3d.apps.lgamazon.stub.OfferAttributes offerAttributes;
    private org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing[] offerListing;

    public Offer() {
    }

    public Offer(
           org.jdesktop.lg3d.apps.lgamazon.stub.Merchant merchant,
           org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller,
           org.jdesktop.lg3d.apps.lgamazon.stub.OfferAttributes offerAttributes,
           org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing[] offerListing) {
           this.merchant = merchant;
           this.seller = seller;
           this.offerAttributes = offerAttributes;
           this.offerListing = offerListing;
    }


    /**
     * Gets the merchant value for this Offer.
     * 
     * @return merchant
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Merchant getMerchant() {
        return merchant;
    }


    /**
     * Sets the merchant value for this Offer.
     * 
     * @param merchant
     */
    public void setMerchant(org.jdesktop.lg3d.apps.lgamazon.stub.Merchant merchant) {
        this.merchant = merchant;
    }


    /**
     * Gets the seller value for this Offer.
     * 
     * @return seller
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Seller getSeller() {
        return seller;
    }


    /**
     * Sets the seller value for this Offer.
     * 
     * @param seller
     */
    public void setSeller(org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller) {
        this.seller = seller;
    }


    /**
     * Gets the offerAttributes value for this Offer.
     * 
     * @return offerAttributes
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OfferAttributes getOfferAttributes() {
        return offerAttributes;
    }


    /**
     * Sets the offerAttributes value for this Offer.
     * 
     * @param offerAttributes
     */
    public void setOfferAttributes(org.jdesktop.lg3d.apps.lgamazon.stub.OfferAttributes offerAttributes) {
        this.offerAttributes = offerAttributes;
    }


    /**
     * Gets the offerListing value for this Offer.
     * 
     * @return offerListing
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing[] getOfferListing() {
        return offerListing;
    }


    /**
     * Sets the offerListing value for this Offer.
     * 
     * @param offerListing
     */
    public void setOfferListing(org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing[] offerListing) {
        this.offerListing = offerListing;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing getOfferListing(int i) {
        return this.offerListing[i];
    }

    public void setOfferListing(int i, org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing _value) {
        this.offerListing[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Offer)) return false;
        Offer other = (Offer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.merchant==null && other.getMerchant()==null) || 
             (this.merchant!=null &&
              this.merchant.equals(other.getMerchant()))) &&
            ((this.seller==null && other.getSeller()==null) || 
             (this.seller!=null &&
              this.seller.equals(other.getSeller()))) &&
            ((this.offerAttributes==null && other.getOfferAttributes()==null) || 
             (this.offerAttributes!=null &&
              this.offerAttributes.equals(other.getOfferAttributes()))) &&
            ((this.offerListing==null && other.getOfferListing()==null) || 
             (this.offerListing!=null &&
              java.util.Arrays.equals(this.offerListing, other.getOfferListing())));
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
        if (getMerchant() != null) {
            _hashCode += getMerchant().hashCode();
        }
        if (getSeller() != null) {
            _hashCode += getSeller().hashCode();
        }
        if (getOfferAttributes() != null) {
            _hashCode += getOfferAttributes().hashCode();
        }
        if (getOfferListing() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOfferListing());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOfferListing(), i);
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
        new org.apache.axis.description.TypeDesc(Offer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Offer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("merchant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Merchant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Merchant"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seller");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Seller"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Seller"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerAttributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferAttributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferAttributes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerListing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferListing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferListing"));
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
