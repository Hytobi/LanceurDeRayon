#!/bin/bash

set -e

. assert.sh

echo "Course lanceur de rayons"

for testfile in `ls COURSE/*.test`
do
   imagefile=${testfile%.test}.png
   time ./raytrace.sh $testfile
   assert "./compare.sh $imagefile ${imagefile#COURSE/} | grep OK" "OK\n"
done

assert_end regression
