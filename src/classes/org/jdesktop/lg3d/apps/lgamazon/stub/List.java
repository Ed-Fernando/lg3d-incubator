/**
 * List.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class List  implements java.io.Serializable {
    private java.lang.String listId;
    private java.lang.String listURL;
    private java.lang.String registryNumber;
    private java.lang.String listName;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListListType listType;
    private org.apache.axis.types.NonNegativeInteger totalItems;
    private org.apache.axis.types.NonNegativeInteger totalPages;
    private java.lang.String dateCreated;
    private java.lang.String occasionDate;
    private java.lang.String customerName;
    private java.lang.String partnerName;
    private java.lang.String additionalName;
    private java.lang.String comment;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Image image;
    private java.math.BigDecimal averageRating;
    private org.apache.axis.types.NonNegativeInteger totalVotes;
    private org.apache.axis.types.NonNegativeInteger totalTimesRead;
    private org.jdesktop.lg3d.apps.lgamazon.stub.ListItem[] listItem;

    public List() {
    }

    public List(
           java.lang.String listId,
           java.lang.String listURL,
           java.lang.String registryNumber,
           java.lang.String listName,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListListType listType,
           org.apache.axis.types.NonNegativeInteger totalItems,
           org.apache.axis.types.NonNegativeInteger totalPages,
           java.lang.String dateCreated,
           java.lang.String occasionDate,
           java.lang.String customerName,
           java.lang.String partnerName,
           java.lang.String additionalName,
           java.lang.String comment,
           org.jdesktop.lg3d.apps.lgamazon.stub.Image image,
           java.math.BigDecimal averageRating,
           org.apache.axis.types.NonNegativeInteger totalVotes,
           org.apache.axis.types.NonNegativeInteger totalTimesRead,
           org.jdesktop.lg3d.apps.lgamazon.stub.ListItem[] listItem) {
           this.listId = listId;
           this.listURL = listURL;
           this.registryNumber = registryNumber;
           this.listName = listName;
           this.listType = listType;
           this.totalItems = totalItems;
           this.totalPages = totalPages;
           this.dateCreated = dateCreated;
           this.occasionDate = occasionDate;
           this.customerName = customerName;
           this.partnerName = partnerName;
           this.additionalName = additionalName;
           this.comment = comment;
           this.image = image;
           this.averageRating = averageRating;
           this.totalVotes = totalVotes;
           this.totalTimesRead = totalTimesRead;
           this.listItem = listItem;
    }


    /**
     * Gets the listId value for this List.
     * 
     * @return listId
     */
    public java.lang.String getListId() {
        return listId;
    }


    /**
     * Sets the listId value for this List.
     * 
     * @param listId
     */
    public void setListId(java.lang.String listId) {
        this.listId = listId;
    }


    /**
     * Gets the listURL value for this List.
     * 
     * @return listURL
     */
    public java.lang.String getListURL() {
        return listURL;
    }


    /**
     * Sets the listURL value for this List.
     * 
     * @param listURL
     */
    public void setListURL(java.lang.String listURL) {
        this.listURL = listURL;
    }


    /**
     * Gets the registryNumber value for this List.
     * 
     * @return registryNumber
     */
    public java.lang.String getRegistryNumber() {
        return registryNumber;
    }


    /**
     * Sets the registryNumber value for this List.
     * 
     * @param registryNumber
     */
    public void setRegistryNumber(java.lang.String registryNumber) {
        this.registryNumber = registryNumber;
    }


    /**
     * Gets the listName value for this List.
     * 
     * @return listName
     */
    public java.lang.String getListName() {
        return listName;
    }


    /**
     * Sets the listName value for this List.
     * 
     * @param listName
     */
    public void setListName(java.lang.String listName) {
        this.listName = listName;
    }


    /**
     * Gets the listType value for this List.
     * 
     * @return listType
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListListType getListType() {
        return listType;
    }


    /**
     * Sets the listType value for this List.
     * 
     * @param listType
     */
    public void setListType(org.jdesktop.lg3d.apps.lgamazon.stub.ListListType listType) {
        this.listType = listType;
    }


    /**
     * Gets the totalItems value for this List.
     * 
     * @return totalItems
     */
    public org.apache.axis.types.NonNegativeInteger getTotalItems() {
        return totalItems;
    }


    /**
     * Sets the totalItems value for this List.
     * 
     * @param totalItems
     */
    public void setTotalItems(org.apache.axis.types.NonNegativeInteger totalItems) {
        this.totalItems = totalItems;
    }


    /**
     * Gets the totalPages value for this List.
     * 
     * @return totalPages
     */
    public org.apache.axis.types.NonNegativeInteger getTotalPages() {
        return totalPages;
    }


    /**
     * Sets the totalPages value for this List.
     * 
     * @param totalPages
     */
    public void setTotalPages(org.apache.axis.types.NonNegativeInteger totalPages) {
        this.totalPages = totalPages;
    }


    /**
     * Gets the dateCreated value for this List.
     * 
     * @return dateCreated
     */
    public java.lang.String getDateCreated() {
        return dateCreated;
    }


    /**
     * Sets the dateCreated value for this List.
     * 
     * @param dateCreated
     */
    public void setDateCreated(java.lang.String dateCreated) {
        this.dateCreated = dateCreated;
    }


    /**
     * Gets the occasionDate value for this List.
     * 
     * @return occasionDate
     */
    public java.lang.String getOccasionDate() {
        return occasionDate;
    }


    /**
     * Sets the occasionDate value for this List.
     * 
     * @param occasionDate
     */
    public void setOccasionDate(java.lang.String occasionDate) {
        this.occasionDate = occasionDate;
    }


    /**
     * Gets the customerName value for this List.
     * 
     * @return customerName
     */
    public java.lang.String getCustomerName() {
        return customerName;
    }


    /**
     * Sets the customerName value for this List.
     * 
     * @param customerName
     */
    public void setCustomerName(java.lang.String customerName) {
        this.customerName = customerName;
    }


    /**
     * Gets the partnerName value for this List.
     * 
     * @return partnerName
     */
    public java.lang.String getPartnerName() {
        return partnerName;
    }


    /**
     * Sets the partnerName value for this List.
     * 
     * @param partnerName
     */
    public void setPartnerName(java.lang.String partnerName) {
        this.partnerName = partnerName;
    }


    /**
     * Gets the additionalName value for this List.
     * 
     * @return additionalName
     */
    public java.lang.String getAdditionalName() {
        return additionalName;
    }


    /**
     * Sets the additionalName value for this List.
     * 
     * @param additionalName
     */
    public void setAdditionalName(java.lang.String additionalName) {
        this.additionalName = additionalName;
    }


    /**
     * Gets the comment value for this List.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this List.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the image value for this List.
     * 
     * @return image
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Image getImage() {
        return image;
    }


    /**
     * Sets the image value for this List.
     * 
     * @param image
     */
    public void setImage(org.jdesktop.lg3d.apps.lgamazon.stub.Image image) {
        this.image = image;
    }


    /**
     * Gets the averageRating value for this List.
     * 
     * @return averageRating
     */
    public java.math.BigDecimal getAverageRating() {
        return averageRating;
    }


    /**
     * Sets the averageRating value for this List.
     * 
     * @param averageRating
     */
    public void setAverageRating(java.math.BigDecimal averageRating) {
        this.averageRating = averageRating;
    }


    /**
     * Gets the totalVotes value for this List.
     * 
     * @return totalVotes
     */
    public org.apache.axis.types.NonNegativeInteger getTotalVotes() {
        return totalVotes;
    }


    /**
     * Sets the totalVotes value for this List.
     * 
     * @param totalVotes
     */
    public void setTotalVotes(org.apache.axis.types.NonNegativeInteger totalVotes) {
        this.totalVotes = totalVotes;
    }


    /**
     * Gets the totalTimesRead value for this List.
     * 
     * @return totalTimesRead
     */
    public org.apache.axis.types.NonNegativeInteger getTotalTimesRead() {
        return totalTimesRead;
    }


    /**
     * Sets the totalTimesRead value for this List.
     * 
     * @param totalTimesRead
     */
    public void setTotalTimesRead(org.apache.axis.types.NonNegativeInteger totalTimesRead) {
        this.totalTimesRead = totalTimesRead;
    }


    /**
     * Gets the listItem value for this List.
     * 
     * @return listItem
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.ListItem[] getListItem() {
        return listItem;
    }


    /**
     * Sets the listItem value for this List.
     * 
     * @param listItem
     */
    public void setListItem(org.jdesktop.lg3d.apps.lgamazon.stub.ListItem[] listItem) {
        this.listItem = listItem;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ListItem getListItem(int i) {
        return this.listItem[i];
    }

    public void setListItem(int i, org.jdesktop.lg3d.apps.lgamazon.stub.ListItem _value) {
        this.listItem[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof List)) return false;
        List other = (List) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listId==null && other.getListId()==null) || 
             (this.listId!=null &&
              this.listId.equals(other.getListId()))) &&
            ((this.listURL==null && other.getListURL()==null) || 
             (this.listURL!=null &&
              this.listURL.equals(other.getListURL()))) &&
            ((this.registryNumber==null && other.getRegistryNumber()==null) || 
             (this.registryNumber!=null &&
              this.registryNumber.equals(other.getRegistryNumber()))) &&
            ((this.listName==null && other.getListName()==null) || 
             (this.listName!=null &&
              this.listName.equals(other.getListName()))) &&
            ((this.listType==null && other.getListType()==null) || 
             (this.listType!=null &&
              this.listType.equals(other.getListType()))) &&
            ((this.totalItems==null && other.getTotalItems()==null) || 
             (this.totalItems!=null &&
              this.totalItems.equals(other.getTotalItems()))) &&
            ((this.totalPages==null && other.getTotalPages()==null) || 
             (this.totalPages!=null &&
              this.totalPages.equals(other.getTotalPages()))) &&
            ((this.dateCreated==null && other.getDateCreated()==null) || 
             (this.dateCreated!=null &&
              this.dateCreated.equals(other.getDateCreated()))) &&
            ((this.occasionDate==null && other.getOccasionDate()==null) || 
             (this.occasionDate!=null &&
              this.occasionDate.equals(other.getOccasionDate()))) &&
            ((this.customerName==null && other.getCustomerName()==null) || 
             (this.customerName!=null &&
              this.customerName.equals(other.getCustomerName()))) &&
            ((this.partnerName==null && other.getPartnerName()==null) || 
             (this.partnerName!=null &&
              this.partnerName.equals(other.getPartnerName()))) &&
            ((this.additionalName==null && other.getAdditionalName()==null) || 
             (this.additionalName!=null &&
              this.additionalName.equals(other.getAdditionalName()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.image==null && other.getImage()==null) || 
             (this.image!=null &&
              this.image.equals(other.getImage()))) &&
            ((this.averageRating==null && other.getAverageRating()==null) || 
             (this.averageRating!=null &&
              this.averageRating.equals(other.getAverageRating()))) &&
            ((this.totalVotes==null && other.getTotalVotes()==null) || 
             (this.totalVotes!=null &&
              this.totalVotes.equals(other.getTotalVotes()))) &&
            ((this.totalTimesRead==null && other.getTotalTimesRead()==null) || 
             (this.totalTimesRead!=null &&
              this.totalTimesRead.equals(other.getTotalTimesRead()))) &&
            ((this.listItem==null && other.getListItem()==null) || 
             (this.listItem!=null &&
              java.util.Arrays.equals(this.listItem, other.getListItem())));
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
        if (getListId() != null) {
            _hashCode += getListId().hashCode();
        }
        if (getListURL() != null) {
            _hashCode += getListURL().hashCode();
        }
        if (getRegistryNumber() != null) {
            _hashCode += getRegistryNumber().hashCode();
        }
        if (getListName() != null) {
            _hashCode += getListName().hashCode();
        }
        if (getListType() != null) {
            _hashCode += getListType().hashCode();
        }
        if (getTotalItems() != null) {
            _hashCode += getTotalItems().hashCode();
        }
        if (getTotalPages() != null) {
            _hashCode += getTotalPages().hashCode();
        }
        if (getDateCreated() != null) {
            _hashCode += getDateCreated().hashCode();
        }
        if (getOccasionDate() != null) {
            _hashCode += getOccasionDate().hashCode();
        }
        if (getCustomerName() != null) {
            _hashCode += getCustomerName().hashCode();
        }
        if (getPartnerName() != null) {
            _hashCode += getPartnerName().hashCode();
        }
        if (getAdditionalName() != null) {
            _hashCode += getAdditionalName().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getImage() != null) {
            _hashCode += getImage().hashCode();
        }
        if (getAverageRating() != null) {
            _hashCode += getAverageRating().hashCode();
        }
        if (getTotalVotes() != null) {
            _hashCode += getTotalVotes().hashCode();
        }
        if (getTotalTimesRead() != null) {
            _hashCode += getTotalTimesRead().hashCode();
        }
        if (getListItem() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListItem());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListItem(), i);
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
        new org.apache.axis.description.TypeDesc(List.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">List"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registryNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "RegistryNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>List>ListType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalItems"));
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
        elemField.setFieldName("dateCreated");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DateCreated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("occasionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OccasionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "PartnerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AdditionalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("image");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("averageRating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "AverageRating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalVotes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalVotes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTimesRead");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TotalTimesRead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listItem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListItem"));
        elemField.setMinOccurs(0);
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
