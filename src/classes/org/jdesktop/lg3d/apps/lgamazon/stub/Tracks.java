/**
 * Tracks.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Tracks  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc[] disc;

    public Tracks() {
    }

    public Tracks(
           org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc[] disc) {
           this.disc = disc;
    }


    /**
     * Gets the disc value for this Tracks.
     * 
     * @return disc
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc[] getDisc() {
        return disc;
    }


    /**
     * Sets the disc value for this Tracks.
     * 
     * @param disc
     */
    public void setDisc(org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc[] disc) {
        this.disc = disc;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc getDisc(int i) {
        return this.disc[i];
    }

    public void setDisc(int i, org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc _value) {
        this.disc[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tracks)) return false;
        Tracks other = (Tracks) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.disc==null && other.getDisc()==null) || 
             (this.disc!=null &&
              java.util.Arrays.equals(this.disc, other.getDisc())));
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
        if (getDisc() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDisc());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDisc(), i);
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
        new org.apache.axis.description.TypeDesc(Tracks.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Tracks"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Disc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Tracks>Disc"));
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
