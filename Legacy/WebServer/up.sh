#!/bin/bash
sudo echo "pulling the eduardoamparo/parrot-says-api docker box..."
sudo docker pull eduardoamparo/parrot-says-api
sudo docker-compose up -d
sudo echo "done"
