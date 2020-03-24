/**
 * Condition.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Condition implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    protected Condition(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _All = "All";
    public static final java.lang.String _New = "New";
    public static final java.lang.String _Used = "Used";
    public static final java.lang.String _Collectible = "Collectible";
    public static final java.lang.String _Refurbished = "Refurbished";
    public static final Condition All = new Condition(_All);
    public static final Condition New = new Condition(_New);
    public static final Condition Used = new Condition(_Used);
    public static final Condition Collectible = new Condition(_Collectible);
    public static final Condition Refurbished = new Condition(_Refurbished);
    public java.lang.String getValue() { return _value_;}
    public static Condition fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Condition enumeration = (Condition)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Condition fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Condition.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Condition"));
    }
    /**
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
