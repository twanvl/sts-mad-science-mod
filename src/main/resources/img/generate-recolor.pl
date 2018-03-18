# Generate recolored orb and card background images
use strict;
use File::temp qw(tempdir);

# Extract from jar file
my $tmpdir;
if (0) {
  #use IO::Uncompress::Unzip qw(unzip);
  my $jarfile = '../../../../../../../ModTheSpire/desktop-1.0.jar';
  $tmpdir = File::Tempdir->new('',CLEANUP=>1);
  print "Extracting files to $tmpdir\n";
  #$tmpdir = '.original';
  mkdir $tmpdir;
  unzip $jarfile => $tmpdir;
} else {
  $tmpdir = 'L:/c/slay-the-spire';
}

# Recolor options
my $lightness  = 100+10;
my $saturation = 100+50;
my $hue        = 100+30;

# make output dirs
mkdir 'cardui/1024';
mkdir 'cardui/512';
mkdir 'characters/inventor/orb';

# files to recolor
my @orig_images = glob("
  $tmpdir/images/cardui/1024/*red*.png
  $tmpdir/images/cardui/512/*red*.png
  $tmpdir/images/ui/topPanel/*Red*.png
  $tmpdir/images/ui/topPanel/red/*.png");

for my $infile (@orig_images) {
  next if $infile =~ /green/i;
  next if $infile =~ /button/i;
  my $outfile = $infile;
  $outfile =~ s{red}{bronze}i;
  $outfile =~ s{$tmpdir/images}{.}i;
  $outfile =~ s{ui/topPanel/bronze}{characters/inventor/orb}i;
  $outfile =~ s{ui/topPanel/energybronzeVFX}{characters/inventor/orb/vfx}i;
  print "$infile -> $outfile   ";
  print "modulate: $lightness,$saturation,$hue\n";
  `convert $infile -modulate $lightness,$saturation,$hue $outfile`;
}

