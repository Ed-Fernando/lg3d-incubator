/**
 * SimilarViewedProducts.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SimilarViewedProducts  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct[] similarViewedProduct;

    public SimilarViewedProducts() {
    }

    public SimilarViewedProducts(
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct[] similarViewedProduct) {
           this.similarViewedProduct = similarViewedProduct;
    }


    /**
     * Gets the similarViewedProduct value for this SimilarViewedProducts.
     * 
     * @return similarViewedProduct
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct[] getSimilarViewedProduct() {
        return similarViewedProduct;
    }


    /**
     * Sets the similarViewedProduct value for this SimilarViewedProducts.
     * 
     * @param similarViewedProduct
     */
    public void setSimilarViewedProduct(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct[] similarViewedProduct) {
        this.similarViewedProduct = similarViewedProduct;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct getSimilarViewedProduct(int i) {
        return this.similarViewedProduct[i];
    }

    public void setSimilarViewedProduct(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct _value) {
        this.similarViewedProduct[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SimilarViewedProducts)) return false;
        SimilarViewedProducts other = (SimilarViewedProducts) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.similarViewedProduct==null && other.getSimilarViewedProduct()==null) || 
             (this.similarViewedProduct!=null &&
              java.util.Arrays.equals(this.similarViewedProduct, other.getSimilarViewedProduct())));
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
        if (getSimilarViewedProduct() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSimilarViewedProduct());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSimilarViewedProduct(), i);
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
        new org.apache.axis.description.TypeDesc(SimilarViewedProducts.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarViewedProducts"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarViewedProduct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarViewedProduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SimilarViewedProducts>SimilarViewedProduct"));
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
