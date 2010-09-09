echo off

set M2_HOME=D:\TOOLS\maven-2.2.1
set JAVA_HOME=D:\TOOLS\Java\jdk1.6.0_21
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%

call java -jar dbex-launcher-1.0-BETA-1.jar

