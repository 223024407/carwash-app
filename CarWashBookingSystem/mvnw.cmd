@echo off
setlocal
set MVNW_HOME=%~dp0
java -jar "%MVNW_HOME%\.mvn\wrapper\maven-wrapper.jar" %*
