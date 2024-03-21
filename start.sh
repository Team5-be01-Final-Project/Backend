#!/usr/bin/env bash

cd /home/ubuntu/app/restdb

./gradlew clean bootJar

sudo docker compose -f docker-compose.yml up -d
