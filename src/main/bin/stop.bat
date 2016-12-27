@echo off

SETLOCAL

if NOT DEFINED IMBOX_HOME set IMBOX_HOME=%~dp0..

set IMBOX_MAINCLASS=ShutdownServer

"%IMBOX_HOME%\bin\start.bat"

ENDLOCAL
