#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

#javac -d $MYPATH/bin -cp bin:src $MYPATH/src/CheckScene.java
java -Dapple.awt.UIElement=true -cp $MYPATH/bin CheckScene "$1" 2>&1

