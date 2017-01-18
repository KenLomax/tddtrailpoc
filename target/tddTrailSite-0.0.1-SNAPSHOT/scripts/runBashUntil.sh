#!/bin/bash

TRAILROOT=/Users/d061192/Trail

if [ -z "$1" ]; then
  echo "No jump points supplied"
else
    if [ -z "$2" ]; then
      echo $content | sed -n '/PoC of a TDD Tr$TRAILROOT/TddTrail/src/main/webapp/tddTrail.html1'/p' 
      
      #|  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}' 
#      sed -n '/PoC of a TDD Trail/,/\>'$1'/p' ~/Trail/TddTrail/src/main/webapp/tddTrail.html |  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}' | bash
    else
       echo $TRAILROOT/TddTrail/src/main/webapp/tddTrail.html sed -n '/\>'$1'/,/'$2'/p' |  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}'  
#      sed -n '/\>'$1'/,/'$2'/p' ~/Trail/TddTrail/src/main/webapp/tddTrail.html |  awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}'  | bash
    fi
fi
