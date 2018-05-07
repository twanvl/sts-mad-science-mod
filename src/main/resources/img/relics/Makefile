# Resize power images
IMAGES:=$(notdir $(wildcard large/*.png))
NOT_RELICS:=
RELICS:=$(basename $(notdir $(wildcard ../../../java/inventormod/relics/*.java)))
MISSING_IMAGES:=$(filter-out $(NOT_RELICS) $(basename $(IMAGES)), $(RELICS))
EXTRA_IMAGES:=$(filter-out $(RELICS),$(basename $(IMAGES)))

all: $(IMAGES) verify

.PHONY: all verify clean

%.png: large/%.png
	convert $^ -resize 128x128 $@

define WARN
echo Warning: $(1);
endef

verify:
	@$(foreach c,$(MISSING_IMAGES),$(call WARN,missing image for relic $(c)))
	@$(foreach c,$(EXTRA_IMAGES),$(call WARN,no relic for image $(c)))

clean:
	rm $(IMAGES32)
	rmdir 32
