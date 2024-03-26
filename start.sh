#!/usr/bin/env bash

cd /home/ubuntu/app/bps

cp .env ../../

cp /BPS/target/*.jar app.jar

sudo -E docker compose -f docker-compose.yml up -d