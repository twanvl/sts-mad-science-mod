# Generate card background images.
# The images are made with imagemagick, with convert -modulate

JARFILE:=../../../../../../../_ModTheSpire/desktop-1.0.jar
TO_RECOLOR:=bg_attack_mad_science.png bg_skill_mad_science.png bg_power_mad_science.png
FILES:=$(TO_RECOLOR) card_mad_science_orb.png
TARGETS:=$(addprefix 512/,$(FILES)) $(addprefix 1024/,$(FILES))
MODULATE:=95,105,120

.PHONY: all clean
all: 1024 512 $(TARGETS) small_mad_science_orb.png

1024 512:
	mkdir -p $@

# Note: no longer separate files, these now have to be extracted from an atlas
#images/cardui/%_red.png:
#	jar xvf $(JARFILE) $@

1024/%_mad_science.png: images/cardui/1024/%_red.png
	convert $< -modulate $(MODULATE) $@
512/%_mad_science.png: images/cardui/512/%_red.png
	convert $< -modulate $(MODULATE) $@

$(addprefix 512/,$(FILES)): 512
$(addprefix 1024/,$(FILES)): 1024

1024/card_mad_science_orb.png: orb.svg
	inkscape $< --export-png=$@

512/card_mad_science_orb.png: 1024/card_mad_science_orb.png
	convert $< -resize 50% -background transparent -splice 81x22 -extent 512x512 $@

small_mad_science_orb.png: small-orb.svg
	inkscape $< --export-png=$@

clean:
	rm -f 512/*.png
	rm -f 1024/*.png
	rmdir 512
	rmdir 1024
