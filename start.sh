#!/usr/bin/env bash

cd /home/ubuntu/app/BPS

./gradlew bootRun

sudo docker compose -f docker-compose.yml up -d
