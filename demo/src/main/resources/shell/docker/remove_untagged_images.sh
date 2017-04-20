#!/bin/sh
_IMAGECOUNT=$(docker images -a | grep "^<none>" | wc -l)

if [ $_IMAGECOUNT > 0 ]
then
    docker rmi --force $(docker images -a | grep "^<none>" | awk '{print $3}')
fi