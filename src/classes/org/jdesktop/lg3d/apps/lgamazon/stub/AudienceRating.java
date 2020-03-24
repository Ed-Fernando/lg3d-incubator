/**
 * AudienceRating.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class AudienceRating implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    protected AudienceRating(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "G";
    public static final java.lang.String _value2 = "PG";
    public static final java.lang.String _value3 = "PG-13";
    public static final java.lang.String _value4 = "R";
    public static final java.lang.String _value5 = "NC-17";
    public static final java.lang.String _value6 = "NR";
    public static final java.lang.String _value7 = "Unrated";
    public static final java.lang.String _value8 = "6";
    public static final java.lang.String _value9 = "12";
    public static final java.lang.String _value10 = "16";
    public static final java.lang.String _value11 = "18";
    public static final java.lang.String _value12 = "FamilyViewing";
    public static final AudienceRating value1 = new AudienceRating(_value1);
    public static final AudienceRating value2 = new AudienceRating(_value2);
    public static final AudienceRating value3 = new AudienceRating(_value3);
    public static final AudienceRating value4 = new AudienceRating(_value4);
    public static final AudienceRating value5 = new AudienceRating(_value5);
    public static final AudienceRating value6 = new AudienceRating(_value6);
    public static final AudienceRating value7 = new AudienceRating(_value7);
    public static final AudienceRating value8 = new AudienceRating(_value8);
    public static final AudienceRating value9 = new AudienceRating(_value9);
    public static final AudienceRating value10 = new AudienceRating(_value10);
    public static final AudienceRating value11 = new AudienceRating(_value11);
    public static final AudienceRating value12 = new AudienceRating(_value12);
    public java.lang.String getValue() { return _value_;}
    public static AudienceRating fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AudienceRating enumeration = (AudienceRating)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AudienceRating fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AudienceRating.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">AudienceRating"));
    }
    /**
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
