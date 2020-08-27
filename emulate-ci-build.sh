#!/bin/sh

set -ex

./gradlew --stop clean
./gradlew ktlintCheck --no-daemon --stacktrace
./gradlew detekt --no-daemon --stacktrace
./gradlew jacocoTestReport jacocoTestReportDebug --no-daemon --stacktrace
./gradlew assembleDebug -xlint --no-daemon --stacktrace
