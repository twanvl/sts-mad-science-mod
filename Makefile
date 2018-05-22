
all:
	make -C src/main/resources/img/cards
	make -C src/main/resources/img/cardui
	make -C src/main/resources/img/powers
	make -C src/main/resources/img/relics
	make -C src/main/resources/img/characters/MadScientist/orb
	mvn package

clean:
	mvn clean
	make clean -C src/main/resources/img/cards
	make clean -C src/main/resources/img/cardui
	make clean -C src/main/resources/img/powers
	make clean -C src/main/resources/img/relics
	make clean -C src/main/resources/img/characters/MadScientist/orb
