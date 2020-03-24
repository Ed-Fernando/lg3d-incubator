/**
 * SearchBinSets.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SearchBinSets  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet[] searchBinSet;

    public SearchBinSets() {
    }

    public SearchBinSets(
           org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet[] searchBinSet) {
           this.searchBinSet = searchBinSet;
    }


    /**
     * Gets the searchBinSet value for this SearchBinSets.
     * 
     * @return searchBinSet
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet[] getSearchBinSet() {
        return searchBinSet;
    }


    /**
     * Sets the searchBinSet value for this SearchBinSets.
     * 
     * @param searchBinSet
     */
    public void setSearchBinSet(org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet[] searchBinSet) {
        this.searchBinSet = searchBinSet;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet getSearchBinSet(int i) {
        return this.searchBinSet[i];
    }

    public void setSearchBinSet(int i, org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet _value) {
        this.searchBinSet[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchBinSets)) return false;
        SearchBinSets other = (SearchBinSets) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.searchBinSet==null && other.getSearchBinSet()==null) || 
             (this.searchBinSet!=null &&
              java.util.Arrays.equals(this.searchBinSet, other.getSearchBinSet())));
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
        if (getSearchBinSet() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchBinSet());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchBinSet(), i);
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
        new org.apache.axis.description.TypeDesc(SearchBinSets.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchBinSets"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchBinSet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchBinSet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchBinSet"));
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
