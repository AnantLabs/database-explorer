echo off
cls
set M2_HOME=C:\TOOLS\maven-3.0.3
set JAVA_HOME=%JAVA_6_HOME%
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%
set MAVEN_OPTS=-Xmx1024m -Xms512m


echo Install required jar in repository for dbex

rem mvn install:install-file -Dfile= -DgroupId= -DartifactId= -Dversion= -Dpackaging=jar
echo Installing AbsoluteLayout.jar::
call mvn install:install-file -Dfile=..\dbex-setup\libs\AbsoluteLayout.jar -DgroupId=org.netbeans -DartifactId=AbsoluteLayout -Dversion=1.0 -Dpackaging=jar

echo Installing tinylaf.jar::
call mvn install:install-file -Dfile=..\dbex-setup\libs\tinylaf.jar -DgroupId=de.muntjak.tinylookandfeel -DartifactId=tiny-lNf -Dversion=2.7 -Dpackaging=jar

echo Installing OfficeLnFs_2.7.jar::
call mvn install:install-file -Dfile=..\dbex-setup\libs\OfficeLnFs_2.7.jar -DgroupId=org.fife.plaf -DartifactId=office-lNf -Dversion=2.7 -Dpackaging=jar

pause