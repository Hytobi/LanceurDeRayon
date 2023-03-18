#!/bin/bash
IMAGE1=$1
IMAGE2=$2

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

#javac -d $MYPATH/bin $MYPATH/src/CompareImage.java
java -cp $MYPATH/bin CompareImage $IMAGE1 $IMAGE2
