#!/usr/bin/env bash

cd /home/ubuntu/app/bps

mvn clean package

sudo docker compose -f docker-compose.yml up -d
