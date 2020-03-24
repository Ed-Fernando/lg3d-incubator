/**
 * Arguments.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Arguments  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument[] argument;

    public Arguments() {
    }

    public Arguments(
           org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument[] argument) {
           this.argument = argument;
    }


    /**
     * Gets the argument value for this Arguments.
     * 
     * @return argument
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument[] getArgument() {
        return argument;
    }


    /**
     * Sets the argument value for this Arguments.
     * 
     * @param argument
     */
    public void setArgument(org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument[] argument) {
        this.argument = argument;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument getArgument(int i) {
        return this.argument[i];
    }

    public void setArgument(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument _value) {
        this.argument[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Arguments)) return false;
        Arguments other = (Arguments) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.argument==null && other.getArgument()==null) || 
             (this.argument!=null &&
              java.util.Arrays.equals(this.argument, other.getArgument())));
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
        if (getArgument() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArgument());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArgument(), i);
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
        new org.apache.axis.description.TypeDesc(Arguments.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Arguments"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("argument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Argument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Arguments>Argument"));
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
