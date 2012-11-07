echo off
cls
rem set M2_HOME=C:\TOOLS\maven-3.0.3
rem set JAVA_HOME=%JAVA_6_HOME%
rem set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%
rem set MAVEN_OPTS=-Xmx1024m -Xms512m


echo Install required jar in repository for dbex

rem mvn install:install-file -Dfile= -DgroupId= -DartifactId= -Dversion= -Dpackaging=jar
echo Installing AbsoluteLayout.jar::
call mvn install:install-file -Dfile=.\AbsoluteLayout.jar -DgroupId=org.netbeans -DartifactId=AbsoluteLayout -Dversion=1.0 -Dpackaging=jar

echo Installing tinylaf.jar::
call mvn install:install-file -Dfile=.\tinylaf.jar -DgroupId=de.muntjak.tinylookandfeel -DartifactId=tiny-lNf -Dversion=2.7 -Dpackaging=jar

echo Installing OfficeLnFs_2.7.jar::
call mvn install:install-file -Dfile=.\OfficeLnFs_2.7.jar -DgroupId=org.fife.plaf -DartifactId=office-lNf -Dversion=2.7 -Dpackaging=jar

echo Installing ojdbc14.jar::
call mvn install:install-file -Dfile=.\ojdbc14.jar -DgroupId=com.oracle.jdbc -DartifactId=ojdbc -Dversion=14 -Dpackaging=jar

echo Installing sqlserver_4.jar::
call mvn install:install-file -Dfile=.\sqlserver_4.jar -DgroupId=com.microsoft.sqlserver.jdbc -DartifactId=sqlserver -Dversion=2.0 -Dpackaging=jar

pause
