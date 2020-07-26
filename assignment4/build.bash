#!/bin/bash
set -e -x

COUNTER=0

while [  $COUNTER -lt 15  ]; do
    echo ""
    let COUNTER=COUNTER+1
done


javac Game.java View.java Controller.java Model.java Tube.java Json.java TubeComparator.java Sprite.java Goomba.java
