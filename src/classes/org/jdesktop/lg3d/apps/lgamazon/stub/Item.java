/**
 * Item.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class Item  implements java.io.Serializable {
    private java.lang.String ASIN;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors;
    private java.lang.String detailPageURL;
    private java.lang.String salesRank;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemImageSets imageSets;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes itemAttributes;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ItemSubjects subjects;
    private org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary offerSummary;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Offers offers;
    private org.jdesktop.lg3d.apps.lgamazon.stub.VariationSummary variationSummary;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Variations variations;
    private org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews customerReviews;
    private org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews editorialReviews;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Accessories accessories;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Tracks tracks;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes browseNodes;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaLists listmaniaLists;
    private org.jdesktop.lg3d.apps.lgamazon.stub.SearchInside searchInside;
    private org.jdesktop.lg3d.apps.lgamazon.stub.PromotionalTag promotionalTag;

    public Item() {
    }

    public Item(
           java.lang.String ASIN,
           org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors,
           java.lang.String detailPageURL,
           java.lang.String salesRank,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemImageSets imageSets,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes itemAttributes,
           org.jdesktop.lg3d.apps.lgamazon.stub.ItemSubjects subjects,
           org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary offerSummary,
           org.jdesktop.lg3d.apps.lgamazon.stub.Offers offers,
           org.jdesktop.lg3d.apps.lgamazon.stub.VariationSummary variationSummary,
           org.jdesktop.lg3d.apps.lgamazon.stub.Variations variations,
           org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews customerReviews,
           org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews editorialReviews,
           org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts,
           org.jdesktop.lg3d.apps.lgamazon.stub.Accessories accessories,
           org.jdesktop.lg3d.apps.lgamazon.stub.Tracks tracks,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes browseNodes,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaLists listmaniaLists,
           org.jdesktop.lg3d.apps.lgamazon.stub.SearchInside searchInside,
           org.jdesktop.lg3d.apps.lgamazon.stub.PromotionalTag promotionalTag) {
           this.ASIN = ASIN;
           this.errors = errors;
           this.detailPageURL = detailPageURL;
           this.salesRank = salesRank;
           this.smallImage = smallImage;
           this.mediumImage = mediumImage;
           this.largeImage = largeImage;
           this.imageSets = imageSets;
           this.itemAttributes = itemAttributes;
           this.subjects = subjects;
           this.offerSummary = offerSummary;
           this.offers = offers;
           this.variationSummary = variationSummary;
           this.variations = variations;
           this.customerReviews = customerReviews;
           this.editorialReviews = editorialReviews;
           this.similarProducts = similarProducts;
           this.accessories = accessories;
           this.tracks = tracks;
           this.browseNodes = browseNodes;
           this.listmaniaLists = listmaniaLists;
           this.searchInside = searchInside;
           this.promotionalTag = promotionalTag;
    }


    /**
     * Gets the ASIN value for this Item.
     * 
     * @return ASIN
     */
    public java.lang.String getASIN() {
        return ASIN;
    }


    /**
     * Sets the ASIN value for this Item.
     * 
     * @param ASIN
     */
    public void setASIN(java.lang.String ASIN) {
        this.ASIN = ASIN;
    }


    /**
     * Gets the errors value for this Item.
     * 
     * @return errors
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Errors getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this Item.
     * 
     * @param errors
     */
    public void setErrors(org.jdesktop.lg3d.apps.lgamazon.stub.Errors errors) {
        this.errors = errors;
    }


    /**
     * Gets the detailPageURL value for this Item.
     * 
     * @return detailPageURL
     */
    public java.lang.String getDetailPageURL() {
        return detailPageURL;
    }


    /**
     * Sets the detailPageURL value for this Item.
     * 
     * @param detailPageURL
     */
    public void setDetailPageURL(java.lang.String detailPageURL) {
        this.detailPageURL = detailPageURL;
    }


    /**
     * Gets the salesRank value for this Item.
     * 
     * @return salesRank
     */
    public java.lang.String getSalesRank() {
        return salesRank;
    }


    /**
     * Sets the salesRank value for this Item.
     * 
     * @param salesRank
     */
    public void setSalesRank(java.lang.String salesRank) {
        this.salesRank = salesRank;
    }


    /**
     * Gets the smallImage value for this Item.
     * 
     * @return smallImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getSmallImage() {
        return smallImage;
    }


    /**
     * Sets the smallImage value for this Item.
     * 
     * @param smallImage
     */
    public void setSmallImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image smallImage) {
        this.smallImage = smallImage;
    }


    /**
     * Gets the mediumImage value for this Item.
     * 
     * @return mediumImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getMediumImage() {
        return mediumImage;
    }


    /**
     * Sets the mediumImage value for this Item.
     * 
     * @param mediumImage
     */
    public void setMediumImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image mediumImage) {
        this.mediumImage = mediumImage;
    }


    /**
     * Gets the largeImage value for this Item.
     * 
     * @return largeImage
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getLargeImage() {
        return largeImage;
    }


    /**
     * Sets the largeImage value for this Item.
     * 
     * @param largeImage
     */
    public void setLargeImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image largeImage) {
        this.largeImage = largeImage;
    }


    /**
     * Gets the imageSets value for this Item.
     * 
     * @return imageSets
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemImageSets getImageSets() {
        return imageSets;
    }


    /**
     * Sets the imageSets value for this Item.
     * 
     * @param imageSets
     */
    public void setImageSets(org.jdesktop.lg3d.apps.lgamazon.stub.ItemImageSets imageSets) {
        this.imageSets = imageSets;
    }


    /**
     * Gets the itemAttributes value for this Item.
     * 
     * @return itemAttributes
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes getItemAttributes() {
        return itemAttributes;
    }


    /**
     * Sets the itemAttributes value for this Item.
     * 
     * @param itemAttributes
     */
    public void setItemAttributes(org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes itemAttributes) {
        this.itemAttributes = itemAttributes;
    }


    /**
     * Gets the subjects value for this Item.
     * 
     * @return subjects
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemSubjects getSubjects() {
        return subjects;
    }


    /**
     * Sets the subjects value for this Item.
     * 
     * @param subjects
     */
    public void setSubjects(org.jdesktop.lg3d.apps.lgamazon.stub.ItemSubjects subjects) {
        this.subjects = subjects;
    }


    /**
     * Gets the offerSummary value for this Item.
     * 
     * @return offerSummary
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary getOfferSummary() {
        return offerSummary;
    }


    /**
     * Sets the offerSummary value for this Item.
     * 
     * @param offerSummary
     */
    public void setOfferSummary(org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary offerSummary) {
        this.offerSummary = offerSummary;
    }


    /**
     * Gets the offers value for this Item.
     * 
     * @return offers
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Offers getOffers() {
        return offers;
    }


    /**
     * Sets the offers value for this Item.
     * 
     * @param offers
     */
    public void setOffers(org.jdesktop.lg3d.apps.lgamazon.stub.Offers offers) {
        this.offers = offers;
    }


    /**
     * Gets the variationSummary value for this Item.
     * 
     * @return variationSummary
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.VariationSummary getVariationSummary() {
        return variationSummary;
    }


    /**
     * Sets the variationSummary value for this Item.
     * 
     * @param variationSummary
     */
    public void setVariationSummary(org.jdesktop.lg3d.apps.lgamazon.stub.VariationSummary variationSummary) {
        this.variationSummary = variationSummary;
    }


    /**
     * Gets the variations value for this Item.
     * 
     * @return variations
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Variations getVariations() {
        return variations;
    }


    /**
     * Sets the variations value for this Item.
     * 
     * @param variations
     */
    public void setVariations(org.jdesktop.lg3d.apps.lgamazon.stub.Variations variations) {
        this.variations = variations;
    }


    /**
     * Gets the customerReviews value for this Item.
     * 
     * @return customerReviews
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews getCustomerReviews() {
        return customerReviews;
    }


    /**
     * Sets the customerReviews value for this Item.
     * 
     * @param customerReviews
     */
    public void setCustomerReviews(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews customerReviews) {
        this.customerReviews = customerReviews;
    }


    /**
     * Gets the editorialReviews value for this Item.
     * 
     * @return editorialReviews
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews getEditorialReviews() {
        return editorialReviews;
    }


    /**
     * Sets the editorialReviews value for this Item.
     * 
     * @param editorialReviews
     */
    public void setEditorialReviews(org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews editorialReviews) {
        this.editorialReviews = editorialReviews;
    }


    /**
     * Gets the similarProducts value for this Item.
     * 
     * @return similarProducts
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts getSimilarProducts() {
        return similarProducts;
    }


    /**
     * Sets the similarProducts value for this Item.
     * 
     * @param similarProducts
     */
    public void setSimilarProducts(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts similarProducts) {
        this.similarProducts = similarProducts;
    }


    /**
     * Gets the accessories value for this Item.
     * 
     * @return accessories
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Accessories getAccessories() {
        return accessories;
    }


    /**
     * Sets the accessories value for this Item.
     * 
     * @param accessories
     */
    public void setAccessories(org.jdesktop.lg3d.apps.lgamazon.stub.Accessories accessories) {
        this.accessories = accessories;
    }


    /**
     * Gets the tracks value for this Item.
     * 
     * @return tracks
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Tracks getTracks() {
        return tracks;
    }


    /**
     * Sets the tracks value for this Item.
     * 
     * @param tracks
     */
    public void setTracks(org.jdesktop.lg3d.apps.lgamazon.stub.Tracks tracks) {
        this.tracks = tracks;
    }


    /**
     * Gets the browseNodes value for this Item.
     * 
     * @return browseNodes
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes getBrowseNodes() {
        return browseNodes;
    }


    /**
     * Sets the browseNodes value for this Item.
     * 
     * @param browseNodes
     */
    public void setBrowseNodes(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes browseNodes) {
        this.browseNodes = browseNodes;
    }


    /**
     * Gets the listmaniaLists value for this Item.
     * 
     * @return listmaniaLists
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaLists getListmaniaLists() {
        return listmaniaLists;
    }


    /**
     * Sets the listmaniaLists value for this Item.
     * 
     * @param listmaniaLists
     */
    public void setListmaniaLists(org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaLists listmaniaLists) {
        this.listmaniaLists = listmaniaLists;
    }


    /**
     * Gets the searchInside value for this Item.
     * 
     * @return searchInside
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.SearchInside getSearchInside() {
        return searchInside;
    }


    /**
     * Sets the searchInside value for this Item.
     * 
     * @param searchInside
     */
    public void setSearchInside(org.jdesktop.lg3d.apps.lgamazon.stub.SearchInside searchInside) {
        this.searchInside = searchInside;
    }


    /**
     * Gets the promotionalTag value for this Item.
     * 
     * @return promotionalTag
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.PromotionalTag getPromotionalTag() {
        return promotionalTag;
    }


    /**
     * Sets the promotionalTag value for this Item.
     * 
     * @param promotionalTag
     */
    public void setPromotionalTag(org.jdesktop.lg3d.apps.lgamazon.stub.PromotionalTag promotionalTag) {
        this.promotionalTag = promotionalTag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Item)) return false;
        Item other = (Item) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ASIN==null && other.getASIN()==null) || 
             (this.ASIN!=null &&
              this.ASIN.equals(other.getASIN()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              this.errors.equals(other.getErrors()))) &&
            ((this.detailPageURL==null && other.getDetailPageURL()==null) || 
             (this.detailPageURL!=null &&
              this.detailPageURL.equals(other.getDetailPageURL()))) &&
            ((this.salesRank==null && other.getSalesRank()==null) || 
             (this.salesRank!=null &&
              this.salesRank.equals(other.getSalesRank()))) &&
            ((this.smallImage==null && other.getSmallImage()==null) || 
             (this.smallImage!=null &&
              this.smallImage.equals(other.getSmallImage()))) &&
            ((this.mediumImage==null && other.getMediumImage()==null) || 
             (this.mediumImage!=null &&
              this.mediumImage.equals(other.getMediumImage()))) &&
            ((this.largeImage==null && other.getLargeImage()==null) || 
             (this.largeImage!=null &&
              this.largeImage.equals(other.getLargeImage()))) &&
            ((this.imageSets==null && other.getImageSets()==null) || 
             (this.imageSets!=null &&
              this.imageSets.equals(other.getImageSets()))) &&
            ((this.itemAttributes==null && other.getItemAttributes()==null) || 
             (this.itemAttributes!=null &&
              this.itemAttributes.equals(other.getItemAttributes()))) &&
            ((this.subjects==null && other.getSubjects()==null) || 
             (this.subjects!=null &&
              this.subjects.equals(other.getSubjects()))) &&
            ((this.offerSummary==null && other.getOfferSummary()==null) || 
             (this.offerSummary!=null &&
              this.offerSummary.equals(other.getOfferSummary()))) &&
            ((this.offers==null && other.getOffers()==null) || 
             (this.offers!=null &&
              this.offers.equals(other.getOffers()))) &&
            ((this.variationSummary==null && other.getVariationSummary()==null) || 
             (this.variationSummary!=null &&
              this.variationSummary.equals(other.getVariationSummary()))) &&
            ((this.variations==null && other.getVariations()==null) || 
             (this.variations!=null &&
              this.variations.equals(other.getVariations()))) &&
            ((this.customerReviews==null && other.getCustomerReviews()==null) || 
             (this.customerReviews!=null &&
              this.customerReviews.equals(other.getCustomerReviews()))) &&
            ((this.editorialReviews==null && other.getEditorialReviews()==null) || 
             (this.editorialReviews!=null &&
              this.editorialReviews.equals(other.getEditorialReviews()))) &&
            ((this.similarProducts==null && other.getSimilarProducts()==null) || 
             (this.similarProducts!=null &&
              this.similarProducts.equals(other.getSimilarProducts()))) &&
            ((this.accessories==null && other.getAccessories()==null) || 
             (this.accessories!=null &&
              this.accessories.equals(other.getAccessories()))) &&
            ((this.tracks==null && other.getTracks()==null) || 
             (this.tracks!=null &&
              this.tracks.equals(other.getTracks()))) &&
            ((this.browseNodes==null && other.getBrowseNodes()==null) || 
             (this.browseNodes!=null &&
              this.browseNodes.equals(other.getBrowseNodes()))) &&
            ((this.listmaniaLists==null && other.getListmaniaLists()==null) || 
             (this.listmaniaLists!=null &&
              this.listmaniaLists.equals(other.getListmaniaLists()))) &&
            ((this.searchInside==null && other.getSearchInside()==null) || 
             (this.searchInside!=null &&
              this.searchInside.equals(other.getSearchInside()))) &&
            ((this.promotionalTag==null && other.getPromotionalTag()==null) || 
             (this.promotionalTag!=null &&
              this.promotionalTag.equals(other.getPromotionalTag())));
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
        if (getASIN() != null) {
            _hashCode += getASIN().hashCode();
        }
        if (getErrors() != null) {
            _hashCode += getErrors().hashCode();
        }
        if (getDetailPageURL() != null) {
            _hashCode += getDetailPageURL().hashCode();
        }
        if (getSalesRank() != null) {
            _hashCode += getSalesRank().hashCode();
        }
        if (getSmallImage() != null) {
            _hashCode += getSmallImage().hashCode();
        }
        if (getMediumImage() != null) {
            _hashCode += getMediumImage().hashCode();
        }
        if (getLargeImage() != null) {
            _hashCode += getLargeImage().hashCode();
        }
        if (getImageSets() != null) {
            _hashCode += getImageSets().hashCode();
        }
        if (getItemAttributes() != null) {
            _hashCode += getItemAttributes().hashCode();
        }
        if (getSubjects() != null) {
            _hashCode += getSubjects().hashCode();
        }
        if (getOfferSummary() != null) {
            _hashCode += getOfferSummary().hashCode();
        }
        if (getOffers() != null) {
            _hashCode += getOffers().hashCode();
        }
        if (getVariationSummary() != null) {
            _hashCode += getVariationSummary().hashCode();
        }
        if (getVariations() != null) {
            _hashCode += getVariations().hashCode();
        }
        if (getCustomerReviews() != null) {
            _hashCode += getCustomerReviews().hashCode();
        }
        if (getEditorialReviews() != null) {
            _hashCode += getEditorialReviews().hashCode();
        }
        if (getSimilarProducts() != null) {
            _hashCode += getSimilarProducts().hashCode();
        }
        if (getAccessories() != null) {
            _hashCode += getAccessories().hashCode();
        }
        if (getTracks() != null) {
            _hashCode += getTracks().hashCode();
        }
        if (getBrowseNodes() != null) {
            _hashCode += getBrowseNodes().hashCode();
        }
        if (getListmaniaLists() != null) {
            _hashCode += getListmaniaLists().hashCode();
        }
        if (getSearchInside() != null) {
            _hashCode += getSearchInside().hashCode();
        }
        if (getPromotionalTag() != null) {
            _hashCode += getPromotionalTag().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Item.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ASIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ASIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Errors"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailPageURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DetailPageURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesRank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SalesRank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smallImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SmallImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediumImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MediumImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("largeImage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "LargeImage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageSets");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ImageSets"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Item>ImageSets"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemAttributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemAttributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemAttributes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subjects");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Subjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Item>Subjects"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offerSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OfferSummary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Offers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Offers"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variationSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "VariationSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "VariationSummary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Variations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Variations"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReviews");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerReviews"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerReviews"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("editorialReviews");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "EditorialReviews"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "EditorialReviews"));
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
        elemField.setFieldName("accessories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Accessories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Accessories"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tracks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Tracks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Tracks"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browseNodes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodes"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listmaniaLists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListmaniaLists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListmaniaLists"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchInside");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchInside"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SearchInside"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("promotionalTag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PromotionalTag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PromotionalTag"));
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
