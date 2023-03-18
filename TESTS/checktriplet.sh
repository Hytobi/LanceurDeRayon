#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

# On compile et on exécute le programme avec me le pacjage math
#javac -d $MYPATH/bin -cp bin:src $MYPATH/src/TestTriplet.java
java -Dapple.awt.UIElement=true -cp $MYPATH/bin TestTriplet "$1"


