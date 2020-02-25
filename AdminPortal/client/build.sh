#!/bin/bash
sudo echo "Welcome to building deployment for Parrot Says Client Application..."
sudo echo ""
sudo echo ""

sudo echo "Building docker image eduardoamparo/parrot-says-client..."
sudo docker build -t eduardoamparo/parrot-says-client .
cd ..
sudo echo "Done. -- Building docker image eduardoamparo/parrot-says-client..."
sudo echo ""
sudo echo ""

sudo echo "Pushing docker image eduardoamparo/parrot-says-client..."
sudo docker push eduardoamparo/parrot-says-client

sudo echo "Loging to Remote server and restart application."
ssh parrotsays "cd parrotsays-client && ./down.sh && ./up.sh"