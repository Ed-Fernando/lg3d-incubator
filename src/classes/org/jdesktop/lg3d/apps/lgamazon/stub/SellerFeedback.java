/**
 * SellerFeedback.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerFeedback  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback[] feedback;

    public SellerFeedback() {
    }

    public SellerFeedback(
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback[] feedback) {
           this.feedback = feedback;
    }


    /**
     * Gets the feedback value for this SellerFeedback.
     * 
     * @return feedback
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback[] getFeedback() {
        return feedback;
    }


    /**
     * Sets the feedback value for this SellerFeedback.
     * 
     * @param feedback
     */
    public void setFeedback(org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback[] feedback) {
        this.feedback = feedback;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback getFeedback(int i) {
        return this.feedback[i];
    }

    public void setFeedback(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback _value) {
        this.feedback[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerFeedback)) return false;
        SellerFeedback other = (SellerFeedback) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.feedback==null && other.getFeedback()==null) || 
             (this.feedback!=null &&
              java.util.Arrays.equals(this.feedback, other.getFeedback())));
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
        if (getFeedback() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFeedback());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFeedback(), i);
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
        new org.apache.axis.description.TypeDesc(SellerFeedback.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerFeedback"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feedback");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Feedback"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SellerFeedback>Feedback"));
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
