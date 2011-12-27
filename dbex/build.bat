echo off
cls
set M2_HOME=C:\TOOLS\maven-2.2.1
set JAVA_HOME=%JAVA_6_HOME%
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%
set MAVEN_OPTS=-Xmx1024m -Xms512m

@if "%1" == "clean" goto clean
@if "%1" == "dsk" goto desktop
@if "%1" == "web" goto web
@if "%1" == "flex" goto flex
@if "%1" == "ecl" goto eclipse
@if "%1" == "findbugs" goto findbugs
@if "%1" == "site" goto site

@echo Building DbEx Desktop and WEB Application.
@echo.
call mvn package -P default
goto end

:clean
@echo Cleaning Application.
@echo.
call mvn clean
goto end

:eclipse
@echo Running eclipse:eclipse Application.
@echo.
call mvn eclipse:eclipse
goto end


:desktop
@echo Building DbEx Desktop Application.
call mvn install -P desktop-app
goto end

:web
@echo Building DbEx WEB Application.
call mvn package -P web-app 
goto end

:flex
@echo Building DbEx FLEX Application.
call mvn package -P only-flex
goto end

:findbugs
@echo Running findbugs on java code
@echo.
call mvn compile findbugs:check
goto end

:site
@echo Running sites
@echo.
call mvn site:site
goto end

:end
pause