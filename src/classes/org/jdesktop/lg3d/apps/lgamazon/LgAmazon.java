/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgAmazon.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.1 $
 * $Date: 2007-03-09 09:38:42 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon;


import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.axis.types.PositiveInteger;
import org.jdesktop.lg3d.apps.lgamazon.book.LgBook;
import org.jdesktop.lg3d.apps.lgamazon.cd.LgCD;
import org.jdesktop.lg3d.apps.lgamazon.component.LabelComponent;
import org.jdesktop.lg3d.apps.lgamazon.component.MainPanel;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.dvd.LgDVD;
import org.jdesktop.lg3d.apps.lgamazon.layout.BookshelfLayout;
import org.jdesktop.lg3d.apps.lgamazon.layout.TileLayout;
import org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceService;
import org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServiceLocator;
import org.jdesktop.lg3d.apps.lgamazon.stub.AWSECommerceServicePortType;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearch;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchRequest;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemSearchResponse;
import org.jdesktop.lg3d.apps.lgamazon.stub.Items;
import org.jdesktop.lg3d.wg.Frame3D;


/**
 * LgAmazon main class.
 */
public class LgAmazon extends Frame3D {
    
    private AWSECommerceService awseService;
    private AWSECommerceServicePortType awseType;
    
    public int currentPage = 1;
    
    public String keyword;
    
    public String searchIndex = MUSIC;
    public final static String BOOKS = "Books";
    public final static String DVD = "DVD";
    public final static String MUSIC = "Music";
    
        
    public LgAmazonListener listener;
    
    private String subscriptionId;
    private Thread searchThread = null;
        
    private Hashtable<String, ProductSet> productTable = new Hashtable<String, ProductSet>();
    
    
    public LgAmazon(String subscriptionId) throws Exception {
        
        this.subscriptionId = subscriptionId;
        
        awseService = new AWSECommerceServiceLocator();
        awseType = awseService.getAWSECommerceServicePort();
        
        MainPanel mainPanel = new MainPanel(this);
        addChild(mainPanel);
        mainPanel.setTranslation(0.0f, 0.05f, 0.0f);        
        
        
        ProductSet book = new ProductSet();
        book.products = new LgBook[10];         
        for (int i = 0; i < book.products.length; i++) {            
            book.products[i] = new LgBook(mainPanel);                         
            addChild(book.products[i]);
        }
                
        ProductSet cd = new ProductSet();
        cd.products = new LgCD[5];
        for (int i = 0; i < cd.products.length; i++) {            
            cd.products[i] = new LgCD(mainPanel);                         
            addChild(cd.products[i]);
        }
        
        ProductSet dvd = new ProductSet();
        dvd.products = new LgDVD[5];
        for (int i = 0; i < dvd.products.length; i++) {            
            dvd.products[i] = new LgDVD(mainPanel);                       
            addChild(dvd.products[i]);
        }

        
        // Setting of layout.
        book.layout = new BookshelfLayout(0.4f);        
        cd.layout = new TileLayout(0.2f, -(float) (Math.PI * 40) / 180);  
        dvd.layout = new TileLayout(0.3f, -(float) (Math.PI * 70) / 180);
        
//        book.layout = new TileLayout(0.4f, -(float) (Math.PI * 30) / 180);
//        cd.layout = new TileLayout(0.4f, -(float) (Math.PI * 40) / 180);
//        dvd.layout = new TileLayout(0.3f, -(float) (Math.PI * 20) / 180);
        
        productTable.put(MUSIC, cd);
        productTable.put(DVD, dvd);
        productTable.put(BOOKS, book);
        
        
        changeEnabled(true);
        changeVisible(true);
    }
        
           
    public void search(String searchIndex, int page) {

        if (searchThread != null) {
            return;
        }
        
        searchThread = new SearchThread(searchIndex, page);
        searchThread.start();
    }
    
    
    class SearchThread extends Thread {
        
        boolean searching = true;
        
        int page;        
        String searchIndex;
        
        SearchThread(String searchIndex, int page) {
            this.page = page;
            this.searchIndex = searchIndex;
        }
        
        public void run() {
            
            ProductSet set = productTable.get(searchIndex);
                        
            goHome(set.products);        
            
            ItemSearchRequest searchRequest = new ItemSearchRequest();
                            
            // The attribute which wants to be acquired here is written. 
            // The one which not is here becomes null. 
            searchRequest.setResponseGroup(new String[] {
                    "ItemAttributes", "Images", "SalesRank", "EditorialReview", "OfferFull", "OfferSummary",
                    "Reviews", "Tracks"});
            // The display number is set. 
            searchRequest.setCount(new PositiveInteger(String.valueOf(set.products.length)));
                                    
            
            ItemSearch itemSearch = new ItemSearch(); 
            itemSearch.setSubscriptionId(subscriptionId);
            itemSearch.setRequest(new ItemSearchRequest[] {searchRequest});
                                    
            searchRequest.setSearchIndex(searchIndex);
            searchRequest.setKeywords(keyword);
            searchRequest.setItemPage(new PositiveInteger(String.valueOf(page)));
            
            try {                
                ItemSearchResponse searchResponse = awseType.itemSearch(itemSearch); 
                Items searchItems = searchResponse.getItems(0);
                
                searching = false;
                
                if (isInterrupted()) {
                    return;
                }
                
                        
                currentPage = page; 
                
                if (searchItems.getTotalResults().intValue() > 0) {
                    setItems(searchItems.getItem(), set.products);        
                    set.layout.layout(set.products, searchItems.getItem().length);
                }
                else {
                    set.layout.layout(set.products, 0);
                }
                
                if (listener != null) {
                    listener.searchResult(searchItems);
                }
            }
            catch (Exception e) {
                
                if (!isInterrupted() && listener != null) {
                    listener.exception(e);
                }
            }
            finally {
                searchThread = null;
            }
        }
        
        
        public void interrupt() {
            
            // It is not possible to interrupt from the state that the retrieval ends. 
            // It does to the layout.
            if (!searching) {
                return;
            }
            
            searchThread = null;
            super.interrupt();            
        }
    }
    
    
    class ProductSet {
        
        Product[] products;
        Layout layout;
    }
    
    
    
    /** 
     * 
     * 
     * @return It is true if it is searching it. 
     */
    public boolean isSearching() {
        return searchThread != null;
    }
    
    
    
    /**
     * The searching is interrupted. 
     * 
     * 
     */
    public void interrupt() {
     
        if (isSearching()) {
            searchThread.interrupt();
        }
    }
    
    
    private void setItems(Item[] items, Product[] products) 
    throws IOException {
        
        for (int i = 0; i < items.length; i++) {
            products[i].setItem(items[i]);
        }
    }
    
    
    private void goHome(Product[] products) {
        
        for (int i = 0; i < products.length; i++) {            
            products[i].goHome();
        }
    }
    
    
    public void clear(String searchIndex) {
        
        ProductSet set = productTable.get(searchIndex);
        
        if (set == null) {
            return;
        }
        
        for (int i = 0; i < set.products.length; i++) {
            set.products[i].changeVisible(false);
        }
        
        LabelComponent.recycleAll();
    }
    
    
    public void nextPage(String searchIndex) {
        
        if (searchThread != null) {
            return;
        }
                    
        search(searchIndex, currentPage + 1);
    }

    public void prevPage(String searchIndex) {
        
        if (currentPage == 1) {
            return;
        }
        
        if (searchThread != null) {
            return;
        }
                    
        search(searchIndex, currentPage - 1);        
    }
    
        
    
    public static void main(String[] args) throws Exception {
        
        // Reading of configuration file. 
        InputStream input = LgAmazon.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/amazon.properties");
        Properties prop = new Properties();
        prop.load(input);
        input.close();
        
        if (prop.containsKey("http.proxyHost")) {
            System.setProperty("http.proxyHost", prop.getProperty("http.proxyHost"));
        }
        if (prop.containsKey("http.proxyPort")) {
            System.setProperty("http.proxyPort", prop.getProperty("http.proxyPort"));
        }
        
        String proxyUser = null; 
        if (prop.containsKey("http.proxyUser")) {
            proxyUser = prop.getProperty("http.proxyUser");
            System.setProperty("http.proxyUser", proxyUser);
        }
        String proxyPassword = null; 
        if (prop.containsKey("http.proxyPassword")) {
            proxyPassword = prop.getProperty("http.proxyPassword");
            System.setProperty("http.proxyPassword", proxyPassword);
        }

        if (proxyUser != null) {            
            // Correspondence of attestation proxy. 
            Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPassword));
        }

        
        new LgAmazon(prop.getProperty("subscriptionId"));
    }
    
    
    static class ProxyAuthenticator extends Authenticator {
        
        String user;
        char[] password;
        
        ProxyAuthenticator(String user, String password) {            
            this.user = user;
            this.password = password.toCharArray();
        }
        
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }
    }
}


