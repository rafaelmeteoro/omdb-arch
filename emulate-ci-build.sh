#!/bin/sh

set -ex

./gradlew --stop
./gradlew clean test --no-daemon --stacktrace
./gradlew clean assembleDebug -xlint --no-daemon --stacktrace
./gradlew clean build jacocoTestReportDebug cobertura coveralls --no-daemon --stacktrace