@echo off
SETLOCAL

if NOT DEFINED IMBOX_HOME set IMBOX_HOME=%~dp0..
if NOT DEFINED JAVA_HOME goto err

REM JAVA options
REM You can set JVM additional options here if you want
if NOT DEFINED JVM_OPTS set JVM_OPTS=-XX:+TieredCompilation -XX:+UseBiasedLocking -XX:+UseParNewGC -XX:InitialCodeCacheSize=8m -XX:ReservedCodeCacheSize=32m -Dorg.terracotta.quartz.skipUpdateCheck=true
REM Combined java options
set JAVA_OPTS=%JAVA_OPTS% %JVM_OPTS%

set IMBOX_CLASSPATH=%IMBOX_HOME%\IM-server.jar;%IMBOX_HOME%\conf;%CLASSPATH%

if NOT DEFINED IMBOX_MAINCLASS set IMBOX_MAINCLASS=Bootstrap

if NOT DEFINED IMBOX_OPTS set IMBOX_OPTS=9999

goto launchIMBOX

:launchIMBOX
"%JAVA_HOME%\bin\java" %JAVA_OPTS% -cp "%IMBOX_CLASSPATH%" %IMBOX_MAINCLASS% %IMBOX_OPTS%
goto finally

:err
echo JAVA_HOME environment variable not set! Take a look at the readme.
pause

:finally
ENDLOCAL
