echo off

set M2_HOME=%MAVEN_HOME%
set JAVA_HOME=%JAVA_6_HOME%
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%

call mvn eclipse:eclipse

pause