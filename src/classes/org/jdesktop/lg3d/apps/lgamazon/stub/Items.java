/**
 * Items.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Items  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Request request;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery;
    private org.apache.axis.types.NonNegativeInteger totalResults;
    private org.apache.axis.types.NonNegativeInteger totalPages;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMap searchResultsMap;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Item[] item;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSets searchBinSets;

    public Items() {
    }

    public Items(
           org.jdesktop.lg3d.apps.lgamazon.stub.Request request,
           org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery,
           org.apache.axis.types.NonNegativeInteger totalResults,
           org.apache.axis.types.NonNegativeInteger totalPages,
           org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMap searchResultsMap,
           org.jdesktop.lg3d.apps.lgamazon.stub.Item[] item,
           org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSets searchBinSets) {
           this.request = request;
           this.correctedQuery = correctedQuery;
           this.totalResults = totalResults;
           this.totalPages = totalPages;
           this.searchResultsMap = searchResultsMap;
           this.item = item;
           this.searchBinSets = searchBinSets;
    }


    /**
     * Gets the request value for this Items.
     * 
     * @return request
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Request getRequest() {
        return request;
    }


    /**
     * Sets the request value for this Items.
     * 
     * @param request
     */
    public void setRequest(org.jdesktop.lg3d.apps.lgamazon.stub.Request request) {
        this.request = request;
    }


    /**
     * Gets the correctedQuery value for this Items.
     * 
     * @return correctedQuery
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery getCorrectedQuery() {
        return correctedQuery;
    }


    /**
     * Sets the correctedQuery value for this Items.
     * 
     * @param correctedQuery
     */
    public void setCorrectedQuery(org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery correctedQuery) {
        this.correctedQuery = correctedQuery;
    }


    /**
     * Gets the totalResults value for this Items.
     * 
     * @return totalResults
     */
    public org.apache.axis.types.NonNegativeInteger getTotalResults() {
        return totalResults;
    }


    /**
     * Sets the totalResults value for this Items.
     * 
     * @param totalResults
     */
    public void setTotalResults(org.apache.axis.types.NonNegativeInteger totalResults) {
        this.totalResults = totalResults;
    }


    /**
     * Gets the totalPages value for this Items.
     * 
     * @return totalPages
     */
    public org.apache.axis.types.NonNegativeInteger getTotalPages() {
        return totalPages;
    }


    /**
     * Sets the totalPages value for this Items.
     * 
     * @param totalPages
     */
    public void setTotalPages(org.apache.axis.types.NonNegativeInteger totalPages) {
        this.totalPages = totalPages;
    }


    /**
     * Gets the searchResultsMap value for this Items.
     * 
     * @return searchResultsMap
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMap getSearchResultsMap() {
        return searchResultsMap;
    }


    /**
     * Sets the searchResultsMap value for this Items.
     * 
     * @param searchResultsMap
     */
    public void setSearchResultsMap(org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMap searchResultsMap) {
        this.searchResultsMap = searchResultsMap;
    }


    /**
     * Gets the item value for this Items.
     * 
     * @return item
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Item[] getItem() {
        return item;
    }


    /**
     * Sets the item value for this Items.
     * 
     * @param item
     */
    public void setItem(org.jdesktop.lg3d.apps.lgamazon.stub.Item[] item) {
        this.item = item;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.Item getItem(int i) {
        return this.item[i];
    }

    public void setItem(int i, org.jdesktop.lg3d.apps.lgamazon.stub.Item _value) {
        this.item[i] = _value;
    }


    /**
     * Gets the searchBinSets value for this Items.
     * 
     * @return searchBinSets
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSets getSearchBinSets() {
        return searchBinSets;
    }


    /**
     * Sets the searchBinSets value for this Items.
     * 
     * @param searchBinSets
     */
    public void setSearchBinSets(org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSets searchBinSets) {
        this.searchBinSets = searchBinSets;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Items)) return false;
        Items other = (Items) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              this.request.equals(other.getRequest()))) &&
            ((this.correctedQuery==null && other.getCorrectedQuery()==null) || 
             (this.correctedQuery!=null &&
              this.correctedQuery.equals(other.getCorrectedQuery()))) &&
            ((this.totalResults==null && other.getTotalResults()==null) || 
             (this.totalResults!=null &&
              this.totalResults.equals(other.getTotalResults()))) &&
            ((this.totalPages==null && other.getTotalPages()==null) || 
             (this.totalPages!=null &&
              this.totalPages.equals(other.getTotalPages()))) &&
            ((this.searchResultsMap==null && other.getSearchResultsMap()==null) || 
             (this.searchResultsMap!=null &&
              this.searchResultsMap.equals(other.getSearchResultsMap()))) &&
            ((this.item==null && other.getItem()==null) || 
             (this.item!=null &&
              java.util.Arrays.equals(this.item, other.getItem()))) &&
            ((this.searchBinSets==null && other.getSearchBinSets()==null) || 
             (this.searchBinSets!=null &&
              this.searchBinSets.equals(other.getSearchBinSets())));
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
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        if (getCorrectedQuery() != null) {
            _hashCode += getCorrectedQuery().hashCode();
        }
        if (getTotalResults() != null) {
            _hashCode += getTotalResults().hashCode();
        }
        if (getTotalPages() != null) {
            _hashCode += getTotalPages().hashCode();
        }
        if (getSearchResultsMap() != null) {
            _hashCode += getSearchResultsMap().hashCode();
        }
        if (getItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItem(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchBinSets() != null) {
            _hashCode += getSearchBinSets().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Items.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Items"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
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
        elemField.setFieldName("totalResults");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalResults"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalPages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchResultsMap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchResultsMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchResultsMap"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("item");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Item"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Item"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchBinSets");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchBinSets"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchBinSets"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
