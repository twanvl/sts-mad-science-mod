all: layer1d.png layer2d.png layer3d.png layer4d.png layer5d.png
.PHONY: all clean

%d.png: %.png
	convert $< -modulate 100,33,100 $@

clean:
	rm -f *d.png
