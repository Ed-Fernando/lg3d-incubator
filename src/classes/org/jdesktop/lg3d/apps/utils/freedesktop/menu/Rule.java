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
 * Rule.java
 *
 * Created on 9 de agosto de 2006, 1:02
 *
 * $Revision: 1.2 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.menu;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

/**
 *
 * @author opsi
 */
public class Rule {
    private static final String All = "All";
    private static final String And = "And";
    private static final String Or = "Or";
    private static final String Not = "Not";
    private static final String Category = "Category";
    private static final String Filename = "Filename";
    
    public enum Logic {
        /**
         * The "And" element contains a list of matching rules. If each of the matching rules inside the "And" element match a desktop entry, then the entire "And" rule matches the desktop entry.
         */
        AND,
        /**
         * The "Or" element contains a list of matching rules. If any of the matching rules inside the "Or" element match a desktop entry, then the entire "Or" rule matches the desktop entry.
         */
        OR,
        /**
         * The "Not" element contains a list of matching rules. If any of the matching rules inside the <Not> element matches a desktop entry, then the entire "Not" rule does not match the desktop entry. That is, matching rules below "Not" have a logical OR relationship.
         */
        NOT,
        /**
         * The "All" element is a matching rule that matches all desktop entries.
         */
        ALL};
        private Logic logic;
        
        private List<Rule> childRules;
        private String category;
        private String fileid;
        
        private int DEF_NUM_CHILD = 4;
        
        protected Rule(Node node) {
            String nodeName = node.getNodeName();
            if(All.equals(nodeName))
                logic = logic.ALL;
            else if(Or.equals(nodeName))
                logic = logic.OR;
            else if(And.equals(nodeName))
                logic = logic.AND;
            else if(Not.equals(nodeName))
                logic = logic.NOT;
            else if(Category.equals(nodeName))
                category = node.getTextContent();
            else if(Filename.equals(nodeName))
                fileid = node.getTextContent();
            else
                throw new IllegalArgumentException("This node is not a rule");
        }
        protected void addRule(Rule child) {
            if(childRules == null) {
                childRules = new ArrayList<Rule>(DEF_NUM_CHILD);
            }
            childRules.add(child);
        }
        protected boolean hasChilds() {
            return childRules != null;
        }
        protected boolean matches(String[] categories,String[] fileids) {
            boolean output = false;
            int matched = 0;
            //If this is not a logical Rule, it's a FileName or a Category
            if(logic == null) {
                if(category != null && categories != null) {
                    for(String cat : categories) {
                        output = category.equals(cat);
                        if(output)
                            break;
                    }
                }
                if(fileid != null && fileids != null && output == false) {
                    for(String id : fileids) {
                        output = fileid.equals(id);
                        if(output)
                            break;
                    }
                }
            } else if(logic == Logic.ALL)
                output = true;
            else {
                if(childRules != null) {
                    for(Rule rule : childRules) {
                        output = rule.matches(categories,fileids);
                        if(output) {
                            if(logic == Logic.OR || logic == Logic.NOT) {
                                break;
                            } else { //logic == Logic.AND or logic == Logic.NOT
                                matched++;
                                output = false;
                            }
                        }
                    }
                }
                if(logic == Logic.AND && matched == childRules.size())
                    output = true;
                else if(logic == Logic.NOT)
                    output = !output;
                
            }
            return output;
        }
        
        public String getCategory() {
            return category;
        }
        
        public String getFileid() {
            return fileid;
        }
        public String toString(){
            if(logic != null)
                return logic.toString();
            else if(category != null)
                return "Category: " + category;
            else
                return "FileId: " + fileid;
        }
}
