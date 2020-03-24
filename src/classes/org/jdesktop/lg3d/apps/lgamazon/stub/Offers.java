/**
 * Offers.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Offers  implements java.io.Serializable {
    private org.apache.axis.types.NonNegativeInteger totalOffers;
    private org.apache.axis.types.NonNegativeInteger totalOfferPages;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Offer[] offer;

    public Offers() {
    }

    public Offers(
           org.apache.axis.types.NonNegativeInteger totalOffers,
           org.apache.axis.types.NonNegativeInteger totalOfferPages,
           org.jdesktop.lg3d.apps.lgamazon.stub.Offer[] offer) {
           this.totalOffers = totalOffers;
           this.totalOfferPages = totalOfferPages;
           this.offer = offer;
    }


    /**
     * Gets the totalOffers value for this Offers.
     * 
     * @return totalOffers
     */
    public org.apache.axis.types.NonNegativeInteger getTotalOffers() {
        return totalOffers;
    }


    /**
     * Sets the totalOffers value for this Offers.
     * 
     * @param totalOffers
     */
    public void setTotalOffers(org.apache.axis.types.NonNegativeInteger totalOffers) {
        this.totalOffers = totalOffers;
    }


    /**
     * Gets the totalOfferPages value for this Offers.
     * 
     * @return totalOfferPages
     */
    public org.apache.axis.types.NonNegativeInteger getTotalOfferPages() {
        return totalOfferPages;
    }


    /**
     * Sets the totalOfferPages value for this Offers.
     * 
     * @param totalOfferPages
     */
    public void setTotalOfferPages(org.apache.axis.types.NonNegativeInteger totalOfferPages) {
        this.totalOfferPages = totalOfferPages;
    }


    /**
     * Gets the offer value for this Offers.
     * 
     * @return offer
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Offer[] getOffer() {
        return offer;
    }


    /**
     * Sets the offer value for this Offers.
     * 
     * @param offer
     */
    public void setOffer(org.jdesktop.lg3d.apps.lgamazon.stub.Offer[] offer) {
        this.offer = offer;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.Offer getOffer(int i) {
        return this.offer[i];
    }

    public void setOffer(int i, org.jdesktop.lg3d.apps.lgamazon.stub.Offer _value) {
        this.offer[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Offers)) return false;
        Offers other = (Offers) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.totalOffers==null && other.getTotalOffers()==null) || 
             (this.totalOffers!=null &&
              this.totalOffers.equals(other.getTotalOffers()))) &&
            ((this.totalOfferPages==null && other.getTotalOfferPages()==null) || 
             (this.totalOfferPages!=null &&
              this.totalOfferPages.equals(other.getTotalOfferPages()))) &&
            ((this.offer==null && other.getOffer()==null) || 
             (this.offer!=null &&
              java.util.Arrays.equals(this.offer, other.getOffer())));
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
        if (getTotalOffers() != null) {
            _hashCode += getTotalOffers().hashCode();
        }
        if (getTotalOfferPages() != null) {
            _hashCode += getTotalOfferPages().hashCode();
        }
        if (getOffer() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOffer());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOffer(), i);
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
        new org.apache.axis.description.TypeDesc(Offers.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Offers"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalOffers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalOffers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalOfferPages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalOfferPages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Offer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Offer"));
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
