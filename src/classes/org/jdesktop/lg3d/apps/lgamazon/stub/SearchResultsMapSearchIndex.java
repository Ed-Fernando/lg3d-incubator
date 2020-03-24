/**
 * SearchResultsMapSearchIndex.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SearchResultsMapSearchIndex  implements java.io.Serializable {
    private java.lang.String indexName;
    private org.apache.axis.types.NonNegativeInteger results;
    private org.apache.axis.types.NonNegativeInteger pages;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery;
    private org.apache.axis.types.PositiveInteger relevanceRank;
    private java.lang.String[] ASIN;

    public SearchResultsMapSearchIndex() {
    }

    public SearchResultsMapSearchIndex(
           java.lang.String indexName,
           org.apache.axis.types.NonNegativeInteger results,
           org.apache.axis.types.NonNegativeInteger pages,
           org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery,
           org.apache.axis.types.PositiveInteger relevanceRank,
           java.lang.String[] ASIN) {
           this.indexName = indexName;
           this.results = results;
           this.pages = pages;
           this.correctedQuery = correctedQuery;
           this.relevanceRank = relevanceRank;
           this.ASIN = ASIN;
    }


    /**
     * Gets the indexName value for this SearchResultsMapSearchIndex.
     * 
     * @return indexName
     */
    public java.lang.String getIndexName() {
        return indexName;
    }


    /**
     * Sets the indexName value for this SearchResultsMapSearchIndex.
     * 
     * @param indexName
     */
    public void setIndexName(java.lang.String indexName) {
        this.indexName = indexName;
    }


    /**
     * Gets the results value for this SearchResultsMapSearchIndex.
     * 
     * @return results
     */
    public org.apache.axis.types.NonNegativeInteger getResults() {
        return results;
    }


    /**
     * Sets the results value for this SearchResultsMapSearchIndex.
     * 
     * @param results
     */
    public void setResults(org.apache.axis.types.NonNegativeInteger results) {
        this.results = results;
    }


    /**
     * Gets the pages value for this SearchResultsMapSearchIndex.
     * 
     * @return pages
     */
    public org.apache.axis.types.NonNegativeInteger getPages() {
        return pages;
    }


    /**
     * Sets the pages value for this SearchResultsMapSearchIndex.
     * 
     * @param pages
     */
    public void setPages(org.apache.axis.types.NonNegativeInteger pages) {
        this.pages = pages;
    }


    /**
     * Gets the correctedQuery value for this SearchResultsMapSearchIndex.
     * 
     * @return correctedQuery
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery getCorrectedQuery() {
        return correctedQuery;
    }


    /**
     * Sets the correctedQuery value for this SearchResultsMapSearchIndex.
     * 
     * @param correctedQuery
     */
    public void setCorrectedQuery(org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery) {
        this.correctedQuery = correctedQuery;
    }


    /**
     * Gets the relevanceRank value for this SearchResultsMapSearchIndex.
     * 
     * @return relevanceRank
     */
    public org.apache.axis.types.PositiveInteger getRelevanceRank() {
        return relevanceRank;
    }


    /**
     * Sets the relevanceRank value for this SearchResultsMapSearchIndex.
     * 
     * @param relevanceRank
     */
    public void setRelevanceRank(org.apache.axis.types.PositiveInteger relevanceRank) {
        this.relevanceRank = relevanceRank;
    }


    /**
     * Gets the ASIN value for this SearchResultsMapSearchIndex.
     * 
     * @return ASIN
     */
    public java.lang.String[] getASIN() {
        return ASIN;
    }


    /**
     * Sets the ASIN value for this SearchResultsMapSearchIndex.
     * 
     * @param ASIN
     */
    public void setASIN(java.lang.String[] ASIN) {
        this.ASIN = ASIN;
    }

    public java.lang.String getASIN(int i) {
        return this.ASIN[i];
    }

    public void setASIN(int i, java.lang.String _value) {
        this.ASIN[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchResultsMapSearchIndex)) return false;
        SearchResultsMapSearchIndex other = (SearchResultsMapSearchIndex) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.indexName==null && other.getIndexName()==null) || 
             (this.indexName!=null &&
              this.indexName.equals(other.getIndexName()))) &&
            ((this.results==null && other.getResults()==null) || 
             (this.results!=null &&
              this.results.equals(other.getResults()))) &&
            ((this.pages==null && other.getPages()==null) || 
             (this.pages!=null &&
              this.pages.equals(other.getPages()))) &&
            ((this.correctedQuery==null && other.getCorrectedQuery()==null) || 
             (this.correctedQuery!=null &&
              this.correctedQuery.equals(other.getCorrectedQuery()))) &&
            ((this.relevanceRank==null && other.getRelevanceRank()==null) || 
             (this.relevanceRank!=null &&
              this.relevanceRank.equals(other.getRelevanceRank()))) &&
            ((this.ASIN==null && other.getASIN()==null) || 
             (this.ASIN!=null &&
              java.util.Arrays.equals(this.ASIN, other.getASIN())));
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
        if (getIndexName() != null) {
            _hashCode += getIndexName().hashCode();
        }
        if (getResults() != null) {
            _hashCode += getResults().hashCode();
        }
        if (getPages() != null) {
            _hashCode += getPages().hashCode();
        }
        if (getCorrectedQuery() != null) {
            _hashCode += getCorrectedQuery().hashCode();
        }
        if (getRelevanceRank() != null) {
            _hashCode += getRelevanceRank().hashCode();
        }
        if (getASIN() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getASIN());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getASIN(), i);
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
        new org.apache.axis.description.TypeDesc(SearchResultsMapSearchIndex.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SearchResultsMap>SearchIndex"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indexName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "IndexName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("results");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Results"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Pages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correctedQuery");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CorrectedQuery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CorrectedQuery"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relevanceRank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "RelevanceRank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ASIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ASIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
