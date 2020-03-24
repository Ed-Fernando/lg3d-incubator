/**
 * SellerSellerFeedbackSummaryFeedbackDateRange.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerSellerFeedbackSummaryFeedbackDateRange  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating[] sellerFeedbackRating;
    private java.lang.String period;  // attribute

    public SellerSellerFeedbackSummaryFeedbackDateRange() {
    }

    public SellerSellerFeedbackSummaryFeedbackDateRange(
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating[] sellerFeedbackRating,
           java.lang.String period) {
           this.sellerFeedbackRating = sellerFeedbackRating;
           this.period = period;
    }


    /**
     * Gets the sellerFeedbackRating value for this SellerSellerFeedbackSummaryFeedbackDateRange.
     * 
     * @return sellerFeedbackRating
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating[] getSellerFeedbackRating() {
        return sellerFeedbackRating;
    }


    /**
     * Sets the sellerFeedbackRating value for this SellerSellerFeedbackSummaryFeedbackDateRange.
     * 
     * @param sellerFeedbackRating
     */
    public void setSellerFeedbackRating(org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating[] sellerFeedbackRating) {
        this.sellerFeedbackRating = sellerFeedbackRating;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating getSellerFeedbackRating(int i) {
        return this.sellerFeedbackRating[i];
    }

    public void setSellerFeedbackRating(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating _value) {
        this.sellerFeedbackRating[i] = _value;
    }


    /**
     * Gets the period value for this SellerSellerFeedbackSummaryFeedbackDateRange.
     * 
     * @return period
     */
    public java.lang.String getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this SellerSellerFeedbackSummaryFeedbackDateRange.
     * 
     * @param period
     */
    public void setPeriod(java.lang.String period) {
        this.period = period;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerSellerFeedbackSummaryFeedbackDateRange)) return false;
        SellerSellerFeedbackSummaryFeedbackDateRange other = (SellerSellerFeedbackSummaryFeedbackDateRange) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sellerFeedbackRating==null && other.getSellerFeedbackRating()==null) || 
             (this.sellerFeedbackRating!=null &&
              java.util.Arrays.equals(this.sellerFeedbackRating, other.getSellerFeedbackRating()))) &&
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod())));
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
        if (getSellerFeedbackRating() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSellerFeedbackRating());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSellerFeedbackRating(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SellerSellerFeedbackSummaryFeedbackDateRange.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Seller>SellerFeedbackSummary>FeedbackDateRange"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("period");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Period"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerFeedbackRating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerFeedbackRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Seller>SellerFeedbackSummary>FeedbackDateRange>SellerFeedbackRating"));
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
