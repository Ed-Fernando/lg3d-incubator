/**
 * ItemLookupRequestIdType.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class ItemLookupRequestIdType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    protected ItemLookupRequestIdType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ASIN = "ASIN";
    public static final java.lang.String _UPC = "UPC";
    public static final java.lang.String _SKU = "SKU";
    public static final java.lang.String _EAN = "EAN";
    public static final ItemLookupRequestIdType ASIN = new ItemLookupRequestIdType(_ASIN);
    public static final ItemLookupRequestIdType UPC = new ItemLookupRequestIdType(_UPC);
    public static final ItemLookupRequestIdType SKU = new ItemLookupRequestIdType(_SKU);
    public static final ItemLookupRequestIdType EAN = new ItemLookupRequestIdType(_EAN);
    public java.lang.String getValue() { return _value_;}
    public static ItemLookupRequestIdType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ItemLookupRequestIdType enumeration = (ItemLookupRequestIdType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ItemLookupRequestIdType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ItemLookupRequestIdType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookupRequest>IdType"));
    }
    /**
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
