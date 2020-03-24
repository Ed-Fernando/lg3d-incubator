/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: HTMLLabel.java,v $
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
 * $Date: 2007-03-09 09:37:37 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.util.Stack;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class HTMLLabel extends LabelComponent {

    private final static SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    
//    static {
//        parserFactory.setValidating(false); 
//    }
    
    private Vector<Object> drawOrder = new Vector<Object>();
    
    
    public HTMLLabel(SimpleAppearance appearance) {
        
        super(appearance);
    }
    
    
    
    public void setText(String text) {
        
        super.setText(text);
        drawOrder.removeAllElements();
    }



    protected void draw(Graphics2D g) {
                
        if (drawOrder.size() == 0) { 
            try {            
                SAXParser parser = parserFactory.newSAXParser();
                parser.parse(new ByteArrayInputStream(getText().getBytes()), new HTMLParser(g));
            }
            catch (Exception e) {
                // System.out.print('*');
                //e.printStackTrace();     
            }
        }
        
        
        for (int i = 0; i < drawOrder.size(); i++) {
            Object obj = drawOrder.get(i);
            drawHTML(g, obj);
        }
    }
    
    
    private void drawHTML(Graphics2D g, Object obj) {
    
        if (obj instanceof Font) {
            
            int as =g.getFontMetrics().getAscent();            
            g.setFont((Font) obj);
            as = g.getFontMetrics().getAscent() - as;
            
            if (as > 0) {
                setY(getY() + as);        
            }
        }
        else if (obj instanceof Color) {
            g.setColor((Color) obj);
        }
        else if (obj instanceof char[]) {
            char[] c = (char[]) obj;
            drawChars(c, 0, c.length);
        }
        else if (obj instanceof String) {
            drawHTMLTag(g, (String) obj);            
        }  
    }
    
    
    private void drawHTMLTag(Graphics2D g, String tag) {
    
        FontMetrics fontMetrics = g.getFontMetrics();
        
        if (tag.equals("br") || tag.equals("ul")) {
            newLine(fontMetrics);
        }
        else if (tag.equals("p")) {
            newLine(fontMetrics);
            newLine(fontMetrics);
        }
        else if (tag.equals("hr")) {
            
          int y = getY();
          
          y += fontMetrics.getHeight();   
          Color now = g.getColor();
          g.setColor(Color.GRAY);
          g.setStroke(
                  new BasicStroke(3.0f * (1.0f / getScale()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));                               
          g.drawLine(viewArea.x, y, viewArea.x + viewArea.width, y);
          g.setColor(now);
          y += fontMetrics.getHeight();   
          
          setY(y);
          setX(viewArea.x);
        }
        else if (tag.equals("li")) {
            
          int y = getY();
          y += fontMetrics.getHeight();   
          int x = viewArea.x;
          
          int r = (int) (10.0f * (1.0f / getScale()));     
          int ry = (y - fontMetrics.getAscent()) + ((fontMetrics.getHeight() - r) / 2);                
          g.fillOval(x, ry - viewPos.y, r, r);  
          
          setX(x + (r * 2));  
          setY(y);
        }
        
    }
    
    
    class HTMLParser extends DefaultHandler {
        
        Stack<Font> fontStack = new Stack<Font>();
        Stack<Color> colorStack = new Stack<Color>();
        
        Graphics2D g;
        
        HTMLParser(Graphics2D g) {
            
            this.g = g;
            
            fontStack.push(g.getFont()); 
            colorStack.push(g.getColor());
            
            drawOrder.add(g.getFont());
            drawOrder.add(g.getColor());
        }
        
        
        public void startElement(
        String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
            
            qName = qName.toLowerCase();
            
            Font cur = fontStack.peek();
            
            Font newFont = null;
            
            if (qName.equals("font")) {
                
                newFont = cur;
                String sizeStr = attributes.getValue("size");                
                if (sizeStr != null) {
                    newFont = getScaledFont(cur.getName(), cur.getStyle(), Integer.parseInt(sizeStr));
                }
                
                String colorStr = attributes.getValue("color"); 
                Color color = (colorStr != null) ? 
                        new Color(Integer.valueOf(colorStr.substring(1, colorStr.length()), 16).intValue()) : (Color) colorStack.peek();                 
                
                colorStack.push(color);                
                drawOrder.add(color);
            }
            else if (qName.equals("b") || qName.equals("strong")) {
                newFont = new Font(cur.getName(), cur.getStyle() | Font.BOLD, cur.getSize());
            }
            else if (qName.equals("i") || qName.equals("em")) {
                newFont = new Font(cur.getName(), cur.getStyle() | Font.ITALIC, cur.getSize());
            }
            else if (qName.equals("br")) {                
                drawOrder.add("br");
            }
            else if (qName.equals("hr")) {
                drawOrder.add("hr");
            }
            else if (qName.equals("p")) {
                drawOrder.add("p");
            }
            else if (qName.equals("li")) {                
                drawOrder.add("li");
            }
            else if (qName.equals("body") || qName.equals("ul")) {
            }
            else {
                System.out.println("unknown tag=" + qName);
            }
            
            
            if (newFont != null) {
                fontStack.push(newFont);
                drawOrder.add(newFont);
            }            
        }
        
        
        
        public void endElement(String uri, String localName, String qName)
        throws SAXException {
            
            qName = qName.toLowerCase();
            
            if (qName.equals("b") || qName.equals("strong") || 
                qName.equals("i") || qName.equals("em")) {
                
                fontStack.pop();
                drawOrder.add(fontStack.peek());
            }
            else if (qName.equals("font")) {
                fontStack.pop();
                colorStack.pop();
                
                drawOrder.add(fontStack.peek());
                drawOrder.add(colorStack.peek());
            }            
            else if (qName.equals("ul")) {
                drawOrder.add("ul");
            }
        }
        
        
        public void characters(char[] ch, int start, int length)
        throws SAXException {
            
            char[] c = new char[length];
            System.arraycopy(ch, start, c, 0, length);
            drawOrder.add(c);
        }      
        
        public void endDocument()
        throws SAXException {
            
            drawOrder.add(fontStack.pop());
            drawOrder.add(colorStack.pop());
        }
    }
    
    
    
//    public static void main(String[] args) {
//        
//        try {
//            class MyHandler implements ErrorHandler {
//                public void warning(SAXParseException e) {
//                    
//                    System.out.println(e.getMessage());
//                }
//                public void error(SAXParseException e) {
//                    
//                    System.out.println(e.getMessage());
//                }
//                public void fatalError(SAXParseException e) {
//                    
//                    System.out.println(e.getMessage());
//                }
//            }
//            
//            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
//            //f.setValidating(true);
//            //f.setNamespaceAware(false);
//            //f.setFeature("http://xml.org/sax/features/validation", true);
//            DocumentBuilder db = f.newDocumentBuilder();
//            
//            db.setErrorHandler(new MyHandler());
//            
//            Document doc = db.parse(
//                    new ByteArrayInputStream("<body><contents/><test>aabbcc<test2>NEST</test2></test>78979</body>".getBytes()));
//            
//            doc = db.parse(new FileInputStream("build.xml"));
//            
//            // Document doc = db.newDocument();
//                        
//            System.out.println("HTMLLabel.draw(1):" + doc);
//            System.out.println("HTMLLabel.draw(1):" + doc.getDocumentElement());
//            System.out.println("HTMLLabel.draw(1):" + doc.getDocumentElement().getChildNodes().getLength());
//            System.out.println("HTMLLabel.draw(1):" + doc.getLocalName());
//            System.out.println("HTMLLabel.draw(1):" + doc.getNodeName());
//            System.out.println("HTMLLabel.draw(2):" + doc.getNextSibling());
//            System.out.println("HTMLLabel.draw(2):" + doc.getChildNodes());
//            
//            //Node n = doc.getFirstChild();
//            //System.out.println("HTMLLabel.draw():" + n);
//            
//            parse(doc.getDocumentElement());
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    
//    static void parse(Node node) {
//        
//        System.out.println("node="+node);
//        System.out.println("name="+node.getNodeName());
//        
//        if (node.getNodeType() == Node.TEXT_NODE) {
//            System.out.println("text="+node.getTextContent());
//        }
//        
//        NodeList child = node.getChildNodes();
//        for (int i = 0; i < child.getLength(); i++) {
//            parse(child.item(i));
//        }
//    }
    
}
