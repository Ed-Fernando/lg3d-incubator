/**
 * SellerListing.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class SellerListing  implements java.io.Serializable {
    private java.lang.String exchangeId;
    private java.lang.String listingId;
    private java.lang.String ASIN;
    private java.lang.String SKU;
    private java.lang.String UPC;
    private java.lang.String EAN;
    private java.lang.Boolean willShipExpedited;
    private java.lang.Boolean willShipInternational;
    private java.lang.String title;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Price price;
    private java.lang.String startDate;
    private java.lang.String endDate;
    private java.lang.String status;
    private java.lang.String quantity;
    private java.lang.String condition;
    private java.lang.String subCondition;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller;

    public SellerListing() {
    }

    public SellerListing(
           java.lang.String exchangeId,
           java.lang.String listingId,
           java.lang.String ASIN,
           java.lang.String SKU,
           java.lang.String UPC,
           java.lang.String EAN,
           java.lang.Boolean willShipExpedited,
           java.lang.Boolean willShipInternational,
           java.lang.String title,
           org.jdesktop.lg3d.apps.lgamazon.stub.Price price,
           java.lang.String startDate,
           java.lang.String endDate,
           java.lang.String status,
           java.lang.String quantity,
           java.lang.String condition,
           java.lang.String subCondition,
           org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller) {
           this.exchangeId = exchangeId;
           this.listingId = listingId;
           this.ASIN = ASIN;
           this.SKU = SKU;
           this.UPC = UPC;
           this.EAN = EAN;
           this.willShipExpedited = willShipExpedited;
           this.willShipInternational = willShipInternational;
           this.title = title;
           this.price = price;
           this.startDate = startDate;
           this.endDate = endDate;
           this.status = status;
           this.quantity = quantity;
           this.condition = condition;
           this.subCondition = subCondition;
           this.seller = seller;
    }


    /**
     * Gets the exchangeId value for this SellerListing.
     * 
     * @return exchangeId
     */
    public java.lang.String getExchangeId() {
        return exchangeId;
    }


    /**
     * Sets the exchangeId value for this SellerListing.
     * 
     * @param exchangeId
     */
    public void setExchangeId(java.lang.String exchangeId) {
        this.exchangeId = exchangeId;
    }


    /**
     * Gets the listingId value for this SellerListing.
     * 
     * @return listingId
     */
    public java.lang.String getListingId() {
        return listingId;
    }


    /**
     * Sets the listingId value for this SellerListing.
     * 
     * @param listingId
     */
    public void setListingId(java.lang.String listingId) {
        this.listingId = listingId;
    }


    /**
     * Gets the ASIN value for this SellerListing.
     * 
     * @return ASIN
     */
    public java.lang.String getASIN() {
        return ASIN;
    }


    /**
     * Sets the ASIN value for this SellerListing.
     * 
     * @param ASIN
     */
    public void setASIN(java.lang.String ASIN) {
        this.ASIN = ASIN;
    }


    /**
     * Gets the SKU value for this SellerListing.
     * 
     * @return SKU
     */
    public java.lang.String getSKU() {
        return SKU;
    }


    /**
     * Sets the SKU value for this SellerListing.
     * 
     * @param SKU
     */
    public void setSKU(java.lang.String SKU) {
        this.SKU = SKU;
    }


    /**
     * Gets the UPC value for this SellerListing.
     * 
     * @return UPC
     */
    public java.lang.String getUPC() {
        return UPC;
    }


    /**
     * Sets the UPC value for this SellerListing.
     * 
     * @param UPC
     */
    public void setUPC(java.lang.String UPC) {
        this.UPC = UPC;
    }


    /**
     * Gets the EAN value for this SellerListing.
     * 
     * @return EAN
     */
    public java.lang.String getEAN() {
        return EAN;
    }


    /**
     * Sets the EAN value for this SellerListing.
     * 
     * @param EAN
     */
    public void setEAN(java.lang.String EAN) {
        this.EAN = EAN;
    }


    /**
     * Gets the willShipExpedited value for this SellerListing.
     * 
     * @return willShipExpedited
     */
    public java.lang.Boolean getWillShipExpedited() {
        return willShipExpedited;
    }


    /**
     * Sets the willShipExpedited value for this SellerListing.
     * 
     * @param willShipExpedited
     */
    public void setWillShipExpedited(java.lang.Boolean willShipExpedited) {
        this.willShipExpedited = willShipExpedited;
    }


    /**
     * Gets the willShipInternational value for this SellerListing.
     * 
     * @return willShipInternational
     */
    public java.lang.Boolean getWillShipInternational() {
        return willShipInternational;
    }


    /**
     * Sets the willShipInternational value for this SellerListing.
     * 
     * @param willShipInternational
     */
    public void setWillShipInternational(java.lang.Boolean willShipInternational) {
        this.willShipInternational = willShipInternational;
    }


    /**
     * Gets the title value for this SellerListing.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this SellerListing.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the price value for this SellerListing.
     * 
     * @return price
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Price getPrice() {
        return price;
    }


    /**
     * Sets the price value for this SellerListing.
     * 
     * @param price
     */
    public void setPrice(org.jdesktop.lg3d.apps.lgamazon.stub.Price price) {
        this.price = price;
    }


    /**
     * Gets the startDate value for this SellerListing.
     * 
     * @return startDate
     */
    public java.lang.String getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this SellerListing.
     * 
     * @param startDate
     */
    public void setStartDate(java.lang.String startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this SellerListing.
     * 
     * @return endDate
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this SellerListing.
     * 
     * @param endDate
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the status value for this SellerListing.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SellerListing.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the quantity value for this SellerListing.
     * 
     * @return quantity
     */
    public java.lang.String getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this SellerListing.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.String quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the condition value for this SellerListing.
     * 
     * @return condition
     */
    public java.lang.String getCondition() {
        return condition;
    }


    /**
     * Sets the condition value for this SellerListing.
     * 
     * @param condition
     */
    public void setCondition(java.lang.String condition) {
        this.condition = condition;
    }


    /**
     * Gets the subCondition value for this SellerListing.
     * 
     * @return subCondition
     */
    public java.lang.String getSubCondition() {
        return subCondition;
    }


    /**
     * Sets the subCondition value for this SellerListing.
     * 
     * @param subCondition
     */
    public void setSubCondition(java.lang.String subCondition) {
        this.subCondition = subCondition;
    }


    /**
     * Gets the seller value for this SellerListing.
     * 
     * @return seller
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Seller getSeller() {
        return seller;
    }


    /**
     * Sets the seller value for this SellerListing.
     * 
     * @param seller
     */
    public void setSeller(org.jdesktop.lg3d.apps.lgamazon.stub.Seller seller) {
        this.seller = seller;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerListing)) return false;
        SellerListing other = (SellerListing) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.exchangeId==null && other.getExchangeId()==null) || 
             (this.exchangeId!=null &&
              this.exchangeId.equals(other.getExchangeId()))) &&
            ((this.listingId==null && other.getListingId()==null) || 
             (this.listingId!=null &&
              this.listingId.equals(other.getListingId()))) &&
            ((this.ASIN==null && other.getASIN()==null) || 
             (this.ASIN!=null &&
              this.ASIN.equals(other.getASIN()))) &&
            ((this.SKU==null && other.getSKU()==null) || 
             (this.SKU!=null &&
              this.SKU.equals(other.getSKU()))) &&
            ((this.UPC==null && other.getUPC()==null) || 
             (this.UPC!=null &&
              this.UPC.equals(other.getUPC()))) &&
            ((this.EAN==null && other.getEAN()==null) || 
             (this.EAN!=null &&
              this.EAN.equals(other.getEAN()))) &&
            ((this.willShipExpedited==null && other.getWillShipExpedited()==null) || 
             (this.willShipExpedited!=null &&
              this.willShipExpedited.equals(other.getWillShipExpedited()))) &&
            ((this.willShipInternational==null && other.getWillShipInternational()==null) || 
             (this.willShipInternational!=null &&
              this.willShipInternational.equals(other.getWillShipInternational()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.quantity==null && other.getQuantity()==null) || 
             (this.quantity!=null &&
              this.quantity.equals(other.getQuantity()))) &&
            ((this.condition==null && other.getCondition()==null) || 
             (this.condition!=null &&
              this.condition.equals(other.getCondition()))) &&
            ((this.subCondition==null && other.getSubCondition()==null) || 
             (this.subCondition!=null &&
              this.subCondition.equals(other.getSubCondition()))) &&
            ((this.seller==null && other.getSeller()==null) || 
             (this.seller!=null &&
              this.seller.equals(other.getSeller())));
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
        if (getExchangeId() != null) {
            _hashCode += getExchangeId().hashCode();
        }
        if (getListingId() != null) {
            _hashCode += getListingId().hashCode();
        }
        if (getASIN() != null) {
            _hashCode += getASIN().hashCode();
        }
        if (getSKU() != null) {
            _hashCode += getSKU().hashCode();
        }
        if (getUPC() != null) {
            _hashCode += getUPC().hashCode();
        }
        if (getEAN() != null) {
            _hashCode += getEAN().hashCode();
        }
        if (getWillShipExpedited() != null) {
            _hashCode += getWillShipExpedited().hashCode();
        }
        if (getWillShipInternational() != null) {
            _hashCode += getWillShipInternational().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getQuantity() != null) {
            _hashCode += getQuantity().hashCode();
        }
        if (getCondition() != null) {
            _hashCode += getCondition().hashCode();
        }
        if (getSubCondition() != null) {
            _hashCode += getSubCondition().hashCode();
        }
        if (getSeller() != null) {
            _hashCode += getSeller().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SellerListing.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListing"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exchangeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ExchangeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listingId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListingId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ASIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ASIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SKU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SKU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UPC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "UPC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EAN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "EAN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("willShipExpedited");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "WillShipExpedited"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("willShipInternational");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "WillShipInternational"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "StartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Quantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("condition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Condition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subCondition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SubCondition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seller");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Seller"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Seller"));
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
