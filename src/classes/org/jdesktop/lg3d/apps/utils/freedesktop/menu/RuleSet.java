/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         *
 ***************************************************************************
 * RuleSet.java
 *
 * Created on 10 de agosto de 2006, 17:00
 *
 * $Revision: 1.2 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.menu;

import java.util.Stack;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author opsi
 */
public class RuleSet {
    private Rule topRule;
    private Stack<Rule> ruleStack;
    /** Creates a new instance of RuleSet */
    public RuleSet() {
        ruleStack = new Stack<Rule>();
    }
    public void processRuleSet(Node root) {
        if(ruleStack!=null) {
            ruleStack.clear();
            topRule = null;
        }
        if(!("Include".equals(root.getNodeName())) && !("Exclude".equals(root.getNodeName())))
            throw new IllegalArgumentException("I only accept <Include> and <Exclude> nodes");
        NodeList nodes = root.getChildNodes();
        for(int i = 0; i < nodes.getLength();i++) {
            if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
                processRule(nodes.item(i));
        }
    }
    private void processRule(Node ruleRoot) {
        Rule newRule = new Rule(ruleRoot);
        startRule(newRule);
        if(ruleRoot.hasChildNodes()) {
            NodeList childs = ruleRoot.getChildNodes();
            for(int i = 0 ; i < childs.getLength();i++) {
                if(childs.item(i).getNodeType() == Node.ELEMENT_NODE)
                    processRule(childs.item(i));
            }
        }
        endRule();
    }
    private void startRule(Rule rule) {
        if(rule == null)
            throw new NullPointerException("null rules not allowed");
        if(topRule == null) {
            topRule = rule;
        } else if(!ruleStack.empty())
            ruleStack.peek().addRule(rule);
//        for(int i = 0 ; i < ruleStack.size(); i++)
//        {
//            System.out.print("\t");
//        }
//        System.out.println("Starting "+ rule);
        ruleStack.push(rule);
    }
    private void endRule() {
//        for(int i = 0 ; i < ruleStack.size()-1; i++)
//        {
//            System.out.print("\t");
//        }
//        System.out.println("Ending " + ruleStack.peek());
        ruleStack.pop();
    }
    public boolean matches(String[] categories, String[] fileids) {
        if(topRule==null)
            return false;
        return topRule.matches(categories,fileids);
    }    
}
