/**
 * ListLookupRequest.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ListLookupRequest  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition;
    private org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod;
    private java.lang.String ISPUPostalCode;
    private java.lang.String listId;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequestListType listType;
    private java.lang.String merchantId;
    private java.lang.String productGroup;
    private org.apache.axis.types.PositiveInteger productPage;
    private java.lang.String[] responseGroup;
    private java.lang.String sort;

    public ListLookupRequest() {
    }

    public ListLookupRequest(
           org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition,
           org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod,
           java.lang.String ISPUPostalCode,
           java.lang.String listId,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequestListType listType,
           java.lang.String merchantId,
           java.lang.String productGroup,
           org.apache.axis.types.PositiveInteger productPage,
           java.lang.String[] responseGroup,
           java.lang.String sort) {
           this.condition = condition;
           this.deliveryMethod = deliveryMethod;
           this.ISPUPostalCode = ISPUPostalCode;
           this.listId = listId;
           this.listType = listType;
           this.merchantId = merchantId;
           this.productGroup = productGroup;
           this.productPage = productPage;
           this.responseGroup = responseGroup;
           this.sort = sort;
    }


    /**
     * Gets the condition value for this ListLookupRequest.
     * 
     * @return condition
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Condition getCondition() {
        return condition;
    }


    /**
     * Sets the condition value for this ListLookupRequest.
     * 
     * @param condition
     */
    public void setCondition(org.jdesktop.lg3d.apps.lgamazon.stub.Condition condition) {
        this.condition = condition;
    }


    /**
     * Gets the deliveryMethod value for this ListLookupRequest.
     * 
     * @return deliveryMethod
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }


    /**
     * Sets the deliveryMethod value for this ListLookupRequest.
     * 
     * @param deliveryMethod
     */
    public void setDeliveryMethod(org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }


    /**
     * Gets the ISPUPostalCode value for this ListLookupRequest.
     * 
     * @return ISPUPostalCode
     */
    public java.lang.String getISPUPostalCode() {
        return ISPUPostalCode;
    }


    /**
     * Sets the ISPUPostalCode value for this ListLookupRequest.
     * 
     * @param ISPUPostalCode
     */
    public void setISPUPostalCode(java.lang.String ISPUPostalCode) {
        this.ISPUPostalCode = ISPUPostalCode;
    }


    /**
     * Gets the listId value for this ListLookupRequest.
     * 
     * @return listId
     */
    public java.lang.String getListId() {
        return listId;
    }


    /**
     * Sets the listId value for this ListLookupRequest.
     * 
     * @param listId
     */
    public void setListId(java.lang.String listId) {
        this.listId = listId;
    }


    /**
     * Gets the listType value for this ListLookupRequest.
     * 
     * @return listType
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequestListType getListType() {
        return listType;
    }


    /**
     * Sets the listType value for this ListLookupRequest.
     * 
     * @param listType
     */
    public void setListType(org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequestListType listType) {
        this.listType = listType;
    }


    /**
     * Gets the merchantId value for this ListLookupRequest.
     * 
     * @return merchantId
     */
    public java.lang.String getMerchantId() {
        return merchantId;
    }


    /**
     * Sets the merchantId value for this ListLookupRequest.
     * 
     * @param merchantId
     */
    public void setMerchantId(java.lang.String merchantId) {
        this.merchantId = merchantId;
    }


    /**
     * Gets the productGroup value for this ListLookupRequest.
     * 
     * @return productGroup
     */
    public java.lang.String getProductGroup() {
        return productGroup;
    }


    /**
     * Sets the productGroup value for this ListLookupRequest.
     * 
     * @param productGroup
     */
    public void setProductGroup(java.lang.String productGroup) {
        this.productGroup = productGroup;
    }


    /**
     * Gets the productPage value for this ListLookupRequest.
     * 
     * @return productPage
     */
    public org.apache.axis.types.PositiveInteger getProductPage() {
        return productPage;
    }


    /**
     * Sets the productPage value for this ListLookupRequest.
     * 
     * @param productPage
     */
    public void setProductPage(org.apache.axis.types.PositiveInteger productPage) {
        this.productPage = productPage;
    }


    /**
     * Gets the responseGroup value for this ListLookupRequest.
     * 
     * @return responseGroup
     */
    public java.lang.String[] getResponseGroup() {
        return responseGroup;
    }


    /**
     * Sets the responseGroup value for this ListLookupRequest.
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
     * Gets the sort value for this ListLookupRequest.
     * 
     * @return sort
     */
    public java.lang.String getSort() {
        return sort;
    }


    /**
     * Sets the sort value for this ListLookupRequest.
     * 
     * @param sort
     */
    public void setSort(java.lang.String sort) {
        this.sort = sort;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListLookupRequest)) return false;
        ListLookupRequest other = (ListLookupRequest) obj;
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
            ((this.ISPUPostalCode==null && other.getISPUPostalCode()==null) || 
             (this.ISPUPostalCode!=null &&
              this.ISPUPostalCode.equals(other.getISPUPostalCode()))) &&
            ((this.listId==null && other.getListId()==null) || 
             (this.listId!=null &&
              this.listId.equals(other.getListId()))) &&
            ((this.listType==null && other.getListType()==null) || 
             (this.listType!=null &&
              this.listType.equals(other.getListType()))) &&
            ((this.merchantId==null && other.getMerchantId()==null) || 
             (this.merchantId!=null &&
              this.merchantId.equals(other.getMerchantId()))) &&
            ((this.productGroup==null && other.getProductGroup()==null) || 
             (this.productGroup!=null &&
              this.productGroup.equals(other.getProductGroup()))) &&
            ((this.productPage==null && other.getProductPage()==null) || 
             (this.productPage!=null &&
              this.productPage.equals(other.getProductPage()))) &&
            ((this.responseGroup==null && other.getResponseGroup()==null) || 
             (this.responseGroup!=null &&
              java.util.Arrays.equals(this.responseGroup, other.getResponseGroup()))) &&
            ((this.sort==null && other.getSort()==null) || 
             (this.sort!=null &&
              this.sort.equals(other.getSort())));
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
        if (getISPUPostalCode() != null) {
            _hashCode += getISPUPostalCode().hashCode();
        }
        if (getListId() != null) {
            _hashCode += getListId().hashCode();
        }
        if (getListType() != null) {
            _hashCode += getListType().hashCode();
        }
        if (getMerchantId() != null) {
            _hashCode += getMerchantId().hashCode();
        }
        if (getProductGroup() != null) {
            _hashCode += getProductGroup().hashCode();
        }
        if (getProductPage() != null) {
            _hashCode += getProductPage().hashCode();
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
        if (getSort() != null) {
            _hashCode += getSort().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListLookupRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookupRequest"));
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
        elemField.setFieldName("ISPUPostalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ISPUPostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookupRequest>ListType"));
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
        elemField.setFieldName("productGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ProductGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ProductPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
        elemField.setFieldName("sort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Sort"));
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
