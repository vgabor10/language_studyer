#!/bin/bash

cd Java
rm *.class
cd ../src
javac -d ../Java LanguageStudyer.java
cd ../Java
java LanguageStudyer
cd ..
