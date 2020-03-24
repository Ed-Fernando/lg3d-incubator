/**
 * Cart.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Cart  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.Request request;
    private java.lang.String cartId;
    private java.lang.String HMAC;
    private java.lang.String URLEncodedHMAC;
    private java.lang.String purchaseURL;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price subTotal;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CartItems cartItems;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SavedForLaterItems savedForLaterItems;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts;
    private org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers;
    private org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProducts similarViewedProducts;
    private org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProducts otherCategoriesSimilarProducts;

    public Cart() {
    }

    public Cart(
           org.jdesktop.lg3d.apps.lgamazon.stub.Request request,
           java.lang.String cartId,
           java.lang.String HMAC,
           java.lang.String URLEncodedHMAC,
           java.lang.String purchaseURL,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price subTotal,
           org.jdesktop.lg3d.apps.lgamazon.stub.CartItems cartItems,
           org.jdesktop.lg3d.apps.lgamazon.stub.SavedForLaterItems savedForLaterItems,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts,
           org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers,
           org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProducts similarViewedProducts,
           org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProducts otherCategoriesSimilarProducts) {
           this.request = request;
           this.cartId = cartId;
           this.HMAC = HMAC;
           this.URLEncodedHMAC = URLEncodedHMAC;
           this.purchaseURL = purchaseURL;
           this.subTotal = subTotal;
           this.cartItems = cartItems;
           this.savedForLaterItems = savedForLaterItems;
           this.similarProducts = similarProducts;
           this.topSellers = topSellers;
           this.newReleases = newReleases;
           this.similarViewedProducts = similarViewedProducts;
           this.otherCategoriesSimilarProducts = otherCategoriesSimilarProducts;
    }


    /**
     * Gets the request value for this Cart.
     * 
     * @return request
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Request getRequest() {
        return request;
    }


    /**
     * Sets the request value for this Cart.
     * 
     * @param request
     */
    public void setRequest(org.jdesktop.lg3d.apps.lgamazon.stub.Request request) {
        this.request = request;
    }


    /**
     * Gets the cartId value for this Cart.
     * 
     * @return cartId
     */
    public java.lang.String getCartId() {
        return cartId;
    }


    /**
     * Sets the cartId value for this Cart.
     * 
     * @param cartId
     */
    public void setCartId(java.lang.String cartId) {
        this.cartId = cartId;
    }


    /**
     * Gets the HMAC value for this Cart.
     * 
     * @return HMAC
     */
    public java.lang.String getHMAC() {
        return HMAC;
    }


    /**
     * Sets the HMAC value for this Cart.
     * 
     * @param HMAC
     */
    public void setHMAC(java.lang.String HMAC) {
        this.HMAC = HMAC;
    }


    /**
     * Gets the URLEncodedHMAC value for this Cart.
     * 
     * @return URLEncodedHMAC
     */
    public java.lang.String getURLEncodedHMAC() {
        return URLEncodedHMAC;
    }


    /**
     * Sets the URLEncodedHMAC value for this Cart.
     * 
     * @param URLEncodedHMAC
     */
    public void setURLEncodedHMAC(java.lang.String URLEncodedHMAC) {
        this.URLEncodedHMAC = URLEncodedHMAC;
    }


    /**
     * Gets the purchaseURL value for this Cart.
     * 
     * @return purchaseURL
     */
    public java.lang.String getPurchaseURL() {
        return purchaseURL;
    }


    /**
     * Sets the purchaseURL value for this Cart.
     * 
     * @param purchaseURL
     */
    public void setPurchaseURL(java.lang.String purchaseURL) {
        this.purchaseURL = purchaseURL;
    }


    /**
     * Gets the subTotal value for this Cart.
     * 
     * @return subTotal
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getSubTotal() {
        return subTotal;
    }


    /**
     * Sets the subTotal value for this Cart.
     * 
     * @param subTotal
     */
    public void setSubTotal(org.jdesktop.lg3d.apps.lgamazon.stub.Price subTotal) {
        this.subTotal = subTotal;
    }


    /**
     * Gets the cartItems value for this Cart.
     * 
     * @return cartItems
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CartItems getCartItems() {
        return cartItems;
    }


    /**
     * Sets the cartItems value for this Cart.
     * 
     * @param cartItems
     */
    public void setCartItems(org.jdesktop.lg3d.apps.lgamazon.stub.CartItems cartItems) {
        this.cartItems = cartItems;
    }


    /**
     * Gets the savedForLaterItems value for this Cart.
     * 
     * @return savedForLaterItems
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SavedForLaterItems getSavedForLaterItems() {
        return savedForLaterItems;
    }


    /**
     * Sets the savedForLaterItems value for this Cart.
     * 
     * @param savedForLaterItems
     */
    public void setSavedForLaterItems(org.jdesktop.lg3d.apps.lgamazon.stub.SavedForLaterItems savedForLaterItems) {
        this.savedForLaterItems = savedForLaterItems;
    }


    /**
     * Gets the similarProducts value for this Cart.
     * 
     * @return similarProducts
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts getSimilarProducts() {
        return similarProducts;
    }


    /**
     * Sets the similarProducts value for this Cart.
     * 
     * @param similarProducts
     */
    public void setSimilarProducts(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts) {
        this.similarProducts = similarProducts;
    }


    /**
     * Gets the topSellers value for this Cart.
     * 
     * @return topSellers
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers getTopSellers() {
        return topSellers;
    }


    /**
     * Sets the topSellers value for this Cart.
     * 
     * @param topSellers
     */
    public void setTopSellers(org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers) {
        this.topSellers = topSellers;
    }


    /**
     * Gets the newReleases value for this Cart.
     * 
     * @return newReleases
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases getNewReleases() {
        return newReleases;
    }


    /**
     * Sets the newReleases value for this Cart.
     * 
     * @param newReleases
     */
    public void setNewReleases(org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases) {
        this.newReleases = newReleases;
    }


    /**
     * Gets the similarViewedProducts value for this Cart.
     * 
     * @return similarViewedProducts
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProducts getSimilarViewedProducts() {
        return similarViewedProducts;
    }


    /**
     * Sets the similarViewedProducts value for this Cart.
     * 
     * @param similarViewedProducts
     */
    public void setSimilarViewedProducts(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProducts similarViewedProducts) {
        this.similarViewedProducts = similarViewedProducts;
    }


    /**
     * Gets the otherCategoriesSimilarProducts value for this Cart.
     * 
     * @return otherCategoriesSimilarProducts
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProducts getOtherCategoriesSimilarProducts() {
        return otherCategoriesSimilarProducts;
    }


    /**
     * Sets the otherCategoriesSimilarProducts value for this Cart.
     * 
     * @param otherCategoriesSimilarProducts
     */
    public void setOtherCategoriesSimilarProducts(org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProducts otherCategoriesSimilarProducts) {
        this.otherCategoriesSimilarProducts = otherCategoriesSimilarProducts;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Cart)) return false;
        Cart other = (Cart) obj;
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
            ((this.cartId==null && other.getCartId()==null) || 
             (this.cartId!=null &&
              this.cartId.equals(other.getCartId()))) &&
            ((this.HMAC==null && other.getHMAC()==null) || 
             (this.HMAC!=null &&
              this.HMAC.equals(other.getHMAC()))) &&
            ((this.URLEncodedHMAC==null && other.getURLEncodedHMAC()==null) || 
             (this.URLEncodedHMAC!=null &&
              this.URLEncodedHMAC.equals(other.getURLEncodedHMAC()))) &&
            ((this.purchaseURL==null && other.getPurchaseURL()==null) || 
             (this.purchaseURL!=null &&
              this.purchaseURL.equals(other.getPurchaseURL()))) &&
            ((this.subTotal==null && other.getSubTotal()==null) || 
             (this.subTotal!=null &&
              this.subTotal.equals(other.getSubTotal()))) &&
            ((this.cartItems==null && other.getCartItems()==null) || 
             (this.cartItems!=null &&
              this.cartItems.equals(other.getCartItems()))) &&
            ((this.savedForLaterItems==null && other.getSavedForLaterItems()==null) || 
             (this.savedForLaterItems!=null &&
              this.savedForLaterItems.equals(other.getSavedForLaterItems()))) &&
            ((this.similarProducts==null && other.getSimilarProducts()==null) || 
             (this.similarProducts!=null &&
              this.similarProducts.equals(other.getSimilarProducts()))) &&
            ((this.topSellers==null && other.getTopSellers()==null) || 
             (this.topSellers!=null &&
              this.topSellers.equals(other.getTopSellers()))) &&
            ((this.newReleases==null && other.getNewReleases()==null) || 
             (this.newReleases!=null &&
              this.newReleases.equals(other.getNewReleases()))) &&
            ((this.similarViewedProducts==null && other.getSimilarViewedProducts()==null) || 
             (this.similarViewedProducts!=null &&
              this.similarViewedProducts.equals(other.getSimilarViewedProducts()))) &&
            ((this.otherCategoriesSimilarProducts==null && other.getOtherCategoriesSimilarProducts()==null) || 
             (this.otherCategoriesSimilarProducts!=null &&
              this.otherCategoriesSimilarProducts.equals(other.getOtherCategoriesSimilarProducts())));
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
        if (getCartId() != null) {
            _hashCode += getCartId().hashCode();
        }
        if (getHMAC() != null) {
            _hashCode += getHMAC().hashCode();
        }
        if (getURLEncodedHMAC() != null) {
            _hashCode += getURLEncodedHMAC().hashCode();
        }
        if (getPurchaseURL() != null) {
            _hashCode += getPurchaseURL().hashCode();
        }
        if (getSubTotal() != null) {
            _hashCode += getSubTotal().hashCode();
        }
        if (getCartItems() != null) {
            _hashCode += getCartItems().hashCode();
        }
        if (getSavedForLaterItems() != null) {
            _hashCode += getSavedForLaterItems().hashCode();
        }
        if (getSimilarProducts() != null) {
            _hashCode += getSimilarProducts().hashCode();
        }
        if (getTopSellers() != null) {
            _hashCode += getTopSellers().hashCode();
        }
        if (getNewReleases() != null) {
            _hashCode += getNewReleases().hashCode();
        }
        if (getSimilarViewedProducts() != null) {
            _hashCode += getSimilarViewedProducts().hashCode();
        }
        if (getOtherCategoriesSimilarProducts() != null) {
            _hashCode += getOtherCategoriesSimilarProducts().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cart.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Cart"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Request"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HMAC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HMAC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("URLEncodedHMAC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "URLEncodedHMAC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaseURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PurchaseURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SubTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cartItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartItems"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("savedForLaterItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SavedForLaterItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SavedForLaterItems"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarProducts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarProducts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarProducts"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topSellers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TopSellers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TopSellers"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newReleases");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "NewReleases"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "NewReleases"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("similarViewedProducts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarViewedProducts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarViewedProducts"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCategoriesSimilarProducts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OtherCategoriesSimilarProducts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OtherCategoriesSimilarProducts"));
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
