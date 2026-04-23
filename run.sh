#!/bin/bash

echo "Starting. Please wait..."
mvn -q clean
mvn -q compile
mvn -q exec:java -Dexec.mainClass="se.yrgo.client.SimpleClient"
echo "Done!"
