#!/bin/bash

JAVA_HOME="/usr/java/jdk1.6.0"
M2_HOME="/home/sabuj/TOOLS/maven-2.2.1"

PATH=$PATH:$JAVA_HOME:$M2_HOME/bin

mvn clean install

