/**
 * Bin.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Bin  implements java.io.Serializable {
    private java.lang.String binName;
    private org.apache.axis.types.PositiveInteger binItemCount;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter[] binParameter;

    public Bin() {
    }

    public Bin(
           java.lang.String binName,
           org.apache.axis.types.PositiveInteger binItemCount,
           org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter[] binParameter) {
           this.binName = binName;
           this.binItemCount = binItemCount;
           this.binParameter = binParameter;
    }


    /**
     * Gets the binName value for this Bin.
     * 
     * @return binName
     */
    public java.lang.String getBinName() {
        return binName;
    }


    /**
     * Sets the binName value for this Bin.
     * 
     * @param binName
     */
    public void setBinName(java.lang.String binName) {
        this.binName = binName;
    }


    /**
     * Gets the binItemCount value for this Bin.
     * 
     * @return binItemCount
     */
    public org.apache.axis.types.PositiveInteger getBinItemCount() {
        return binItemCount;
    }


    /**
     * Sets the binItemCount value for this Bin.
     * 
     * @param binItemCount
     */
    public void setBinItemCount(org.apache.axis.types.PositiveInteger binItemCount) {
        this.binItemCount = binItemCount;
    }


    /**
     * Gets the binParameter value for this Bin.
     * 
     * @return binParameter
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter[] getBinParameter() {
        return binParameter;
    }


    /**
     * Sets the binParameter value for this Bin.
     * 
     * @param binParameter
     */
    public void setBinParameter(org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter[] binParameter) {
        this.binParameter = binParameter;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter getBinParameter(int i) {
        return this.binParameter[i];
    }

    public void setBinParameter(int i, org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter _value) {
        this.binParameter[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Bin)) return false;
        Bin other = (Bin) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.binName==null && other.getBinName()==null) || 
             (this.binName!=null &&
              this.binName.equals(other.getBinName()))) &&
            ((this.binItemCount==null && other.getBinItemCount()==null) || 
             (this.binItemCount!=null &&
              this.binItemCount.equals(other.getBinItemCount()))) &&
            ((this.binParameter==null && other.getBinParameter()==null) || 
             (this.binParameter!=null &&
              java.util.Arrays.equals(this.binParameter, other.getBinParameter())));
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
        if (getBinName() != null) {
            _hashCode += getBinName().hashCode();
        }
        if (getBinItemCount() != null) {
            _hashCode += getBinItemCount().hashCode();
        }
        if (getBinParameter() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBinParameter());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBinParameter(), i);
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
        new org.apache.axis.description.TypeDesc(Bin.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Bin"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BinName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binItemCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BinItemCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("binParameter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BinParameter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Bin>BinParameter"));
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
