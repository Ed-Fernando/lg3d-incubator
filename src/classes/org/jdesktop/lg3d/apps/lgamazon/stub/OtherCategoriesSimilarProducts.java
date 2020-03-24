/**
 * OtherCategoriesSimilarProducts.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class OtherCategoriesSimilarProducts  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct[] otherCategoriesSimilarProduct;

    public OtherCategoriesSimilarProducts() {
    }

    public OtherCategoriesSimilarProducts(
           org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct[] otherCategoriesSimilarProduct) {
           this.otherCategoriesSimilarProduct = otherCategoriesSimilarProduct;
    }


    /**
     * Gets the otherCategoriesSimilarProduct value for this OtherCategoriesSimilarProducts.
     * 
     * @return otherCategoriesSimilarProduct
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct[] getOtherCategoriesSimilarProduct() {
        return otherCategoriesSimilarProduct;
    }


    /**
     * Sets the otherCategoriesSimilarProduct value for this OtherCategoriesSimilarProducts.
     * 
     * @param otherCategoriesSimilarProduct
     */
    public void setOtherCategoriesSimilarProduct(org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct[] otherCategoriesSimilarProduct) {
        this.otherCategoriesSimilarProduct = otherCategoriesSimilarProduct;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct getOtherCategoriesSimilarProduct(int i) {
        return this.otherCategoriesSimilarProduct[i];
    }

    public void setOtherCategoriesSimilarProduct(int i, org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct _value) {
        this.otherCategoriesSimilarProduct[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OtherCategoriesSimilarProducts)) return false;
        OtherCategoriesSimilarProducts other = (OtherCategoriesSimilarProducts) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.otherCategoriesSimilarProduct==null && other.getOtherCategoriesSimilarProduct()==null) || 
             (this.otherCategoriesSimilarProduct!=null &&
              java.util.Arrays.equals(this.otherCategoriesSimilarProduct, other.getOtherCategoriesSimilarProduct())));
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
        if (getOtherCategoriesSimilarProduct() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOtherCategoriesSimilarProduct());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOtherCategoriesSimilarProduct(), i);
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
        new org.apache.axis.description.TypeDesc(OtherCategoriesSimilarProducts.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OtherCategoriesSimilarProducts"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCategoriesSimilarProduct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OtherCategoriesSimilarProduct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OtherCategoriesSimilarProducts>OtherCategoriesSimilarProduct"));
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
