@echo off
setlocal enabledelayedexpansion
color 0b
title Fit Resume version 0.10.1
echo Running Fit Resume Version 0.10.1
set "batch_file_dir=%~dp0"
for %%a in ("%~dp0") do set "PATH_ROOT=%%~fa"
REM call configuring environment variable
CALL %PATH_ROOT%bin\envinstallation.bat
endlocal
pause
rem https://stackoverflow.com/questions/26055421/get-path-two-levels-up-in-batch-file