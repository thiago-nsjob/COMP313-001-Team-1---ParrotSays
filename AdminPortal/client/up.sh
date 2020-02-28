#!/bin/bash
sudo echo "pulling the eduardoamparo/parrot-says-client docker box..."
sudo docker pull eduardoamparo/parrot-says-client
sudo docker-compose up -d
sudo echo "done"