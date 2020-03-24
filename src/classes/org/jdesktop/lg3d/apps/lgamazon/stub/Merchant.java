/**
 * Merchant.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Merchant  implements java.io.Serializable {
    private java.lang.String merchantId;
    private java.lang.String name;
    private java.lang.String glancePage;
    private java.math.BigDecimal averageFeedbackRating;
    private org.apache.axis.types.NonNegativeInteger totalFeedback;
    private org.apache.axis.types.NonNegativeInteger totalFeedbackPages;

    public Merchant() {
    }

    public Merchant(
           java.lang.String merchantId,
           java.lang.String name,
           java.lang.String glancePage,
           java.math.BigDecimal averageFeedbackRating,
           org.apache.axis.types.NonNegativeInteger totalFeedback,
           org.apache.axis.types.NonNegativeInteger totalFeedbackPages) {
           this.merchantId = merchantId;
           this.name = name;
           this.glancePage = glancePage;
           this.averageFeedbackRating = averageFeedbackRating;
           this.totalFeedback = totalFeedback;
           this.totalFeedbackPages = totalFeedbackPages;
    }


    /**
     * Gets the merchantId value for this Merchant.
     * 
     * @return merchantId
     */
    public java.lang.String getMerchantId() {
        return merchantId;
    }


    /**
     * Sets the merchantId value for this Merchant.
     * 
     * @param merchantId
     */
    public void setMerchantId(java.lang.String merchantId) {
        this.merchantId = merchantId;
    }


    /**
     * Gets the name value for this Merchant.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Merchant.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the glancePage value for this Merchant.
     * 
     * @return glancePage
     */
    public java.lang.String getGlancePage() {
        return glancePage;
    }


    /**
     * Sets the glancePage value for this Merchant.
     * 
     * @param glancePage
     */
    public void setGlancePage(java.lang.String glancePage) {
        this.glancePage = glancePage;
    }


    /**
     * Gets the averageFeedbackRating value for this Merchant.
     * 
     * @return averageFeedbackRating
     */
    public java.math.BigDecimal getAverageFeedbackRating() {
        return averageFeedbackRating;
    }


    /**
     * Sets the averageFeedbackRating value for this Merchant.
     * 
     * @param averageFeedbackRating
     */
    public void setAverageFeedbackRating(java.math.BigDecimal averageFeedbackRating) {
        this.averageFeedbackRating = averageFeedbackRating;
    }


    /**
     * Gets the totalFeedback value for this Merchant.
     * 
     * @return totalFeedback
     */
    public org.apache.axis.types.NonNegativeInteger getTotalFeedback() {
        return totalFeedback;
    }


    /**
     * Sets the totalFeedback value for this Merchant.
     * 
     * @param totalFeedback
     */
    public void setTotalFeedback(org.apache.axis.types.NonNegativeInteger totalFeedback) {
        this.totalFeedback = totalFeedback;
    }


    /**
     * Gets the totalFeedbackPages value for this Merchant.
     * 
     * @return totalFeedbackPages
     */
    public org.apache.axis.types.NonNegativeInteger getTotalFeedbackPages() {
        return totalFeedbackPages;
    }


    /**
     * Sets the totalFeedbackPages value for this Merchant.
     * 
     * @param totalFeedbackPages
     */
    public void setTotalFeedbackPages(org.apache.axis.types.NonNegativeInteger totalFeedbackPages) {
        this.totalFeedbackPages = totalFeedbackPages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Merchant)) return false;
        Merchant other = (Merchant) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.merchantId==null && other.getMerchantId()==null) || 
             (this.merchantId!=null &&
              this.merchantId.equals(other.getMerchantId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.glancePage==null && other.getGlancePage()==null) || 
             (this.glancePage!=null &&
              this.glancePage.equals(other.getGlancePage()))) &&
            ((this.averageFeedbackRating==null && other.getAverageFeedbackRating()==null) || 
             (this.averageFeedbackRating!=null &&
              this.averageFeedbackRating.equals(other.getAverageFeedbackRating()))) &&
            ((this.totalFeedback==null && other.getTotalFeedback()==null) || 
             (this.totalFeedback!=null &&
              this.totalFeedback.equals(other.getTotalFeedback()))) &&
            ((this.totalFeedbackPages==null && other.getTotalFeedbackPages()==null) || 
             (this.totalFeedbackPages!=null &&
              this.totalFeedbackPages.equals(other.getTotalFeedbackPages())));
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
        if (getMerchantId() != null) {
            _hashCode += getMerchantId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getGlancePage() != null) {
            _hashCode += getGlancePage().hashCode();
        }
        if (getAverageFeedbackRating() != null) {
            _hashCode += getAverageFeedbackRating().hashCode();
        }
        if (getTotalFeedback() != null) {
            _hashCode += getTotalFeedback().hashCode();
        }
        if (getTotalFeedbackPages() != null) {
            _hashCode += getTotalFeedbackPages().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Merchant.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Merchant"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("merchantId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MerchantId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("glancePage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "GlancePage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("averageFeedbackRating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AverageFeedbackRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalFeedback");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalFeedback"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalFeedbackPages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalFeedbackPages"));
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
