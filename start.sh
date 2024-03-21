#!/usr/bin/env bash

cd /home/ubuntu/app/BPS

./mvnw clean install

sudo docker compose -f docker-compose.yml up -d
