#!/bin/sh

set -ex

./gradlew --stop
./gradlew clean ktlintCheck --stacktrace
./gradlew clean detekt --stacktrace
./gradlew clean test --stacktrace
./gradlew clean jacocoTestReport --stacktrace
./gradlew clean assembleDebug -xlint --stacktrace