/**
 * SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating  implements java.io.Serializable {
    private org.apache.axis.types.NonNegativeInteger count;
    private org.apache.axis.types.NonNegativeInteger percentage;
    private java.lang.String type;  // attribute

    public SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating() {
    }

    public SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating(
           org.apache.axis.types.NonNegativeInteger count,
           org.apache.axis.types.NonNegativeInteger percentage,
           java.lang.String type) {
           this.count = count;
           this.percentage = percentage;
           this.type = type;
    }


    /**
     * Gets the count value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @return count
     */
    public org.apache.axis.types.NonNegativeInteger getCount() {
        return count;
    }


    /**
     * Sets the count value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @param count
     */
    public void setCount(org.apache.axis.types.NonNegativeInteger count) {
        this.count = count;
    }


    /**
     * Gets the percentage value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @return percentage
     */
    public org.apache.axis.types.NonNegativeInteger getPercentage() {
        return percentage;
    }


    /**
     * Sets the percentage value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @param percentage
     */
    public void setPercentage(org.apache.axis.types.NonNegativeInteger percentage) {
        this.percentage = percentage;
    }


    /**
     * Gets the type value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating)) return false;
        SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating other = (SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount()))) &&
            ((this.percentage==null && other.getPercentage()==null) || 
             (this.percentage!=null &&
              this.percentage.equals(other.getPercentage()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        if (getPercentage() != null) {
            _hashCode += getPercentage().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Seller>SellerFeedbackSummary>FeedbackDateRange>SellerFeedbackRating"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("type");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Type"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Percentage"));
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
