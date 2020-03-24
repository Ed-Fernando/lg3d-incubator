/**
 * SimilarProducts.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SimilarProducts  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct[] similarProduct;

    public SimilarProducts() {
    }

    public SimilarProducts(
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct[] similarProduct) {
           this.similarProduct = similarProduct;
    }


    /**
     * Gets the similarProduct value for this SimilarProducts.
     * 
     * @return similarProduct
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct[] getSimilarProduct() {
        return similarProduct;
    }


    /**
     * Sets the similarProduct value for this SimilarProducts.
     * 
     * @param similarProduct
     */
    public void setSimilarProduct(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct[] similarProduct) {
        this.similarProduct = similarProduct;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct getSimilarProduct(int i) {
        return this.similarProduct[i];
    }

    public void setSimilarProduct(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct _value) {
        this.similarProduct[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SimilarProducts)) return false;
        SimilarProducts other = (SimilarProducts) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.similarProduct==null && other.getSimilarProduct()==null) || 
             (this.similarProduct!=null &&
              java.util.Arrays.equals(this.similarProduct, other.getSimilarProduct())));
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
        if (getSimilarProduct() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSimilarProduct());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSimilarProduct(), i);
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
        new org.apache.axis.description.TypeDesc(SimilarProducts.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarProducts"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarProduct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarProduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SimilarProducts>SimilarProduct"));
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
