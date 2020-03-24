/**
 * ImageSet.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ImageSet  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image swatchImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage;
    private java.lang.String category;  // attribute

    public ImageSet() {
    }

    public ImageSet(
           org.jdesktop.lg3d.apps.lgamazon.stub.Image swatchImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage,
           java.lang.String category) {
           this.swatchImage = swatchImage;
           this.smallImage = smallImage;
           this.mediumImage = mediumImage;
           this.largeImage = largeImage;
           this.category = category;
    }


    /**
     * Gets the swatchImage value for this ImageSet.
     * 
     * @return swatchImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getSwatchImage() {
        return swatchImage;
    }


    /**
     * Sets the swatchImage value for this ImageSet.
     * 
     * @param swatchImage
     */
    public void setSwatchImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image swatchImage) {
        this.swatchImage = swatchImage;
    }


    /**
     * Gets the smallImage value for this ImageSet.
     * 
     * @return smallImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getSmallImage() {
        return smallImage;
    }


    /**
     * Sets the smallImage value for this ImageSet.
     * 
     * @param smallImage
     */
    public void setSmallImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage) {
        this.smallImage = smallImage;
    }


    /**
     * Gets the mediumImage value for this ImageSet.
     * 
     * @return mediumImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getMediumImage() {
        return mediumImage;
    }


    /**
     * Sets the mediumImage value for this ImageSet.
     * 
     * @param mediumImage
     */
    public void setMediumImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage) {
        this.mediumImage = mediumImage;
    }


    /**
     * Gets the largeImage value for this ImageSet.
     * 
     * @return largeImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getLargeImage() {
        return largeImage;
    }


    /**
     * Sets the largeImage value for this ImageSet.
     * 
     * @param largeImage
     */
    public void setLargeImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage) {
        this.largeImage = largeImage;
    }


    /**
     * Gets the category value for this ImageSet.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this ImageSet.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImageSet)) return false;
        ImageSet other = (ImageSet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.swatchImage==null && other.getSwatchImage()==null) || 
             (this.swatchImage!=null &&
              this.swatchImage.equals(other.getSwatchImage()))) &&
            ((this.smallImage==null && other.getSmallImage()==null) || 
             (this.smallImage!=null &&
              this.smallImage.equals(other.getSmallImage()))) &&
            ((this.mediumImage==null && other.getMediumImage()==null) || 
             (this.mediumImage!=null &&
              this.mediumImage.equals(other.getMediumImage()))) &&
            ((this.largeImage==null && other.getLargeImage()==null) || 
             (this.largeImage!=null &&
              this.largeImage.equals(other.getLargeImage()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory())));
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
        if (getSwatchImage() != null) {
            _hashCode += getSwatchImage().hashCode();
        }
        if (getSmallImage() != null) {
            _hashCode += getSmallImage().hashCode();
        }
        if (getMediumImage() != null) {
            _hashCode += getMediumImage().hashCode();
        }
        if (getLargeImage() != null) {
            _hashCode += getLargeImage().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImageSet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ImageSet"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("category");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Category"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("swatchImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SwatchImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smallImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SmallImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediumImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MediumImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("largeImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LargeImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
