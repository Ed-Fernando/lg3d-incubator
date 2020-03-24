/**
 * AWSECommerceServiceBindingStub.java
 *
 */

package org.jdesktop.lg3d.apps.lgamazon.stub;

public class AWSECommerceServiceBindingStub extends org.apache.axis.client.Stub implements org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServicePortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[19];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Help");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Help"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Help"), org.jdesktop.lg3d.apps.lgamazon.stub.Help.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">HelpResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ItemSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemSearch"), org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemSearchResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearchResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ItemLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("BrowseNodeLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodeLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodeLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ListSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListSearch"), org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListSearchResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearchResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ListLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CustomerContentSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentSearch"), org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentSearchResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearchResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CustomerContentLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SimilarityLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SellerLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CartGet");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGet"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartGet"), org.jdesktop.lg3d.apps.lgamazon.stub.CartGet.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartGetResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGetResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CartCreate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreate"), org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreateResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CartAdd");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAdd"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAdd"), org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAddResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAddResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CartModify");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModify"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartModify"), org.jdesktop.lg3d.apps.lgamazon.stub.CartModify.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartModifyResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModifyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CartClear");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClear"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartClear"), org.jdesktop.lg3d.apps.lgamazon.stub.CartClear.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartClearResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClearResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("TransactionLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SellerListingSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearch"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingSearch"), org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingSearchResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearchResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SellerListingLookup");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookup"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingLookup"), org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingLookupResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookupResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MultiOperation");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MultiOperation"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">MultiOperation"), org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperation.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">MultiOperationResponse"));
        oper.setReturnClass(org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "MultiOperationResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

    }

    public AWSECommerceServiceBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AWSECommerceServiceBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AWSECommerceServiceBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>>Transaction>Shipments>Shipment>Packages>Package");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionShipmentsShipmentPackagesPackage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Seller>SellerFeedbackSummary>FeedbackDateRange>SellerFeedbackRating");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRangeSellerFeedbackRating.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Transaction>Shipments>Shipment>Packages");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionShipmentsShipmentPackages.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>>Transaction>Shipments>Shipment>ShipmentItems");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionShipmentsShipmentShipmentItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>CartModifyRequest>Items>Item>Action");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItemAction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>ItemAttributes>Languages>Language");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguagesLanguage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Seller>SellerFeedbackSummary>FeedbackDateRange");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummaryFeedbackDateRange.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Tracks>Disc>Track");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>>Transaction>Shipments>Shipment");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionShipmentsShipment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Accessories>Accessory");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.AccessoriesAccessory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Arguments>Argument");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ArgumentsArgument.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Bin>BinParameter");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BinBinParameter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>BrowseNode>Ancestors");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeAncestors.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>BrowseNode>Children");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeChildren.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>CartAddRequest>Items>Item");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItemsItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>CartCreateRequest>Items>Item");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequestItemsItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>CartModifyRequest>Items>Item");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItemsItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Customer>Location");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerLocation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Errors>Error");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ErrorsError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>HTTPHeaders>Header");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeadersHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Item>ImageSets");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemImageSets.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Item>Subjects");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemSubjects.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ItemAttributes>Creator");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesCreator.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ItemAttributes>ItemDimensions");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesItemDimensions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ItemAttributes>Languages");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesLanguages.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ItemAttributes>PackageDimensions");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributesPackageDimensions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>List>ListType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListListType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ListmaniaLists>ListmaniaList");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaListsListmaniaList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>NewReleases>NewRelease");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.NewReleasesNewRelease.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OperationInformation>AvailableParameters");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformationAvailableParameters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OperationInformation>AvailableResponseGroups");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformationAvailableResponseGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OperationInformation>DefaultResponseGroups");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformationDefaultResponseGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OperationInformation>RequiredParameters");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformationRequiredParameters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>OtherCategoriesSimilarProducts>OtherCategoriesSimilarProduct");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProductsOtherCategoriesSimilarProduct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ResponseGroupInformation>Elements");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformationElements.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>ResponseGroupInformation>ValidOperations");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformationValidOperations.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SearchInside>Excerpt");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchInsideExcerpt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SearchResultsMap>SearchIndex");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMapSearchIndex.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Seller>Location");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerLocation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Seller>SellerFeedbackSummary");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerSellerFeedbackSummary.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SellerFeedback>Feedback");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedbackFeedback.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SimilarProducts>SimilarProduct");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProductsSimilarProduct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>SimilarViewedProducts>SimilarViewedProduct");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProductsSimilarViewedProduct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>TopSellers>TopSeller");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TopSellersTopSeller.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Tracks>Disc");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Transaction>Shipments");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionShipments.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Transaction>Totals");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionTotals.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>Transaction>TransactionItems");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionTransactionItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">>TransactionItem>ChildTransactionItems");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItemChildTransactionItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Accessories");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Accessories.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Arguments");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Arguments.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">AudienceRating");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.AudienceRating.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Bin");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Bin.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNode");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodeLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodeLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">BrowseNodes");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Cart");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Cart.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAdd");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAddRequest>Items");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequestItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartAddResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartClear");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartClear.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartClearResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreate");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreateRequest>Items");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequestItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartCreateResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartGet");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartGet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartGetResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartItems");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartModify");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModify.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartModifyRequest>Items");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequestItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CartModifyResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Condition");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Condition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CorrectedQuery");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CorrectedQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Customer");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Customer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentSearch");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerContentSearchResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">CustomerReviews");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Customers");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Customers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">DeliveryMethod");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.DeliveryMethod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">EditorialReview");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReview.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">EditorialReviews");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Errors");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Errors.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Help");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Help.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">HelpRequest>HelpType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequestHelpType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">HelpResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">HTTPHeaders");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.HTTPHeaders.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ImageSet");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ImageSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Information");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Information.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Item");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Item.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemAttributes");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookupRequest>IdType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequestIdType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Items");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Items.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemSearch");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemSearchRequest>Availability");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequestAvailability.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ItemSearchResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">List");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.List.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListItem");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookupRequest>ListType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequestListType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListmaniaLists");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListmaniaLists.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Lists");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Lists.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListSearch");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListSearchRequest>ListType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequestListType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ListSearchResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Merchant");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Merchant.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">MultiOperation");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">MultiOperationResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">NewReleases");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.NewReleases.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Offer");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Offer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OfferAttributes");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OfferAttributes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OfferListing");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OfferListing.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Offers");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Offers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OfferSummary");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OperationInformation");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OperationRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OperationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">OtherCategoriesSimilarProducts");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.OtherCategoriesSimilarProducts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">PromotionalTag");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.PromotionalTag.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Request");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Request.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">ResponseGroupInformation");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ResponseGroupInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Review");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Review.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SavedForLaterItems");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SavedForLaterItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchBinSet");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchBinSets");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchBinSets.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchInside");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchInside.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SearchResultsMap");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SearchResultsMap.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Seller");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Seller.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerFeedback");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerFeedback.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListing");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListing.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingLookupRequest>IdType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequestIdType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListings");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListings.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingSearch");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingSearchRequest>OfferStatus");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequestOfferStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerListingSearchResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SellerLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Sellers");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Sellers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookupRequest>SimilarityType");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequestSimilarityType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarityLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarProducts");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarProducts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">SimilarViewedProducts");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarViewedProducts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TopSellers");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TopSellers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Tracks");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Tracks.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Transaction");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Transaction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionItem");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionLookup");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">TransactionLookupResponse");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Transactions");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Transactions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">VariationDimensions");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.VariationDimensions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">Variations");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Variations.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", ">VariationSummary");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.VariationSummary.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Address");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "BrowseNodeLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartAddRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartAddRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartClearRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartClearRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartCreateRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartGetRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartGetRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartItem");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CartModifyRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "CustomerContentSearchRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "DecimalWithUnits");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.DecimalWithUnits.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "HelpRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.HelpRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Image");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Image.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ItemSearchRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "ListSearchRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "NonNegativeIntegerWithUnits");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.NonNegativeIntegerWithUnits.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "Price");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.Price.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerListingSearchRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SellerLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "SimilarityLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "StringWithUnits");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.StringWithUnits.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://webservices.amazon.com/AWSECommerceService/2006-03-08", "TransactionLookupRequest");
            cachedSerQNames.add(qName);
            cls = org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            synchronized (this) {
                if (firstCall()) {
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("[en]-(Failure trying to get the Call object)", _t);
        }
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse help(org.jdesktop.lg3d.apps.lgamazon.stub.Help body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "Help"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.HelpResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse itemSearch(org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ItemSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse itemLookup(org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ItemLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.ItemLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse browseNodeLookup(org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "BrowseNodeLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.BrowseNodeLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse listSearch(org.jdesktop.lg3d.apps.lgamazon.stub.ListSearch body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ListSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.ListSearchResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse listLookup(org.jdesktop.lg3d.apps.lgamazon.stub.ListLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ListLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.ListLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse customerContentSearch(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearch body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CustomerContentSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentSearchResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse customerContentLookup(org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CustomerContentLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CustomerContentLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse similarityLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SimilarityLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.SimilarityLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse sellerLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SellerLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.SellerLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse cartGet(org.jdesktop.lg3d.apps.lgamazon.stub.CartGet body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CartGet"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CartGetResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse cartCreate(org.jdesktop.lg3d.apps.lgamazon.stub.CartCreate body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CartCreate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CartCreateResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse cartAdd(org.jdesktop.lg3d.apps.lgamazon.stub.CartAdd body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CartAdd"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CartAddResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse cartModify(org.jdesktop.lg3d.apps.lgamazon.stub.CartModify body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CartModify"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CartModifyResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse cartClear(org.jdesktop.lg3d.apps.lgamazon.stub.CartClear body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "CartClear"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.CartClearResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse transactionLookup(org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "TransactionLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.TransactionLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse sellerListingSearch(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearch body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SellerListingSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingSearchResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse sellerListingLookup(org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookup body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SellerListingLookup"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.SellerListingLookupResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse multiOperation(org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperation body) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "MultiOperation"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {body});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.jdesktop.lg3d.apps.lgamazon.stub.MultiOperationResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
