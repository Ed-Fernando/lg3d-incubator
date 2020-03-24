/**
 * TopSellers.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TopSellers  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller[] topSeller;

    public TopSellers() {
    }

    public TopSellers(
           org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller[] topSeller) {
           this.topSeller = topSeller;
    }


    /**
     * Gets the topSeller value for this TopSellers.
     * 
     * @return topSeller
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller[] getTopSeller() {
        return topSeller;
    }


    /**
     * Sets the topSeller value for this TopSellers.
     * 
     * @param topSeller
     */
    public void setTopSeller(org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller[] topSeller) {
        this.topSeller = topSeller;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller getTopSeller(int i) {
        return this.topSeller[i];
    }

    public void setTopSeller(int i, org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller _value) {
        this.topSeller[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopSellers)) return false;
        TopSellers other = (TopSellers) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topSeller==null && other.getTopSeller()==null) || 
             (this.topSeller!=null &&
              java.util.Arrays.equals(this.topSeller, other.getTopSeller())));
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
        if (getTopSeller() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTopSeller());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTopSeller(), i);
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
        new org.apache.axis.description.TypeDesc(TopSellers.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TopSellers"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topSeller");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TopSeller"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>TopSellers>TopSeller"));
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
