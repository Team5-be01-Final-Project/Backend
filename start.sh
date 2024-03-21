#!/usr/bin/env bash

cd /home/ubuntu/app/bps

./mvnw clean install

sudo docker compose -f docker-compose.yml up -d
