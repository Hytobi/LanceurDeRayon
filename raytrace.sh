#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")


#if [ ! -f "myapp.mf" ]
#then
    #touch "myapp.mf"
    #echo "Manifest-Version: 1.0" > ./myapp.mf
    #echo "Main-Class: RayTracer" > ./myapp.mf
#fi
#

#javac -d $MYPATH/bin -cp bin:src $MYPATH/src/RayTracer.java
#jar cmf manifest.mf raytracer.jar -C bin . && echo
java -Dapple.awt.UIElement=true -jar $MYPATH/raytracer.jar $1 2>&1


