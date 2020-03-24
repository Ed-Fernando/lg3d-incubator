use Math::Trig ':radial';

if ($#ARGV != 0)
  {
    print "Usage: $0 ...something\n";
    exit(1);
  }

$infile = $ARGV[0];
$outfile = "NodeData.java";
open(IN, "$infile") || die "cannot open IN\n";
open(OUT, ">$outfile") || die "cannot open OUT\n";

%century = (
  '"Bardeen%2C+John"', 20,
  '"Beaumarchais%2C+Caron+de"', 18,
  '"Beccaria%2C+Cesare"', 18,
  '"Bell%2C+Alexander+Graham"', 19,
  '"Bohr%2C+Niels+Henrik+David"', 20,
  '"Born%2C+Max"', 20,
  '"Bragg%2C+Sir+William+Henry"', 20,
  '"Brattain%2C+Walter+Houser"', 20,
  '"Braun%2C+Karl+Ferdinand"', 19,
  '"Brewster%2C+Sir+David"', 19,
  '"Broglie%2C+Louis%2C+Duke+de"', 20,
  '"Buchan%2C+John"', 20,
  '"Byron%2C+George+Gordon"', 19,
  '"Cassini+family"', 18,
  '"Communism"', 0,
  '"Curie%2C+Marie"', 20,
  '"Darwin%2C+Charles+Robert"', 19,
  '"Darwin%2C+Erasmus"', 18,
  '"Davy%2C+Sir+Humphry"', 19,
  '"Debussy%2C+Achille-Claude"', 19,
  '"Descartes%2C+Rene"', 17,
  '"Diaghilev%2C+Sergei+Pavlovich"', 20,
  '"Eastman%2C+George"', 20,
  '"Edison%2C+Thomas+Alva"', 20,
  '"Einstein%2C+Albert"', 20,
  '"electric+motor"', 0,
  '"Electrolysis"', 0,
  '"Electromagnetic+radiation"', 0,
  '"electromagnetism"', 0,
  '"Engels%2C+Friedrich"', 19,
  '"Eugenics"', 0,
  '"Evolution%2C+theory+of"', 0,
  '"Faraday%2C+Michael"', 19,
  '"Follen%2C+Karl"', 19,
  '"Franklin%2C+Benjamin"', 18,
  '"Freud%2C+Sigmund"', 20,
  '"Galilei%2C+Galileo"', 17,
  '"Gall%2C+Franz+Josef"', 19,
  '"Galton%2C+Sir+Francis"', 20,
  '"Galvani%2C+Luigi"', 18,
  '"Goddard%2C+Robert+Hutchings"', 20,
  '"Goethe%2C+Johann+Wolfgang+von"', 19,
  '"gyroscope"', 0,
  '"Hales%2C+Stephen"', 17,
  '"Hamilton%2C+Sir+William"', 19,
  '"Heisenberg%2C+Werner+Karl"', 20,
  '"helicopter"', 0,
  '"Helmholtz%2C+Hermann+von"', 19,
  '"Herschel%2C+Sir+William"', 19,
  '"Hertz%2C+Heinrich+Rudolf"', 19,
  '"Herzl%2C+Theodor"', 20,
  '"Hoffmann%2C+Ernst+Theodor+Amadeus"', 19,
  '"Hypnosis"', 0,
  '"Induction"', 0,
  '"James%2C+Henry"', 20,
  '"Jefferson%2C+Pres.+Thomas"', 19,
  '"kaleidoscope"', 0,
  '"Langevin%2C+Paul"', 20,
  '"Laser"', 0,
  '"Lawrence%2C+Thomas+Edward"', 20,
  '"Leibniz%2C+Gottfried+Wilhelm"', 17,
  '"Lenin%2C+Vladimir+Ilich"', 20,
  '"Lesseps%2C+Ferdinand%2C+Vicomte+de"', 19,
  '"Light+bulb"', 0,
  '"Liquid-fuel+rocket"', 0,
  '"Liszt%2C+Franz"', 19,
  '"Mach%2C+Ernst"', 19,
  '"Mach+number"', 0,
  '"Mahler%2C+Gustav"', 20,
  '"Mao+Zedong"', 20,
  '"Marconi%2C+Guglielmo"', 20,
  '"Marx%2C+Karl"', 19,
  '"Mesmer%2C+Franz+Anton"', 18,
  '"Mozart%2C+Wolfgang+Amadeus"', 18,
  '"Napoleon+Bonaparte"', 19,
  '"Nehru%2C+Jawaharlal"', 20,
  '"Nelson%2C+Horatio"', 19,
  '"Nipkow%2C+Paul+Gottlieb"', 20,
  '"Oersted%2C+Hans+Christian"', 19,
  '"Oscilloscope"', 0,
  '"Planck%2C+Max"', 20,
  '"Poe%2C+Edgar+Allan"', 19,
  '"psychoanalysis"', 0,
  '"Purkinje+cells"', 0,
  '"Purkinje%2C+Jan+Evangelista"', 19,
  '"Quantum+physics"', 0,
  '"Rachmaninov%2C+Sergey"', 20,
  '"Radar"', 0,
  '"radio"', 0,
  '"Radioactivity"', 0,
  '"Reductionism"', 0,
  '"Relativity"', 0,
  '"Roosevelt%2C+Pres.+Franklin+Delano"', 20,
  '"Rutherford%2C+Ernest"', 20,
  '"Schelling%2C+Frederick+Wilhelm+Joseph+von"', 18,
  '"Shaw%2C+George+Bernard"', 20,
  '"Shockley%2C+William+Bradford"', 20,
  '"Sikorsky%2C+Igor+Ivanovich"', 20,
  '"Somerville%2C+Mary"', 20,
  '"Sonar"', 0,
  '"Sound+on+film"', 0,
  '"Spaceflight"', 0,
  '"Spencer%2C+Herbert"', 20,
  '"Sperry%2C+Elmer+Ambrose"', 20,
  '"Spinoza%2C+Baruch"', 17,
  '"Spurzheim%2C+Johann"', 19,
  '"Stalin"', 20,
  '"Suez+Canal"', 0,
  '"Swinton%2C+Alan+Archibald+Campbell"', 20,
  '"Tchaikovsky%2C+Peter+Ilich"', 19,
  '"Telephone"', 0,
  '"Tesla%2C+Nikola"', 20,
  '"Transistor"', 0,
  '"TV"', 0,
  '"Tykociner%2C+Joseph"', 20,
  '"Verdi%2C+Giuseppe+Fortunino+Francesco"', 19,
  '"Volta%2C+Alessandro"', 18,
  '"Voltaire%2C+Francois-Marie+Arouet+de"', 18,
  '"Watson-Watt%2C+Robert"', 20,
  '"Watt%2C+James"', 18,
  '"Wedgwood%2C+Josiah"', 18,
  '"Wells%2C+Herbert+George"', 20,
  '"Westinghouse"', 19,
  '"Zionism"', 0,
  '"Zworykin%2C+Vladimir+Kosma"', 20
  );

@line = <IN>;
close (IN);

$count = -1;
$newNode = 0;
$nodeName = "?";
$nodeId = "-1";
$nodeCent = "-1";
$nodeLabel = "?";
$nodeX = 0;
$nodeY = 0;
$nodeZ = 0; 

# I know I should be using here documents for long runs of text but I'm too
# lazy to look up the Perl syntax right now. 

print OUT "/*\n";
print OUT " * \$Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/tools/create_NodeData.pl,v 1.2 2005-06-24 19:57:15 paulby Exp $\n";
print OUT " * \$Date: 2005-06-24 19:57:15 $\n";
print OUT " *\n";
print OUT " * Joint Copyright (c) 2005 by\n";
print OUT " *   James A. Zaun, Consultant,\n";
print OUT " *   The Burke Institute,\n";
print OUT " *   Sun Microsystems, Inc.\n";
print OUT " * ALL RIGHTS RESERVED.\n";
print OUT " *\n";
print OUT " * The contents of this file are subject to the GNU General Public\n";
print OUT " * License, Version 2 (the \"License\"); you may not use this file\n";
print OUT " * except in compliance with the License. A copy of the License is\n";
print OUT " * available at http://www.opensource.org/licenses/gpl-license.php.\n";
print OUT " */\n";
print OUT "\n";
print OUT "package org.jdesktop.lg3d.apps.kwebdemo1.singletons;\n";
print OUT "\n";
print OUT "/*\n";
print OUT " * I prefer to explicitly show each class import rather use the '*' wildcard.\n";
print OUT " * While it is more typing, the reader can see exactly where each class is\n";
print OUT " * coming from, and it avoids software version skew problems were a class name\n";
print OUT " * may be added to a package in the future that is already in use by another\n";
print OUT " * package thus causing a naming conflict when using wildcards.  -jim zaun  \n";
print OUT " */\n";
print OUT "import java.util.Hashtable;\n";
print OUT "import java.util.Enumeration;\n";
print OUT "import java.lang.Integer;\n";
print OUT "\n";
print OUT "import org.jdesktop.lg3d.apps.kwebdemo1.nodes.*;\n";
print OUT "\n";
print OUT "/**\n";
print OUT " * James Burke's Knowledge Web graph node data.\n";
print OUT " * \n";
print OUT " * <p style=\"color: red; font-weight: bold\">Note: This class is autogenerated\n";
print OUT " * from a Perl 5.8 script, <code>create_NodeData.pl</code> located in the\n";
print OUT " * <code>tools</code> subdirectory.  DO NOT EDIT THIS CODE DIRECTLY.</p>\n";
print OUT " * \n";
print OUT " * <p>This is only a small portion of the complete KWeb graph for\n";
print OUT " * the purposes of this demo only.  The full dataset is copyrighted under\n";
print OUT " * a different copyright agreement. Only this portion is used by permission of\n";
print OUT " * the Burke Institute and put under the GNU General Public License.</p>\n";
print OUT " * \n";
print OUT " * <p>This class is normally used in conjunction with the\n";
print OUT " * {\@link EdgeData EdgeData} class which contains the graph edge details\n";
print OUT " * that create associations between the Node classes.</p>\n";
print OUT " *\n";
print OUT " * \@see org.jdesktop.lg3d.apps.kwebdemo1.nodes.BasicNode\n";
print OUT " * \@see org.jdesktop.lg3d.apps.kwebdemo1.nodes.JourneyNode\n";
print OUT " * \@see org.jdesktop.lg3d.apps.kwebdemo1.nodes.JourneyNodeA\n";
print OUT " *\n";
print OUT " * \@author Jim Zaun &lt;jz-lg\@zaun.com&gt; (a.k.a. &lt;zaun\@acm.org&gt;)\n";
print OUT " * \@version \$Revision: 1.2 $\n";
print OUT " * \@since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0\n";
print OUT " */\n";
print OUT "public class NodeData\n";
print OUT "{\n";
print OUT "  static private NodeData nodeData = null;\n";
print OUT "  private Hashtable<String,BasicNode> nodeTable =\n";
print OUT "    new Hashtable<String,BasicNode>(1023);\n";
print OUT "  private String nodeFontName = \"SansSerif\";\n";
print OUT "\n";
print OUT "  /**
print OUT "   * Horrible hack to meet the JavaOne 2005 deadline.  This needs to be
print OUT "   * rethought.  Used in <code>KWebMouseEnteredEventAdapter</code> to relate
print OUT "   * Java3D Shape3D picks to KWeb Node Ids.
print OUT "   */
print OUT "  public Hashtable<Integer,Integer> nodePickTable =
print OUT "    new Hashtable<Integer,Integer>(1023);
print OUT "\n";
print OUT "  /*\n";
print OUT "   * The contructor is hidden so the class can only be invoked by\n";
print OUT "   * getInstance().\n";
print OUT "   */\n";
print OUT "  private NodeData()\n";
print OUT "  {\n";
print OUT "    BasicNode node = null;\n";

foreach $line (@line)
  {
    chomp($line);
    # chop($line); # get rid of the CR too -- ONLY CYGWIN
    if ($line =~ /^\s*node\s+\[/)
      {
        $count++;
        $newNode = 1;
      }
    if (($line =~ /^\s*comment\s+".*"/) && $newNode)
      {
        ($nodeName) = ($line =~ /^\s*comment\s+(".*")/);
        $nodeCent = $century{$nodeName};
      }
    if (($line =~ /^\s*id\s+\d+/) && $newNode)
      {
        ($nodeId) = ($line =~ /^\s*id\s+(\d+)/);
      }
    if (($line =~ /^\s*label\s+".*"/) && $newNode)
      {
        ($nodeLabel) = ($line =~ /^\s*label\s+(".*")/);
      }
    if (($line =~ /^\s*x\s+.*/) && $newNode)
      {
        ($nodeX) = ($line =~ /^\s*x\s+(.*)/);
      }
    if (($line =~ /^\s*y\s+[-]?\d+\.\d+/) && $newNode)
      {
        ($nodeY) = ($line =~ /^\s*y\s+([-]?\d+\.\d+)/);
      }
    if (($line =~ /^\s*z\s+[-]?\d+\.\d+/) && $newNode)
      {
        ($nodeZ) = ($line =~ /^\s*z\s+([-]?\d+\.\d+)/);
      }
    if (($line =~ /^\s*\]/) && $newNode)
      {
        ($rho, $theta, $phi) = cartesian_to_spherical($nodeX, $nodeY, $nodeZ);
        if ($nodeName eq '"Mozart%2C+Wolfgang+Amadeus"')
          { print OUT "    node = new JourneyNodeA(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Beaumarchais%2C+Caron+de"')
          { print OUT "    node = new JourneyNodeB(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Jefferson%2C+Pres.+Thomas"')
          { print OUT "    node = new JourneyNodeC(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Beccaria%2C+Cesare"')
          { print OUT "    node = new JourneyNodeD(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Gall%2C+Franz+Josef"')
          { print OUT "    node = new JourneyNodeE(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Spurzheim%2C+Johann"')
          { print OUT "    node = new JourneyNodeF(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Follen%2C+Karl"')
          { print OUT "    node = new JourneyNodeG(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Hoffmann%2C+Ernst+Theodor+Amadeus"')
          { print OUT "    node = new JourneyNodeH(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Poe%2C+Edgar+Allan"')
          { print OUT "    node = new JourneyNodeI(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Rachmaninov%2C+Sergey"')
          { print OUT "    node = new JourneyNodeJ(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"Sikorsky%2C+Igor+Ivanovich"')
          { print OUT "    node = new JourneyNodeK(this, $nodeId, $nodeName,\n"; }
        elsif ($nodeName eq '"helicopter"')
          { print OUT "    node = new JourneyNodeL(this, $nodeId, $nodeName,\n"; }
        else
          { print OUT "    node = new BasicNode(this, $nodeId, $nodeName,\n"; }
        print OUT "                       $nodeCent, $theta, $phi,\n";
        print OUT "                       $nodeLabel);\n";
        print OUT "    nodeTable.put(\"$nodeId\", node);\n";  
        $newNode = 0;
        $nodeName = "?";
        $nodeId = "-1";
        $nodeLabel = "?";
        $nodeX = 0;
        $nodeY = 0;
        $nodeZ = 0; 
      }
  }
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get an instance of this class.\n";
print OUT "   * Implements the singleton design pattern.\n";
print OUT "   * \@return reference to only instance of this class\n";
print OUT "   */\n";
print OUT "  static public NodeData getInstance()\n";
print OUT "  {\n";
print OUT "    if (nodeData == null) nodeData = new NodeData();\n";
print OUT "    return nodeData;\n";
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get a node.\n";
print OUT "   * \n";
print OUT "   * While the Node object returned is a BasicNode, the actual object is\n";
print OUT "   * likely to be a derived class object.  See the Node class references in\n";
print OUT "   * this class declaration (above). \n";
print OUT "   * \n";
print OUT "   * \@param nodeId Node ID of node.\n";
print OUT "   * \@return a Node Object or <code>null</code> if none\n";
print OUT "   */\n";
print OUT "  public BasicNode getNode(String nodeId)\n";
print OUT "  {\n";
print OUT "    try\n";
print OUT "      {\n";
print OUT "        Integer.parseInt(nodeId);\n";
print OUT "      }\n";
print OUT "    catch (NumberFormatException e)\n";
print OUT "      {\n";
print OUT "        return null;\n";
print OUT "      }\n";
print OUT "    return nodeTable.get(nodeId);\n";
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get a node.\n";
print OUT "   * \n";
print OUT "   * While the Node object returned is a BasicNode, the actual object is\n";
print OUT "   * likely to be a derived class object.  See the Node class references in\n";
print OUT "   * this class declaration (above). \n";
print OUT "   * \n";
print OUT "   * \@param nodeId Node ID of node.\n";
print OUT "   * \@return an Node Object or <code>null</code> if none\n";
print OUT "   */\n";
print OUT "  public BasicNode getNode(int nodeId)\n";
print OUT "  {\n";
print OUT "    return nodeTable.get(Integer.toString(nodeId));\n";
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get an enumeration of all the Node objects.\n";
print OUT "   * \n";
print OUT "   * Use the Enumeration methods on the returned object to fetch each Node\n";
print OUT "   * object sequentially.\n";
print OUT "   * \n";
print OUT "   * \@return an Enumeration object for retreiving Node objects\n";
print OUT "   */\n";
print OUT "  public Enumeration<BasicNode> elements()\n";
print OUT "  {\n";
print OUT "    return nodeTable.elements();\n";
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get the Node font.\n";
print OUT "   * \n";
print OUT "   * \@return get font name for nodes.\n";
print OUT "   */\n";
print OUT "  public String getNodeFontName()\n";
print OUT "  {\n";
print OUT "    return nodeFontName;\n";
print OUT "  }\n";
print OUT "\n";
print OUT "  /**\n";
print OUT "   * Get a good fontname\n";
print OUT "   *\n";
print OUT "   * @return name of a font for node labels.\n";
print OUT "   */\n";
print OUT "  private String getAvailableFont()\n";
print OUT "  {\n";
print OUT "    java.awt.GraphicsEnvironment genv =\n";
print OUT "      java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();\n";
print OUT "    genv.preferProportionalFonts();\n";
print OUT "    String[] names = genv.getAvailableFontFamilyNames();\n";
print OUT "    for (int i = 0; i < names.length; i++)\n";
print OUT "      {\n";
print OUT "        if (names[i].equals(\"Aharoni CLM\")) return names[i];\n";
print OUT "        else if (names[i].equals(\"Century Gothic\")) return names[i];\n";
print OUT "        else if (names[i].equals(\"URW Gothic L\")) return names[i];\n";
print OUT "      }\n";
print OUT "    return \"SansSerif\";\n";
print OUT "  }\n";
print OUT "\n";
print OUT "}\n";
close (OUT);
