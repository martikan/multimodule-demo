.PHONY: clean test build

# clean all modules.
clean:
	rm -rf target
	make -C cars-api clean
	make -C common clean

build: clean
	mvn package -Dmaven.test.skip

test:
	mvn test