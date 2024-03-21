#!/usr/bin/env bash

cd /home/ubuntu/app/bps/BPS

mvn clean install -DskipTests

sudo docker compose -f docker-compose.yml up -d
