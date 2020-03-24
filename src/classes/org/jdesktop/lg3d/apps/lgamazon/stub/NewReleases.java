/**
 * NewReleases.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class NewReleases  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease[] newRelease;

    public NewReleases() {
    }

    public NewReleases(
           org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease[] newRelease) {
           this.newRelease = newRelease;
    }


    /**
     * Gets the newRelease value for this NewReleases.
     * 
     * @return newRelease
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease[] getNewRelease() {
        return newRelease;
    }


    /**
     * Sets the newRelease value for this NewReleases.
     * 
     * @param newRelease
     */
    public void setNewRelease(org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease[] newRelease) {
        this.newRelease = newRelease;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease getNewRelease(int i) {
        return this.newRelease[i];
    }

    public void setNewRelease(int i, org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease _value) {
        this.newRelease[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NewReleases)) return false;
        NewReleases other = (NewReleases) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.newRelease==null && other.getNewRelease()==null) || 
             (this.newRelease!=null &&
              java.util.Arrays.equals(this.newRelease, other.getNewRelease())));
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
        if (getNewRelease() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNewRelease());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNewRelease(), i);
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
        new org.apache.axis.description.TypeDesc(NewReleases.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">NewReleases"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newRelease");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "NewRelease"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>NewReleases>NewRelease"));
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
