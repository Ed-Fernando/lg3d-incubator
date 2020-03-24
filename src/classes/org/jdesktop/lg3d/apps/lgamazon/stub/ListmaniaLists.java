/**
 * ListmaniaLists.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ListmaniaLists  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList[] listmaniaList;

    public ListmaniaLists() {
    }

    public ListmaniaLists(
           org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList[] listmaniaList) {
           this.listmaniaList = listmaniaList;
    }


    /**
     * Gets the listmaniaList value for this ListmaniaLists.
     * 
     * @return listmaniaList
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList[] getListmaniaList() {
        return listmaniaList;
    }


    /**
     * Sets the listmaniaList value for this ListmaniaLists.
     * 
     * @param listmaniaList
     */
    public void setListmaniaList(org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList[] listmaniaList) {
        this.listmaniaList = listmaniaList;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList getListmaniaList(int i) {
        return this.listmaniaList[i];
    }

    public void setListmaniaList(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList _value) {
        this.listmaniaList[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListmaniaLists)) return false;
        ListmaniaLists other = (ListmaniaLists) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listmaniaList==null && other.getListmaniaList()==null) || 
             (this.listmaniaList!=null &&
              java.util.Arrays.equals(this.listmaniaList, other.getListmaniaList())));
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
        if (getListmaniaList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListmaniaList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListmaniaList(), i);
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
        new org.apache.axis.description.TypeDesc(ListmaniaLists.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListmaniaLists"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listmaniaList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListmaniaList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ListmaniaLists>ListmaniaList"));
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
