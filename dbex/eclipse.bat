echo off

set M2_HOME=D:\TOOLS\maven-2.2.1
set JAVA_HOME=D:\TOOLS\Java\jdk1.6.0
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%

call mvn eclipse:eclipse

pause