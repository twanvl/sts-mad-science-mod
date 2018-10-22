# Scale down images from 64*64 to 32*32
use strict;

for my $infile (glob("64/*.png")) {
  my $outfile = $infile;
  $outfile =~ s{64}{32}i;
  `convert $infile -resize 32x32 $outfile`;
}
