echo off

set M2=D:\TOOLS\M2_Branch
set JAVA_HOME=%JAVA_6_HOME%
set Path=%Path%;%M2_HOME%\bin;%JAVA_HOME%

@if "%1" == "clean" goto clean
@if "%1" == "dsk" goto desktop
@if "%1" == "web" goto web
@if "%1" == "flex" goto flex
@if "%1" == "ecl" goto eclipse
@if "%1" == "findbugs" goto findbugs

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
call mvn compile findbugs:check -e
goto end

:end
pause