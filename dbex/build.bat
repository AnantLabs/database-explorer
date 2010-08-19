echo off

set M2_HOME=D:\TOOLS\maven-2.2.1
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_18
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%

call mvn clean install

pause