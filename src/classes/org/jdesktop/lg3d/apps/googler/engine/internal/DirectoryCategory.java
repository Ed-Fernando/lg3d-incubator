/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************
 *
 * This file was originally  auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 29, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package org.jdesktop.lg3d.apps.googler.engine.internal;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class DirectoryCategory  implements Serializable {
    private String fullViewableName;
    private String specialEncoding;

    public DirectoryCategory() {
    }

    public DirectoryCategory(
           String fullViewableName,
           String specialEncoding) {
           this.fullViewableName = fullViewableName;
           this.specialEncoding = specialEncoding;
    }


    /**
     * Gets the fullViewableName value for this DirectoryCategory.
     * 
     * @return fullViewableName
     */
    public String getFullViewableName() {
        return fullViewableName;
    }


    /**
     * Sets the fullViewableName value for this DirectoryCategory.
     * 
     * @param fullViewableName
     */
    public void setFullViewableName(String fullViewableName) {
        this.fullViewableName = fullViewableName;
    }


    /**
     * Gets the specialEncoding value for this DirectoryCategory.
     * 
     * @return specialEncoding
     */
    public String getSpecialEncoding() {
        return specialEncoding;
    }


    /**
     * Sets the specialEncoding value for this DirectoryCategory.
     * 
     * @param specialEncoding
     */
    public void setSpecialEncoding(String specialEncoding) {
        this.specialEncoding = specialEncoding;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof DirectoryCategory)) return false;
        DirectoryCategory other = (DirectoryCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fullViewableName==null && other.getFullViewableName()==null) || 
             (this.fullViewableName!=null &&
              this.fullViewableName.equals(other.getFullViewableName()))) &&
            ((this.specialEncoding==null && other.getSpecialEncoding()==null) || 
             (this.specialEncoding!=null &&
              this.specialEncoding.equals(other.getSpecialEncoding())));
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
        if (getFullViewableName() != null) {
            _hashCode += getFullViewableName().hashCode();
        }
        if (getSpecialEncoding() != null) {
            _hashCode += getSpecialEncoding().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc =
        new TypeDesc(DirectoryCategory.class, true);

    static {
        typeDesc.setXmlType(new QName("urn:GoogleSearch", "DirectoryCategory"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("fullViewableName");
        elemField.setXmlName(new QName("", "fullViewableName"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("specialEncoding");
        elemField.setXmlName(new QName("", "specialEncoding"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType, 
           Class _javaType,  
           QName _xmlType) {
        return 
          new  BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType, 
           Class _javaType,  
           QName _xmlType) {
        return 
          new  BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
