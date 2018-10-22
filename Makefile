
all:
	make -C src/main/resources/madsciencemod/images/cards
	make -C src/main/resources/madsciencemod/images/cardui
	make -C src/main/resources/madsciencemod/images/powers
	make -C src/main/resources/madsciencemod/images/relics
	make -C src/main/resources/madsciencemod/images/characters/MadScientist/orb
	mvn package

clean:
	mvn clean
	make clean -C src/main/resources/madsciencemod/images/cards
	make clean -C src/main/resources/madsciencemod/images/cardui
	make clean -C src/main/resources/madsciencemod/images/powers
	make clean -C src/main/resources/madsciencemod/images/relics
	make clean -C src/main/resources/madsciencemod/images/characters/MadScientist/orb
