/**
 * ItemImageSets.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ItemImageSets  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet[] imageSet;

    public ItemImageSets() {
    }

    public ItemImageSets(
           org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet[] imageSet) {
           this.imageSet = imageSet;
    }


    /**
     * Gets the imageSet value for this ItemImageSets.
     * 
     * @return imageSet
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet[] getImageSet() {
        return imageSet;
    }


    /**
     * Sets the imageSet value for this ItemImageSets.
     * 
     * @param imageSet
     */
    public void setImageSet(org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet[] imageSet) {
        this.imageSet = imageSet;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet getImageSet(int i) {
        return this.imageSet[i];
    }

    public void setImageSet(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet _value) {
        this.imageSet[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemImageSets)) return false;
        ItemImageSets other = (ItemImageSets) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.imageSet==null && other.getImageSet()==null) || 
             (this.imageSet!=null &&
              java.util.Arrays.equals(this.imageSet, other.getImageSet())));
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
        if (getImageSet() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImageSet());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImageSet(), i);
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
        new org.apache.axis.description.TypeDesc(ItemImageSets.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Item>ImageSets"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageSet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ImageSet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ImageSet"));
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
