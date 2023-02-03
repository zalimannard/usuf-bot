#!/bin/bash

git pull

gradle clean
gradle assemble

docker-compose stop

export PREFIX=$1
export TOKEN=$2

docker-compose up --build -d
