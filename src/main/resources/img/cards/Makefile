# Generate card images
IMAGES:=$(notdir $(wildcard large/*.png))
NOT_CARDS:=AbstractMadScienceCard AbstractTrinket
CARDS:=$(basename $(notdir $(wildcard ../../../java/madsciencemod/cards/*.java)))
CARDS+=
MISSING_IMAGES:=$(filter-out $(NOT_CARDS) $(basename $(IMAGES)), $(CARDS))
EXTRA_IMAGES:=$(filter-out $(CARDS),$(basename $(IMAGES)))
BIG_IMAGES:=$(patsubst %.png,%_p.png,$(IMAGES))

all: $(IMAGES) $(BIG_IMAGES) verify

.PHONY: all verify clean

%.png: large/%.png
	convert $^ -resize 50% $@

%_p.png: large/%.png
	cp $^ $@

define WARN
echo Warning: $(1);
endef

verify:
	@$(foreach c,$(MISSING_IMAGES),$(call WARN,missing image for $(c)))
	@$(foreach c,$(EXTRA_IMAGES),$(call WARN,no card for image $(c)))

clean:
	rm -f *.png