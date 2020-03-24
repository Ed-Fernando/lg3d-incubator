/**
 * CartCreate.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class CartCreate  implements java.io.Serializable {
    private java.lang.String marketplaceDomain;
    private java.lang.String AWSAccessKeyId;
    private java.lang.String subscriptionId;
    private java.lang.String associateTag;
    private java.lang.String validate;
    private java.lang.String XMLEscaping;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest shared;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest[] request;

    public CartCreate() {
    }

    public CartCreate(
           java.lang.String marketplaceDomain,
           java.lang.String AWSAccessKeyId,
           java.lang.String subscriptionId,
           java.lang.String associateTag,
           java.lang.String validate,
           java.lang.String XMLEscaping,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest shared,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest[] request) {
           this.marketplaceDomain = marketplaceDomain;
           this.AWSAccessKeyId = AWSAccessKeyId;
           this.subscriptionId = subscriptionId;
           this.associateTag = associateTag;
           this.validate = validate;
           this.XMLEscaping = XMLEscaping;
           this.shared = shared;
           this.request = request;
    }


    /**
     * Gets the marketplaceDomain value for this CartCreate.
     * 
     * @return marketplaceDomain
     */
    public java.lang.String getMarketplaceDomain() {
        return marketplaceDomain;
    }


    /**
     * Sets the marketplaceDomain value for this CartCreate.
     * 
     * @param marketplaceDomain
     */
    public void setMarketplaceDomain(java.lang.String marketplaceDomain) {
        this.marketplaceDomain = marketplaceDomain;
    }


    /**
     * Gets the AWSAccessKeyId value for this CartCreate.
     * 
     * @return AWSAccessKeyId
     */
    public java.lang.String getAWSAccessKeyId() {
        return AWSAccessKeyId;
    }


    /**
     * Sets the AWSAccessKeyId value for this CartCreate.
     * 
     * @param AWSAccessKeyId
     */
    public void setAWSAccessKeyId(java.lang.String AWSAccessKeyId) {
        this.AWSAccessKeyId = AWSAccessKeyId;
    }


    /**
     * Gets the subscriptionId value for this CartCreate.
     * 
     * @return subscriptionId
     */
    public java.lang.String getSubscriptionId() {
        return subscriptionId;
    }


    /**
     * Sets the subscriptionId value for this CartCreate.
     * 
     * @param subscriptionId
     */
    public void setSubscriptionId(java.lang.String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }


    /**
     * Gets the associateTag value for this CartCreate.
     * 
     * @return associateTag
     */
    public java.lang.String getAssociateTag() {
        return associateTag;
    }


    /**
     * Sets the associateTag value for this CartCreate.
     * 
     * @param associateTag
     */
    public void setAssociateTag(java.lang.String associateTag) {
        this.associateTag = associateTag;
    }


    /**
     * Gets the validate value for this CartCreate.
     * 
     * @return validate
     */
    public java.lang.String getValidate() {
        return validate;
    }


    /**
     * Sets the validate value for this CartCreate.
     * 
     * @param validate
     */
    public void setValidate(java.lang.String validate) {
        this.validate = validate;
    }


    /**
     * Gets the XMLEscaping value for this CartCreate.
     * 
     * @return XMLEscaping
     */
    public java.lang.String getXMLEscaping() {
        return XMLEscaping;
    }


    /**
     * Sets the XMLEscaping value for this CartCreate.
     * 
     * @param XMLEscaping
     */
    public void setXMLEscaping(java.lang.String XMLEscaping) {
        this.XMLEscaping = XMLEscaping;
    }


    /**
     * Gets the shared value for this CartCreate.
     * 
     * @return shared
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest getShared() {
        return shared;
    }


    /**
     * Sets the shared value for this CartCreate.
     * 
     * @param shared
     */
    public void setShared(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest shared) {
        this.shared = shared;
    }


    /**
     * Gets the request value for this CartCreate.
     * 
     * @return request
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest[] getRequest() {
        return request;
    }


    /**
     * Sets the request value for this CartCreate.
     * 
     * @param request
     */
    public void setRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest[] request) {
        this.request = request;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest getRequest(int i) {
        return this.request[i];
    }

    public void setRequest(int i, org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest _value) {
        this.request[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CartCreate)) return false;
        CartCreate other = (CartCreate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.marketplaceDomain==null && other.getMarketplaceDomain()==null) || 
             (this.marketplaceDomain!=null &&
              this.marketplaceDomain.equals(other.getMarketplaceDomain()))) &&
            ((this.AWSAccessKeyId==null && other.getAWSAccessKeyId()==null) || 
             (this.AWSAccessKeyId!=null &&
              this.AWSAccessKeyId.equals(other.getAWSAccessKeyId()))) &&
            ((this.subscriptionId==null && other.getSubscriptionId()==null) || 
             (this.subscriptionId!=null &&
              this.subscriptionId.equals(other.getSubscriptionId()))) &&
            ((this.associateTag==null && other.getAssociateTag()==null) || 
             (this.associateTag!=null &&
              this.associateTag.equals(other.getAssociateTag()))) &&
            ((this.validate==null && other.getValidate()==null) || 
             (this.validate!=null &&
              this.validate.equals(other.getValidate()))) &&
            ((this.XMLEscaping==null && other.getXMLEscaping()==null) || 
             (this.XMLEscaping!=null &&
              this.XMLEscaping.equals(other.getXMLEscaping()))) &&
            ((this.shared==null && other.getShared()==null) || 
             (this.shared!=null &&
              this.shared.equals(other.getShared()))) &&
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              java.util.Arrays.equals(this.request, other.getRequest())));
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
        if (getMarketplaceDomain() != null) {
            _hashCode += getMarketplaceDomain().hashCode();
        }
        if (getAWSAccessKeyId() != null) {
            _hashCode += getAWSAccessKeyId().hashCode();
        }
        if (getSubscriptionId() != null) {
            _hashCode += getSubscriptionId().hashCode();
        }
        if (getAssociateTag() != null) {
            _hashCode += getAssociateTag().hashCode();
        }
        if (getValidate() != null) {
            _hashCode += getValidate().hashCode();
        }
        if (getXMLEscaping() != null) {
            _hashCode += getXMLEscaping().hashCode();
        }
        if (getShared() != null) {
            _hashCode += getShared().hashCode();
        }
        if (getRequest() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequest());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequest(), i);
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
        new org.apache.axis.description.TypeDesc(CartCreate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("marketplaceDomain");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MarketplaceDomain"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AWSAccessKeyId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AWSAccessKeyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SubscriptionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("associateTag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AssociateTag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Validate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XMLEscaping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "XMLEscaping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shared");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Shared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateRequest"));
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
