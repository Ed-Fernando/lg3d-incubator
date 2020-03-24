/**
 * ListListType.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ListListType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    protected ListListType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _WishList = "WishList";
    public static final java.lang.String _WeddingRegistry = "WeddingRegistry";
    public static final java.lang.String _BabyRegistry = "BabyRegistry";
    public static final java.lang.String _Listmania = "Listmania";
    public static final ListListType WishList = new ListListType(_WishList);
    public static final ListListType WeddingRegistry = new ListListType(_WeddingRegistry);
    public static final ListListType BabyRegistry = new ListListType(_BabyRegistry);
    public static final ListListType Listmania = new ListListType(_Listmania);
    public java.lang.String getValue() { return _value_;}
    public static ListListType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ListListType enumeration = (ListListType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ListListType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListListType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>List>ListType"));
    }
    /**
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
