.PHONY: clean build run

OUT_DIR = ./target

APP_NAME = cars-api

DATABASE_URL = postgresql://cars:aaa@localhost:5432/cars?sslmode=disable
DATABASE_USER = cars
DATABASE_PASS = aaa
DATABASE_DB = cars
DATABASE_DRIVER = org.postgresql.Driver
TEST_DATABASE_DB = testcars
TEST_DATABASE_URL = jdbc:postgresql://localhost:5432/$(TEST_DATABASE_DB)

clean:
	mvn clean
	rm -rf ./target

build:
	make -C ../ build

run-dev: build
	java -jar $(OUT_DIR)/$(APP_NAME).jar --spring.profiles.active=dev --server.port=8081

run: build
	java -jar $(OUT_DIR)/$(APP_NAME).jar

postgres.init:
	docker run --name postgres13 -p 5432:5432 -e POSTGRES_USER=$(DATABASE_USER) -e POSTGRES_PASSWORD=$(DATABASE_PASS) -d postgres:13

postgres.create:
	docker exec -it postgres13 createdb --username=$(DATABASE_USER) --owner=$(DATABASE_USER) $(DATABASE_DB)

postgres.create-test:
	docker exec -it postgres13 createdb --username=$(DATABASE_USER) --owner=$(DATABASE_USER) $(TEST_DATABASE_DB)

postgres.start:
	docker start postgres13

postgres.drop:
	docker exec -it postgres13 dropdb --username=$(DATABASE_USER) $(DATABASE_DB)

postgres.drop-test:
	docker exec -it postgres13 dropdb --username=$(DATABASE_USER) $(TEST_DATABASE_DB)
