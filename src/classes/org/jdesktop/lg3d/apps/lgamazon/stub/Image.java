/**
 * Image.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Image  implements java.io.Serializable {
    private java.lang.String URL;
    private org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits height;
    private org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits width;
    private java.lang.String isVerified;

    public Image() {
    }

    public Image(
           java.lang.String URL,
           org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits height,
           org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits width,
           java.lang.String isVerified) {
           this.URL = URL;
           this.height = height;
           this.width = width;
           this.isVerified = isVerified;
    }


    /**
     * Gets the URL value for this Image.
     * 
     * @return URL
     */
    public java.lang.String getURL() {
        return URL;
    }


    /**
     * Sets the URL value for this Image.
     * 
     * @param URL
     */
    public void setURL(java.lang.String URL) {
        this.URL = URL;
    }


    /**
     * Gets the height value for this Image.
     * 
     * @return height
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits getHeight() {
        return height;
    }


    /**
     * Sets the height value for this Image.
     * 
     * @param height
     */
    public void setHeight(org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits height) {
        this.height = height;
    }


    /**
     * Gets the width value for this Image.
     * 
     * @return width
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits getWidth() {
        return width;
    }


    /**
     * Sets the width value for this Image.
     * 
     * @param width
     */
    public void setWidth(org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits width) {
        this.width = width;
    }


    /**
     * Gets the isVerified value for this Image.
     * 
     * @return isVerified
     */
    public java.lang.String getIsVerified() {
        return isVerified;
    }


    /**
     * Sets the isVerified value for this Image.
     * 
     * @param isVerified
     */
    public void setIsVerified(java.lang.String isVerified) {
        this.isVerified = isVerified;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Image)) return false;
        Image other = (Image) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.URL==null && other.getURL()==null) || 
             (this.URL!=null &&
              this.URL.equals(other.getURL()))) &&
            ((this.height==null && other.getHeight()==null) || 
             (this.height!=null &&
              this.height.equals(other.getHeight()))) &&
            ((this.width==null && other.getWidth()==null) || 
             (this.width!=null &&
              this.width.equals(other.getWidth()))) &&
            ((this.isVerified==null && other.getIsVerified()==null) || 
             (this.isVerified!=null &&
              this.isVerified.equals(other.getIsVerified())));
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
        if (getURL() != null) {
            _hashCode += getURL().hashCode();
        }
        if (getHeight() != null) {
            _hashCode += getHeight().hashCode();
        }
        if (getWidth() != null) {
            _hashCode += getWidth().hashCode();
        }
        if (getIsVerified() != null) {
            _hashCode += getIsVerified().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Image.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("URL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "URL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("height");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Height"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DecimalWithUnits"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("width");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Width"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DecimalWithUnits"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isVerified");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "IsVerified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
