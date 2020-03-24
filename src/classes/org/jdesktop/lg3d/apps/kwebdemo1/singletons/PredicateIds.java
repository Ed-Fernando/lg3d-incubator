/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/singletons/PredicateIds.java,v 1.2 2005-06-24 19:57:14 paulby Exp $
 * $Date: 2005-06-24 19:57:14 $
 *
 * Joint Copyright (c) 2005 by
 *   James A. Zaun, Consultant,
 *   The Burke Institute,
 *   Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 */

package org.jdesktop.lg3d.apps.kwebdemo1.singletons;

import java.util.Hashtable;

/**
 * James Burke's Knowledge Web predicates or transition text values associated
 * with the edges that provide the association between the nodes.
 * 
 * <p>This class is references by
 * {@link org.jdesktop.lg3d.apps.kwebdemo1.edges.BasicEdge BasicEdge}
 * ...</p>
 *
 * @see org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData
 * @see org.jdesktop.lg3d.apps.kwebdemo1.singletons.EdgeData
 *
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class PredicateIds extends Object
{
  Hashtable<String,Integer> predicates =
    new Hashtable<String,Integer>(171);

  public PredicateIds()
  {
    predicates.put("?", 1);
    predicates.put("a developer of", 2);
    predicates.put("a fan was", 3);
    predicates.put("a follower of", 4);
    predicates.put("a pal of", 5);
    predicates.put("a patient was", 6);
    predicates.put("a supporter of", 7);
    predicates.put("admired by", 8);
    predicates.put("admired", 9);
    predicates.put("admirer of", 10);
    predicates.put("affair with", 11);
    predicates.put("allied with", 12);
    predicates.put("anticipated by", 13);
    predicates.put("anticipated", 14);
    predicates.put("approached by", 15);
    predicates.put("assistant to", 16);
    predicates.put("assistant was", 17);
    predicates.put("assisted by", 18);
    predicates.put("attended premiere of", 19);
    predicates.put("based on discovery by", 20);
    predicates.put("biography written by", 21);
    predicates.put("build by", 22);
    predicates.put("built", 23);
    predicates.put("co- founded by", 24);
    predicates.put("co-founded by", 25);
    predicates.put("co-invented by", 26);
    predicates.put("collaborated with", 27);
    predicates.put("commissioned by", 28);
    predicates.put("commissioned", 29);
    predicates.put("commonly thought to be invented by team led by", 30);
    predicates.put("consulted", 31);
    predicates.put("corresponded with", 32);
    predicates.put("critiqued", 33);
    predicates.put("cuckolded", 34);
    predicates.put("developed by", 35);
    predicates.put("developed work by", 36);
    predicates.put("development aided by", 37);
    predicates.put("disagreed with theory of", 38);
    predicates.put("disagreed with", 39);
    predicates.put("discovered by", 40);
    predicates.put("discovered", 41);
    predicates.put("disputed by", 42);
    predicates.put("drew on work of", 43);
    predicates.put("early development by", 44);
    predicates.put("employed", 45);
    predicates.put("encouraged by", 46);
    predicates.put("fellow-socialist was", 47);
    predicates.put("first developed by", 48);
    predicates.put("first investigated by", 49);
    predicates.put("followed up work of", 50);
    predicates.put("follower was", 51);
    predicates.put("founded by", 52);
    predicates.put("funded by", 53);
    predicates.put("gave funeral oration for", 54);
    predicates.put("good review from", 55);
    predicates.put("grandfather was", 56);
    predicates.put("he invented", 57);
    predicates.put("hero was", 58);
    predicates.put("his brother hired", 59);
    predicates.put("his elogy given by", 60);
    predicates.put("his partner was", 61);
    predicates.put("his work developed by", 62);
    predicates.put("his work related to", 63);
    predicates.put("honoured", 64);
    predicates.put("improved phonograph of", 65);
    predicates.put("influenced by", 66);
    predicates.put("influenced", 67);
    predicates.put("initiated by", 68);
    predicates.put("inspired by", 69);
    predicates.put("inspired", 70);
    predicates.put("interviewed by", 71);
    predicates.put("interviewed", 72);
    predicates.put("invented by", 73);
    predicates.put("invented", 74);
    predicates.put("invention superseded by", 75);
    predicates.put("invested in", 76);
    predicates.put("investigated", 77);
    predicates.put("investigated by", 78);
    predicates.put("knew", 79);
    predicates.put("laid basis for work of", 80);
    predicates.put("laid ground for", 81);
    predicates.put("lectured on", 82);
    predicates.put("lover of", 83);
    predicates.put("made possible work of", 84);
    predicates.put("mentored by", 85);
    predicates.put("mentored", 86);
    predicates.put("met partner of", 87);
    predicates.put("met", 88);
    predicates.put("might have anticipated", 89);
    predicates.put("named after", 90);
    predicates.put("nearly anticipated by", 91);
    predicates.put("neighbor was", 92);
    predicates.put("opposed by", 93);
    predicates.put("picked up on work of", 94);
    predicates.put("pioneered by", 95);
    predicates.put("played for", 96);
    predicates.put("poems set to music by", 97);
    predicates.put("premiere attended by", 98);
    predicates.put("provided film for", 99);
    predicates.put("read works of", 100);
    predicates.put("rejected view of", 101);
    predicates.put("related to work of", 102);
    predicates.put("related to", 103);
    predicates.put("replaced", 104);
    predicates.put("rival telephone by", 105);
    predicates.put("rival was", 106);
    predicates.put("satirized by", 107);
    predicates.put("satirized", 108);
    predicates.put("sent first work to", 109);
    predicates.put("served under", 110);
    predicates.put("set to music poems of", 111);
    predicates.put("shared patron with", 112);
    predicates.put("shared view of", 113);
    predicates.put("signed treaty with", 114);
    predicates.put("studied by", 115);
    predicates.put("studied work of", 116);
    predicates.put("studied", 117);
    predicates.put("superseded by", 118);
    predicates.put("superseded work of", 119);
    predicates.put("supported by", 120);
    predicates.put("supported theory of", 121);
    predicates.put("supported", 122);
    predicates.put("taught by", 123);
    predicates.put("taught", 124);
    predicates.put("theory applied by", 125);
    predicates.put("theory developed by", 126);
    predicates.put("theory disproved by", 127);
    predicates.put("theory rejected by", 128);
    predicates.put("used story by", 129);
    predicates.put("used theory of", 130);
    predicates.put("used work of", 131);
    predicates.put("used", 132);
    predicates.put("visited by", 133);
    predicates.put("visited", 134);
    predicates.put("was associated with", 135);
    predicates.put("was invented by", 136);
    predicates.put("was pioneered by", 137);
    predicates.put("was succeeded by", 138);
    predicates.put("was used by", 139);
    predicates.put("won Nobel with", 140);
    predicates.put("work adapted by", 141);
    predicates.put("work aided", 142);
    predicates.put("work developed by", 143);
    predicates.put("work read by", 144);
    predicates.put("work related to", 145);
    predicates.put("worked for", 146);
    predicates.put("worked with", 147);
    predicates.put("wrote Aida for opening of", 148);
    predicates.put("wrote for", 149);
    predicates.put("wrote parody of", 150);
    predicates.put("wrote preface for", 151);
  }
}
