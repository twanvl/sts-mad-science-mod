# Resize power images
IMAGES:=$(notdir $(wildcard large/*.png))
NOT_RELICS:=AbstractInventorRelic
RELICS:=$(basename $(notdir $(wildcard ../../../java/inventormod/relics/*.java)))
MISSING_IMAGES:=$(filter-out $(NOT_RELICS) $(basename $(IMAGES)), $(RELICS))
EXTRA_IMAGES:=$(filter-out $(RELICS),$(basename $(IMAGES)))
OUTLINE_IMAGES:=$(addprefix outline/,$(IMAGES))

all: $(IMAGES) $(OUTLINE_IMAGES) verify

.PHONY: all verify clean

%.png: large/%.png
	convert $^ -resize 128x128 $@

outline:
	mkdir -p outline

outline/%.png: %.png outline
	convert $< -evaluate set 0 -negate  -blur 12x1.5  -channel A -level 0%,5% +channel $@

define WARN
echo Warning: $(1);
endef

verify:
	@$(foreach c,$(MISSING_IMAGES),$(call WARN,missing image for relic $(c)))
	@$(foreach c,$(EXTRA_IMAGES),$(call WARN,no relic for image $(c)))

clean:
	rm $(IMAGES)
	rm $(OUTLINE_IMAGES)
	rmdir outline
