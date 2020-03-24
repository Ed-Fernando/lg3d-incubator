/**
 * VariationSummary.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class VariationSummary  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestPrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price highestPrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestSalePrice;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price highestSalePrice;
    private java.lang.String singleMerchantId;

    public VariationSummary() {
    }

    public VariationSummary(
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestPrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price highestPrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestSalePrice,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price highestSalePrice,
           java.lang.String singleMerchantId) {
           this.lowestPrice = lowestPrice;
           this.highestPrice = highestPrice;
           this.lowestSalePrice = lowestSalePrice;
           this.highestSalePrice = highestSalePrice;
           this.singleMerchantId = singleMerchantId;
    }


    /**
     * Gets the lowestPrice value for this VariationSummary.
     * 
     * @return lowestPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestPrice() {
        return lowestPrice;
    }


    /**
     * Sets the lowestPrice value for this VariationSummary.
     * 
     * @param lowestPrice
     */
    public void setLowestPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestPrice) {
        this.lowestPrice = lowestPrice;
    }


    /**
     * Gets the highestPrice value for this VariationSummary.
     * 
     * @return highestPrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getHighestPrice() {
        return highestPrice;
    }


    /**
     * Sets the highestPrice value for this VariationSummary.
     * 
     * @param highestPrice
     */
    public void setHighestPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price highestPrice) {
        this.highestPrice = highestPrice;
    }


    /**
     * Gets the lowestSalePrice value for this VariationSummary.
     * 
     * @return lowestSalePrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getLowestSalePrice() {
        return lowestSalePrice;
    }


    /**
     * Sets the lowestSalePrice value for this VariationSummary.
     * 
     * @param lowestSalePrice
     */
    public void setLowestSalePrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price lowestSalePrice) {
        this.lowestSalePrice = lowestSalePrice;
    }


    /**
     * Gets the highestSalePrice value for this VariationSummary.
     * 
     * @return highestSalePrice
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getHighestSalePrice() {
        return highestSalePrice;
    }


    /**
     * Sets the highestSalePrice value for this VariationSummary.
     * 
     * @param highestSalePrice
     */
    public void setHighestSalePrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price highestSalePrice) {
        this.highestSalePrice = highestSalePrice;
    }


    /**
     * Gets the singleMerchantId value for this VariationSummary.
     * 
     * @return singleMerchantId
     */
    public java.lang.String getSingleMerchantId() {
        return singleMerchantId;
    }


    /**
     * Sets the singleMerchantId value for this VariationSummary.
     * 
     * @param singleMerchantId
     */
    public void setSingleMerchantId(java.lang.String singleMerchantId) {
        this.singleMerchantId = singleMerchantId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariationSummary)) return false;
        VariationSummary other = (VariationSummary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lowestPrice==null && other.getLowestPrice()==null) || 
             (this.lowestPrice!=null &&
              this.lowestPrice.equals(other.getLowestPrice()))) &&
            ((this.highestPrice==null && other.getHighestPrice()==null) || 
             (this.highestPrice!=null &&
              this.highestPrice.equals(other.getHighestPrice()))) &&
            ((this.lowestSalePrice==null && other.getLowestSalePrice()==null) || 
             (this.lowestSalePrice!=null &&
              this.lowestSalePrice.equals(other.getLowestSalePrice()))) &&
            ((this.highestSalePrice==null && other.getHighestSalePrice()==null) || 
             (this.highestSalePrice!=null &&
              this.highestSalePrice.equals(other.getHighestSalePrice()))) &&
            ((this.singleMerchantId==null && other.getSingleMerchantId()==null) || 
             (this.singleMerchantId!=null &&
              this.singleMerchantId.equals(other.getSingleMerchantId())));
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
        if (getLowestPrice() != null) {
            _hashCode += getLowestPrice().hashCode();
        }
        if (getHighestPrice() != null) {
            _hashCode += getHighestPrice().hashCode();
        }
        if (getLowestSalePrice() != null) {
            _hashCode += getLowestSalePrice().hashCode();
        }
        if (getHighestSalePrice() != null) {
            _hashCode += getHighestSalePrice().hashCode();
        }
        if (getSingleMerchantId() != null) {
            _hashCode += getSingleMerchantId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VariationSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">VariationSummary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("highestPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HighestPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowestSalePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LowestSalePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("highestSalePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HighestSalePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("singleMerchantId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SingleMerchantId"));
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
