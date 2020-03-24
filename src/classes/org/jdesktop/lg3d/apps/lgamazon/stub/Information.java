/**
 * Information.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Information  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Request request;
    private org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation[] operationInformation;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation[] responseGroupInformation;

    public Information() {
    }

    public Information(
           org.jdesktop.lg3d.apps.lgamazon.stub.Request request,
           org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation[] operationInformation,
           org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation[] responseGroupInformation) {
           this.request = request;
           this.operationInformation = operationInformation;
           this.responseGroupInformation = responseGroupInformation;
    }


    /**
     * Gets the request value for this Information.
     * 
     * @return request
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Request getRequest() {
        return request;
    }


    /**
     * Sets the request value for this Information.
     * 
     * @param request
     */
    public void setRequest(org.jdesktop.lg3d.apps.lgamazon.stub.Request request) {
        this.request = request;
    }


    /**
     * Gets the operationInformation value for this Information.
     * 
     * @return operationInformation
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation[] getOperationInformation() {
        return operationInformation;
    }


    /**
     * Sets the operationInformation value for this Information.
     * 
     * @param operationInformation
     */
    public void setOperationInformation(org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation[] operationInformation) {
        this.operationInformation = operationInformation;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation getOperationInformation(int i) {
        return this.operationInformation[i];
    }

    public void setOperationInformation(int i, org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation _value) {
        this.operationInformation[i] = _value;
    }


    /**
     * Gets the responseGroupInformation value for this Information.
     * 
     * @return responseGroupInformation
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation[] getResponseGroupInformation() {
        return responseGroupInformation;
    }


    /**
     * Sets the responseGroupInformation value for this Information.
     * 
     * @param responseGroupInformation
     */
    public void setResponseGroupInformation(org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation[] responseGroupInformation) {
        this.responseGroupInformation = responseGroupInformation;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation getResponseGroupInformation(int i) {
        return this.responseGroupInformation[i];
    }

    public void setResponseGroupInformation(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation _value) {
        this.responseGroupInformation[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Information)) return false;
        Information other = (Information) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              this.request.equals(other.getRequest()))) &&
            ((this.operationInformation==null && other.getOperationInformation()==null) || 
             (this.operationInformation!=null &&
              java.util.Arrays.equals(this.operationInformation, other.getOperationInformation()))) &&
            ((this.responseGroupInformation==null && other.getResponseGroupInformation()==null) || 
             (this.responseGroupInformation!=null &&
              java.util.Arrays.equals(this.responseGroupInformation, other.getResponseGroupInformation())));
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
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        if (getOperationInformation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperationInformation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperationInformation(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResponseGroupInformation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResponseGroupInformation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResponseGroupInformation(), i);
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
        new org.apache.axis.description.TypeDesc(Information.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Information"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationInformation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationInformation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationInformation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseGroupInformation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ResponseGroupInformation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ResponseGroupInformation"));
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
