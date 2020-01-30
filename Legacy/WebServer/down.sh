#!/bin/bash
sudo echo "shouting down the docker compose file..."
sudo docker-compose down -v --rmi local
sudo echo "done."
