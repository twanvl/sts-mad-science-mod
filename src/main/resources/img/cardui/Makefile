JARFILE:=../../../../../../_ModTheSpire/desktop-1.0.jar
FILES:=bg_attack_bronze.png bg_skill_bronze.png bg_power_bronze.png
TARGETS:=$(addprefix 512/,$(FILES)) $(addprefix 1024/,$(FILES))
MODULATE:=95,105,120

all: $(TARGETS)
.PHONY: all

1024 512:
	mkdir -p $@

%_red.png: $(JARFILE)
	jar xvf ../../../../../../_ModTheSpire/desktop-1.0.jar images/cardui/$@
	mv images/cardui/$@ $@

%_bronze.png: %_red.png
	convert $< -modulate $(MODULATE) $@

$(addprefix 512/,$(FILES)): 512
$(addprefix 1024/,$(FILES)): 1024