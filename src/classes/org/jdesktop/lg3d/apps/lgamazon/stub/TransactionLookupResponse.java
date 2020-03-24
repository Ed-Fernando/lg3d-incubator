/**
 * TransactionLookupResponse.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class TransactionLookupResponse  implements java.io.Serializable {
    private org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest;
    private org.jdesktop.lg3d.apps.lgamazon.stub.Transactions[] transactions;

    public TransactionLookupResponse() {
    }

    public TransactionLookupResponse(
           org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest,
           org.jdesktop.lg3d.apps.lgamazon.stub.Transactions[] transactions) {
           this.operationRequest = operationRequest;
           this.transactions = transactions;
    }


    /**
     * Gets the operationRequest value for this TransactionLookupResponse.
     * 
     * @return operationRequest
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest getOperationRequest() {
        return operationRequest;
    }


    /**
     * Sets the operationRequest value for this TransactionLookupResponse.
     * 
     * @param operationRequest
     */
    public void setOperationRequest(org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest operationRequest) {
        this.operationRequest = operationRequest;
    }


    /**
     * Gets the transactions value for this TransactionLookupResponse.
     * 
     * @return transactions
     */
    public org.jdesktop.lg3d.apps.lgamazon.stub.Transactions[] getTransactions() {
        return transactions;
    }


    /**
     * Sets the transactions value for this TransactionLookupResponse.
     * 
     * @param transactions
     */
    public void setTransactions(org.jdesktop.lg3d.apps.lgamazon.stub.Transactions[] transactions) {
        this.transactions = transactions;
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.Transactions getTransactions(int i) {
        return this.transactions[i];
    }

    public void setTransactions(int i, org.jdesktop.lg3d.apps.lgamazon.stub.Transactions _value) {
        this.transactions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionLookupResponse)) return false;
        TransactionLookupResponse other = (TransactionLookupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operationRequest==null && other.getOperationRequest()==null) || 
             (this.operationRequest!=null &&
              this.operationRequest.equals(other.getOperationRequest()))) &&
            ((this.transactions==null && other.getTransactions()==null) || 
             (this.transactions!=null &&
              java.util.Arrays.equals(this.transactions, other.getTransactions())));
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
        if (getOperationRequest() != null) {
            _hashCode += getOperationRequest().hashCode();
        }
        if (getTransactions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransactions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransactions(), i);
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
        new org.apache.axis.description.TypeDesc(TransactionLookupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionLookupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "OperationRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Transactions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Transactions"));
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
