#!perl
#!/usr/bin/perl -w
$|=1;

use XML::Parser;
use Math::Trig ':radial';

if ($#ARGV != 1)
  {
    print ("Usage: $0 $1 ...something\n");
    exit(1);
  }

%nodeid = (
  'Bardeen%2C+John', 95,
  'Beaumarchais%2C+Caron+de', 114,
  'Beccaria%2C+Cesare', 116,
  'Bell%2C+Alexander+Graham', 126,
  'Bohr%2C+Niels+Henrik+David', 182,
  'Born%2C+Max', 197,
  'Bragg%2C+Sir+William+Henry', 209,
  'Brattain%2C+Walter+Houser', 217,
  'Braun%2C+Karl+Ferdinand', 218,
  'Brewster%2C+Sir+David', 227,
  'Broglie%2C+Louis%2C+Duke+de', 235,
  'Buchan%2C+John', 254,
  'Byron%2C+George+Gordon', 275,
  'Cassini+family', 309,
  'Communism', 379,
  'Curie%2C+Marie', 428,
  'Darwin%2C+Charles+Robert', 444,
  'Darwin%2C+Erasmus', 445,
  'Davy%2C+Sir+Humphry', 449,
  'Debussy%2C+Achille-Claude', 450,
  'Descartes%2C+Rene', 460,
  'Diaghilev%2C+Sergei+Pavlovich', 464,
  'Eastman%2C+George', 516,
  'Edison%2C+Thomas+Alva', 518,
  'Einstein%2C+Albert', 524,
  'electric+motor', 529,
  'Electrolysis', 531,
  'Electromagnetic+radiation', 532,
  'electromagnetism', 533,
  'Engels%2C+Friedrich', 545,
  'Eugenics', 553,
  'Evolution%2C+theory+of', 559,
  'Faraday%2C+Michael', 565,
  'Follen%2C+Karl', 594,
  'Franklin%2C+Benjamin', 609,
  'Freud%2C+Sigmund', 619,
  'Galilei%2C+Galileo', 632,
  'Gall%2C+Franz+Josef', 635,
  'Galton%2C+Sir+Francis', 637,
  'Galvani%2C+Luigi', 638,
  'Goddard%2C+Robert+Hutchings', 684,
  'Goethe%2C+Johann+Wolfgang+von', 689,
  'gyroscope', 744,
  'Hales%2C+Stephen', 748,
  'Hamilton%2C+Sir+William', 759,
  'Heisenberg%2C+Werner+Karl', 787,
  'helicopter', 788,
  'Helmholtz%2C+Hermann+von', 789,
  'Herschel%2C+Sir+William', 802,
  'Hertz%2C+Heinrich+Rudolf', 803,
  'Herzl%2C+Theodor', 804,
  'Hoffmann%2C+Ernst+Theodor+Amadeus', 815,
  'Hypnosis', 855,
  'Induction', 858,
  'James%2C+Henry', 871,
  'Jefferson%2C+Pres.+Thomas', 876,
  'kaleidoscope', 899,
  'Langevin%2C+Paul', 959,
  'Laser', 963,
  'Lawrence%2C+Thomas+Edward', 970,
  'Leibniz%2C+Gottfried+Wilhelm', 981,
  'Lenin%2C+Vladimir+Ilich', 983,
  'Lesseps%2C+Ferdinand%2C+Vicomte+de', 986,
  'Light+bulb', 991,
  'Liquid-fuel+rocket', 1001,
  'Liszt%2C+Franz', 1004,
  'Mach%2C+Ernst', 1038,
  'Mach+number', 1039,
  'Mahler%2C+Gustav', 1048,
  'Mao+Zedong', 1061,
  'Marconi%2C+Guglielmo', 1062,
  'Marx%2C+Karl', 1076,
  'Mesmer%2C+Franz+Anton', 1117,
  'Mozart%2C+Wolfgang+Amadeus', 1168,
  'Napoleon+Bonaparte', 1186,
  'Nehru%2C+Jawaharlal', 1199,
  'Nelson%2C+Horatio', 1201,
  'Nipkow%2C+Paul+Gottlieb', 1217,
  'Oersted%2C+Hans+Christian', 1227,
  'Oscilloscope', 1241,
  'Planck%2C+Max', 1317,
  'Poe%2C+Edgar+Allan', 1327,
  'psychoanalysis', 1350,
  'Purkinje+cells', 1356,
  'Purkinje%2C+Jan+Evangelista', 1357,
  'Quantum+physics', 1358,
  'Rachmaninov%2C+Sergey', 1361,
  'Radar', 1363,
  'radio', 1364,
  'Radioactivity', 1365,
  'Reductionism', 1383,
  'Relativity', 1387,
  'Roosevelt%2C+Pres.+Franklin+Delano', 1429,
  'Rutherford%2C+Ernest', 1447,
  'Schelling%2C+Frederick+Wilhelm+Joseph+von', 1464,
  'Shaw%2C+George+Bernard', 1497,
  'Shockley%2C+William+Bradford', 1505,
  'Sikorsky%2C+Igor+Ivanovich', 1517,
  'Somerville%2C+Mary', 1545,
  'Sonar', 1546,
  'Sound+on+film', 1548,
  'Spaceflight', 1551,
  'Spencer%2C+Herbert', 1553,
  'Sperry%2C+Elmer+Ambrose', 1556,
  'Spinoza%2C+Baruch', 1557,
  'Spurzheim%2C+Johann', 1560,
  'Stalin', 1563,
  'Suez+Canal', 1588,
  'Swinton%2C+Alan+Archibald+Campbell', 1598,
  'Tchaikovsky%2C+Peter+Ilich', 1604,
  'Telephone', 1607,
  'Tesla%2C+Nikola', 1614,
  'Transistor', 1648,
  'TV', 1662,
  'Tykociner%2C+Joseph', 1664,
  'Verdi%2C+Giuseppe+Fortunino+Francesco', 1689,
  'Volta%2C+Alessandro', 1712,
  'Voltaire%2C+Francois-Marie+Arouet+de', 1713,
  'Watson-Watt%2C+Robert', 1730,
  'Watt%2C+James', 1731,
  'Wedgwood%2C+Josiah', 1738,
  'Wells%2C+Herbert+George', 1743,
  'Westinghouse', 1748,
  'Zionism', 1800,
  'Zworykin%2C+Vladimir+Kosma', 1806
  );

$infile = $ARGV[1];
print STDERR "$ARGV[1]\n";

$xmlParser = new XML::Parser;

$xmlParser->setHandlers(Start => \&startElement,
                        End   => \&endElement,
                        Char  => \&charData,
                        Default => \&default);

$xmlParser->parsefile($infile);

$tag = "";
$nid = 0;

sub randomNodePos
{
  my ($callcount) = @_;
  for ($i = 0; $i < int($callcount); $i++) { rand(); }
  $theta = rand(2.0*3.1415926536);
  $phi = rand(3.1415926536);
  my ($x, $y, $z) = spherical_to_cartesian(1.0, $theta, $phi);
  print "    x $x\n";
  print "    y $y\n";
  print "    z $z\n";
}

sub startElement
{
  my ($parseinst, $element, %attrs) = @_;
  SWITCH:
    {
      if ($element eq "kweb-node")
        {
          print "  node [\n";
          if (defined $nodeid{$attrs{'id'}})
            {
              $nid = $nodeid{$attrs{'id'}};
              print "    comment \"$attrs{'id'}\"\n";
              print "    id $nid\n";
              randomNodePos($nid);
            }
          last SWITCH;
        }
      if ($element eq "one-liner")
        {
          $tag = "one-liner";
          print "    label \""; 
          last SWITCH;
        }
    }
}

sub endElement
{
  my ($parseinst, $element) = @_;
  $tag = "";
  if ($element eq "kweb-node") { $nid = 0; }
  if ($element eq "one-liner") { print "\"\n    ]\n"; }
  # do nothing
}

sub charData
{
  my ($parseinst, $data) = @_;
  if ($tag eq "one-liner" && $nid > 0)
    {
      $data =~ s/\r|\n|\t//g;
      $data =~ s/"/'/g;
      $data =~ s/ $//;
      print $data;
    }
  # do nothing
}

sub default
{
  my ($parseinst, $data) = @_;
  # do nothing
}