#!/bin/sh
./gradlew bootJar
docker image build -t repair-shop .
