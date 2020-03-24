/**
 * SellerSellerFeedbackSummary.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerSellerFeedbackSummary  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange[] feedbackDateRange;

    public SellerSellerFeedbackSummary() {
    }

    public SellerSellerFeedbackSummary(
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange[] feedbackDateRange) {
           this.feedbackDateRange = feedbackDateRange;
    }


    /**
     * Gets the feedbackDateRange value for this SellerSellerFeedbackSummary.
     * 
     * @return feedbackDateRange
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange[] getFeedbackDateRange() {
        return feedbackDateRange;
    }


    /**
     * Sets the feedbackDateRange value for this SellerSellerFeedbackSummary.
     * 
     * @param feedbackDateRange
     */
    public void setFeedbackDateRange(org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange[] feedbackDateRange) {
        this.feedbackDateRange = feedbackDateRange;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange getFeedbackDateRange(int i) {
        return this.feedbackDateRange[i];
    }

    public void setFeedbackDateRange(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange _value) {
        this.feedbackDateRange[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerSellerFeedbackSummary)) return false;
        SellerSellerFeedbackSummary other = (SellerSellerFeedbackSummary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.feedbackDateRange==null && other.getFeedbackDateRange()==null) || 
             (this.feedbackDateRange!=null &&
              java.util.Arrays.equals(this.feedbackDateRange, other.getFeedbackDateRange())));
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
        if (getFeedbackDateRange() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFeedbackDateRange());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFeedbackDateRange(), i);
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
        new org.apache.axis.description.TypeDesc(SellerSellerFeedbackSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Seller>SellerFeedbackSummary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feedbackDateRange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "FeedbackDateRange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Seller>SellerFeedbackSummary>FeedbackDateRange"));
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
