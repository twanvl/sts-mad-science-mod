# Resize power images
IMAGES:=$(notdir $(wildcard large/*.png))
NOT_POWERS:=Jetpack AbstractMadScience
 # Note: Jetpack uses a built in image
POWERS:=$(patsubst %Power.java,%, $(notdir $(wildcard ../../../../java/madsciencemod/powers/*.java)))
MISSING_IMAGES:=$(filter-out $(NOT_POWERS) $(basename $(IMAGES)), $(POWERS))
EXTRA_IMAGES:=$(filter-out $(POWERS),$(basename $(IMAGES)))
IMAGES32:=$(addprefix 32/,$(IMAGES))

all: 32 $(IMAGES32) verify

.PHONY: all verify clean

32:
	mkdir -p $@
64:
	mkdir -p $@

32/%.png: large/%.png
	convert $^ -resize 32x32 $@

64/%.png: large/%.png
	convert $^ -resize 64x64 $@

define WARN
echo Warning: $(1);
endef

verify:
	@$(foreach c,$(MISSING_IMAGES),$(call WARN,missing image for power $(c)))
	@$(foreach c,$(EXTRA_IMAGES),$(call WARN,no power for image $(c)))

clean:
	rm -f 32/*.png
	rmdir 32
