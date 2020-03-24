/**
 * SearchInsideExcerpt.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SearchInsideExcerpt  implements java.io.Serializable {
    private java.lang.String checksum;
    private java.lang.String pageType;
    private java.lang.String pageNumber;
    private java.lang.String sequenceNumber;
    private java.lang.String text;

    public SearchInsideExcerpt() {
    }

    public SearchInsideExcerpt(
           java.lang.String checksum,
           java.lang.String pageType,
           java.lang.String pageNumber,
           java.lang.String sequenceNumber,
           java.lang.String text) {
           this.checksum = checksum;
           this.pageType = pageType;
           this.pageNumber = pageNumber;
           this.sequenceNumber = sequenceNumber;
           this.text = text;
    }


    /**
     * Gets the checksum value for this SearchInsideExcerpt.
     * 
     * @return checksum
     */
    public java.lang.String getChecksum() {
        return checksum;
    }


    /**
     * Sets the checksum value for this SearchInsideExcerpt.
     * 
     * @param checksum
     */
    public void setChecksum(java.lang.String checksum) {
        this.checksum = checksum;
    }


    /**
     * Gets the pageType value for this SearchInsideExcerpt.
     * 
     * @return pageType
     */
    public java.lang.String getPageType() {
        return pageType;
    }


    /**
     * Sets the pageType value for this SearchInsideExcerpt.
     * 
     * @param pageType
     */
    public void setPageType(java.lang.String pageType) {
        this.pageType = pageType;
    }


    /**
     * Gets the pageNumber value for this SearchInsideExcerpt.
     * 
     * @return pageNumber
     */
    public java.lang.String getPageNumber() {
        return pageNumber;
    }


    /**
     * Sets the pageNumber value for this SearchInsideExcerpt.
     * 
     * @param pageNumber
     */
    public void setPageNumber(java.lang.String pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * Gets the sequenceNumber value for this SearchInsideExcerpt.
     * 
     * @return sequenceNumber
     */
    public java.lang.String getSequenceNumber() {
        return sequenceNumber;
    }


    /**
     * Sets the sequenceNumber value for this SearchInsideExcerpt.
     * 
     * @param sequenceNumber
     */
    public void setSequenceNumber(java.lang.String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }


    /**
     * Gets the text value for this SearchInsideExcerpt.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this SearchInsideExcerpt.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchInsideExcerpt)) return false;
        SearchInsideExcerpt other = (SearchInsideExcerpt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.checksum==null && other.getChecksum()==null) || 
             (this.checksum!=null &&
              this.checksum.equals(other.getChecksum()))) &&
            ((this.pageType==null && other.getPageType()==null) || 
             (this.pageType!=null &&
              this.pageType.equals(other.getPageType()))) &&
            ((this.pageNumber==null && other.getPageNumber()==null) || 
             (this.pageNumber!=null &&
              this.pageNumber.equals(other.getPageNumber()))) &&
            ((this.sequenceNumber==null && other.getSequenceNumber()==null) || 
             (this.sequenceNumber!=null &&
              this.sequenceNumber.equals(other.getSequenceNumber()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText())));
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
        if (getChecksum() != null) {
            _hashCode += getChecksum().hashCode();
        }
        if (getPageType() != null) {
            _hashCode += getPageType().hashCode();
        }
        if (getPageNumber() != null) {
            _hashCode += getPageNumber().hashCode();
        }
        if (getSequenceNumber() != null) {
            _hashCode += getSequenceNumber().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchInsideExcerpt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SearchInside>Excerpt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checksum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Checksum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PageNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SequenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Text"));
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
