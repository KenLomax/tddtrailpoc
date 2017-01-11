#!/bin/bash

if [ -z "$1" ]
  then
    echo "No argument supplied"  
    exit -1;  
fi

# sed -n '/PoC of a TDD Trail/,/Create a new extension/p' ~/Trail/TddTrail/src/main/webapp/tddTrail.html |  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}'

sed -n '/PoC of a TDD Trail/,/'$1'/p' ~/Trail/TddTrail/src/main/webapp/tddTrail.html |  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}' | bash

