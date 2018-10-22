# Resize relic images
IMAGES:=$(notdir $(wildcard source/*.png))
NOT_RELICS:=AbstractMadScienceRelic
RELICS:=$(basename $(notdir $(wildcard ../../../../java/madsciencemod/relics/*.java)))
MISSING_IMAGES:=$(filter-out $(NOT_RELICS) $(basename $(IMAGES)), $(RELICS))
EXTRA_IMAGES:=$(filter-out $(RELICS),$(basename $(IMAGES)))
OUTLINE_IMAGES:=$(addprefix outline/,$(IMAGES))
LARGE_IMAGES:=$(addprefix large/,$(IMAGES))

all: $(IMAGES) $(OUTLINE_IMAGES) $(LARGE_IMAGES) verify

.PHONY: all verify clean

%.png: source/%.png
	convert $^ -resize 128x128 $@

outline:
	mkdir -p outline

outline/%.png: %.png | outline
	convert $< -evaluate set 0 -negate  -blur 12x1.5  -channel A -level 0%,5% +channel $@

large:
	mkdir -p large

large/%.png: source/%.png | large
	convert $< -resize 256x256 $@

define WARN
echo Warning: $(1);
endef

verify:
	@$(foreach c,$(MISSING_IMAGES),$(call WARN,missing image for relic $(c)))
	@$(foreach c,$(EXTRA_IMAGES),$(call WARN,no relic for image $(c)))

clean:
	rm -f *.png
	rm -f outline/*.png
	rmdir outline
