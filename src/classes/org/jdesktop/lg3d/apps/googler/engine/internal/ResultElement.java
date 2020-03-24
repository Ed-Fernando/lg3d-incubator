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
 * This file was originally auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 29, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package org.jdesktop.lg3d.apps.googler.engine.internal;



public class ResultElement  implements java.io.Serializable {
    private String summary;
    private String URL;
    private String snippet;
    private String title;
    private String cachedSize;
    private boolean relatedInformationPresent;
    private String hostName;
    private DirectoryCategory directoryCategory;
    private String directoryTitle;
    private int pagerank;
    
    public ResultElement() {
    }
    
    public ResultElement(
            String summary,
            String URL,
            String snippet,
            String title,
            String cachedSize,
            boolean relatedInformationPresent,
            String hostName,
            DirectoryCategory directoryCategory,
            String directoryTitle) {
        this.summary = summary;
        this.URL = URL;
        this.snippet = snippet;
        this.title = title;
        this.cachedSize = cachedSize;
        this.relatedInformationPresent = relatedInformationPresent;
        this.hostName = hostName;
        this.directoryCategory = directoryCategory;
        this.directoryTitle = directoryTitle;        
    }
    
    
    /**
     * Gets the summary value for this ResultElement.
     *
     * @return summary
     */
    public String getSummary() {
        return summary;
    }
    
    
    /**
     * Sets the summary value for this ResultElement.
     *
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    
    /**
     * Gets the URL value for this ResultElement.
     *
     * @return URL
     */
    public String getURL() {
        return URL;
    }
    
    
    /**
     * Sets the URL value for this ResultElement.
     *
     * @param URL
     */
    public void setURL(String URL) {
        this.URL = URL;
    }
    
    
    /**
     * Gets the snippet value for this ResultElement.
     *
     * @return snippet
     */
    public String getSnippet() {
        return snippet;
    }
    
    
    /**
     * Sets the snippet value for this ResultElement.
     *
     * @param snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
    
    
    /**
     * Gets the title value for this ResultElement.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    
    /**
     * Sets the title value for this ResultElement.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    /**
     * Gets the cachedSize value for this ResultElement.
     *
     * @return cachedSize
     */
    public String getCachedSize() {
        return cachedSize;
    }
    
    
    /**
     * Sets the cachedSize value for this ResultElement.
     *
     * @param cachedSize
     */
    public void setCachedSize(String cachedSize) {
        this.cachedSize = cachedSize;
    }
    
    
    /**
     * Gets the relatedInformationPresent value for this ResultElement.
     *
     * @return relatedInformationPresent
     */
    public boolean isRelatedInformationPresent() {
        return relatedInformationPresent;
    }
    
    
    /**
     * Sets the relatedInformationPresent value for this ResultElement.
     *
     * @param relatedInformationPresent
     */
    public void setRelatedInformationPresent(boolean relatedInformationPresent) {
        this.relatedInformationPresent = relatedInformationPresent;
    }
    
    
    /**
     * Gets the hostName value for this ResultElement.
     *
     * @return hostName
     */
    public String getHostName() {
        return hostName;
    }
    
    
    /**
     * Sets the hostName value for this ResultElement.
     *
     * @param hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    
    /**
     * Gets the directoryCategory value for this ResultElement.
     *
     * @return directoryCategory
     */
    public DirectoryCategory getDirectoryCategory() {
        return directoryCategory;
    }
    
    
    /**
     * Sets the directoryCategory value for this ResultElement.
     *
     * @param directoryCategory
     */
    public void setDirectoryCategory(DirectoryCategory directoryCategory) {
        this.directoryCategory = directoryCategory;
    }
    
    
    /**
     * Gets the directoryTitle value for this ResultElement.
     *
     * @return directoryTitle
     */
    public String getDirectoryTitle() {
        return directoryTitle;
    }
    
    
    /**
     * Sets the directoryTitle value for this ResultElement.
     *
     * @param directoryTitle
     */
    public void setDirectoryTitle(String directoryTitle) {
        this.directoryTitle = directoryTitle;
    }
    /**
     * Gets the pagerank value for this ResultElement.
     *
     */
    public int getPagerank() {
        return pagerank;
    }
    /**
     * Sets the pagerank value for this ResultElement.
     *
     * @param pagerank
     */

    public void setPagerank(int pagerank) {
        this.pagerank = pagerank;
    }
    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ResultElement)) return false;
        ResultElement other = (ResultElement) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.summary==null && other.getSummary()==null) ||
                (this.summary!=null &&
                this.summary.equals(other.getSummary()))) &&
                ((this.URL==null && other.getURL()==null) ||
                (this.URL!=null &&
                this.URL.equals(other.getURL()))) &&
                ((this.snippet==null && other.getSnippet()==null) ||
                (this.snippet!=null &&
                this.snippet.equals(other.getSnippet()))) &&
                ((this.title==null && other.getTitle()==null) ||
                (this.title!=null &&
                this.title.equals(other.getTitle()))) &&
                ((this.cachedSize==null && other.getCachedSize()==null) ||
                (this.cachedSize!=null &&
                this.cachedSize.equals(other.getCachedSize()))) &&
                this.relatedInformationPresent == other.isRelatedInformationPresent() &&
                ((this.hostName==null && other.getHostName()==null) ||
                (this.hostName!=null &&
                this.hostName.equals(other.getHostName()))) &&
                ((this.directoryCategory==null && other.getDirectoryCategory()==null) ||
                (this.directoryCategory!=null &&
                this.directoryCategory.equals(other.getDirectoryCategory()))) &&
                ((this.directoryTitle==null && other.getDirectoryTitle()==null) ||
                (this.directoryTitle!=null &&
                this.directoryTitle.equals(other.getDirectoryTitle())));
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
        if (getSummary() != null) {
            _hashCode += getSummary().hashCode();
        }
        if (getURL() != null) {
            _hashCode += getURL().hashCode();
        }
        if (getSnippet() != null) {
            _hashCode += getSnippet().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCachedSize() != null) {
            _hashCode += getCachedSize().hashCode();
        }
        _hashCode += (isRelatedInformationPresent() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getHostName() != null) {
            _hashCode += getHostName().hashCode();
        }
        if (getDirectoryCategory() != null) {
            _hashCode += getDirectoryCategory().hashCode();
        }
        if (getDirectoryTitle() != null) {
            _hashCode += getDirectoryTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ResultElement.class, true);
    
    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:GoogleSearch", "ResultElement"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "summary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("URL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "URL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("snippet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "snippet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cachedSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cachedSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relatedInformationPresent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relatedInformationPresent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hostName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directoryCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "directoryCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:GoogleSearch", "DirectoryCategory"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directoryTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "directoryTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
    
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }
    
    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }
    
    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            String mechType,
            Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanDeserializer(
                _javaType, _xmlType, typeDesc);
    }

    public String toString() {

        String retValue = "";
        retValue+=getTitle();
        //retValue+=getURL()+ "\n";
        return retValue;
    }

    
    
}
