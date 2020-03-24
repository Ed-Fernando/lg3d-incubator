#!perl
#!/usr/bin/perl -w
$|=1;

use XML::Parser;

if ($#ARGV != 1)
  {
    print ("Usage: $0 $1 ...something\n");
    exit(1);
  }
$id = $ARGV[0];
$infile = $ARGV[1];

$xmlParser = new XML::Parser;

$xmlParser->setHandlers(Start => \&startElement,
                        End   => \&endElement,
                        Char  => \&charData,
                        Default => \&default);

$xmlParser->parsefile($infile);

sub startElement
{
  my ($parseinst, $element, %attrs) = @_;
  if ($element eq "kweb-node")
    {
      print "  \'$attrs{'id'}\', $id,\n";
    }
}

sub endElement
{
  my ($parseinst, $element) = @_;
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