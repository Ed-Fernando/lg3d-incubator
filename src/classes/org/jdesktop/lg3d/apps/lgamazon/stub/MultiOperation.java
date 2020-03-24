/**
 * MultiOperation.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class MultiOperation  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Help help;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch itemSearch;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup itemLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch listSearch;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup listLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch customerContentSearch;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup customerContentLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup similarityLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup sellerLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartGet cartGet;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd cartAdd;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate cartCreate;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartModify cartModify;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartClear cartClear;
    private org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup transactionLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch sellerListingSearch;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup sellerListingLookup;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup browseNodeLookup;

    public MultiOperation() {
    }

    public MultiOperation(
           org.jdesktop.lg3d.apps.lgamazon.stub.Help help,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch itemSearch,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup itemLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch listSearch,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup listLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch customerContentSearch,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup customerContentLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup similarityLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup sellerLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartGet cartGet,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd cartAdd,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate cartCreate,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartModify cartModify,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartClear cartClear,
           org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup transactionLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch sellerListingSearch,
           org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup sellerListingLookup,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup browseNodeLookup) {
           this.help = help;
           this.itemSearch = itemSearch;
           this.itemLookup = itemLookup;
           this.listSearch = listSearch;
           this.listLookup = listLookup;
           this.customerContentSearch = customerContentSearch;
           this.customerContentLookup = customerContentLookup;
           this.similarityLookup = similarityLookup;
           this.sellerLookup = sellerLookup;
           this.cartGet = cartGet;
           this.cartAdd = cartAdd;
           this.cartCreate = cartCreate;
           this.cartModify = cartModify;
           this.cartClear = cartClear;
           this.transactionLookup = transactionLookup;
           this.sellerListingSearch = sellerListingSearch;
           this.sellerListingLookup = sellerListingLookup;
           this.browseNodeLookup = browseNodeLookup;
    }


    /**
     * Gets the help value for this MultiOperation.
     * 
     * @return help
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Help getHelp() {
        return help;
    }


    /**
     * Sets the help value for this MultiOperation.
     * 
     * @param help
     */
    public void setHelp(org.jdesktop.lg3d.apps.lgamazon.stub.Help help) {
        this.help = help;
    }


    /**
     * Gets the itemSearch value for this MultiOperation.
     * 
     * @return itemSearch
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch getItemSearch() {
        return itemSearch;
    }


    /**
     * Sets the itemSearch value for this MultiOperation.
     * 
     * @param itemSearch
     */
    public void setItemSearch(org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch itemSearch) {
        this.itemSearch = itemSearch;
    }


    /**
     * Gets the itemLookup value for this MultiOperation.
     * 
     * @return itemLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup getItemLookup() {
        return itemLookup;
    }


    /**
     * Sets the itemLookup value for this MultiOperation.
     * 
     * @param itemLookup
     */
    public void setItemLookup(org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup itemLookup) {
        this.itemLookup = itemLookup;
    }


    /**
     * Gets the listSearch value for this MultiOperation.
     * 
     * @return listSearch
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch getListSearch() {
        return listSearch;
    }


    /**
     * Sets the listSearch value for this MultiOperation.
     * 
     * @param listSearch
     */
    public void setListSearch(org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch listSearch) {
        this.listSearch = listSearch;
    }


    /**
     * Gets the listLookup value for this MultiOperation.
     * 
     * @return listLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup getListLookup() {
        return listLookup;
    }


    /**
     * Sets the listLookup value for this MultiOperation.
     * 
     * @param listLookup
     */
    public void setListLookup(org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup listLookup) {
        this.listLookup = listLookup;
    }


    /**
     * Gets the customerContentSearch value for this MultiOperation.
     * 
     * @return customerContentSearch
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch getCustomerContentSearch() {
        return customerContentSearch;
    }


    /**
     * Sets the customerContentSearch value for this MultiOperation.
     * 
     * @param customerContentSearch
     */
    public void setCustomerContentSearch(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch customerContentSearch) {
        this.customerContentSearch = customerContentSearch;
    }


    /**
     * Gets the customerContentLookup value for this MultiOperation.
     * 
     * @return customerContentLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup getCustomerContentLookup() {
        return customerContentLookup;
    }


    /**
     * Sets the customerContentLookup value for this MultiOperation.
     * 
     * @param customerContentLookup
     */
    public void setCustomerContentLookup(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup customerContentLookup) {
        this.customerContentLookup = customerContentLookup;
    }


    /**
     * Gets the similarityLookup value for this MultiOperation.
     * 
     * @return similarityLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup getSimilarityLookup() {
        return similarityLookup;
    }


    /**
     * Sets the similarityLookup value for this MultiOperation.
     * 
     * @param similarityLookup
     */
    public void setSimilarityLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup similarityLookup) {
        this.similarityLookup = similarityLookup;
    }


    /**
     * Gets the sellerLookup value for this MultiOperation.
     * 
     * @return sellerLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup getSellerLookup() {
        return sellerLookup;
    }


    /**
     * Sets the sellerLookup value for this MultiOperation.
     * 
     * @param sellerLookup
     */
    public void setSellerLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup sellerLookup) {
        this.sellerLookup = sellerLookup;
    }


    /**
     * Gets the cartGet value for this MultiOperation.
     * 
     * @return cartGet
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartGet getCartGet() {
        return cartGet;
    }


    /**
     * Sets the cartGet value for this MultiOperation.
     * 
     * @param cartGet
     */
    public void setCartGet(org.jdesktop.lg3d.apps.lgamazon.stub.CartGet cartGet) {
        this.cartGet = cartGet;
    }


    /**
     * Gets the cartAdd value for this MultiOperation.
     * 
     * @return cartAdd
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd getCartAdd() {
        return cartAdd;
    }


    /**
     * Sets the cartAdd value for this MultiOperation.
     * 
     * @param cartAdd
     */
    public void setCartAdd(org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd cartAdd) {
        this.cartAdd = cartAdd;
    }


    /**
     * Gets the cartCreate value for this MultiOperation.
     * 
     * @return cartCreate
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate getCartCreate() {
        return cartCreate;
    }


    /**
     * Sets the cartCreate value for this MultiOperation.
     * 
     * @param cartCreate
     */
    public void setCartCreate(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate cartCreate) {
        this.cartCreate = cartCreate;
    }


    /**
     * Gets the cartModify value for this MultiOperation.
     * 
     * @return cartModify
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartModify getCartModify() {
        return cartModify;
    }


    /**
     * Sets the cartModify value for this MultiOperation.
     * 
     * @param cartModify
     */
    public void setCartModify(org.jdesktop.lg3d.apps.lgamazon.stub.CartModify cartModify) {
        this.cartModify = cartModify;
    }


    /**
     * Gets the cartClear value for this MultiOperation.
     * 
     * @return cartClear
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartClear getCartClear() {
        return cartClear;
    }


    /**
     * Sets the cartClear value for this MultiOperation.
     * 
     * @param cartClear
     */
    public void setCartClear(org.jdesktop.lg3d.apps.lgamazon.stub.CartClear cartClear) {
        this.cartClear = cartClear;
    }


    /**
     * Gets the transactionLookup value for this MultiOperation.
     * 
     * @return transactionLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup getTransactionLookup() {
        return transactionLookup;
    }


    /**
     * Sets the transactionLookup value for this MultiOperation.
     * 
     * @param transactionLookup
     */
    public void setTransactionLookup(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup transactionLookup) {
        this.transactionLookup = transactionLookup;
    }


    /**
     * Gets the sellerListingSearch value for this MultiOperation.
     * 
     * @return sellerListingSearch
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch getSellerListingSearch() {
        return sellerListingSearch;
    }


    /**
     * Sets the sellerListingSearch value for this MultiOperation.
     * 
     * @param sellerListingSearch
     */
    public void setSellerListingSearch(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch sellerListingSearch) {
        this.sellerListingSearch = sellerListingSearch;
    }


    /**
     * Gets the sellerListingLookup value for this MultiOperation.
     * 
     * @return sellerListingLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup getSellerListingLookup() {
        return sellerListingLookup;
    }


    /**
     * Sets the sellerListingLookup value for this MultiOperation.
     * 
     * @param sellerListingLookup
     */
    public void setSellerListingLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup sellerListingLookup) {
        this.sellerListingLookup = sellerListingLookup;
    }


    /**
     * Gets the browseNodeLookup value for this MultiOperation.
     * 
     * @return browseNodeLookup
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup getBrowseNodeLookup() {
        return browseNodeLookup;
    }


    /**
     * Sets the browseNodeLookup value for this MultiOperation.
     * 
     * @param browseNodeLookup
     */
    public void setBrowseNodeLookup(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup browseNodeLookup) {
        this.browseNodeLookup = browseNodeLookup;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MultiOperation)) return false;
        MultiOperation other = (MultiOperation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.help==null && other.getHelp()==null) || 
             (this.help!=null &&
              this.help.equals(other.getHelp()))) &&
            ((this.itemSearch==null && other.getItemSearch()==null) || 
             (this.itemSearch!=null &&
              this.itemSearch.equals(other.getItemSearch()))) &&
            ((this.itemLookup==null && other.getItemLookup()==null) || 
             (this.itemLookup!=null &&
              this.itemLookup.equals(other.getItemLookup()))) &&
            ((this.listSearch==null && other.getListSearch()==null) || 
             (this.listSearch!=null &&
              this.listSearch.equals(other.getListSearch()))) &&
            ((this.listLookup==null && other.getListLookup()==null) || 
             (this.listLookup!=null &&
              this.listLookup.equals(other.getListLookup()))) &&
            ((this.customerContentSearch==null && other.getCustomerContentSearch()==null) || 
             (this.customerContentSearch!=null &&
              this.customerContentSearch.equals(other.getCustomerContentSearch()))) &&
            ((this.customerContentLookup==null && other.getCustomerContentLookup()==null) || 
             (this.customerContentLookup!=null &&
              this.customerContentLookup.equals(other.getCustomerContentLookup()))) &&
            ((this.similarityLookup==null && other.getSimilarityLookup()==null) || 
             (this.similarityLookup!=null &&
              this.similarityLookup.equals(other.getSimilarityLookup()))) &&
            ((this.sellerLookup==null && other.getSellerLookup()==null) || 
             (this.sellerLookup!=null &&
              this.sellerLookup.equals(other.getSellerLookup()))) &&
            ((this.cartGet==null && other.getCartGet()==null) || 
             (this.cartGet!=null &&
              this.cartGet.equals(other.getCartGet()))) &&
            ((this.cartAdd==null && other.getCartAdd()==null) || 
             (this.cartAdd!=null &&
              this.cartAdd.equals(other.getCartAdd()))) &&
            ((this.cartCreate==null && other.getCartCreate()==null) || 
             (this.cartCreate!=null &&
              this.cartCreate.equals(other.getCartCreate()))) &&
            ((this.cartModify==null && other.getCartModify()==null) || 
             (this.cartModify!=null &&
              this.cartModify.equals(other.getCartModify()))) &&
            ((this.cartClear==null && other.getCartClear()==null) || 
             (this.cartClear!=null &&
              this.cartClear.equals(other.getCartClear()))) &&
            ((this.transactionLookup==null && other.getTransactionLookup()==null) || 
             (this.transactionLookup!=null &&
              this.transactionLookup.equals(other.getTransactionLookup()))) &&
            ((this.sellerListingSearch==null && other.getSellerListingSearch()==null) || 
             (this.sellerListingSearch!=null &&
              this.sellerListingSearch.equals(other.getSellerListingSearch()))) &&
            ((this.sellerListingLookup==null && other.getSellerListingLookup()==null) || 
             (this.sellerListingLookup!=null &&
              this.sellerListingLookup.equals(other.getSellerListingLookup()))) &&
            ((this.browseNodeLookup==null && other.getBrowseNodeLookup()==null) || 
             (this.browseNodeLookup!=null &&
              this.browseNodeLookup.equals(other.getBrowseNodeLookup())));
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
        if (getHelp() != null) {
            _hashCode += getHelp().hashCode();
        }
        if (getItemSearch() != null) {
            _hashCode += getItemSearch().hashCode();
        }
        if (getItemLookup() != null) {
            _hashCode += getItemLookup().hashCode();
        }
        if (getListSearch() != null) {
            _hashCode += getListSearch().hashCode();
        }
        if (getListLookup() != null) {
            _hashCode += getListLookup().hashCode();
        }
        if (getCustomerContentSearch() != null) {
            _hashCode += getCustomerContentSearch().hashCode();
        }
        if (getCustomerContentLookup() != null) {
            _hashCode += getCustomerContentLookup().hashCode();
        }
        if (getSimilarityLookup() != null) {
            _hashCode += getSimilarityLookup().hashCode();
        }
        if (getSellerLookup() != null) {
            _hashCode += getSellerLookup().hashCode();
        }
        if (getCartGet() != null) {
            _hashCode += getCartGet().hashCode();
        }
        if (getCartAdd() != null) {
            _hashCode += getCartAdd().hashCode();
        }
        if (getCartCreate() != null) {
            _hashCode += getCartCreate().hashCode();
        }
        if (getCartModify() != null) {
            _hashCode += getCartModify().hashCode();
        }
        if (getCartClear() != null) {
            _hashCode += getCartClear().hashCode();
        }
        if (getTransactionLookup() != null) {
            _hashCode += getTransactionLookup().hashCode();
        }
        if (getSellerListingSearch() != null) {
            _hashCode += getSellerListingSearch().hashCode();
        }
        if (getSellerListingLookup() != null) {
            _hashCode += getSellerListingLookup().hashCode();
        }
        if (getBrowseNodeLookup() != null) {
            _hashCode += getBrowseNodeLookup().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MultiOperation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">MultiOperation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("help");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Help"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Help"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemSearch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearch"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listSearch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearch"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerContentSearch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearch"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerContentLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarityLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartGet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGet"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartAdd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAdd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAdd"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartCreate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartModify");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModify"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartClear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClear"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerListingSearch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearch"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerListingLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookup"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browseNodeLookup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookup"));
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
