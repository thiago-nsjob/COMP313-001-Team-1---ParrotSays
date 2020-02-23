#!/bin/bash
sudo echo "Welcome to building deployment for Parrot Says Application - Java EE application..."
sudo echo ""
sudo echo ""

sudo echo "Building docker image eduardoamparo/parrot-says-api..."
cd ParrotSays_API/
mvn clean package
sudo docker build -t eduardoamparo/parrot-says-api .
cd ..
sudo echo "Done. -- Building docker image eduardoamparo/parrot-says-api..."
sudo echo ""
sudo echo ""

sudo echo "Pushing docker image eduardoamparo/parrot-says-api..."
sudo docker push eduardoamparo/parrot-says-api

sudo echo "Loging to Remote server and restart application."
ssh parrotsays "cd parrotsays && ./down.sh && ./up.sh"