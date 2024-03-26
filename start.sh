#!/usr/bin/env bash

cd /home/ubuntu/app/bps

sudo cp .env ../../

sudo cp /BPS/target/*.jar app.jar

sudo -E docker compose -f docker-compose.yml up -d