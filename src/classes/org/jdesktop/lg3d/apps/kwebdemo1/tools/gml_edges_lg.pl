#!perl
#!/usr/bin/perl -w
$|=1;

use XML::Parser;

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

%weight = (
  '?', 1.0,
  'a developer of', 3.0,
  'a fan was', 2.0,
  'a follower of', 2.0,
  'a pal of', 2.0,
  'a patient was', 0.5,
  'a supporter of', 1.0,
  'admired by', 1.5,
  'admired', 1.5,
  'admirer of', 1.5,
  'affair with', 0.7,
  'allied with', 1.0,
  'anticipated by', 0.5,
  'anticipated', 0.5,
  'approached by', 1.0,
  'assistant to', 2.0,
  'assistant was', 2.0,
  'assisted by', 2.0,
  'attended premiere of', 1.0,
  'based on discovery by', 1.5,
  'biography written by', 1.5,
  'build by', 3.0,
  'built', 3.0,
  'co- founded by', 3.0,
  'co-founded by', 3.0,
  'co-invented by', 3.0,
  'collaborated with', 2.0,
  'commissioned by', 1.5,
  'commissioned', 1.5,
  'commonly thought to be invented by team led by', 3.0,
  'consulted', 1.5,
  'corresponded with', 1.5,
  'critiqued', 0.5,
  'cuckolded', 0.5,
  'developed by', 3.0,
  'developed work by', 1.5,
  'development aided by', 1.5,
  'disagreed with theory of', 0.5,
  'disagreed with', 0.5,
  'discovered by', 3.0,
  'discovered', 3.0,
  'disputed by', 0.5,
  'drew on work of', 1.0,
  'early development by', 1.5,
  'employed', 1.0,
  'encouraged by', 1.0,
  'fellow-socialist was', 1.5,
  'first developed by', 3.0,
  'first investigated by', 3.0,
  'followed up work of', 1.5,
  'follower was', 2.0,
  'founded by', 3.0,
  'funded by', 1.0,
  'gave funeral oration for', 1.0,
  'good review from', 0.5,
  'grandfather was', 1.0,
  'he invented', 3.0,
  'hero was', 2.0,
  'his brother hired', 0.25,
  'his elogy given by', 1.5,
  'his partner was', 2.0,
  'his work developed by', 1.5,
  'his work related to', 1.0,
  'honoured', 1.5,
  'improved phonograph of', 1.5,
  'influenced by', 1.0,
  'influenced', 1.0,
  'initiated by', 1.5,
  'inspired by', 1.5,
  'inspired', 1.5,
  'interviewed by', 0.7,
  'interviewed', 0.7,
  'invented by', 3.0,
  'invented', 3.0,
  'invention superseded by', 0.5,
  'invested in', 1.5,
  'investigated', 1.0,
  'investigated by', 1.0,
  'knew', 0.2,
  'laid basis for work of', 1.0,
  'laid ground for', 1.0,
  'lectured on', 1.0,
  'lover of', 3.0,
  'made possible work of', 1.0,
  'mentored by', 2.0,
  'mentored', 2.0,
  'met partner of', 0.3,
  'met', 0.5,
  'might have anticipated', 0.3,
  'named after', 2.0,
  'nearly anticipated by', 0.3,
  'neighbor was', 0.5,
  'opposed by', 0.3,
  'picked up on work of', 0.5,
  'pioneered by', 3.0,
  'played for', 1.5,
  'poems set to music by', 1.0,
  'premiere attended by', 0.7,
  'provided film for', 0.5,
  'read works of', 0.5,
  'rejected view of', 0.2,
  'related to work of', 0.3,
  'related to', 0.3,
  'replaced', 1.0,
  'rival telephone by', 1.0,
  'rival was', 1.0,
  'satirized by', 1.5,
  'satirized', 1.5,
  'sent first work to', 1.0,
  'served under', 2.0,
  'set to music poems of', 0.5,
  'shared patron with', 0.25,
  'shared view of', 0.5,
  'signed treaty with', 1.0,
  'studied by', 0.5,
  'studied work of', 0.5,
  'studied', 0.5,
  'superseded by', 0.5,
  'superseded work of', 0.5,
  'supported by', 1.5,
  'supported theory of', 1.5,
  'supported', 1.5,
  'taught by', 2.0,
  'taught', 2.0,
  'theory applied by', 1.5,
  'theory developed by',2.0,
  'theory disproved by', 0.3,
  'theory rejected by', 0.3,
  'used story by', 0.5,
  'used theory of', 1.0,
  'used work of', 1.0,
  'used', 1.0,
  'visited by', 0.3,
  'visited', 0.3,
  'was associated with', 1.0,
  'was invented by', 3.0,
  'was pioneered by', 2.0,
  'was succeeded by', 0.5,
  'was used by', 1.0,
  'won Nobel with', 1.5,
  'work adapted by', 1.5,
  'work aided', 1.0,
  'work developed by', 2.0,
  'work read by', 0.5,
  'work related to', 0.5,
  'worked for', 2.0,
  'worked with', 2.0,
  'wrote Aida for opening of', 1.5,
  'wrote for', 1.5,
  'wrote parody of', 1.5,
  'wrote preface for', 1.5
  );

$infile = $ARGV[1];

$xmlParser = new XML::Parser;

$xmlParser->setHandlers(Start => \&startElement,
                        End   => \&endElement,
                        Char  => \&charData,
                        Default => \&default);

$xmlParser->parsefile($infile);

$tag = "";
$nid = 0;
$nname = "";

sub startElement
{
  my ($parseinst, $element, %attrs) = @_;
  SWITCH:
    {
      if ($element eq "kweb-node")
        {
          if (defined $nodeid{$attrs{'id'}})
            {
              $nname = $attrs{'id'};
              $nid = $nodeid{$attrs{'id'}};
            }
          last SWITCH;
        }
      if ($element eq "link")
        {
          if (defined $nodeid{$attrs{'idref'}})
            {
              if (defined $weight{$attrs{'trans'}})
                {
                  $w = $weight{$attrs{'trans'}};
                }
              else
                {
                  $w = 1.0;
                }      
              print "  edge [\n";
              $id = $nodeid{$attrs{'idref'}};
              print "    source $nid\n";
              print "    comment \"$nname\"\n";
              print "    weight $w\n";
              print "    label \"$attrs{'trans'}\"\n";
              print "    target $id\n";
              print "    comment \"$attrs{'idref'}\"\n";
              print "    ]\n";
            }
          last SWITCH;
        }
    }
}

sub endElement
{
  my ($parseinst, $element) = @_;
  $tag = "";
  if ($element eq "kweb-node") { $nid = 0; $nname = ""; }
  # do nothing
}

sub charData
{
  my ($parseinst, $data) = @_;
  # do nothing
}

sub default
{
  my ($parseinst, $data) = @_;
  # do nothing
}