/**
 * SimilarityLookupRequest.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SimilarityLookupRequest  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition;
    private org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod;
    private java.lang.String[] itemId;
    private java.lang.String ISPUPostalCode;
    private java.lang.String merchantId;
    private java.lang.String[] responseGroup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequestSimilarityType similarityType;

    public SimilarityLookupRequest() {
    }

    public SimilarityLookupRequest(
           org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition,
           org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod,
           java.lang.String[] itemId,
           java.lang.String ISPUPostalCode,
           java.lang.String merchantId,
           java.lang.String[] responseGroup,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequestSimilarityType similarityType) {
           this.condition = condition;
           this.deliveryMethod = deliveryMethod;
           this.itemId = itemId;
           this.ISPUPostalCode = ISPUPostalCode;
           this.merchantId = merchantId;
           this.responseGroup = responseGroup;
           this.similarityType = similarityType;
    }


    /**
     * Gets the condition value for this SimilarityLookupRequest.
     * 
     * @return condition
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Condition getCondition() {
        return condition;
    }


    /**
     * Sets the condition value for this SimilarityLookupRequest.
     * 
     * @param condition
     */
    public void setCondition(org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition) {
        this.condition = condition;
    }


    /**
     * Gets the deliveryMethod value for this SimilarityLookupRequest.
     * 
     * @return deliveryMethod
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }


    /**
     * Sets the deliveryMethod value for this SimilarityLookupRequest.
     * 
     * @param deliveryMethod
     */
    public void setDeliveryMethod(org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    /**
     * Gets the itemId value for this SimilarityLookupRequest.
     * 
     * @return itemId
     */
    public java.lang.String[] getItemId() {
        return itemId;
    }


    /**
     * Sets the itemId value for this SimilarityLookupRequest.
     * 
     * @param itemId
     */
    public void setItemId(java.lang.String[] itemId) {
        this.itemId = itemId;
    }

    public java.lang.String getItemId(int i) {
        return this.itemId[i];
    }

    public void setItemId(int i, java.lang.String _value) {
        this.itemId[i] = _value;
    }


    /**
     * Gets the ISPUPostalCode value for this SimilarityLookupRequest.
     * 
     * @return ISPUPostalCode
     */
    public java.lang.String getISPUPostalCode() {
        return ISPUPostalCode;
    }


    /**
     * Sets the ISPUPostalCode value for this SimilarityLookupRequest.
     * 
     * @param ISPUPostalCode
     */
    public void setISPUPostalCode(java.lang.String ISPUPostalCode) {
        this.ISPUPostalCode = ISPUPostalCode;
    }


    /**
     * Gets the merchantId value for this SimilarityLookupRequest.
     * 
     * @return merchantId
     */
    public java.lang.String getMerchantId() {
        return merchantId;
    }


    /**
     * Sets the merchantId value for this SimilarityLookupRequest.
     * 
     * @param merchantId
     */
    public void setMerchantId(java.lang.String merchantId) {
        this.merchantId = merchantId;
    }


    /**
     * Gets the responseGroup value for this SimilarityLookupRequest.
     * 
     * @return responseGroup
     */
    public java.lang.String[] getResponseGroup() {
        return responseGroup;
    }


    /**
     * Sets the responseGroup value for this SimilarityLookupRequest.
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
     * Gets the similarityType value for this SimilarityLookupRequest.
     * 
     * @return similarityType
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequestSimilarityType getSimilarityType() {
        return similarityType;
    }


    /**
     * Sets the similarityType value for this SimilarityLookupRequest.
     * 
     * @param similarityType
     */
    public void setSimilarityType(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequestSimilarityType similarityType) {
        this.similarityType = similarityType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SimilarityLookupRequest)) return false;
        SimilarityLookupRequest other = (SimilarityLookupRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.condition==null && other.getCondition()==null) || 
             (this.condition!=null &&
              this.condition.equals(other.getCondition()))) &&
            ((this.deliveryMethod==null && other.getDeliveryMethod()==null) || 
             (this.deliveryMethod!=null &&
              this.deliveryMethod.equals(other.getDeliveryMethod()))) &&
            ((this.itemId==null && other.getItemId()==null) || 
             (this.itemId!=null &&
              java.util.Arrays.equals(this.itemId, other.getItemId()))) &&
            ((this.ISPUPostalCode==null && other.getISPUPostalCode()==null) || 
             (this.ISPUPostalCode!=null &&
              this.ISPUPostalCode.equals(other.getISPUPostalCode()))) &&
            ((this.merchantId==null && other.getMerchantId()==null) || 
             (this.merchantId!=null &&
              this.merchantId.equals(other.getMerchantId()))) &&
            ((this.responseGroup==null && other.getResponseGroup()==null) || 
             (this.responseGroup!=null &&
              java.util.Arrays.equals(this.responseGroup, other.getResponseGroup()))) &&
            ((this.similarityType==null && other.getSimilarityType()==null) || 
             (this.similarityType!=null &&
              this.similarityType.equals(other.getSimilarityType())));
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
        if (getCondition() != null) {
            _hashCode += getCondition().hashCode();
        }
        if (getDeliveryMethod() != null) {
            _hashCode += getDeliveryMethod().hashCode();
        }
        if (getItemId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemId(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getISPUPostalCode() != null) {
            _hashCode += getISPUPostalCode().hashCode();
        }
        if (getMerchantId() != null) {
            _hashCode += getMerchantId().hashCode();
        }
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
        if (getSimilarityType() != null) {
            _hashCode += getSimilarityType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SimilarityLookupRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookupRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("condition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Condition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Condition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DeliveryMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DeliveryMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ISPUPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ISPUPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("merchantId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MerchantId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ResponseGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookupRequest>SimilarityType"));
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
