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
import java.lang.reflect.Array;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;


/**
 * Encapsulates all the results returned from Google in one search
 */
public class GoogleSearchResult  implements java.io.Serializable {
    /**
     * Indicates if there was some filtering in the results
     */
    private boolean documentFiltering;
    /**
     * Indicates if there was some filtering in the results
     */
    private String searchComments;
    /**
     * Number of results that Google expects for the associated query
     */
    private int estimatedTotalResultsCount;
    /**
     * If true, the total results estimation is exact
     */
    private boolean estimateIsExact;
    /**
     * The individual results for the search
     */
    private ResultElement[] resultElements;
    /**
     * The string that was sent to Google for the query
     */
    private String searchQuery;
    /**
     * Zero-based index of the first result in this object
     */
    private int startIndex;
    /**
     * Zero-based index for the last element in this object
     */
    private int endIndex;
    /**
     * Recomendation from Google to the user regarding the search
     */
    private String searchTips;
    /**
     * Categories that seems to be related to this search
     */
    private DirectoryCategory[] directoryCategories;
    /**
     * Time needed by Google to process this request
     */
    private double searchTime;
    
    /**
     * Empty Constructor
     */
    public GoogleSearchResult() {
    }
    
    /**
     * Creates a new GoogleSearchResult with all it's atributes initiated
     * @param documentFiltering Indicates if there was some filtering in the results
     * @param searchComments Indicates if there was some filtering in the results
     * @param estimatedTotalResultsCount Number of results that Google expects for the associated query
     * @param estimateIsExact If true, the total results estimation is exact
     * @param resultElements The individual results for the search
     * @param searchQuery The string that was sent to Google for the query
     * @param startIndex Zero-based index of the first result in this object
     * @param endIndex Zero-based index of the last result in this object
     * @param searchTips Recomendation from Google to the user regarding the search
     * @param directoryCategories Categories that seems to be related to this search
     * @param searchTime Time needed by Google to process this request
     */
    public GoogleSearchResult(
            boolean documentFiltering, String searchComments,
            int estimatedTotalResultsCount, boolean estimateIsExact,
            ResultElement[] resultElements, String searchQuery,
            int startIndex, int endIndex, String searchTips,
            DirectoryCategory[] directoryCategories, double searchTime) {
        this.documentFiltering = documentFiltering;
        this.searchComments = searchComments;
        this.estimatedTotalResultsCount = estimatedTotalResultsCount;
        this.estimateIsExact = estimateIsExact;
        this.resultElements = resultElements;
        this.searchQuery = searchQuery;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.searchTips = searchTips;
        this.directoryCategories = directoryCategories;
        this.searchTime = searchTime;
    }
    
    
    /**
     * Gets the documentFiltering value for this GoogleSearchResult.
     *
     * @return documentFiltering
     */
    public boolean isDocumentFiltering() {
        return documentFiltering;
    }
    
    
    /**
     * Sets the documentFiltering value for this GoogleSearchResult.
     * @param documentFiltering Indicates if there was some filtering in the results
     */
    public void setDocumentFiltering(boolean documentFiltering) {
        this.documentFiltering = documentFiltering;
    }
    
    
    /**
     * Gets the searchComments value for this GoogleSearchResult.
     *
     * @return searchComments
     */
    public String getSearchComments() {
        return searchComments;
    }
    
    
    /**
     * Sets the searchComments value for this GoogleSearchResult.
     * @param searchComments Indicates if there was some filtering in the results
     */
    public void setSearchComments(String searchComments) {
        this.searchComments = searchComments;
    }
    
    
    /**
     * Gets the estimatedTotalResultsCount value for this GoogleSearchResult.
     *
     * @return estimatedTotalResultsCount
     */
    public int getEstimatedTotalResultsCount() {
        return estimatedTotalResultsCount;
    }
    
    
    /**
     * Sets the estimatedTotalResultsCount value for this GoogleSearchResult.
     * @param estimatedTotalResultsCount Number of results that Google expects for the associated query
     */
    public void setEstimatedTotalResultsCount(int estimatedTotalResultsCount) {
        this.estimatedTotalResultsCount = estimatedTotalResultsCount;
    }
    
    
    /**
     * Gets the estimateIsExact value for this GoogleSearchResult.
     *
     * @return estimateIsExact
     */
    public boolean isEstimateIsExact() {
        return estimateIsExact;
    }
    
    
    /**
     * Sets the estimateIsExact value for this GoogleSearchResult.
     * @param estimateIsExact If true, the total results estimation is exact
     */
    public void setEstimateIsExact(boolean estimateIsExact) {
        this.estimateIsExact = estimateIsExact;
    }
    
    
    /**
     * Gets the resultElements value for this GoogleSearchResult.
     *
     * @return resultElements
     */
    public ResultElement[] getResultElements() {
        return resultElements;
    }
    
    
    /**
     * Sets the resultElements value for this GoogleSearchResult.
     * @param resultElements The individual results for the search
     */
    public void setResultElements(ResultElement[] resultElements) {
        this.resultElements = resultElements;
    }
    
    
    /**
     * Gets the searchQuery value for this GoogleSearchResult.
     *
     * @return searchQuery
     */
    public String getSearchQuery() {
        return searchQuery;
    }
    
    
    /**
     * Sets the searchQuery value for this GoogleSearchResult.
     * @param searchQuery The string that was sent to Google for the query
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    
    /**
     * Gets the startIndex value for this GoogleSearchResult.
     *
     * @return startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }
    
    
    /**
     * Sets the startIndex value for this GoogleSearchResult.
     * @param startIndex Zero-based index of the first result in this object
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    
    
    /**
     * Gets the endIndex value for this GoogleSearchResult.
     *
     * @return endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }
    
    
    /**
     * Sets the endIndex value for this GoogleSearchResult.
     * @param endIndex Zero-based index for the last element in this object
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    
    
    /**
     * Gets the searchTips value for this GoogleSearchResult.
     *
     * @return searchTips
     */
    public String getSearchTips() {
        return searchTips;
    }
    
    
    /**
     * Sets the searchTips value for this GoogleSearchResult.
     * @param searchTips Recomendation from Google to the user regarding the search
     */
    public void setSearchTips(String searchTips) {
        this.searchTips = searchTips;
    }
    
    
    /**
     * Gets the directoryCategories value for this GoogleSearchResult.
     *
     * @return directoryCategories
     */
    public DirectoryCategory[] getDirectoryCategories() {
        return directoryCategories;
    }
    
    
    /**
     * Sets the directoryCategories value for this GoogleSearchResult.
     * @param directoryCategories Categories that seems to be related to this search
     */
    public void setDirectoryCategories(DirectoryCategory[] directoryCategories) {
        this.directoryCategories = directoryCategories;
    }
    
    
    /**
     * Gets the searchTime value for this GoogleSearchResult.
     *
     * @return searchTime
     */
    public double getSearchTime() {
        return searchTime;
    }
    
    
    /**
     * Sets the searchTime value for this GoogleSearchResult.
     * @param searchTime Time needed by Google to process this request
     */
    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }
    
    /**
     * Temporary object used for the calculations made in equals
     */
    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GoogleSearchResult)) return false;
        GoogleSearchResult other = (GoogleSearchResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                this.documentFiltering == other.isDocumentFiltering() &&
                ((this.searchComments==null && other.getSearchComments()==null) ||
                (this.searchComments!=null &&
                this.searchComments.equals(other.getSearchComments()))) &&
                this.estimatedTotalResultsCount == other.getEstimatedTotalResultsCount() &&
                this.estimateIsExact == other.isEstimateIsExact() &&
                ((this.resultElements==null && other.getResultElements()==null) ||
                (this.resultElements!=null &&
                java.util.Arrays.equals(this.resultElements, other.getResultElements()))) &&
                ((this.searchQuery==null && other.getSearchQuery()==null) ||
                (this.searchQuery!=null &&
                this.searchQuery.equals(other.getSearchQuery()))) &&
                this.startIndex == other.getStartIndex() &&
                this.endIndex == other.getEndIndex() &&
                ((this.searchTips==null && other.getSearchTips()==null) ||
                (this.searchTips!=null &&
                this.searchTips.equals(other.getSearchTips()))) &&
                ((this.directoryCategories==null && other.getDirectoryCategories()==null) ||
                (this.directoryCategories!=null &&
                java.util.Arrays.equals(this.directoryCategories, other.getDirectoryCategories()))) &&
                this.searchTime == other.getSearchTime();
        __equalsCalc = null;
        return _equals;
    }
    
    /**
     * Used while calculating hashcode for this object
     */
    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (isDocumentFiltering() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSearchComments() != null) {
            _hashCode += getSearchComments().hashCode();
        }
        _hashCode += getEstimatedTotalResultsCount();
        _hashCode += (isEstimateIsExact() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getResultElements() != null) {
            for (int i=0;
            i<Array.getLength(getResultElements());
            i++) {
                Object obj = Array.get(getResultElements(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchQuery() != null) {
            _hashCode += getSearchQuery().hashCode();
        }
        _hashCode += getStartIndex();
        _hashCode += getEndIndex();
        if (getSearchTips() != null) {
            _hashCode += getSearchTips().hashCode();
        }
        if (getDirectoryCategories() != null) {
            for (int i=0;
            i<Array.getLength(getDirectoryCategories());
            i++) {
                Object obj = Array.get(getDirectoryCategories(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Double(getSearchTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }
    
    // Type metadata
    private static TypeDesc typeDesc =
            new TypeDesc(GoogleSearchResult.class, true);
    
    static {
        typeDesc.setXmlType(new QName("urn:GoogleSearch", "GoogleSearchResult"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("documentFiltering");
        elemField.setXmlName(new QName("", "documentFiltering"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("searchComments");
        elemField.setXmlName(new QName("", "searchComments"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("estimatedTotalResultsCount");
        elemField.setXmlName(new QName("", "estimatedTotalResultsCount"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("estimateIsExact");
        elemField.setXmlName(new QName("", "estimateIsExact"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("resultElements");
        elemField.setXmlName(new QName("", "resultElements"));
        elemField.setXmlType(new QName("urn:GoogleSearch", "ResultElement"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("searchQuery");
        elemField.setXmlName(new QName("", "searchQuery"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("startIndex");
        elemField.setXmlName(new QName("", "startIndex"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("endIndex");
        elemField.setXmlName(new QName("", "endIndex"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("searchTips");
        elemField.setXmlName(new QName("", "searchTips"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("directoryCategories");
        elemField.setXmlName(new QName("", "directoryCategories"));
        elemField.setXmlType(new QName("urn:GoogleSearch", "DirectoryCategory"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("searchTime");
        elemField.setXmlName(new QName("", "searchTime"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
    
    /**
     * Return type metadata object
     * @return The TypeDesc for this result
     */
    public static TypeDesc getTypeDesc() {
        return typeDesc;
    }
    
    /**
     * Get Custom Serializer
     */
    public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanSerializer(_javaType, _xmlType, typeDesc);
    }
    
    /**
     * Get Custom Deserializer
     */
    public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanDeserializer(_javaType, _xmlType, typeDesc);
    }
    
}
