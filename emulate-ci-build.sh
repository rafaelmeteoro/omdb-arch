#!/bin/sh

set -ex

./gradlew --stop clean
./gradlew ktlintCheck --stacktrace
./gradlew detekt --stacktrace
./gradlew jacocoTestReport jacocoTestReportDebug --stacktrace
./gradlew assembleDebug -xlint --stacktrace
