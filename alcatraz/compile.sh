#!/bin/bash

mkdir -p build/classes

javac -d build/classes src/calculator/client/*.java src/calculator/server/*.java src/calculator/common/*.java
