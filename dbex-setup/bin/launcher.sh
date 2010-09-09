#!/bin/bash
#
# Normally, editing this script should not be required.
#
# To specify an alternative Java Runtime Environment, uncomment the following line and edit the path
ORACLE_GUI_JAVA_HOME="/usr/java/jdk1.6.0"

# Set the direnctory containing the jar file to execute
ORACLE_GUI_JAR_DIR="./"
#Set the jar file name
ORACLE_GUI_JAR_NAME="oracle-gui-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Set the maximum heap size
MAXIMUM_HEAP_SIZE=256m

# If you experience problems, e.g. incorrectly painted windows, try to uncomment one of the following two lines
#export AWT_TOOLKIT=MToolkit
export AWT_TOOLKIT=XToolkit

if [ "$ORACLE_GUI_JAVA_HOME" = "" ] ; then
    ORACLE_GUI_JAVA_HOME=$JAVA_HOME
fi

_JAVA_EXEC="java"
if [ "$ORACLE_GUI_JAVA_HOME" != "" ] ; then
    _TMP="$ORACLE_GUI_JAVA_HOME/bin/java"
    if [ -f "$_TMP" ] ; then
        if [ -x "$_TMP" ] ; then
            _JAVA_EXEC="$_TMP"
        else
            echo "Warning: $_TMP is not executable"
        fi
    else
        echo "Warning: $_TMP does not exist"
    fi
fi

if ! which "$_JAVA_EXEC" >/dev/null ; then
    echo "Error: No java environment found"
    exit 1
fi


java -Xmx${MAXIMUM_HEAP_SIZE} -jar "$ORACLE_GUI_JAR_DIR/$ORACLE_GUI_JAR_NAME" "$@"
