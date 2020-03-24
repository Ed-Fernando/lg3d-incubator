/*
 * Clock.java
 *
 * Copyright (c) 2003 Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * Created on November 18, 2003, 7:18 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.text.SimpleDateFormat;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;

import java.util.Date;
import java.util.Locale;

import javax.swing.JApplet;

/**
 *
 * @author  asrivast
 */
public class Clock extends JApplet implements Runnable {
    // -- The thread that displays the timer
    private Thread timer;
    // -- Dimensions to draw the hands of the clock
    int lastxs, lastys, lastxm, lastym, lastxh, lastyh;
    // -- Formats the date displayed
    SimpleDateFormat formatter;
    String lastDate;
    Font clockFaceFont;
    Date currentDate;
    Color handColor;
    Color numberColor;
    
    
    /** Creates a new instance of Clock */
    public Clock () {
    }

    public void init() {
        int x, y;
        lastxs = lastys = lastxm = lastym = lastxh = lastyh = 0;
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", 
                                          Locale.getDefault());
        currentDate = new Date(System.currentTimeMillis());
        lastDate = formatter.format(currentDate);
        clockFaceFont = new Font("Serif", Font.PLAIN, 9);
        handColor = Color.blue;
        numberColor = Color.red;
        
        try {
            setBackground(
                    new Color(Integer.parseInt(getParameter("bgcolor"), 16)));
        } catch(Exception exp) {}
        try {
           handColor = 
	       new Color(Integer.parseInt(getParameter("fgcolor1"), 16));
        } catch(Exception exp) {}
        try {
            numberColor = 
		  new Color(Integer.parseInt(getParameter("fgcolor2"), 16));
        } catch(Exception exp) {}
    }
    
    /**
     * Plot points allows calculation to only cover 45 degrees of the circle,
     * and then mirror
     */
    public void plotPoints(int x0, int y0, int x, int y, Graphics graphics) {
        graphics.drawLine(x0 + x, y0 + y, x0 + x, y0 + y);
        graphics.drawLine(x0 + y, y0 + x, x0 + y, y0 + x);
        graphics.drawLine(x0 + y, y0 - x, x0 + y, y0 - x);
        graphics.drawLine(x0 + x, y0 - y, x0 + x, y0 - y);
        graphics.drawLine(x0 - x, y0 - y, x0 - x, y0 - y);
        graphics.drawLine(x0 - y, y0 - x, x0 - y, y0 - x);
        graphics.drawLine(x0 - y, y0 + x, x0 - y, y0 + x);
        graphics.drawLine(x0 - x, y0 + y, x0 - x, y0 + y);
    }
    
    /**
     * Circle is just a Bresenham's algorithm for a scan converted circle
     */
    public void circle(int x0, int y0, int r, Graphics graphics) {
        int x, y;
        float d;
        x = 0;
        y = r;
        d = 5/4-r;
        plotPoints(x0, y0, x, y, graphics);
        
        while(y > x) {
            if(d < 0) {
                d = d + 2*x + 3;
                x++;
            } else {
                d = d + 2*(x - y) + 5;
                x++;
                y--;
            }
            plotPoints(x0, y0, x, y, graphics);
        }
    }
    
    // -- Paint in the main part of the program
    public void paint(Graphics graphics) {
        int xh, yh, xm, ym, xs, ys, s = 0, m = 10, h = 10, xcenter, ycenter;
        String today;
        currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = 
                         new SimpleDateFormat("s", Locale.getDefault());
        try {
            s = Integer.parseInt(formatter.format(currentDate));
        } catch(NumberFormatException nfExp) {
            s = 0;
        }
        formatter.applyPattern("m");
        try {
            m = Integer.parseInt(formatter.format(currentDate));
        } catch(NumberFormatException nfExp) {
            m = 10;
        }
        formatter.applyPattern("h");
        try {
            h = Integer.parseInt(formatter.format(currentDate));
        } catch(NumberFormatException nfExp) {
            h = 10;
        }
        formatter.applyPattern("EEE MMM dd HH:mm:ss yyyy");
        today = formatter.format(currentDate);
        xcenter = 80;
        ycenter = 55;
        
        xs = (int)(Math.cos(s * 3.14f/30 - 3.14f/2) * 45 + xcenter);
        ys = (int)(Math.sin(s * 3.14f/30 - 3.14f/2) * 45 + ycenter);
        xm = (int)(Math.cos(m * 3.14f/30 - 3.14f/2) * 40 + xcenter);
        ym = (int)(Math.sin(m * 3.14f/30 - 3.14f/2) * 40 + ycenter);
        xh = 
          (int)(Math.cos((h * 30 + m/2) * 3.14f/180 - 3.14f/2) * 30 + xcenter);
        yh = 
          (int)(Math.sin((h * 30 + m/2) * 3.14f/180 - 3.14f/2) * 30 + ycenter);
        
        // -- Draw the circle and numbers
        graphics.setFont(clockFaceFont);
        graphics.setColor(handColor);
        circle(xcenter, ycenter, 50, graphics);
        
        graphics.setColor(numberColor);
        graphics.drawString("9", xcenter - 45, ycenter + 3);
        graphics.drawString("3", xcenter + 40, ycenter + 3);
        graphics.drawString("12", xcenter - 5, ycenter - 37);
        graphics.drawString("6", xcenter - 3, ycenter + 45);
        
        // -- Erase if necessary and redraw
        graphics.setColor(getBackground());
        if(xs != lastxs || ys != lastys) {
            graphics.drawLine(xcenter, ycenter, lastxs, lastys);
        }
        if(xm != lastxm || ym != lastym) {
            graphics.drawLine(xcenter, ycenter - 1, lastxm, lastym);
            graphics.drawLine(xcenter - 1, ycenter, lastxh, lastym);
        }
        if(xh != lastxh || yh != lastyh) {
            graphics.drawLine(xcenter, ycenter - 1, lastxh, lastyh);
            graphics.drawLine(xcenter - 1, ycenter, lastxh, lastyh);
        }
        graphics.setColor(numberColor);
        graphics.drawString("", 5, 125);
        graphics.drawLine(xcenter, ycenter, xs, ys);
        graphics.setColor(handColor);
        graphics.drawLine(xcenter, ycenter - 1, xm, ym);
        graphics.drawLine(xcenter - 1, ycenter, xm, ym);
        graphics.drawLine(xcenter, ycenter - 1, xh, yh);
        graphics.drawLine(xcenter - 1, ycenter, xh, yh);
        
        lastxs = xs; lastys = ys; lastxm = xm; 
        lastym = ym; lastxh = xh; lastyh = yh;
        
        lastDate = today;
        currentDate = null;
    }
    
    public void start() {
        timer = new Thread(this);
        timer.start();
    }
    
    public void stop() {
        timer = null;
    }
    
    public void run () {
        Thread me = Thread.currentThread();
        while(timer == me) {
            try {
                Thread.currentThread().sleep(100);
            } catch(InterruptedException iExp) {
                // -- do nothing
            }
            repaint();
        }
    }
    
    public void update(Graphics graphics) {
        paint(graphics);
    }
    
    public String getAppletInfo() {
        return "Title : Clock Author : Rachel Gollub, 1995. An analog clock.";
    }
    
    public String[][] getParameterInfo() {
        String[][] info = {
            {"bgcolor", "hexadecimal RGB number", 
            "Default color is the color of your browser."},
            {"fgcolor1", "hexadecimal RGB number",
            "Default is blue."},
            {"fgcolor2", "hexadecimal RGB number",
            "Default is dark gray."}
        };
        return info;
    }
}
