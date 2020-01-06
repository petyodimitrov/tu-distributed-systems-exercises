@echo off

:: SET "TEMPPATH=%0\..\bin"
:: FOR /F "delims=" %%F IN ("%TEMPPATH%") DO SET "TEMPPATH=%%~fF"
:: set CLASSPATH=%TEMPPATH%
:: echo %CLASSPATH%

set CLASSPATH=target/client-server-rmi-1.0-SNAPSHOT.jar
rmiregistry
