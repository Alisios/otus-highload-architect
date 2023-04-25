#!/usr/bin/env bash

rm docker-stats.csv

while true; do
  docker stats --no-stream --format "table {{.Name}};{{.CPUPerc}};{{.MemPerc}};{{.MemUsage}};{{.NetIO}};{{.BlockIO}};{{.PIDs}}" |
    tail -n +2 |
    awk -v date=";$(date +%T)" '{print $0, date}' >> docker-stats.csv
  sleep 5
done
