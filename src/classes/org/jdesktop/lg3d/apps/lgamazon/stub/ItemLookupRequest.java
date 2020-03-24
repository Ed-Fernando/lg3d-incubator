/**
 * ItemLookupRequest.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ItemLookupRequest  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition;
    private org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod;
    private java.lang.String futureLaunchDate;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequestIdType idType;
    private java.lang.String ISPUPostalCode;
    private java.lang.String merchantId;
    private org.apache.axis.types.PositiveInteger offerPage;
    private java.lang.String[] itemId;
    private java.lang.String[] responseGroup;
    private org.apache.axis.types.PositiveInteger reviewPage;
    private java.lang.String searchIndex;
    private java.lang.String searchInsideKeywords;
    private org.apache.axis.types.PositiveInteger variationPage;

    public ItemLookupRequest() {
    }

    public ItemLookupRequest(
           org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition,
           org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod,
           java.lang.String futureLaunchDate,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequestIdType idType,
           java.lang.String ISPUPostalCode,
           java.lang.String merchantId,
           org.apache.axis.types.PositiveInteger offerPage,
           java.lang.String[] itemId,
           java.lang.String[] responseGroup,
           org.apache.axis.types.PositiveInteger reviewPage,
           java.lang.String searchIndex,
           java.lang.String searchInsideKeywords,
           org.apache.axis.types.PositiveInteger variationPage) {
           this.condition = condition;
           this.deliveryMethod = deliveryMethod;
           this.futureLaunchDate = futureLaunchDate;
           this.idType = idType;
           this.ISPUPostalCode = ISPUPostalCode;
           this.merchantId = merchantId;
           this.offerPage = offerPage;
           this.itemId = itemId;
           this.responseGroup = responseGroup;
           this.reviewPage = reviewPage;
           this.searchIndex = searchIndex;
           this.searchInsideKeywords = searchInsideKeywords;
           this.variationPage = variationPage;
    }


    /**
     * Gets the condition value for this ItemLookupRequest.
     * 
     * @return condition
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Condition getCondition() {
        return condition;
    }


    /**
     * Sets the condition value for this ItemLookupRequest.
     * 
     * @param condition
     */
    public void setCondition(org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition) {
        this.condition = condition;
    }


    /**
     * Gets the deliveryMethod value for this ItemLookupRequest.
     * 
     * @return deliveryMethod
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }


    /**
     * Sets the deliveryMethod value for this ItemLookupRequest.
     * 
     * @param deliveryMethod
     */
    public void setDeliveryMethod(org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    /**
     * Gets the futureLaunchDate value for this ItemLookupRequest.
     * 
     * @return futureLaunchDate
     */
    public java.lang.String getFutureLaunchDate() {
        return futureLaunchDate;
    }


    /**
     * Sets the futureLaunchDate value for this ItemLookupRequest.
     * 
     * @param futureLaunchDate
     */
    public void setFutureLaunchDate(java.lang.String futureLaunchDate) {
        this.futureLaunchDate = futureLaunchDate;
    }


    /**
     * Gets the idType value for this ItemLookupRequest.
     * 
     * @return idType
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequestIdType getIdType() {
        return idType;
    }


    /**
     * Sets the idType value for this ItemLookupRequest.
     * 
     * @param idType
     */
    public void setIdType(org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequestIdType idType) {
        this.idType = idType;
    }


    /**
     * Gets the ISPUPostalCode value for this ItemLookupRequest.
     * 
     * @return ISPUPostalCode
     */
    public java.lang.String getISPUPostalCode() {
        return ISPUPostalCode;
    }


    /**
     * Sets the ISPUPostalCode value for this ItemLookupRequest.
     * 
     * @param ISPUPostalCode
     */
    public void setISPUPostalCode(java.lang.String ISPUPostalCode) {
        this.ISPUPostalCode = ISPUPostalCode;
    }


    /**
     * Gets the merchantId value for this ItemLookupRequest.
     * 
     * @return merchantId
     */
    public java.lang.String getMerchantId() {
        return merchantId;
    }


    /**
     * Sets the merchantId value for this ItemLookupRequest.
     * 
     * @param merchantId
     */
    public void setMerchantId(java.lang.String merchantId) {
        this.merchantId = merchantId;
    }


    /**
     * Gets the offerPage value for this ItemLookupRequest.
     * 
     * @return offerPage
     */
    public org.apache.axis.types.PositiveInteger getOfferPage() {
        return offerPage;
    }


    /**
     * Sets the offerPage value for this ItemLookupRequest.
     * 
     * @param offerPage
     */
    public void setOfferPage(org.apache.axis.types.PositiveInteger offerPage) {
        this.offerPage = offerPage;
    }


    /**
     * Gets the itemId value for this ItemLookupRequest.
     * 
     * @return itemId
     */
    public java.lang.String[] getItemId() {
        return itemId;
    }


    /**
     * Sets the itemId value for this ItemLookupRequest.
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
     * Gets the responseGroup value for this ItemLookupRequest.
     * 
     * @return responseGroup
     */
    public java.lang.String[] getResponseGroup() {
        return responseGroup;
    }


    /**
     * Sets the responseGroup value for this ItemLookupRequest.
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
     * Gets the reviewPage value for this ItemLookupRequest.
     * 
     * @return reviewPage
     */
    public org.apache.axis.types.PositiveInteger getReviewPage() {
        return reviewPage;
    }


    /**
     * Sets the reviewPage value for this ItemLookupRequest.
     * 
     * @param reviewPage
     */
    public void setReviewPage(org.apache.axis.types.PositiveInteger reviewPage) {
        this.reviewPage = reviewPage;
    }


    /**
     * Gets the searchIndex value for this ItemLookupRequest.
     * 
     * @return searchIndex
     */
    public java.lang.String getSearchIndex() {
        return searchIndex;
    }


    /**
     * Sets the searchIndex value for this ItemLookupRequest.
     * 
     * @param searchIndex
     */
    public void setSearchIndex(java.lang.String searchIndex) {
        this.searchIndex = searchIndex;
    }


    /**
     * Gets the searchInsideKeywords value for this ItemLookupRequest.
     * 
     * @return searchInsideKeywords
     */
    public java.lang.String getSearchInsideKeywords() {
        return searchInsideKeywords;
    }


    /**
     * Sets the searchInsideKeywords value for this ItemLookupRequest.
     * 
     * @param searchInsideKeywords
     */
    public void setSearchInsideKeywords(java.lang.String searchInsideKeywords) {
        this.searchInsideKeywords = searchInsideKeywords;
    }


    /**
     * Gets the variationPage value for this ItemLookupRequest.
     * 
     * @return variationPage
     */
    public org.apache.axis.types.PositiveInteger getVariationPage() {
        return variationPage;
    }


    /**
     * Sets the variationPage value for this ItemLookupRequest.
     * 
     * @param variationPage
     */
    public void setVariationPage(org.apache.axis.types.PositiveInteger variationPage) {
        this.variationPage = variationPage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemLookupRequest)) return false;
        ItemLookupRequest other = (ItemLookupRequest) obj;
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
            ((this.futureLaunchDate==null && other.getFutureLaunchDate()==null) || 
             (this.futureLaunchDate!=null &&
              this.futureLaunchDate.equals(other.getFutureLaunchDate()))) &&
            ((this.idType==null && other.getIdType()==null) || 
             (this.idType!=null &&
              this.idType.equals(other.getIdType()))) &&
            ((this.ISPUPostalCode==null && other.getISPUPostalCode()==null) || 
             (this.ISPUPostalCode!=null &&
              this.ISPUPostalCode.equals(other.getISPUPostalCode()))) &&
            ((this.merchantId==null && other.getMerchantId()==null) || 
             (this.merchantId!=null &&
              this.merchantId.equals(other.getMerchantId()))) &&
            ((this.offerPage==null && other.getOfferPage()==null) || 
             (this.offerPage!=null &&
              this.offerPage.equals(other.getOfferPage()))) &&
            ((this.itemId==null && other.getItemId()==null) || 
             (this.itemId!=null &&
              java.util.Arrays.equals(this.itemId, other.getItemId()))) &&
            ((this.responseGroup==null && other.getResponseGroup()==null) || 
             (this.responseGroup!=null &&
              java.util.Arrays.equals(this.responseGroup, other.getResponseGroup()))) &&
            ((this.reviewPage==null && other.getReviewPage()==null) || 
             (this.reviewPage!=null &&
              this.reviewPage.equals(other.getReviewPage()))) &&
            ((this.searchIndex==null && other.getSearchIndex()==null) || 
             (this.searchIndex!=null &&
              this.searchIndex.equals(other.getSearchIndex()))) &&
            ((this.searchInsideKeywords==null && other.getSearchInsideKeywords()==null) || 
             (this.searchInsideKeywords!=null &&
              this.searchInsideKeywords.equals(other.getSearchInsideKeywords()))) &&
            ((this.variationPage==null && other.getVariationPage()==null) || 
             (this.variationPage!=null &&
              this.variationPage.equals(other.getVariationPage())));
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
        if (getFutureLaunchDate() != null) {
            _hashCode += getFutureLaunchDate().hashCode();
        }
        if (getIdType() != null) {
            _hashCode += getIdType().hashCode();
        }
        if (getISPUPostalCode() != null) {
            _hashCode += getISPUPostalCode().hashCode();
        }
        if (getMerchantId() != null) {
            _hashCode += getMerchantId().hashCode();
        }
        if (getOfferPage() != null) {
            _hashCode += getOfferPage().hashCode();
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
        if (getReviewPage() != null) {
            _hashCode += getReviewPage().hashCode();
        }
        if (getSearchIndex() != null) {
            _hashCode += getSearchIndex().hashCode();
        }
        if (getSearchInsideKeywords() != null) {
            _hashCode += getSearchInsideKeywords().hashCode();
        }
        if (getVariationPage() != null) {
            _hashCode += getVariationPage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ItemLookupRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookupRequest"));
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
        elemField.setFieldName("futureLaunchDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "FutureLaunchDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "IdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookupRequest>IdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setFieldName("offerPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
        elemField.setFieldName("responseGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ResponseGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reviewPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ReviewPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchInsideKeywords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchInsideKeywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variationPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "VariationPage"));
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
