#!/usr/bin/env bash

cd /home/ubuntu/app/bps

cp ./BPS/target/*.jar app.jar

sudo docker compose -f docker-compose.yml up -d --force-recreate --build