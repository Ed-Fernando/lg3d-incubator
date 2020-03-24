/**
 * TracksDisc.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TracksDisc  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack[] track;
    private org.apache.axis.types.PositiveInteger number;  // attribute

    public TracksDisc() {
    }

    public TracksDisc(
           org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack[] track,
           org.apache.axis.types.PositiveInteger number) {
           this.track = track;
           this.number = number;
    }


    /**
     * Gets the track value for this TracksDisc.
     * 
     * @return track
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack[] getTrack() {
        return track;
    }


    /**
     * Sets the track value for this TracksDisc.
     * 
     * @param track
     */
    public void setTrack(org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack[] track) {
        this.track = track;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack getTrack(int i) {
        return this.track[i];
    }

    public void setTrack(int i, org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack _value) {
        this.track[i] = _value;
    }


    /**
     * Gets the number value for this TracksDisc.
     * 
     * @return number
     */
    public org.apache.axis.types.PositiveInteger getNumber() {
        return number;
    }


    /**
     * Sets the number value for this TracksDisc.
     * 
     * @param number
     */
    public void setNumber(org.apache.axis.types.PositiveInteger number) {
        this.number = number;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TracksDisc)) return false;
        TracksDisc other = (TracksDisc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.track==null && other.getTrack()==null) || 
             (this.track!=null &&
              java.util.Arrays.equals(this.track, other.getTrack()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber())));
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
        if (getTrack() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrack());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrack(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TracksDisc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Tracks>Disc"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("number");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Number"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("track");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Track"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Tracks>Disc>Track"));
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
