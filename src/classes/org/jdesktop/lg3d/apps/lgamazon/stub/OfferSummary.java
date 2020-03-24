/**
 * OfferSummary.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class OfferSummary  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestNewPrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestUsedPrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestCollectiblePrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestRefurbishedPrice;
    private java.lang.String totalNew;
    private java.lang.String totalUsed;
    private java.lang.String totalCollectible;
    private java.lang.String totalRefurbished;

    public OfferSummary() {
    }

    public OfferSummary(
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestNewPrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestUsedPrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestCollectiblePrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestRefurbishedPrice,
           java.lang.String totalNew,
           java.lang.String totalUsed,
           java.lang.String totalCollectible,
           java.lang.String totalRefurbished) {
           this.lowestNewPrice = lowestNewPrice;
           this.lowestUsedPrice = lowestUsedPrice;
           this.lowestCollectiblePrice = lowestCollectiblePrice;
           this.lowestRefurbishedPrice = lowestRefurbishedPrice;
           this.totalNew = totalNew;
           this.totalUsed = totalUsed;
           this.totalCollectible = totalCollectible;
           this.totalRefurbished = totalRefurbished;
    }


    /**
     * Gets the lowestNewPrice value for this OfferSummary.
     * 
     * @return lowestNewPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestNewPrice() {
        return lowestNewPrice;
    }


    /**
     * Sets the lowestNewPrice value for this OfferSummary.
     * 
     * @param lowestNewPrice
     */
    public void setLowestNewPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestNewPrice) {
        this.lowestNewPrice = lowestNewPrice;
    }


    /**
     * Gets the lowestUsedPrice value for this OfferSummary.
     * 
     * @return lowestUsedPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestUsedPrice() {
        return lowestUsedPrice;
    }


    /**
     * Sets the lowestUsedPrice value for this OfferSummary.
     * 
     * @param lowestUsedPrice
     */
    public void setLowestUsedPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestUsedPrice) {
        this.lowestUsedPrice = lowestUsedPrice;
    }


    /**
     * Gets the lowestCollectiblePrice value for this OfferSummary.
     * 
     * @return lowestCollectiblePrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestCollectiblePrice() {
        return lowestCollectiblePrice;
    }


    /**
     * Sets the lowestCollectiblePrice value for this OfferSummary.
     * 
     * @param lowestCollectiblePrice
     */
    public void setLowestCollectiblePrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestCollectiblePrice) {
        this.lowestCollectiblePrice = lowestCollectiblePrice;
    }


    /**
     * Gets the lowestRefurbishedPrice value for this OfferSummary.
     * 
     * @return lowestRefurbishedPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestRefurbishedPrice() {
        return lowestRefurbishedPrice;
    }


    /**
     * Sets the lowestRefurbishedPrice value for this OfferSummary.
     * 
     * @param lowestRefurbishedPrice
     */
    public void setLowestRefurbishedPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestRefurbishedPrice) {
        this.lowestRefurbishedPrice = lowestRefurbishedPrice;
    }


    /**
     * Gets the totalNew value for this OfferSummary.
     * 
     * @return totalNew
     */
    public java.lang.String getTotalNew() {
        return totalNew;
    }


    /**
     * Sets the totalNew value for this OfferSummary.
     * 
     * @param totalNew
     */
    public void setTotalNew(java.lang.String totalNew) {
        this.totalNew = totalNew;
    }


    /**
     * Gets the totalUsed value for this OfferSummary.
     * 
     * @return totalUsed
     */
    public java.lang.String getTotalUsed() {
        return totalUsed;
    }


    /**
     * Sets the totalUsed value for this OfferSummary.
     * 
     * @param totalUsed
     */
    public void setTotalUsed(java.lang.String totalUsed) {
        this.totalUsed = totalUsed;
    }


    /**
     * Gets the totalCollectible value for this OfferSummary.
     * 
     * @return totalCollectible
     */
    public java.lang.String getTotalCollectible() {
        return totalCollectible;
    }


    /**
     * Sets the totalCollectible value for this OfferSummary.
     * 
     * @param totalCollectible
     */
    public void setTotalCollectible(java.lang.String totalCollectible) {
        this.totalCollectible = totalCollectible;
    }


    /**
     * Gets the totalRefurbished value for this OfferSummary.
     * 
     * @return totalRefurbished
     */
    public java.lang.String getTotalRefurbished() {
        return totalRefurbished;
    }


    /**
     * Sets the totalRefurbished value for this OfferSummary.
     * 
     * @param totalRefurbished
     */
    public void setTotalRefurbished(java.lang.String totalRefurbished) {
        this.totalRefurbished = totalRefurbished;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OfferSummary)) return false;
        OfferSummary other = (OfferSummary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lowestNewPrice==null && other.getLowestNewPrice()==null) || 
             (this.lowestNewPrice!=null &&
              this.lowestNewPrice.equals(other.getLowestNewPrice()))) &&
            ((this.lowestUsedPrice==null && other.getLowestUsedPrice()==null) || 
             (this.lowestUsedPrice!=null &&
              this.lowestUsedPrice.equals(other.getLowestUsedPrice()))) &&
            ((this.lowestCollectiblePrice==null && other.getLowestCollectiblePrice()==null) || 
             (this.lowestCollectiblePrice!=null &&
              this.lowestCollectiblePrice.equals(other.getLowestCollectiblePrice()))) &&
            ((this.lowestRefurbishedPrice==null && other.getLowestRefurbishedPrice()==null) || 
             (this.lowestRefurbishedPrice!=null &&
              this.lowestRefurbishedPrice.equals(other.getLowestRefurbishedPrice()))) &&
            ((this.totalNew==null && other.getTotalNew()==null) || 
             (this.totalNew!=null &&
              this.totalNew.equals(other.getTotalNew()))) &&
            ((this.totalUsed==null && other.getTotalUsed()==null) || 
             (this.totalUsed!=null &&
              this.totalUsed.equals(other.getTotalUsed()))) &&
            ((this.totalCollectible==null && other.getTotalCollectible()==null) || 
             (this.totalCollectible!=null &&
              this.totalCollectible.equals(other.getTotalCollectible()))) &&
            ((this.totalRefurbished==null && other.getTotalRefurbished()==null) || 
             (this.totalRefurbished!=null &&
              this.totalRefurbished.equals(other.getTotalRefurbished())));
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
        if (getLowestNewPrice() != null) {
            _hashCode += getLowestNewPrice().hashCode();
        }
        if (getLowestUsedPrice() != null) {
            _hashCode += getLowestUsedPrice().hashCode();
        }
        if (getLowestCollectiblePrice() != null) {
            _hashCode += getLowestCollectiblePrice().hashCode();
        }
        if (getLowestRefurbishedPrice() != null) {
            _hashCode += getLowestRefurbishedPrice().hashCode();
        }
        if (getTotalNew() != null) {
            _hashCode += getTotalNew().hashCode();
        }
        if (getTotalUsed() != null) {
            _hashCode += getTotalUsed().hashCode();
        }
        if (getTotalCollectible() != null) {
            _hashCode += getTotalCollectible().hashCode();
        }
        if (getTotalRefurbished() != null) {
            _hashCode += getTotalRefurbished().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfferSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OfferSummary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestNewPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestNewPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestUsedPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestUsedPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestCollectiblePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestCollectiblePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestRefurbishedPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestRefurbishedPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalNew");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalNew"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalUsed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalUsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCollectible");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalCollectible"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRefurbished");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalRefurbished"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
