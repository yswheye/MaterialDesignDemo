#!/usr/bin/env bash

./gradlew clean build

say '打包完成'

./gradlew uploadPgyer

say '上传完成'