/**
 * BrowseNode.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class BrowseNode  implements java.io.Serializable {
    private java.lang.String browseNodeId;
    private java.lang.String name;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeChildren children;
    private org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeAncestors ancestors;
    private org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers;
    private org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases;

    public BrowseNode() {
    }

    public BrowseNode(
           java.lang.String browseNodeId,
           java.lang.String name,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeChildren children,
           org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeAncestors ancestors,
           org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers,
           org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases) {
           this.browseNodeId = browseNodeId;
           this.name = name;
           this.children = children;
           this.ancestors = ancestors;
           this.topSellers = topSellers;
           this.newReleases = newReleases;
    }


    /**
     * Gets the browseNodeId value for this BrowseNode.
     * 
     * @return browseNodeId
     */
    public java.lang.String getBrowseNodeId() {
        return browseNodeId;
    }


    /**
     * Sets the browseNodeId value for this BrowseNode.
     * 
     * @param browseNodeId
     */
    public void setBrowseNodeId(java.lang.String browseNodeId) {
        this.browseNodeId = browseNodeId;
    }


    /**
     * Gets the name value for this BrowseNode.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this BrowseNode.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the children value for this BrowseNode.
     * 
     * @return children
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeChildren getChildren() {
        return children;
    }


    /**
     * Sets the children value for this BrowseNode.
     * 
     * @param children
     */
    public void setChildren(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeChildren children) {
        this.children = children;
    }


    /**
     * Gets the ancestors value for this BrowseNode.
     * 
     * @return ancestors
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeAncestors getAncestors() {
        return ancestors;
    }


    /**
     * Sets the ancestors value for this BrowseNode.
     * 
     * @param ancestors
     */
    public void setAncestors(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeAncestors ancestors) {
        this.ancestors = ancestors;
    }


    /**
     * Gets the topSellers value for this BrowseNode.
     * 
     * @return topSellers
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers getTopSellers() {
        return topSellers;
    }


    /**
     * Sets the topSellers value for this BrowseNode.
     * 
     * @param topSellers
     */
    public void setTopSellers(org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers topSellers) {
        this.topSellers = topSellers;
    }


    /**
     * Gets the newReleases value for this BrowseNode.
     * 
     * @return newReleases
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases getNewReleases() {
        return newReleases;
    }


    /**
     * Sets the newReleases value for this BrowseNode.
     * 
     * @param newReleases
     */
    public void setNewReleases(org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases newReleases) {
        this.newReleases = newReleases;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BrowseNode)) return false;
        BrowseNode other = (BrowseNode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.browseNodeId==null && other.getBrowseNodeId()==null) || 
             (this.browseNodeId!=null &&
              this.browseNodeId.equals(other.getBrowseNodeId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.children==null && other.getChildren()==null) || 
             (this.children!=null &&
              this.children.equals(other.getChildren()))) &&
            ((this.ancestors==null && other.getAncestors()==null) || 
             (this.ancestors!=null &&
              this.ancestors.equals(other.getAncestors()))) &&
            ((this.topSellers==null && other.getTopSellers()==null) || 
             (this.topSellers!=null &&
              this.topSellers.equals(other.getTopSellers()))) &&
            ((this.newReleases==null && other.getNewReleases()==null) || 
             (this.newReleases!=null &&
              this.newReleases.equals(other.getNewReleases())));
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
        if (getBrowseNodeId() != null) {
            _hashCode += getBrowseNodeId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getChildren() != null) {
            _hashCode += getChildren().hashCode();
        }
        if (getAncestors() != null) {
            _hashCode += getAncestors().hashCode();
        }
        if (getTopSellers() != null) {
            _hashCode += getTopSellers().hashCode();
        }
        if (getNewReleases() != null) {
            _hashCode += getNewReleases().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BrowseNode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("browseNodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("children");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Children"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>BrowseNode>Children"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ancestors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Ancestors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>BrowseNode>Ancestors"));
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
