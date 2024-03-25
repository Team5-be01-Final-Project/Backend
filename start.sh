#!/usr/bin/env bash

echo "==========================="
echo "DB_URL: ${DB_URL}"
echo "DB_USERNAME: ${DB_USERNAME}"
echo "DB_PASSWORD:${DB_PASSWORD}"
echo "==========================="

cd /home/ubuntu/app/bps/BPS

mvn clean install -DskipTests

cd ..

sudo -E docker compose -f docker-compose.yml up -d