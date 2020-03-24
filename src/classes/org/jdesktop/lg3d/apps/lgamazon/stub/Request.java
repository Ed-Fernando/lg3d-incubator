/**
 * Request.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Request  implements java.io.Serializable {
    private java.lang.String isValid;
    private org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest helpRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupRequest browseNodeLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest itemSearchRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequest itemLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequest listSearchRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequest listLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchRequest customerContentSearchRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupRequest customerContentLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequest similarityLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartGetRequest cartGetRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequest cartAddRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest cartCreateRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequest cartModifyRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartClearRequest cartClearRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupRequest transactionLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequest sellerListingSearchRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequest sellerListingLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupRequest sellerLookupRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors;

    public Request() {
    }

    public Request(
           java.lang.String isValid,
           org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest helpRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupRequest browseNodeLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest itemSearchRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequest itemLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequest listSearchRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequest listLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchRequest customerContentSearchRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupRequest customerContentLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequest similarityLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartGetRequest cartGetRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequest cartAddRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest cartCreateRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequest cartModifyRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartClearRequest cartClearRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupRequest transactionLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequest sellerListingSearchRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequest sellerListingLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupRequest sellerLookupRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors) {
           this.isValid = isValid;
           this.helpRequest = helpRequest;
           this.browseNodeLookupRequest = browseNodeLookupRequest;
           this.itemSearchRequest = itemSearchRequest;
           this.itemLookupRequest = itemLookupRequest;
           this.listSearchRequest = listSearchRequest;
           this.listLookupRequest = listLookupRequest;
           this.customerContentSearchRequest = customerContentSearchRequest;
           this.customerContentLookupRequest = customerContentLookupRequest;
           this.similarityLookupRequest = similarityLookupRequest;
           this.cartGetRequest = cartGetRequest;
           this.cartAddRequest = cartAddRequest;
           this.cartCreateRequest = cartCreateRequest;
           this.cartModifyRequest = cartModifyRequest;
           this.cartClearRequest = cartClearRequest;
           this.transactionLookupRequest = transactionLookupRequest;
           this.sellerListingSearchRequest = sellerListingSearchRequest;
           this.sellerListingLookupRequest = sellerListingLookupRequest;
           this.sellerLookupRequest = sellerLookupRequest;
           this.errors = errors;
    }


    /**
     * Gets the isValid value for this Request.
     * 
     * @return isValid
     */
    public java.lang.String getIsValid() {
        return isValid;
    }


    /**
     * Sets the isValid value for this Request.
     * 
     * @param isValid
     */
    public void setIsValid(java.lang.String isValid) {
        this.isValid = isValid;
    }


    /**
     * Gets the helpRequest value for this Request.
     * 
     * @return helpRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest getHelpRequest() {
        return helpRequest;
    }


    /**
     * Sets the helpRequest value for this Request.
     * 
     * @param helpRequest
     */
    public void setHelpRequest(org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest helpRequest) {
        this.helpRequest = helpRequest;
    }


    /**
     * Gets the browseNodeLookupRequest value for this Request.
     * 
     * @return browseNodeLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupRequest getBrowseNodeLookupRequest() {
        return browseNodeLookupRequest;
    }


    /**
     * Sets the browseNodeLookupRequest value for this Request.
     * 
     * @param browseNodeLookupRequest
     */
    public void setBrowseNodeLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupRequest browseNodeLookupRequest) {
        this.browseNodeLookupRequest = browseNodeLookupRequest;
    }


    /**
     * Gets the itemSearchRequest value for this Request.
     * 
     * @return itemSearchRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest getItemSearchRequest() {
        return itemSearchRequest;
    }


    /**
     * Sets the itemSearchRequest value for this Request.
     * 
     * @param itemSearchRequest
     */
    public void setItemSearchRequest(org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest itemSearchRequest) {
        this.itemSearchRequest = itemSearchRequest;
    }


    /**
     * Gets the itemLookupRequest value for this Request.
     * 
     * @return itemLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequest getItemLookupRequest() {
        return itemLookupRequest;
    }


    /**
     * Sets the itemLookupRequest value for this Request.
     * 
     * @param itemLookupRequest
     */
    public void setItemLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequest itemLookupRequest) {
        this.itemLookupRequest = itemLookupRequest;
    }


    /**
     * Gets the listSearchRequest value for this Request.
     * 
     * @return listSearchRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequest getListSearchRequest() {
        return listSearchRequest;
    }


    /**
     * Sets the listSearchRequest value for this Request.
     * 
     * @param listSearchRequest
     */
    public void setListSearchRequest(org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequest listSearchRequest) {
        this.listSearchRequest = listSearchRequest;
    }


    /**
     * Gets the listLookupRequest value for this Request.
     * 
     * @return listLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequest getListLookupRequest() {
        return listLookupRequest;
    }


    /**
     * Sets the listLookupRequest value for this Request.
     * 
     * @param listLookupRequest
     */
    public void setListLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequest listLookupRequest) {
        this.listLookupRequest = listLookupRequest;
    }


    /**
     * Gets the customerContentSearchRequest value for this Request.
     * 
     * @return customerContentSearchRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchRequest getCustomerContentSearchRequest() {
        return customerContentSearchRequest;
    }


    /**
     * Sets the customerContentSearchRequest value for this Request.
     * 
     * @param customerContentSearchRequest
     */
    public void setCustomerContentSearchRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchRequest customerContentSearchRequest) {
        this.customerContentSearchRequest = customerContentSearchRequest;
    }


    /**
     * Gets the customerContentLookupRequest value for this Request.
     * 
     * @return customerContentLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupRequest getCustomerContentLookupRequest() {
        return customerContentLookupRequest;
    }


    /**
     * Sets the customerContentLookupRequest value for this Request.
     * 
     * @param customerContentLookupRequest
     */
    public void setCustomerContentLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupRequest customerContentLookupRequest) {
        this.customerContentLookupRequest = customerContentLookupRequest;
    }


    /**
     * Gets the similarityLookupRequest value for this Request.
     * 
     * @return similarityLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequest getSimilarityLookupRequest() {
        return similarityLookupRequest;
    }


    /**
     * Sets the similarityLookupRequest value for this Request.
     * 
     * @param similarityLookupRequest
     */
    public void setSimilarityLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequest similarityLookupRequest) {
        this.similarityLookupRequest = similarityLookupRequest;
    }


    /**
     * Gets the cartGetRequest value for this Request.
     * 
     * @return cartGetRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartGetRequest getCartGetRequest() {
        return cartGetRequest;
    }


    /**
     * Sets the cartGetRequest value for this Request.
     * 
     * @param cartGetRequest
     */
    public void setCartGetRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartGetRequest cartGetRequest) {
        this.cartGetRequest = cartGetRequest;
    }


    /**
     * Gets the cartAddRequest value for this Request.
     * 
     * @return cartAddRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequest getCartAddRequest() {
        return cartAddRequest;
    }


    /**
     * Sets the cartAddRequest value for this Request.
     * 
     * @param cartAddRequest
     */
    public void setCartAddRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequest cartAddRequest) {
        this.cartAddRequest = cartAddRequest;
    }


    /**
     * Gets the cartCreateRequest value for this Request.
     * 
     * @return cartCreateRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest getCartCreateRequest() {
        return cartCreateRequest;
    }


    /**
     * Sets the cartCreateRequest value for this Request.
     * 
     * @param cartCreateRequest
     */
    public void setCartCreateRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest cartCreateRequest) {
        this.cartCreateRequest = cartCreateRequest;
    }


    /**
     * Gets the cartModifyRequest value for this Request.
     * 
     * @return cartModifyRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequest getCartModifyRequest() {
        return cartModifyRequest;
    }


    /**
     * Sets the cartModifyRequest value for this Request.
     * 
     * @param cartModifyRequest
     */
    public void setCartModifyRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequest cartModifyRequest) {
        this.cartModifyRequest = cartModifyRequest;
    }


    /**
     * Gets the cartClearRequest value for this Request.
     * 
     * @return cartClearRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartClearRequest getCartClearRequest() {
        return cartClearRequest;
    }


    /**
     * Sets the cartClearRequest value for this Request.
     * 
     * @param cartClearRequest
     */
    public void setCartClearRequest(org.jdesktop.lg3d.apps.lgamazon.stub.CartClearRequest cartClearRequest) {
        this.cartClearRequest = cartClearRequest;
    }


    /**
     * Gets the transactionLookupRequest value for this Request.
     * 
     * @return transactionLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupRequest getTransactionLookupRequest() {
        return transactionLookupRequest;
    }


    /**
     * Sets the transactionLookupRequest value for this Request.
     * 
     * @param transactionLookupRequest
     */
    public void setTransactionLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupRequest transactionLookupRequest) {
        this.transactionLookupRequest = transactionLookupRequest;
    }


    /**
     * Gets the sellerListingSearchRequest value for this Request.
     * 
     * @return sellerListingSearchRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequest getSellerListingSearchRequest() {
        return sellerListingSearchRequest;
    }


    /**
     * Sets the sellerListingSearchRequest value for this Request.
     * 
     * @param sellerListingSearchRequest
     */
    public void setSellerListingSearchRequest(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequest sellerListingSearchRequest) {
        this.sellerListingSearchRequest = sellerListingSearchRequest;
    }


    /**
     * Gets the sellerListingLookupRequest value for this Request.
     * 
     * @return sellerListingLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequest getSellerListingLookupRequest() {
        return sellerListingLookupRequest;
    }


    /**
     * Sets the sellerListingLookupRequest value for this Request.
     * 
     * @param sellerListingLookupRequest
     */
    public void setSellerListingLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequest sellerListingLookupRequest) {
        this.sellerListingLookupRequest = sellerListingLookupRequest;
    }


    /**
     * Gets the sellerLookupRequest value for this Request.
     * 
     * @return sellerLookupRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupRequest getSellerLookupRequest() {
        return sellerLookupRequest;
    }


    /**
     * Sets the sellerLookupRequest value for this Request.
     * 
     * @param sellerLookupRequest
     */
    public void setSellerLookupRequest(org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupRequest sellerLookupRequest) {
        this.sellerLookupRequest = sellerLookupRequest;
    }


    /**
     * Gets the errors value for this Request.
     * 
     * @return errors
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Errors getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this Request.
     * 
     * @param errors
     */
    public void setErrors(org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors) {
        this.errors = errors;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request)) return false;
        Request other = (Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.isValid==null && other.getIsValid()==null) || 
             (this.isValid!=null &&
              this.isValid.equals(other.getIsValid()))) &&
            ((this.helpRequest==null && other.getHelpRequest()==null) || 
             (this.helpRequest!=null &&
              this.helpRequest.equals(other.getHelpRequest()))) &&
            ((this.browseNodeLookupRequest==null && other.getBrowseNodeLookupRequest()==null) || 
             (this.browseNodeLookupRequest!=null &&
              this.browseNodeLookupRequest.equals(other.getBrowseNodeLookupRequest()))) &&
            ((this.itemSearchRequest==null && other.getItemSearchRequest()==null) || 
             (this.itemSearchRequest!=null &&
              this.itemSearchRequest.equals(other.getItemSearchRequest()))) &&
            ((this.itemLookupRequest==null && other.getItemLookupRequest()==null) || 
             (this.itemLookupRequest!=null &&
              this.itemLookupRequest.equals(other.getItemLookupRequest()))) &&
            ((this.listSearchRequest==null && other.getListSearchRequest()==null) || 
             (this.listSearchRequest!=null &&
              this.listSearchRequest.equals(other.getListSearchRequest()))) &&
            ((this.listLookupRequest==null && other.getListLookupRequest()==null) || 
             (this.listLookupRequest!=null &&
              this.listLookupRequest.equals(other.getListLookupRequest()))) &&
            ((this.customerContentSearchRequest==null && other.getCustomerContentSearchRequest()==null) || 
             (this.customerContentSearchRequest!=null &&
              this.customerContentSearchRequest.equals(other.getCustomerContentSearchRequest()))) &&
            ((this.customerContentLookupRequest==null && other.getCustomerContentLookupRequest()==null) || 
             (this.customerContentLookupRequest!=null &&
              this.customerContentLookupRequest.equals(other.getCustomerContentLookupRequest()))) &&
            ((this.similarityLookupRequest==null && other.getSimilarityLookupRequest()==null) || 
             (this.similarityLookupRequest!=null &&
              this.similarityLookupRequest.equals(other.getSimilarityLookupRequest()))) &&
            ((this.cartGetRequest==null && other.getCartGetRequest()==null) || 
             (this.cartGetRequest!=null &&
              this.cartGetRequest.equals(other.getCartGetRequest()))) &&
            ((this.cartAddRequest==null && other.getCartAddRequest()==null) || 
             (this.cartAddRequest!=null &&
              this.cartAddRequest.equals(other.getCartAddRequest()))) &&
            ((this.cartCreateRequest==null && other.getCartCreateRequest()==null) || 
             (this.cartCreateRequest!=null &&
              this.cartCreateRequest.equals(other.getCartCreateRequest()))) &&
            ((this.cartModifyRequest==null && other.getCartModifyRequest()==null) || 
             (this.cartModifyRequest!=null &&
              this.cartModifyRequest.equals(other.getCartModifyRequest()))) &&
            ((this.cartClearRequest==null && other.getCartClearRequest()==null) || 
             (this.cartClearRequest!=null &&
              this.cartClearRequest.equals(other.getCartClearRequest()))) &&
            ((this.transactionLookupRequest==null && other.getTransactionLookupRequest()==null) || 
             (this.transactionLookupRequest!=null &&
              this.transactionLookupRequest.equals(other.getTransactionLookupRequest()))) &&
            ((this.sellerListingSearchRequest==null && other.getSellerListingSearchRequest()==null) || 
             (this.sellerListingSearchRequest!=null &&
              this.sellerListingSearchRequest.equals(other.getSellerListingSearchRequest()))) &&
            ((this.sellerListingLookupRequest==null && other.getSellerListingLookupRequest()==null) || 
             (this.sellerListingLookupRequest!=null &&
              this.sellerListingLookupRequest.equals(other.getSellerListingLookupRequest()))) &&
            ((this.sellerLookupRequest==null && other.getSellerLookupRequest()==null) || 
             (this.sellerLookupRequest!=null &&
              this.sellerLookupRequest.equals(other.getSellerLookupRequest()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              this.errors.equals(other.getErrors())));
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
        if (getIsValid() != null) {
            _hashCode += getIsValid().hashCode();
        }
        if (getHelpRequest() != null) {
            _hashCode += getHelpRequest().hashCode();
        }
        if (getBrowseNodeLookupRequest() != null) {
            _hashCode += getBrowseNodeLookupRequest().hashCode();
        }
        if (getItemSearchRequest() != null) {
            _hashCode += getItemSearchRequest().hashCode();
        }
        if (getItemLookupRequest() != null) {
            _hashCode += getItemLookupRequest().hashCode();
        }
        if (getListSearchRequest() != null) {
            _hashCode += getListSearchRequest().hashCode();
        }
        if (getListLookupRequest() != null) {
            _hashCode += getListLookupRequest().hashCode();
        }
        if (getCustomerContentSearchRequest() != null) {
            _hashCode += getCustomerContentSearchRequest().hashCode();
        }
        if (getCustomerContentLookupRequest() != null) {
            _hashCode += getCustomerContentLookupRequest().hashCode();
        }
        if (getSimilarityLookupRequest() != null) {
            _hashCode += getSimilarityLookupRequest().hashCode();
        }
        if (getCartGetRequest() != null) {
            _hashCode += getCartGetRequest().hashCode();
        }
        if (getCartAddRequest() != null) {
            _hashCode += getCartAddRequest().hashCode();
        }
        if (getCartCreateRequest() != null) {
            _hashCode += getCartCreateRequest().hashCode();
        }
        if (getCartModifyRequest() != null) {
            _hashCode += getCartModifyRequest().hashCode();
        }
        if (getCartClearRequest() != null) {
            _hashCode += getCartClearRequest().hashCode();
        }
        if (getTransactionLookupRequest() != null) {
            _hashCode += getTransactionLookupRequest().hashCode();
        }
        if (getSellerListingSearchRequest() != null) {
            _hashCode += getSellerListingSearchRequest().hashCode();
        }
        if (getSellerListingLookupRequest() != null) {
            _hashCode += getSellerListingLookupRequest().hashCode();
        }
        if (getSellerLookupRequest() != null) {
            _hashCode += getSellerLookupRequest().hashCode();
        }
        if (getErrors() != null) {
            _hashCode += getErrors().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isValid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "IsValid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("helpRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browseNodeLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemSearchRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearchRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearchRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listSearchRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearchRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearchRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerContentSearchRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearchRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearchRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerContentLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarityLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartGetRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGetRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGetRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartAddRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAddRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAddRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartCreateRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartModifyRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModifyRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModifyRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartClearRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClearRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClearRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerListingSearchRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearchRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearchRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerListingLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerLookupRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookupRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookupRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Errors"));
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
