/**
 * Help.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Help  implements java.io.Serializable {
    private java.lang.String marketplaceDomain;
    private java.lang.String AWSAccessKeyId;
    private java.lang.String subscriptionId;
    private java.lang.String associateTag;
    private java.lang.String validate;
    private org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest shared;
    private org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest[] request;

    public Help() {
    }

    public Help(
           java.lang.String marketplaceDomain,
           java.lang.String AWSAccessKeyId,
           java.lang.String subscriptionId,
           java.lang.String associateTag,
           java.lang.String validate,
           org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest shared,
           org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest[] request) {
           this.marketplaceDomain = marketplaceDomain;
           this.AWSAccessKeyId = AWSAccessKeyId;
           this.subscriptionId = subscriptionId;
           this.associateTag = associateTag;
           this.validate = validate;
           this.shared = shared;
           this.request = request;
    }


    /**
     * Gets the marketplaceDomain value for this Help.
     * 
     * @return marketplaceDomain
     */
    public java.lang.String getMarketplaceDomain() {
        return marketplaceDomain;
    }


    /**
     * Sets the marketplaceDomain value for this Help.
     * 
     * @param marketplaceDomain
     */
    public void setMarketplaceDomain(java.lang.String marketplaceDomain) {
        this.marketplaceDomain = marketplaceDomain;
    }


    /**
     * Gets the AWSAccessKeyId value for this Help.
     * 
     * @return AWSAccessKeyId
     */
    public java.lang.String getAWSAccessKeyId() {
        return AWSAccessKeyId;
    }


    /**
     * Sets the AWSAccessKeyId value for this Help.
     * 
     * @param AWSAccessKeyId
     */
    public void setAWSAccessKeyId(java.lang.String AWSAccessKeyId) {
        this.AWSAccessKeyId = AWSAccessKeyId;
    }


    /**
     * Gets the subscriptionId value for this Help.
     * 
     * @return subscriptionId
     */
    public java.lang.String getSubscriptionId() {
        return subscriptionId;
    }


    /**
     * Sets the subscriptionId value for this Help.
     * 
     * @param subscriptionId
     */
    public void setSubscriptionId(java.lang.String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }


    /**
     * Gets the associateTag value for this Help.
     * 
     * @return associateTag
     */
    public java.lang.String getAssociateTag() {
        return associateTag;
    }


    /**
     * Sets the associateTag value for this Help.
     * 
     * @param associateTag
     */
    public void setAssociateTag(java.lang.String associateTag) {
        this.associateTag = associateTag;
    }


    /**
     * Gets the validate value for this Help.
     * 
     * @return validate
     */
    public java.lang.String getValidate() {
        return validate;
    }


    /**
     * Sets the validate value for this Help.
     * 
     * @param validate
     */
    public void setValidate(java.lang.String validate) {
        this.validate = validate;
    }


    /**
     * Gets the shared value for this Help.
     * 
     * @return shared
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest getShared() {
        return shared;
    }


    /**
     * Sets the shared value for this Help.
     * 
     * @param shared
     */
    public void setShared(org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest shared) {
        this.shared = shared;
    }


    /**
     * Gets the request value for this Help.
     * 
     * @return request
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest[] getRequest() {
        return request;
    }


    /**
     * Sets the request value for this Help.
     * 
     * @param request
     */
    public void setRequest(org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest[] request) {
        this.request = request;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest getRequest(int i) {
        return this.request[i];
    }

    public void setRequest(int i, org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest _value) {
        this.request[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Help)) return false;
        Help other = (Help) obj;
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
        new org.apache.axis.description.TypeDesc(Help.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Help"));
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
        elemField.setFieldName("shared");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Shared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpRequest"));
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
