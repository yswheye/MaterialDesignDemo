#!/bin/bash
#./gradlew clean assemblerelease
./gradlew clean build \
-x lint \
-profile