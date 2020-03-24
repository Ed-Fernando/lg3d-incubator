/**
 * SellerLookupRequest.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerLookupRequest  implements java.io.Serializable {
    private java.lang.String[] responseGroup;
    private java.lang.String[] sellerId;
    private org.apache.axis.types.PositiveInteger feedbackPage;

    public SellerLookupRequest() {
    }

    public SellerLookupRequest(
           java.lang.String[] responseGroup,
           java.lang.String[] sellerId,
           org.apache.axis.types.PositiveInteger feedbackPage) {
           this.responseGroup = responseGroup;
           this.sellerId = sellerId;
           this.feedbackPage = feedbackPage;
    }


    /**
     * Gets the responseGroup value for this SellerLookupRequest.
     * 
     * @return responseGroup
     */
    public java.lang.String[] getResponseGroup() {
        return responseGroup;
    }


    /**
     * Sets the responseGroup value for this SellerLookupRequest.
     * 
     * @param responseGroup
     */
    public void setResponseGroup(java.lang.String[] responseGroup) {
        this.responseGroup = responseGroup;
    }

    public java.lang.String getResponseGroup(int i) {
        return this.responseGroup[i];
    }

    public void setResponseGroup(int i, java.lang.String _value) {
        this.responseGroup[i] = _value;
    }


    /**
     * Gets the sellerId value for this SellerLookupRequest.
     * 
     * @return sellerId
     */
    public java.lang.String[] getSellerId() {
        return sellerId;
    }


    /**
     * Sets the sellerId value for this SellerLookupRequest.
     * 
     * @param sellerId
     */
    public void setSellerId(java.lang.String[] sellerId) {
        this.sellerId = sellerId;
    }

    public java.lang.String getSellerId(int i) {
        return this.sellerId[i];
    }

    public void setSellerId(int i, java.lang.String _value) {
        this.sellerId[i] = _value;
    }


    /**
     * Gets the feedbackPage value for this SellerLookupRequest.
     * 
     * @return feedbackPage
     */
    public org.apache.axis.types.PositiveInteger getFeedbackPage() {
        return feedbackPage;
    }


    /**
     * Sets the feedbackPage value for this SellerLookupRequest.
     * 
     * @param feedbackPage
     */
    public void setFeedbackPage(org.apache.axis.types.PositiveInteger feedbackPage) {
        this.feedbackPage = feedbackPage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerLookupRequest)) return false;
        SellerLookupRequest other = (SellerLookupRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseGroup==null && other.getResponseGroup()==null) || 
             (this.responseGroup!=null &&
              java.util.Arrays.equals(this.responseGroup, other.getResponseGroup()))) &&
            ((this.sellerId==null && other.getSellerId()==null) || 
             (this.sellerId!=null &&
              java.util.Arrays.equals(this.sellerId, other.getSellerId()))) &&
            ((this.feedbackPage==null && other.getFeedbackPage()==null) || 
             (this.feedbackPage!=null &&
              this.feedbackPage.equals(other.getFeedbackPage())));
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
        if (getResponseGroup() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResponseGroup());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResponseGroup(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSellerId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSellerId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSellerId(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFeedbackPage() != null) {
            _hashCode += getFeedbackPage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SellerLookupRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookupRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ResponseGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feedbackPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "FeedbackPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
