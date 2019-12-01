#!/usr/bin/env bash
sed -i'' 's/br.com.edsb.hackathon/'"$1"'/g' app/build.gradle
appPath=$(echo -n $1 | sed 's/\./\//g')
mkdir -p app/src/main/java/$appPath
mv app/src/main/java/br/com/edsb/hackathonaasp/* app/src/main/java/$appPath
rm -rf app/src/main/java/br/com/edsb/hackathonaasp
find . -name "*.java" -exec sed -i'' 's/br.com.edsb.hackathon/'"$1"'/g' {} +
find . -name "*.xml" -exec sed -i'' 's/br.com.edsb.hackathon/'"$1"'/g' {} +
